package uk.co.devooght.stock.client.view;

import static uk.co.devooght.stock.client.root.LayoutUtils.*;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.inject.Inject;

public class HomePage extends Viewport {

  private LayoutContainer productTree;
  private LayoutContainer topBar;
  private LayoutContainer mainPanel;

  @Inject
  public HomePage(ProductTree productTree, MainPanel mainPanel, TopPanel topPanel, SearchPanel searchPanel) {

    this.topBar = topPanel;
    this.productTree = productTree;
    this.mainPanel = mainPanel;

    setLayout(new FitLayout());

    ContentPanel layout = new ContentPanel(new FitLayout());
    layout.getHeader().setText("de Vooght Ltd");
    layout.getHeader().setTextStyle("wibble");

    LayoutContainer inner = new LayoutContainer();
    inner.setLayout(new BorderLayout());
    inner.setStyleAttribute("padding", "5px");
    layout.add(inner);

    //ContentPanel leftbar = new ContentPanel(new AccordionLayout());
    //leftbar.setHeaderVisible(false);
//    productTree.setBorders(false);
//    leftbar.add(productTree);
//    searchPanel.setBorders(false);
//    leftbar.add(searchPanel);

    inner.add(productTree, split(collapseable(west(percentSize(0.25f)))));
    inner.add(topBar, north(percentSize(30)));
    inner.add(mainPanel, centre());

    add(layout);
  }

}
