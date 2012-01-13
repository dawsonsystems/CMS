package uk.co.devooght.stock.client.root;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class LayoutUtils {

  public static BorderLayoutData west(BorderLayoutData data) {
    data.setRegion(Style.LayoutRegion.WEST);
    return data;
  }
  public static BorderLayoutData east(BorderLayoutData data) {
    data.setRegion(Style.LayoutRegion.EAST);
    return data;
  }
  public static BorderLayoutData south(BorderLayoutData data) {
    data.setRegion(Style.LayoutRegion.SOUTH);
    return data;
  }
  public static BorderLayoutData north(BorderLayoutData data) {
    data.setRegion(Style.LayoutRegion.NORTH);
    return data;
  }
  public static BorderLayoutData centre(BorderLayoutData data) {
    data.setRegion(Style.LayoutRegion.CENTER);
    return data;
  }
  public static BorderLayoutData centre() {
    BorderLayoutData data = new BorderLayoutData(Style.LayoutRegion.CENTER);

    data.setMargins(new Margins(0,0,0,5));
    return data;
  }
  public static BorderLayoutData percentSize(float size) {
    BorderLayoutData data = new BorderLayoutData(Style.LayoutRegion.CENTER);

    data.setSize(size);

    return data;
  }
  public static BorderLayoutData collapseable(BorderLayoutData data) {
    data.setCollapsible(true);
    return data;
  }
  public static BorderLayoutData split(BorderLayoutData data) {
    data.setSplit(true);
    return data;
  }
}
