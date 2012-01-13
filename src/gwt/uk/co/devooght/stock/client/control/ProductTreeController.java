package uk.co.devooght.stock.client.control;

import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;
import uk.co.devooght.stock.client.view.ProductTree;

import javax.inject.Inject;
import java.util.List;

public class ProductTreeController extends Controller {

  private Dispatcher dispatcher;
  private ProductTree productTree;

  @Inject
  public ProductTreeController(Dispatcher dispatcher, ProductTree productTree) {
    dispatcher.addController(this);
    registerEventTypes(StockEvents.START_APP, StockEvents.REFRESH_PRODUCTS);
    this.dispatcher = dispatcher;
    this.productTree = productTree;
  }

  @Override
  public void handleEvent(AppEvent appEvent) {
    GWT.log("Refreshing the tree..");
    productTree.load();
  }
}
