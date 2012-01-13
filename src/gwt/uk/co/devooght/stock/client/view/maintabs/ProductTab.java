package uk.co.devooght.stock.client.view.maintabs;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.ProductServiceAsync;
import uk.co.devooght.stock.client.view.maintabs.product.BasicInfoPanel;
import uk.co.devooght.stock.client.view.maintabs.product.ImagePanel;
import uk.co.devooght.stock.client.view.maintabs.product.SkuPanel;

import static uk.co.devooght.stock.client.root.LayoutUtils.*;

public class ProductTab extends TabItem {

  private Dispatcher dispatcher;
  private ProductDTO product;
  private ProductServiceAsync productServiceAsync;

  public ProductTab(Dispatcher dispatcher, ProductDTO productDTO, ProductServiceAsync productServiceAsync) {
    this.dispatcher = dispatcher;
    this.product = productDTO;
    this.productServiceAsync = productServiceAsync;
    setup();
  }

  private void setup() {
    setId(""+product.getId());
    setClosable(true);

    setText(product.getName() + "-" + product.getProductCode());

    //TODO, add the other component views, SKU, Image etc

    setLayout(new FitLayout());

    LayoutContainer body = new LayoutContainer(new BorderLayout());
    add(body);

    body.add(new BasicInfoPanel(dispatcher, product), west(percentSize(0.5f)));
    body.add(new SkuPanel(dispatcher, product, productServiceAsync), split(east(percentSize(0.5f))));
    body.add(new ImagePanel(dispatcher, product, productServiceAsync), split(south(percentSize(300))));
  }

}
