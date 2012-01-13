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
import uk.co.devooght.stock.client.view.MainPanel;
import uk.co.devooght.stock.client.view.ProductTree;

import javax.inject.Inject;
import java.util.List;

public class MainPanelController extends Controller {

  private Dispatcher dispatcher;
  private MainPanel mainPanel;

  @Inject
  public MainPanelController(Dispatcher dispatcher, MainPanel mainPanel) {
    dispatcher.addController(this);
    registerEventTypes(StockEvents.PRODUCT_SELECTED);
    this.dispatcher = dispatcher;
    this.mainPanel = mainPanel;
  }

  @Override
  public void handleEvent(AppEvent appEvent) {
    if (appEvent.getType() == StockEvents.PRODUCT_SELECTED) {
      BeanModel model = appEvent.getData();
      mainPanel.showProduct(model.<ProductDTO>getBean());
    }
  }
}
