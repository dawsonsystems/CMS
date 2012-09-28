package uk.co.devooght.stock;

import com.extjs.gxt.ui.client.data.BeanModelTag;

import java.math.BigDecimal;
import java.util.Set;

public class ProductDTO implements grails.plugins.dto.DTO, BeanModelTag {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String productCode;
    private String category;
    private String pattern;
    private String shape;
    private Set<SkuDTO> skus;
    private String altMaterial;

  public String getAltMaterial() {
    return altMaterial;
  }

  public void setAltMaterial(String altMaterial) {
    this.altMaterial = altMaterial;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getShape() {
    return shape;
  }

  public void setShape(String shape) {
    this.shape = shape;
  }

  public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

  public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public Set<SkuDTO> getSkus() { return skus; }
    public void setSkus(Set<SkuDTO> skus) { this.skus = skus; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductDTO[");
        sb.append("\n\tid: " + this.id);
        sb.append("\n\tname: " + this.name);
        sb.append("\n\tproductCode: " + this.productCode);
        sb.append("\n\tskus: " + this.skus);
        sb.append("]");
        return sb.toString();
    }
}
