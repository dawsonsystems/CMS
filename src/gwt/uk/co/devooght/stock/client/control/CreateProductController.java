package uk.co.devooght.stock.client.control;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;
import uk.co.devooght.stock.client.view.CreateProductDialog;
import uk.co.devooght.stock.client.view.MainPanel;
import uk.co.devooght.stock.client.view.ProductTree;

import javax.inject.Inject;
import java.util.List;

public class CreateProductController extends Controller {

  private Dispatcher dispatcher;
  private CreateProductDialog dialog;

  @Inject
  public CreateProductController(Dispatcher dispatcher, CreateProductDialog dialog) {
    dispatcher.addController(this);
    registerEventTypes(StockEvents.CREATE_PRODUCT);
    this.dispatcher = dispatcher;
    this.dialog = dialog;
  }

  @Override
  public void handleEvent(AppEvent appEvent) {
    if (appEvent.getType() == StockEvents.CREATE_PRODUCT) {
      dialog.show();
    }
  }
}
