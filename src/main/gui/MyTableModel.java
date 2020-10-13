package main.gui;

import main.entity.Route;
import main.entity.RouteSet;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
        super();
        String[] columns = {"user", "id", "name", "x", "y", "creationDate", "nameFrom", "xFrom", "yFrom", "nameTo", "xTo", "yTo", "distance"};
        for (String s : columns) addColumn(s);


    }


    public void fillRows(RouteSet routeSet) {
        while (getRowCount() > 0) removeRow(0);
        for (Route route : routeSet.getSet()) {
            if (route.getFrom() != null) {
                addRow(new Object[] {route.getUser(), route.getId(), route.getName(), route.getCoordinates().getX(),
                        route.getCoordinates().getY(), route.getCreationDate().toString(),
                        route.getFrom().getName(), route.getFrom().getX(), route.getFrom().getY(),
                        route.getTo().getName(), route.getTo().getX(), route.getTo().getY(), route.getDistance()});
            } else {
                addRow(new Object[] {route.getUser(), route.getId(), route.getName(), route.getCoordinates().getX(),
                        route.getCoordinates().getY(), route.getCreationDate().toString(),
                        null, null, null,
                        route.getTo().getName(), route.getTo().getX(), route.getTo().getY(), route.getDistance()});
            }

        }
    }



    @Override
    public Class<?> getColumnClass(int column) {
        try {
            switch (column) {
                case 0:
                case 2:
                case 5:
                case 6:
                case 9:
                    return String.class;
                case 1:
                    return Long.class;
                case 3:
                    return Float.class;
                case 4:
                case 7:
                case 8:
                case 10:
                case 12:
                case 11:
                    return Integer.class;
                default:
                    return null;
            }

        }catch (NullPointerException e) {
            return null;
        }
    }


}
