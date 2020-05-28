package gui;

import communication.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PipedWriter;
import java.nio.channels.Pipe;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.*;


public class TablePanel extends JPanel {
    private final JTextField idFilter;
    private final JTextField xFilter;
    private final JTextField governmentFilter;
    private final JTextField areaFilter;
    private final JTextField govnameFilter;
    private final JTextField timeFilter;
    private final JTextField govheiFilter;
    private final JTextField yFilter;
    private final JTextField metersFilter;
    private final JTextField populationFilter;
    private final JTextField govageFilter;
    private final JTextField keyFilter;
    private final JTextField climateFilter;
    private final JTextField solFilter;
    private LinkedHashMap<String, JTextField> map = new LinkedHashMap<>();
    private JTable table;
    PipedWriter cmdWriter;
    private ResourceBundle res;
    private TableRowSorter<CitiesTableModel> sorter;
    private JTextField ownerFilter;
    private JTextField nameFilter;

    public TablePanel(CitiesTableModel tableModel, PipedWriter cmdWriter, ResourceBundle res) {
        super();
        this.cmdWriter = cmdWriter;
        this.res = res;
        setLayout(new BorderLayout());
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.addMouseListener(new ClickHandler());
        table.setDefaultRenderer(LocalDateTime.class, new DateRenderer(res.getLocale()));

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new GridLayout(1,16));
        ownerFilter = new JTextField();
        map.put("Owner", ownerFilter);
        idFilter = new JTextField();
        map.put("Id", idFilter);
        nameFilter = new JTextField();
        map.put("Name", nameFilter);
        xFilter = new JTextField();
        map.put("X", xFilter);
        yFilter = new JTextField();
        map.put("Y",yFilter);
        areaFilter = new JTextField();
        map.put("Area", areaFilter);
        populationFilter = new JTextField();
        map.put("Population", populationFilter);
        metersFilter = new JTextField();
        map.put("Meters", metersFilter);
        climateFilter = new JTextField();
        map.put("Climate", climateFilter);
        governmentFilter = new JTextField();
        map.put("Government", governmentFilter);
        solFilter = new JTextField();
        map.put("Standard of Living", solFilter);
        govnameFilter = new JTextField();
        map.put("Gov name", govnameFilter);
        govageFilter = new JTextField();
        map.put("Gov Age", govageFilter);
        govheiFilter = new JTextField();
        map.put("Gov Hei", govheiFilter);
        timeFilter = new JTextField();
        map.put("Time", timeFilter);
        keyFilter = new JTextField();
        map.put("Key", keyFilter);

        int i = 0;
        for (Map.Entry<String, JTextField> elem: map.entrySet()){
            int finalI = i;
            filterPanel.add(elem.getValue());
            TextPrompt tp = new TextPrompt(elem.getKey(), elem.getValue());
            tp.changeAlpha(128);
            elem.getValue().getDocument().addDocumentListener(
                    new DocumentListener() {
                        public void changedUpdate(DocumentEvent e) {
                            textFilter(elem.getKey(), finalI);
                        }
                        public void insertUpdate(DocumentEvent e) {
                            textFilter(elem.getKey(), finalI);
                        }
                        public void removeUpdate(DocumentEvent e) {
                            textFilter(elem.getKey(), finalI);
                        }
                    });
            i++;
        }
        add(filterPanel, BorderLayout.NORTH);
    }



    public void setRes(ResourceBundle res){
        this.res = res;
    }

    private void textFilter(String field, int index) {
        RowFilter<CitiesTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(map.get(field).getText(), index);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
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

    static class DateRenderer extends DefaultTableCellRenderer {
        DateTimeFormatter formatter;
        public DateRenderer(Locale locale) {
            super();
            formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
        }

        public void setValue(Object value) {
            setText(formatter.format((LocalDateTime) value));
        }
    }

    public void updateTimeRender(){
        table.setDefaultRenderer(LocalDateTime.class, new DateRenderer(res.getLocale()));
    }
}