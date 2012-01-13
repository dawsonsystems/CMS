package uk.co.devooght.stock.client.control;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.ProductServiceAsync;
import uk.co.devooght.stock.client.root.StockEvents;
import uk.co.devooght.stock.client.view.CreateProductDialog;
import uk.co.devooght.stock.client.view.MainPanel;
import uk.co.devooght.stock.client.view.ProductTree;

import javax.inject.Inject;
import java.util.List;

public class SaveProductController extends Controller implements AsyncCallback<Boolean> {

  private Dispatcher dispatcher;
  private ProductServiceAsync productServiceAsync;
  private CreateProductDialog productDialog;

  @Inject
  public SaveProductController(Dispatcher dispatcher, ProductServiceAsync productServiceAsync, CreateProductDialog productDialog) {
    dispatcher.addController(this);
    registerEventTypes(StockEvents.SAVE_PRODUCT);
    this.dispatcher = dispatcher;
    this.productServiceAsync = productServiceAsync;
    this.productDialog = productDialog;
  }

  @Override
  public void handleEvent(AppEvent appEvent) {
    ProductDTO dto = appEvent.getData();
    productServiceAsync.saveProduct(dto, this);
  }

  public void onFailure(Throwable caught) {
    Info.display("Couldn't save....", caught.getMessage());
  }

  public void onSuccess(Boolean result) {
    Info.display("Saved product", "");
    dispatcher.dispatch(StockEvents.REFRESH_PRODUCTS);
  }
}
