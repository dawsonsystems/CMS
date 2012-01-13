package uk.co.devooght.stock.client.control;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
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

public class DeleteProductController extends Controller implements AsyncCallback<Boolean> {

  private Dispatcher dispatcher;
  private ProductServiceAsync productServiceAsync;

  @Inject
  public DeleteProductController(Dispatcher dispatcher, ProductServiceAsync productServiceAsync) {
    dispatcher.addController(this);
    registerEventTypes(StockEvents.DELETE_PRODUCT);
    this.dispatcher = dispatcher;
    this.productServiceAsync = productServiceAsync;
  }

  @Override
  public void handleEvent(final AppEvent appEvent) {
    MessageBox.confirm("Confirm Product Removal", "This cannot be un-done!", new Listener<MessageBoxEvent>() {
      public void handleEvent(MessageBoxEvent be) {
        if (be.getButtonClicked().getText().equals("Yes")) {
          ProductDTO dto = appEvent.getData();
          productServiceAsync.removeProduct(dto, DeleteProductController.this);
        }
      }
    });
  }

  public void onFailure(Throwable caught) {
    Info.display("Couldn't remove....", caught.getMessage());
  }

  public void onSuccess(Boolean result) {
    Info.display("Removed product", "");
    dispatcher.dispatch(StockEvents.REFRESH_PRODUCTS);
  }
}
