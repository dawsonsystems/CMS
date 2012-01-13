package shop

import org.springframework.web.context.request.RequestContextHolder as RCH

import javax.servlet.http.Cookie
import shop.stock.Sku

class Basket {

  static final String BASKET_COOKIE_NAME = "BAS"
  String id

  static hasMany = [items: BasketItem]
  static transients = ['grandTotal', 'totalItems', 'totalProducts']

  static mapping = {
    id generator:'assigned'
  }

  def removeItem(Sku sku) {
    BasketItem item = items.find { it.sku.id == sku.id}

    if (item) {
      removeFromItems(item)
    }
  }

  def recalculate() {
    items.each {
      it.price = it.sku.price
      it.lineTotal = it.price * it.quantity
    }
  }

  def addItem(Sku sku) {
    BasketItem item = items.find { it.sku.id == sku.id}

    if (item) {
      item.addQuantity(1)
    } else {
      item = new BasketItem(sku: sku)
      item.addQuantity(1)
      items << item
    }
  }

  BigDecimal getGrandTotal() {
    return items.sum { it.lineTotal }
  }

  BigDecimal getTotalItems() {
    return items.sum { it.quantity }
  }

  BigDecimal getTotalProducts() {
    return items.size()
  }

  def afterInsert() {
    sendCookie()
  }

  def afterUpdate() {
    sendCookie()
  }

  def clear() {
    items.clear()
  }

  def sendCookie() {
    def c = new Cookie(BASKET_COOKIE_NAME, id)
    c.setPath("/")
    // want to expire it immediately if its not an available division.
    c.setMaxAge(60 * 60 * 24 * 30)
    RCH.requestAttributes.getCurrentResponse().addCookie(c)
  }
}
