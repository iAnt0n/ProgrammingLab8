package gui;

import collection.City;

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
    VisualJPanel visPanel;

    public MainJFrame(VisualJPanel visPanel, CitiesTableModel tableModel, PipedReader resultReader,PipedWriter cmdWriter, Locale[] locales) {
        super("Главный Фрэйм блять");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.resultReader =  resultReader;
        this.tableModel = tableModel;
        this.cmdWriter = cmdWriter;
        this.locales = locales;
        int localeIndex = 0;
        for (int i = 0; i < locales.length; i++){
            if (Locale.getDefault().equals(locales[i])) localeIndex = i;
        }
        this.res = ResourceBundle.getBundle("resources.ProgramResources", locales[localeIndex]);
//        localeComboBox
        JComboBox<Locale> localeCombo = new JComboBox<>(locales);
        localeCombo.setSelectedItem(locales[localeIndex]);
//        ScreenSize
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width*3/5,screen.height*3/5);
//        Adding Components
        setLayout(new BorderLayout());
        tablePanel = new TablePanel(tableModel,cmdWriter, res);
        upperPanel = new NorthInfoJPanel(cmdWriter, res);
        cityReader = new ReadCity(cmdWriter,this);
        humanReader = new ReadHuman(cmdWriter,this);
        resultTextArea = new JTextArea(5,44);
        resultTextArea.setFont(new Font("Serif", Font.PLAIN,20));
        JScrollPane scroll = new JScrollPane(resultTextArea);
        this.visPanel = visPanel;
        add(scroll,BorderLayout.SOUTH);
        add(upperPanel, BorderLayout.NORTH);
        add(visPanel,BorderLayout.CENTER);
        add(new ButtonJPanel(this,cmdWriter),BorderLayout.WEST);
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
    public VisualJPanel getVisPanel(){
        return visPanel;
    }
    public static void updateVisual(ConcurrentHashMap<String, City> map){

    }
}
