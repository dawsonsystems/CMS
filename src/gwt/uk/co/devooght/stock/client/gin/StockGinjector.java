package uk.co.devooght.stock.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import uk.co.devooght.stock.client.view.HomePage;


@GinModules(StockModule.class)
public interface StockGinjector extends Ginjector {

    HomePage getHomePage();
}
