package com.pharmc.representation.consoleV2;

import com.pharmc.representation.consoleV2.views.interfaces.ViewInterface;
import com.pharmc.representation.consoleV2.views.interfaces.ViewManagerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ViewManager implements ViewManagerInterface {
    private Router router;
    private ViewInterface currentView;
    private Object[] currentArgs;

    private ArrayList<ViewInterface> viewHistory = new ArrayList<>();
    private ArrayList<Object[]> argsHistory = new ArrayList<>();

    public ViewManager(Router router) {
        this.router = router;
    }

    public void start() {
        redirect("/");

        while (true) {
            currentView.render(currentArgs);
        }
    }

    public void redirect(String route, Object... args) {
        newHistoryEntry(currentView, currentArgs);
        currentView = router.get(route);
        currentArgs = args;
        if (currentView == null) {
            throw new RuntimeException("No route found for " + route);
        }
    }

    public void back() {
        if (viewHistory.size() == 0) {
            throw new RuntimeException("No history to go back to");
        }
        currentView = viewHistory.remove(viewHistory.size() - 1);
        currentArgs = argsHistory.remove(argsHistory.size() - 1);
    }

    private void newHistoryEntry(ViewInterface view, Object... args) {
        viewHistory.add(view);
        argsHistory.add(args);
    }
}
