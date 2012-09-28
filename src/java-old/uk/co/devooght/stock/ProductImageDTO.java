package uk.co.devooght.stock;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class ProductImageDTO implements grails.plugins.dto.DTO, BeanModelTag {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String location;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductImageDTO[");
        sb.append("\n\tid: " + this.id);
        sb.append("\n\tlocation: " + this.location);
        sb.append("]");
        return sb.toString();
    }
}
