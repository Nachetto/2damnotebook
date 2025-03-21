package ui.main;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.util.AnnotationLiteral;
import javafx.application.Application;
import javafx.stage.Stage;

public class DIJavafx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setResizable(true);
        container.getBeanManager().getEvent().select(new AnnotationLiteral<StartupScene>() {
        }).fire(primaryStage);
    }

}
