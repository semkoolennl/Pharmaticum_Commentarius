package com.pharmc.representation.consoleV2;

import com.pharmc.representation.consoleV2.views.interfaces.ViewInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class Router {
    private HashMap<String, ViewInterface> routes = new HashMap<>();

    public ViewInterface get(String route) {
        return routes.get(route);
    }

    public void register(ViewInterface view) {
        routes.put(view.getRoute(), view);
    }

    public String toString() {
        ArrayList<String[]> routeStrings = new ArrayList<>();

        for (String route : routes.keySet()) {
            // only keep the package name from the views package on
            String className = routes.get(route).getClass().getName();
            String[] parts = className.split("\\.");
            for (String part : parts) {
                if (part.equals("views")) {
                    break;
                }
                className = className.replace(part + ".", "");
            }
            String routeName = "[" + route + "] ";
            routeStrings.add(new String[]{routeName, className});
        }

        ArrayList<String> routeStringsFormatted = new ArrayList<>();
        int longestRouteName = 0;
        for (String[] routeString : routeStrings) {
            if (routeString[0].length() > longestRouteName) {
                longestRouteName = routeString[0].length();
            }
        }

        for (String[] routeString : routeStrings) {
            routeStringsFormatted.add(String.format("%-" + longestRouteName + "s %s", routeString[0], routeString[1]));
        }

        return String.join("\n", routeStringsFormatted);
    }
}
