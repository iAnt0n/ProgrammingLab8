package gui;

import collection.City;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


/**
 * TableDemo is just like SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class TablePanel extends JPanel {
    private JTable table;
    private JFrame frame;

    public TablePanel(JFrame frame, CitiesTableModel tableModel) {
        super();
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

    class ClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            if(e.getClickCount() == 2) {
                JDialog dialog = new JDialog(frame, "ОКНО ЕБАТЬ", false);
                dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                dialog.setSize(500, 400);
                dialog.setVisible(true);
            }
        }
    }
}