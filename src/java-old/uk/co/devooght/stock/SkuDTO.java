package uk.co.devooght.stock;

import com.extjs.gxt.ui.client.data.BeanModelTag;

import java.math.BigDecimal;

public class SkuDTO implements grails.plugins.dto.DTO, BeanModelTag {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Double price;
    private Double costPrice;
    private String stockCode;
    private int inventoryLevel;
    private Double weight;
    private Double length;
    private Double diameter;
    private String ringSize;

  public Double getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(Double costPrice) {
    this.costPrice = costPrice;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public Double getLength() {
    return length;
  }

  public void setLength(Double length) {
    this.length = length;
  }

  public Double getDiameter() {
    return diameter;
  }

  public void setDiameter(Double diameter) {
    this.diameter = diameter;
  }

  public String getRingSize() {
    return ringSize;
  }

  public void setRingSize(String ringSize) {
    this.ringSize = ringSize;
  }

  public int getInventoryLevel() {
      return inventoryLevel;
    }

    public void setInventoryLevel(int inventoryLevel) {
      this.inventoryLevel = inventoryLevel;
    }

  public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getStockCode() { return stockCode; }
    public void setStockCode(String stockCode) { this.stockCode = stockCode; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SkuDTO[");
        sb.append("\n\tid: " + this.id);
        sb.append("\n\tprice: " + this.price);
        sb.append("\n\tstockCode: " + this.stockCode);
        sb.append("]");
        return sb.toString();
    }
}
