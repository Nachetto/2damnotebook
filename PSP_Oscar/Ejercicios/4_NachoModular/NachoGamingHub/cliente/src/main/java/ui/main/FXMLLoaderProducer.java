package ui.main;


import javafx.fxml.FXMLLoader;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class FXMLLoaderProducer {

    Instance<Object> instance;

    @Inject
    public FXMLLoaderProducer(Instance<Object> instance) {
        this.instance = instance;
    }

    @Produces
    public FXMLLoader createLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(controller -> instance.select(controller).get());
        return loader;
    }
}
