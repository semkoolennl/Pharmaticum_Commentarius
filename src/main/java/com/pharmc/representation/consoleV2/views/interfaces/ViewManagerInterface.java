package com.pharmc.representation.consoleV2.views.interfaces;

public interface ViewManagerInterface {
    void start();
    void redirect(String route, Object... args);
    void back();
}
