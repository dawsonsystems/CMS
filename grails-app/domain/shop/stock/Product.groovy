package shop.stock

class Product {

  String name
  String productCode
  String category
  String shape
  String pattern
  String altMaterial

  String copyText

  static hasMany = [skus: Sku]
  static transients = ['multiSku']

  //for things like rings etc
  boolean isMultiSku() {
    false
  }

  Sku bestSku() {
    if (skus) {
      return skus.find {
        //TODO, include a price check (ie, lowest price that is available)
        it.inventoryLevel > 0
      }
    }
    return null
  }

  static searchable = {
    skus component:true
  }

  static constraints = {
    productCode blank:false, unique:true
    name nullable:true
    category blank:false
    shape nullable:true
    pattern nullable:true
    altMaterial nullable: true
    copyText nullable:true, blank:true
  }
}
