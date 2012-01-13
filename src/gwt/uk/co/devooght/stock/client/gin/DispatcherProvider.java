package uk.co.devooght.stock.client.gin;

import com.extjs.gxt.ui.client.mvc.Dispatcher;

import javax.inject.Provider;

public class DispatcherProvider implements Provider<Dispatcher> {
  public Dispatcher get() {
    return Dispatcher.get();
  }
}
