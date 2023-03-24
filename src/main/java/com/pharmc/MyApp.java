package com.pharmc;


import com.pharmc.infrastructure.setup.Bootstrap;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.interfaces.MainUiInterface;

public class MyApp {
    public static void main(String[] args) {
        Container container = Bootstrap.prod();
        MainUiInterface ui  = container.resolve(MainUiInterface.class);
        ui.start();
    }
}
