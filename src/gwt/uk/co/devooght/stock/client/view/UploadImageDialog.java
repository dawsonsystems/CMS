package uk.co.devooght.stock.client.view;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;

@Singleton
public class UploadImageDialog extends Dialog {

  private FormPanel panel;
  private Dispatcher dispatcher;

  HiddenField<String> productIdField;

  @Inject
  public UploadImageDialog(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
    setHeading("File Upload Example");
    setLayout(new FitLayout());
    setWidth(550);
    setHeight(200);

    panel = new FormPanel();

    panel.setHeaderVisible(false);
    panel.setFieldWidth(300);
    panel.setLabelWidth(200);
//    panel.setAction("/devooght/stock/upload2");
    panel.setAction("/igglewibble");
    panel.setEncoding(FormPanel.Encoding.MULTIPART);
    panel.setMethod(FormPanel.Method.POST);
    panel.setButtonAlign(Style.HorizontalAlignment.CENTER);

    productIdField = new HiddenField<String>();
    productIdField.setName("productId");
    panel.add(productIdField);

    FileUploadField file = new FileUploadField();
    file.setAllowBlank(false);
    file.setName("uploadedfile");
    file.setFieldLabel("File");
    panel.add(file);

    getButtonBar().removeAll();

    Button btn = new Button("Reset");
    btn.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        panel.reset();
      }
    });
    addButton(btn);

    btn = new Button("Upload");
    btn.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        if (!panel.isValid()) {
          return;
        }
        // normally would submit the form but for example no server set up to
        // handle the post
        panel.submit();
        hide();
      }
    });
    add(panel);
    addButton(btn);
  }

  public void setProduct(ProductDTO product) {
    productIdField.setValue("" + product.getId());
  }

  public void show() {
    super.show();
    panel.reset();
  }
}
