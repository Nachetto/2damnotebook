package org.example.ui;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;


public class Tester {

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();


        Main main = container.select(Main.class).get();
        main.run();

        weld.shutdown();
    }

}