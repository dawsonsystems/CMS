package cms

import java.text.NumberFormat
import uk.co.devooght.UrlSanitizer
import shop.stock.Product
import shop.stock.ProductImage
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ShopTagLib {
  static namespace = "shop"
  static format = NumberFormat.getCurrencyInstance(Locale.UK)

  def asCurrency = { attrs ->
    if (attrs.value == null) {
			attrs.value = 0.0
		}
		out << format.format(attrs.value)
  }

  def productUrl = { attrs ->
    def productCode = attrs.productCode

    if (productCode) {
      def product = Product.findByProductCode(productCode)
      def name = UrlSanitizer.sanitize(product.name)
      out << g.createLink(uri:"/p/${product.productCode}/${name}")
    } else {
      out << "PRODUCT ${productCode} NOT FOUND"
    }
  }

  def productImageUrl = { attrs ->
    String size = attrs.size

    Product product = attrs.product

    if (!product && attrs.productCode) {
      product = Product.findByProductCode(attrs.productCode)
    }

    def image = ProductImage.findByProduct(product)

    if (image) {
      out << getProductImageUrlForSize(image, size)
    } else {
      out << ConfigurationHolder.config.images.noImageAvailableLocation
    }
  }

  def getProductImageUrlForSize(ProductImage image, size) {

    def imageName = new File(image.location).name

    return "${ConfigurationHolder.config.images.serverPath}/${size}/${imageName}"
  }

  def addToBasketUrl = { attrs ->
    def sku = attrs.sku

  }

  def removeFromBasketUrl = { attrs ->
    def sku = attrs.sku

  }

    def wcmRenderEngine
    def wcmContentRepositoryService

    def render = { attrs ->
      println "WIBBLE!!!"
        def path = attrs.path
        if (!path) {
            throwTagError "No [path] attribute supplied to wcm:render tag. Specify the Weceem content URI that you wish to render"
        }

        def space = request.space

        def uriInfo = wcmContentRepositoryService.resolveSpaceAndURI(path)
        if (!space) {
          space = uriInfo.space
        }
      println "THE SPACE IS ${space}"
        // @todo enhance the logic to work same as WcmContentController
        def node = wcmContentRepositoryService.findContentForPath(uriInfo.uri, space)
        if (node) {
            // @todo Should verify it is embeddable content type here i.e. images/downloads can't embed!
            out << g.include(controller:'wcmContent', action:'show', params:[uri:path])
        } else {
            out << "Content not found at [${path}]"
        }
    }

}
