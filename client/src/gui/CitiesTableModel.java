package gui;

import collection.City;
import collection.Climate;
import collection.Government;
import collection.StandardOfLiving;
import communication.Connector;
import communication.TransferObject;
import communication.User;
import utils.UserInterface;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CitiesTableModel extends AbstractTableModel {
    private Vector<String> columnNames;
    private Vector<Vector<Object>> data = new Vector<>();
    public CitiesTableModel(UserInterface ui, ResourceBundle res,TransferObject table){
            changeColumnNames(res);
            updateTable((ConcurrentHashMap<String, City>) table.getComplexArgs());
    }

    public void changeColumnNames(ResourceBundle res){
        columnNames = new Vector<>(Arrays.asList(res.getString("owner"),
                res.getString("id"),
                res.getString("name"),
                res.getString("x"),
                res.getString("y"),
                res.getString("area"),
                res.getString("population"),
                res.getString("meters"),
                res.getString("climate"),
                res.getString("government"),
                res.getString("sol"),
                res.getString("govname"),
                res.getString("govage"),
                res.getString("govhei"),
                res.getString("time"),
                res.getString("key")));
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    @Override
    public Class<?> getColumnClass(int c) {
        switch (c){
            case 0:
            case 2:
            case 11:
            case 15:
                return String.class;
            case 1:
            case 4:
            case 12:
                return Integer.class;
            case 5:
            case 7:
                return Float.class;
            case 6:
                return Long.class;
            case 8:
                return Climate.class;
            case 9:
                return Government.class;
            case 10:
                return StandardOfLiving.class;
            case 3:
            case 13:
                return Double.class;
            case 14:
                return LocalDateTime.class;
        }
        return Object.class;
    }

    public void updateTable(ConcurrentHashMap<String, City> hashMap) {
        data = new Vector<>();
        for (Map.Entry<String, City> e: hashMap.entrySet()){
            Vector<Object> row = new Vector<>(Arrays.asList(e.getValue().getDataRow()));
            row.add(e.getKey());
            data.add(row);
        }
        fireTableDataChanged();
    }
}