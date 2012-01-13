package uk.co.devooght.stock.client.view;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.gin.ProductTabFactory;
import uk.co.devooght.stock.client.root.StockEvents;

import javax.inject.Inject;

public class MainPanel extends LayoutContainer {

  private TabPanel tabs;
  private Dispatcher dispatcher;
  private ProductTabFactory productTabFactory;

  @Inject
  public MainPanel(Dispatcher dispatcher, ProductTabFactory productTabFactory) {
    this.productTabFactory = productTabFactory;
    this.dispatcher = dispatcher;
    setBorders(false);
    setLayout(new FitLayout());

    tabs = new TabPanel();
    tabs.setBorders(false);
    tabs.setBodyBorder(true);

    tabs.add(dashboard());

    add(tabs);
  }

  public void showProduct(ProductDTO product) {
    TabItem item = tabs.getItemByItemId("" + product.getId());

    if (item == null) {
      item = productTabFactory.newProductTab(product);
      tabs.add(item);
    }

    tabs.setSelection(item);
  }

  private TabItem dashboard() {
    TabItem item = new TabItem();
    item.setText("Welcome/ Dashboard");
    item.setClosable(false);
    item.setLayout(new CenterLayout());
    item.add(new Html("<h1>Welcome to de Vooght Stock management</h1>"));
    return item;
  }

}
