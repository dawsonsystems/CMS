package uk.co.devooght.stock.client.view;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;

public class SearchPanel extends ContentPanel {

  private TreePanel<ModelData> tree;

  public SearchPanel() {
    setLayout(new FitLayout());

    setHeading("Search Results");

    setBorders(true);
    tree = new TreePanel(new TreeStore());
    add(tree);
  }

}
