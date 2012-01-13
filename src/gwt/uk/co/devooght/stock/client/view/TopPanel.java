package uk.co.devooght.stock.client.view;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import uk.co.devooght.stock.client.root.StockEvents;

import javax.inject.Inject;

public class TopPanel extends LayoutContainer {

  private Dispatcher dispatcher;

  @Inject
  public TopPanel(final Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
    setLayout(new RowLayout(Style.Orientation.HORIZONTAL));

    Button prod = new Button("Create Product");
    prod.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent buttonEvent) {
        dispatcher.dispatch(new AppEvent(StockEvents.CREATE_PRODUCT));
      }
    });

    add(prod);
    add(new Button("Create Collection"));
    setHeight(30);
  }

}

