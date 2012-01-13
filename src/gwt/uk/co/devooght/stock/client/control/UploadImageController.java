package uk.co.devooght.stock.client.control;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;
import uk.co.devooght.stock.client.view.CreateProductDialog;
import uk.co.devooght.stock.client.view.UploadImageDialog;

import javax.inject.Inject;

public class UploadImageController extends Controller {

  private Dispatcher dispatcher;
  private UploadImageDialog dialog;

  @Inject
  public UploadImageController(Dispatcher dispatcher, UploadImageDialog dialog) {
    dispatcher.addController(this);
    registerEventTypes(StockEvents.UploadNewImage.PRODUCT_IMAGE_UPLOAD);
    this.dispatcher = dispatcher;
    this.dialog = dialog;
  }

  @Override
  public void handleEvent(AppEvent appEvent) {
    if (appEvent.getType() == StockEvents.UploadNewImage.PRODUCT_IMAGE_UPLOAD) {
      ProductDTO product = appEvent.getData();
      dialog.setProduct(product);
      dialog.show();
    } else if (appEvent.getType() == StockEvents.UPLOAD_IMAGE) {

    }
  }
}
