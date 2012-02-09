package cms.image

import shop.stock.ProductImage
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.RenderingHints
import java.awt.Graphics2D
import java.awt.AlphaComposite

class ProductImageUtil {




  static void createThumb(ProductImage image) {

//    def imagePath = new File(ConfigurationHolder.config.images.serverPath, image.location)

    def imageDir =  new File("/home/david/Documents/sharedrose/de Vooght Pictures/Cufflinks").absolutePath

    def imageToEdit = new File(image.location).name

    println "I'll be editing $imageToEdit in $imageDir"

    GroovyImage.main(["-d", imageDir,
				"-e", "fit(${ImageSize.THUMB.width}px, ${ImageSize.THUMB.height}px)",
				"-p", imageToEdit,
				"-q", "thumb-${imageToEdit}"] as String[])
  }
  static void createMain(ProductImage image) {

  }

  static void main(String[] args) {

    createThumb(new ProductImage(location:"/home/david/Documents/sharedrose/de Vooght Pictures/Cufflinks/C48 - square 1.jpg"))

  }











}

static enum ImageSize {
  MAIN(900,900),
  THUMB(250,250)

  int width
  int height

  private ImageSize(int height, int width) {
    this.height = height
    this.width = width
  }

}