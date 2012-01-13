package uk.co.devooght.stock.client.root;

import com.extjs.gxt.themes.client.Slate;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.util.ThemeManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import uk.co.devooght.stock.client.gin.StockGinjector;
import uk.co.devooght.stock.client.view.HomePage;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockEntryPoint implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
      GXT.setDefaultTheme(Slate.SLATE, false);
        for (Theme theme: ThemeManager.getThemes()) {
        GWT.log("Seen " + theme.getId());
      }

        StockGinjector ginjector = GWT.create(StockGinjector.class);
        RootLayoutPanel.get().add(ginjector.getHomePage());
      Dispatcher.get().dispatch(StockEvents.START_APP);
    }
}
