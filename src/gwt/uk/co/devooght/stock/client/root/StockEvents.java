package uk.co.devooght.stock.client.root;


import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.co.devooght.stock.ProductDTO;

public class StockEvents {

  //user wants to create a new product
  public static EventType CREATE_PRODUCT = new EventType();
  //user is saving a product, either new or updating.
  public static EventType SAVE_PRODUCT = new EventType();
  public static EventType DELETE_PRODUCT = new EventType();

  public static EventType CREATE_COLLECTION = new EventType();

  public static EventType START_APP = new EventType();
  public static EventType REFRESH_PRODUCTS = new EventType();

  public static EventType PRODUCT_SELECTED = new EventType();

  public static EventType UPLOAD_IMAGE = new EventType();

  public static class UploadNewImage extends AppEvent {

    public static EventType PRODUCT_IMAGE_UPLOAD = new EventType();
    private AsyncCallback whenSaved;
    private ProductDTO dto;

    public UploadNewImage(ProductDTO productDTO, AsyncCallback whenSaved) {
      super(PRODUCT_IMAGE_UPLOAD);
      setData(productDTO);
      this.whenSaved = whenSaved;
      this.dto = productDTO;
    }

    public AsyncCallback getWhenSaved() {
      return whenSaved;
    }

    public ProductDTO getProduct() {
      return dto;
    }
  }

}
