package uk.co.devooght.stock;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

public interface ProductServiceAsync {
  void getTree(AsyncCallback<List<ProductDTO>> callback);
  void saveProduct(ProductDTO productDTO, AsyncCallback<Boolean> callback);
  void removeProduct(ProductDTO productDTO, AsyncCallback<Boolean> callback);
  void getSkus(ProductDTO productDTO, AsyncCallback<List<SkuDTO>> callback);
  void saveSku(ProductDTO product, SkuDTO sku, AsyncCallback<Boolean> callback);
  void getImages(ProductDTO product, AsyncCallback<List<ProductImageDTO>> callback);
}
