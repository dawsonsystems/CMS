package shop

import org.apache.http.HttpEntity
import org.apache.http.entity.StringEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.HttpResponse
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.HttpClient
import groovy.xml.MarkupBuilder
import shop.stock.Sku

class BasketController {

  def add = {
    def skuId = params.stockCode

    def sku = Sku.findByStockCode(skuId)

    if (!sku) {
      flash.error="Product with id ${skuId} doesn't exist"
      redirect(controller:"home", action:"basket")
      return
    }

    Basket basket = request.basket
    basket.addItem(sku)
    flash.success="Added ${sku.product.name} to your basket"
    redirect controller:"home", action:"basket"
  }
  def remove = {
    def skuId = params.long("sku")

    def sku = Sku.get(skuId)

    if (!sku) {
      redirect(controller:"home", action:"basket")
      return
    }

    Basket basket = request.basket
    basket.removeItem(sku)
    flash.success="Removed ${sku.product.name} from your basket"
    redirect controller:"home", action:"basket"
  }

  def update = {
    updateBasket(request.basket)
    redirect controller:"home", action:"basket"
  }

  def clear = {
    request.basket.clear()
  }

  def checkout = {
   // updateBasket(request.basket)

    def writer = new StringWriter()

    //create the basket to send.
    def build = new MarkupBuilder(new PrintWriter(writer))

    //TODO print the basket out, then clear it.  Ideally convert to an order.... for when we enable the orders/ customer logins
    Basket basket = request.basket

    build."checkout-shopping-cart"(xmlns:"http://checkout.google.com/schema/2") {
      'shopping-cart' {
        items {
          basket.items.each { BasketItem basketItem ->
            "item" {
              'item-name'(basketItem.sku.product.name)
              'item-description'(basketItem.sku.product.name)
              'unit-price'(currency:"GBP", "${basketItem.price}")
              quantity("${basketItem.quantity}")
            }
          }
        }
      }
    }

    log.debug("Connecting to ${grailsApplication.config.google.checkout.url}")
    def auth = "${grailsApplication.config.google.checkout.id}:${grailsApplication.config.google.checkout.key}".encodeAsBase64()
    log.info "BASIC " + auth

    def url = null

    def response = withHttp(uri:grailsApplication.config.google.checkout.url) {
      headers."Authorization" = "Basic " + auth

      post(body:writer.toString()) { resp, reader ->
       url =reader.toString()
      }
    }

    redirect(url:url)
  }

  def sendHttps(String httpUrl, String data) {
    HttpClient httpClient = new DefaultHttpClient()
    HttpResponse response
    try {
        HttpPost httpPost = new HttpPost(httpUrl)
        httpPost.setHeader("Content-Type", "text/xml")

        HttpEntity reqEntity = new StringEntity(data, "UTF-8")
        reqEntity.setContentType("text/xml")
        reqEntity.setChunked(true)

        httpPost.setEntity(reqEntity)
        log.info "executing request " + httpPost.getRequestLine()

        response = httpClient.execute(httpPost)
        HttpEntity resEntity = response.getEntity()

        log.info response.getStatusLine()
        if (resEntity != null) {
            log.with {
                info "Response content length: " + resEntity.getContentLength()
                if (isDebugEnabled()) {
                    debug "Response Chunked?: " + resEntity.isChunked()
                    debug "Response Encoding: " + resEntity.contentEncoding
                    debug "Response Content: " + resEntity.content.text
                }
            }
        }
        // EntityUtils.consume(resEntity);
    }
    finally {
        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpClient.getConnectionManager().shutdown()
    }
    return response.getStatusLine()
  }

  def updateBasket(Basket basket) {
    basket.items.each { item ->
      item.quantity = params.int("${item.sku.stockCode}")
    }
    basket.items.removeAll { it.quantity == 0 }

    basket.recalculate()
  }
}
