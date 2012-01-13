package shop

import shop.stock.Product

class HomeController {

  def index = {
    render(view: "/content/render", model: [weceemView:"index"])
  }

  def product = {
    println "Loading product ${params.id}"
    request.product = Product.findByProductCode(params.id)
    println "Product is ${request.product}"

    render(view: "/content/render", model: [weceemView:"shop/product"])
  }

  def collection = {

    request.products = Product.findAllByCategory(params.id)

    render(view: "/content/render", model: [weceemView:"shop/collection"])
  }

  def basket = {

    render(view: "/content/render", model: [weceemView:"shop/basket"])
  }
}
