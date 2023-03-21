package com.pharmc;

import com.pharmc.application.Container;
import com.pharmc.representation.interfaces.MainUiInterface;

public class MyApp {
    public static void main(String[] args) {
        Container container = new Container();
        MainUiInterface ui  = container.resolve(MainUiInterface.class);
        ui.start();
    }
}
