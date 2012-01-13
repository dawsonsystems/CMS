package uk.co.devooght.stock.client.view.maintabs.product;

import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gxt.multiupload.MultiUploadPresenter;
import gxt.multiupload.MultiUploadView;
import gxt.multiupload.listener.FileBeforeSubmitListener;
import uk.co.devooght.stock.ProductDTO;
import uk.co.devooght.stock.ProductImageDTO;
import uk.co.devooght.stock.ProductServiceAsync;
import gxt.multiupload.model.Model;

import java.util.Arrays;
import java.util.List;

public class ImagePanel extends ContentPanel {

  private ProductServiceAsync productServiceAsync;
  private Dispatcher dispatcher;
  private ProductDTO product;
  private ListView<BeanModel> view;

  public ImagePanel(final Dispatcher dispatcher, final ProductDTO product, final ProductServiceAsync productServiceAsync) {
    this.productServiceAsync = productServiceAsync;
    this.dispatcher = dispatcher;
    this.product = product;
    setBorders(true);
    setHeaderVisible(false);
//    setLayout(new RowLayout(Style.Orientation.HORIZONTAL));
//    setHeight("100%");
    ToolBar bar = new ToolBar();

    bar.add(upload());

    setTopComponent(bar);

    RpcProxy<List<ProductImageDTO>> proxy = new RpcProxy<List<ProductImageDTO>>() {
      @Override
      protected void load(Object loadConfig, AsyncCallback<List<ProductImageDTO>> callback) {
        productServiceAsync.getImages(product, callback);
      }
    };

    ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(proxy,
        new BeanModelReader());
    ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
    loader.load();

    view = new ListView<BeanModel>() {
      @Override
      protected BeanModel prepareData(BeanModel model) {
        ProductImageDTO photo = model.getBean();

//        long size = photo.getSize() / 1000;
//        model.set("shortName", Format.ellipse(photo.getName(), 15));
//        model.set("sizeString", NumberFormat.getFormat("#0").format(size) + "k");
//        model.set("dateString", DateTimeFormat.getMediumDateTimeFormat().format(photo.getDate()));
//        model.set("path", GWT.getHostPageBaseURL() + photo.getPath());
        return model;
      }
    };

    view.setId("img-chooser-view");
    view.setTemplate(getTemplate());
    view.setBorders(false);
    view.setStore(store);
    view.setItemSelector("div.thumb-wrap");
    add(view);
  }

  private Button upload() {
    Button upload = new Button("Upload New Image");


    upload.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        ColumnModel columnModel = new ColumnModel(Arrays.asList(new ColumnConfig("name", "File Name", 200), new ColumnConfig("state", "State", 200)));
        Grid<Model> grid = new Grid<Model>(new ListStore<Model>(), columnModel);
        grid.getStore().setMonitorChanges(true);
        grid.getView().setForceFit(true);
        grid.getView().setAutoFill(true);
        grid.getView().setShowDirtyCells(false);

        final MultiUploadView view = new MultiUploadView(grid);
        view.getFormPanel().setAction("/devooght/stock/upload?productId="+product.getId());
        MultiUploadPresenter presenter = new MultiUploadPresenter(view);
        presenter.go();
      }
    });

    /*upload.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        dispatcher.dispatch(new StockEvents.UploadNewImage(product, new AsyncCallback() {
          public void onFailure(Throwable caught) {  }
          public void onSuccess(Object result) {
            view.getStore().getLoader().load();
          }
        }));
      }
    });  */

    return upload;
  }

  private native String getTemplate() /*-{
    return ['<tpl for=".">',
    '<div class="thumb-wrap" style="display:inline-block; border: 1px solid white">',
    '<div class="thumb"><img src="{location}" title="{name}" style="width:200px;"></div>',
    '</div>',
    '</tpl>'].join("");
  }-*/;
}
