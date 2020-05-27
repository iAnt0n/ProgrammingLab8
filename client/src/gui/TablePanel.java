package gui;

import collection.City;
import communication.User;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PipedWriter;
import java.nio.channels.Pipe;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class TablePanel extends JPanel {
    private JTable table;
    private User user;
    PipedWriter cmdWriter;
    private ResourceBundle res;

    public TablePanel(CitiesTableModel tableModel, PipedWriter cmdWriter, ResourceBundle res) {
        super();
        this.cmdWriter = cmdWriter;
        this.res = res;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        TableRowSorter<CitiesTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.addMouseListener(new ClickHandler());

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    public void setRes(ResourceBundle res){
        this.res = res;
    }

    class ClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            if(e.getClickCount() == 2) {
                HashMap<String, Object> result = new HashMap<>();
                for (int i = 0; i < table.getColumnCount(); i++) {
                    result.put(table.getColumnName(i), table.getValueAt(row, i));
                }
                if (result.get(res.getString("owner")).equals(User.getLogin())){
                    new EditDialog(result, cmdWriter, res);
                }
                else new InfoDialog(result, res);
            }
        }
    }
}