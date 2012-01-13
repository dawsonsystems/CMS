package shop.stock

class ProductImage {

  Product product
  //TODO, use a CDN?
  String location

  static belongsTo = [Product]

  static constraints = {
    location(blank:false)
  }
}
