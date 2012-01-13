package shop

import shop.stock.Sku

class Item {

  Sku sku
  Integer quantity = 0
  BigDecimal price
  BigDecimal lineTotal

  def addQuantity(int quantity) {
    this.quantity += quantity
    this.price = sku.price
    lineTotal = price * this.quantity
  }

  static constraints = {
    sku nullable:false
    quantity nullable:false
    price nullable:false
    lineTotal nullable:false
  }
}
