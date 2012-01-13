package uk.co.devooght.stock.client.gin;

import com.extjs.gxt.ui.client.mvc.Dispatcher;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.ProductServiceAsync;
import uk.co.devooght.stock.client.view.maintabs.ProductTab;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductTabFactory {

  private Dispatcher dispatcher;
  private ProductServiceAsync productServiceAsync;

  @Inject
  public ProductTabFactory(Dispatcher dispatcher, ProductServiceAsync productServiceAsync) {
    this.dispatcher = dispatcher;
    this.productServiceAsync = productServiceAsync;
  }

  public ProductTab newProductTab(ProductDTO productDTO) {
    return new ProductTab(dispatcher, productDTO, productServiceAsync);
  }

}
