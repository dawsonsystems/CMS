package uk.co.devooght.stock.client.view;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class CreateProductDialog extends Dialog {

  private FormPanel formPanel;
  private Dispatcher dispatcher;

  private TextField<String> name;
  private TextField<String> code;
  private TextField<String> category;

  @Inject
  public CreateProductDialog(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
    setLayout(new FitLayout());
    setWidth(550);
    setHeight(200);

    setHeading("Create new Product");

    formPanel = new FormPanel();
    formPanel.setHeaderVisible(false);
    formPanel.setFieldWidth(300);
    formPanel.setLabelWidth(200);
    name = new TextField<String>();
    name.setFieldLabel("Name");
    name.setAllowBlank(true);
    formPanel.add(name);

    code = new TextField<String>();
    code.setFieldLabel("Product Code");
    code.setAllowBlank(false);
    formPanel.add(code);

    category = new TextField<String>();
    category.setFieldLabel("Product Category (Necklace etc)");
    category.setAllowBlank(false);
    formPanel.add(category);

    //TODO, add some SKU information to allow streamlines product/ sku creation

    add(formPanel);

    getButtonBar().removeAll();
    getButtonBar().add(saveButton());
  }

  private Button saveButton() {
    Button button = new Button("Save");

    button.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent buttonEvent) {
        if (formPanel.isValid()) {
          hide();
          AppEvent event = new AppEvent(StockEvents.SAVE_PRODUCT);
          event.setData(getProduct());
          dispatcher.dispatch(event);
        }
      }
    });

    return button;
  }

  public void show() {
    formPanel.clear();
    super.show();
  }

  public ProductDTO getProduct() {
    ProductDTO dto = new ProductDTO();

    dto.setProductCode(code.getValue());
    dto.setName(name.getValue());
    dto.setCategory(category.getValue());

    return dto;
  }

}
