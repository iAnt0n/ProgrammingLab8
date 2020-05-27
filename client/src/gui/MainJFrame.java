package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class MainJFrame extends JFrame {
    private PipedReader resultReader;
    private PipedWriter cmdWriter;
    public static JTextArea resultTextArea;
    static ReadCity cityReader;
    static ReadHuman humanReader;
    private NorthInfoJPanel upperPanel;
    private ResourceBundle res;
    private CitiesTableModel tableModel;
    private TablePanel tablePanel;
    private Locale[] locales;
    public static boolean standart;

    public MainJFrame(String header, CitiesTableModel tableModel, PipedReader resultReader,PipedWriter cmdWriter, Locale[] locales) {
        super(header);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.resultReader =  resultReader;
        this.cmdWriter = cmdWriter;
        this.tableModel = tableModel;
        this.locales = locales;
        JComboBox<Locale> localeCombo = new JComboBox<>(locales);
        int localeIndex = 0;
        for (int i = 0; i < locales.length; i++){
            if (Locale.getDefault().equals(locales[i])) localeIndex = i;
        }
        localeCombo.setSelectedItem(locales[localeIndex]);
        this.res = ResourceBundle.getBundle("resources.ProgramResources", locales[localeIndex]);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width*3/5,screen.height*3/5);
        setLayout(new BorderLayout());
        tablePanel = new TablePanel(tableModel,cmdWriter, res);
        add(tablePanel,BorderLayout.CENTER);
        add(new ButtonJPanel(this,cmdWriter),BorderLayout.WEST);
        resultTextArea = new JTextArea(5,44);
        resultTextArea.setFont(new Font("Serif", Font.PLAIN,20));
        JScrollPane scroll = new JScrollPane(resultTextArea);
        add(scroll,BorderLayout.SOUTH);
        upperPanel = new NorthInfoJPanel(cmdWriter, res);
        add(upperPanel, BorderLayout.NORTH);
        cityReader = new ReadCity(cmdWriter,this);
        humanReader = new ReadHuman(cmdWriter,this);

        localeCombo.addActionListener(e -> {
            setCurrentLocale((Locale) localeCombo.getSelectedItem());
            validate();
        });
        upperPanel.add(localeCombo, BorderLayout.EAST);
    }

    public static void readCity(){
        cityReader.prepare();
    }
    public static void readHuman(){
        humanReader.prepare();
    }

    public void setCurrentLocale(Locale locale) {
        res = ResourceBundle.getBundle("resources.ProgramResources", locale);
        tablePanel.setRes(res);
        tableModel.changeColumnNames(res);
        tableModel.fireTableStructureChanged();
        upperPanel.updateText(res);
    }
}
