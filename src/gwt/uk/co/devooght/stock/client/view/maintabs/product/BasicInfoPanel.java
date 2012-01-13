package uk.co.devooght.stock.client.view.maintabs.product;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;

import java.math.BigDecimal;

public class BasicInfoPanel extends FormPanel {

  private TextField<String> code;
  private TextField<String> category;
  private TextField<String> shape;
  private TextField<String> pattern;
  private TextField<String> altMaterial;
  private ProductDTO product;

  public BasicInfoPanel(final Dispatcher dispatcher, ProductDTO product) {
    this.product = product;
    setFieldWidth(300);
    setLabelWidth(200);

     //TODO, share this setup with the create dialog
    setHeading("Product Information");
    code = new TextField<String>();
    code.setFieldLabel("Product Code");
    code.setAllowBlank(false);
    code.setValue(product.getProductCode());
    add(code);

    category = new TextField<String>();
    category.setFieldLabel("Product Category (Necklace etc)");
    category.setAllowBlank(false);
    category.setValue(product.getCategory());
    add(category);

    pattern = new TextField<String>();
    pattern.setFieldLabel("Jewellery Pattern");
    pattern.setAllowBlank(true);
    pattern.setValue(product.getPattern());
    add(pattern);

    shape = new TextField<String>();
    shape.setFieldLabel("Jewellery Shape");
    shape.setAllowBlank(true);
    shape.setValue(product.getShape());
    add(shape);

    altMaterial = new TextField<String>();
    altMaterial.setFieldLabel("Alternate Material (eg, turquoise)");
    altMaterial.setAllowBlank(true);
    altMaterial.setValue(product.getAltMaterial());
    add(altMaterial);

    ButtonBar buttons = new ButtonBar();

    Button save = new Button("Save");

    save.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent buttonEvent) {
        if (isValid()) {
          AppEvent event = new AppEvent(StockEvents.SAVE_PRODUCT);
          event.setData(getProduct());
          dispatcher.dispatch(event);
        }
      }
    });

    buttons.add(save);

    Button delete = new Button("Delete Product Entirely");

    delete.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent buttonEvent) {
        AppEvent event = new AppEvent(StockEvents.DELETE_PRODUCT);
        event.setData(getProduct());
        dispatcher.dispatch(event);
      }
    });
    buttons.add(delete);

    add(buttons);
  }
  private ProductDTO getProduct() {
    product.setProductCode(code.getValue());
    product.setPattern(pattern.getValue());
    product.setShape(shape.getValue());
    product.setAltMaterial(altMaterial.getValue());
    return product;
  }
}
