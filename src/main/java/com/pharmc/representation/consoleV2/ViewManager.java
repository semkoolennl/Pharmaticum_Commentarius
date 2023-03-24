package com.pharmc.representation.consoleV2;

import com.pharmc.representation.consoleV2.views.interfaces.ViewInterface;
import com.pharmc.representation.consoleV2.views.interfaces.ViewManagerInterface;

import java.util.ArrayList;

public class ViewManager implements ViewManagerInterface {
    private Router router;
    private ViewInterface currentView;
    private ArrayList<ViewInterface> history = new ArrayList<>();

    public ViewManager(Router router) {
        this.router = router;
    }

    public void start() {
        redirect("/");

        while (true) {
            currentView.render();
        }
    }

    public void redirect(String route) {
        history.add(currentView);
        currentView = router.get(route);
        if (currentView == null) {
            throw new RuntimeException("No route found for " + route);
        }
    }

    public void back() {
        if (history.size() > 0) {
            currentView = history.get(history.size() - 1);
            history.remove(history.size() - 1);
        }
    }
}
