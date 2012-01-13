package uk.co.devooght.stock;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("rpc")
public interface ProductService extends RemoteService {
  List<ProductDTO> getTree();
  List<SkuDTO> getSkus(ProductDTO productDTO);
  Boolean saveProduct(ProductDTO productDTO);
  Boolean removeProduct(ProductDTO productDTO);
  Boolean saveSku(ProductDTO product, SkuDTO sku);
  List<ProductImageDTO> getImages(ProductDTO product);
}
