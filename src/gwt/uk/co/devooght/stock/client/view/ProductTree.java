package uk.co.devooght.stock.client.view;

import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.co.devooght.stock.ProductServiceAsync;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.client.root.StockEvents;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductTree extends ContentPanel {

//  private TreePanel<BeanModel> tree;
  private Grid<ModelData> grid;
  private ProductServiceAsync productServiceAsync;
  private Dispatcher dispatcher;

  @Inject
  public ProductTree(ProductServiceAsync productServiceAsync, final Dispatcher dispatcher) {
    this.productServiceAsync = productServiceAsync;
    this.dispatcher = dispatcher;

    setLayout(new FitLayout());

    setHeading("Products");

    setBorders(true);

    RpcProxy treeProxy= new RpcProxy<List<ModelData>>() {
    @Override
      protected void load(Object loadConfig,
                final AsyncCallback<List<ModelData>> callback) {
        ProductTree.this.productServiceAsync.getTree(new AsyncCallback<List<ProductDTO>>() {
          public void onFailure(Throwable caught) {
            callback.onFailure(caught);
          }

          public void onSuccess(List<ProductDTO> result) {
            List<ModelData> ret = new ArrayList();
            BeanModelFactory factory = BeanModelLookup.get().getFactory(ProductDTO.class);

            for (ProductDTO dto : result) {
              ret.add(factory.createModel(dto));
            }
            callback.onSuccess(ret);
          }
        });
      }
    };

    Button refresh = new Button("Refresh");

    refresh.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent buttonEvent) {
        load();
        buttonEvent.preventDefault();
        buttonEvent.setCancelled(true);
        buttonEvent.stopEvent();
      }
    });

    getHeader().addTool(refresh);

    grid = new Grid<ModelData>(new ListStore<ModelData>(new BaseListLoader(treeProxy)), new ColumnModel(columns()));

    grid.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<ModelData>() {
      @Override
      public void selectionChanged(SelectionChangedEvent<ModelData> modelDataSelectionChangedEvent) {
        AppEvent ev = new AppEvent(StockEvents.PRODUCT_SELECTED);
        ev.setData(modelDataSelectionChangedEvent.getSelectedItem());
        dispatcher.dispatch(ev);
      }
    });

    add(grid);
  }
  private List<ColumnConfig> columns() {
    return Arrays.asList(new ColumnConfig("name", "Name", 200), new ColumnConfig("productCode", "Product Code", 200));
  }
  public void load() {
    grid.getStore().getLoader().load();
  }
}
