package shop

//import org.apache.commons.lang3.text.WordUtils
//import shop.stock.Product
//import shop.stock.ProductImage
//import uk.co.devooght.stock.ProductDTO
//import shop.stock.Sku
//import uk.co.devooght.stock.SkuDTO
//import uk.co.devooght.stock.ProductImageDTO

class ProductService /*implements uk.co.devooght.stock.ProductService*/ {
  static expose = [ 'gwt:uk.co.devooght' ]

  static transactional = true

//  List getTree() {
//    return Product.list(['order':"name"])
//  }
//
 /* List<ProductDTO> getTree() {
    return Product.list(['order':"name"]).toDTO(ProductDTO)
  }
  Boolean removeProduct(ProductDTO productDTO) {
    Product product

    if (productDTO.id) {
      product = Product.get(productDTO.id)
    } else {
      throw new RuntimeException("Unable to remove product, does not exist")
    }
    ProductImage.findAllByProduct(product).each {
      it.delete(flush:true)
    }
    Sku.findAllByProduct(product).each {
      it.delete(flush:true)
    }

    product.delete(flush:true)
    return true
  }
  Boolean saveProduct(ProductDTO productDTO) {

    Product product

    if (productDTO.id) {
      product = Product.get(productDTO.id)
    } else {
      product = new Product()
    }

    product.name = generateProductName(productDTO)
    product.productCode = productDTO.productCode
    product.category = productDTO.category
    product.shape = productDTO.shape
    product.pattern = productDTO.pattern
    product.altMaterial = productDTO.altMaterial

    if (product.save(flush:true)) {
      return true
    }
    product.errors.allErrors.each {
      println it
    }
    return false
  }

  private String generateProductName(ProductDTO product) {
    def name = "filigree silver"
    if (product.pattern) {
      name += " ${product.pattern}"
    }
    if (product.shape) {
      name+= " ${product.shape} shaped"
    }
    if (product.category) {
      name += " ${product.category}"
    }
    if (product.altMaterial) {
      name += " with ${product.altMaterial}"
    }

    return WordUtils.capitalizeFully(name)
  }

  List<uk.co.devooght.stock.SkuDTO> getSkus(ProductDTO productDTO) {
    (Product.get(productDTO.id).skus.toDTO(SkuDTO) as List).sort { it.stockCode }
  }

  Boolean saveSku(ProductDTO productdto, SkuDTO sku) {

    if (sku.id) {
      //update an existing SKU
      Sku existingSku = Sku.get(sku.id)
      existingSku.inventoryLevel = sku.inventoryLevel
      existingSku.price = sku.price as BigDecimal
      existingSku.costPrice = sku.costPrice as BigDecimal
      existingSku.stockCode = sku.stockCode
      existingSku.weight = sku.weight as BigDecimal
      existingSku.length = sku.length as BigDecimal
      existingSku.diameter = sku.diameter as BigDecimal
      existingSku.ringSize = sku.ringSize
      return true
    }

    //save a new SKU
    Product product = Product.get(productdto.id)
    Sku newSku = new Sku(product: product, inventoryLevel:sku.inventoryLevel,
                         price: sku.price as BigDecimal,
                        stockCode: sku.stockCode,
                        costPrice:sku.costPrice as BigDecimal,
                        weight:sku.weight as BigDecimal,
                        length:sku.length as BigDecimal,
                        diameter:sku.diameter as BigDecimal,
                        ringSize:sku.ringSize)

    if (!newSku.save(flush:true)) {
      newSku.errors.allErrors.each {
        println it
      }
      return false
    }
    return true
  }

  List<ProductImageDTO> getImages(ProductDTO product) {
    def ret = ProductImage.findAllByProduct(Product.get(product.id))

    def dtos = (ret.toDTO(ProductImageDTO) as List)

    return dtos.sort {
      it.id
    }
  } */
}
