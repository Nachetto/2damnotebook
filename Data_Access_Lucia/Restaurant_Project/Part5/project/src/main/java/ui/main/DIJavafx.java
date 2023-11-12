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
        try {
            final SeContainer container = initializer.initialize();
            primaryStage.setMinWidth(749);
            primaryStage.setMinHeight(450);
            primaryStage.setResizable(true);
            container.getBeanManager().getEvent().select(new AnnotationLiteral<StartupScene>() {
            }).fire(primaryStage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
