package gui;

import collection.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainJFrame extends JFrame {
    private PipedReader resultReader;
    private PipedWriter cmdWriter;
    public static JTextArea resultTextArea;
    static ReadCity cityReader;
    static ReadHuman humanReader;
    private NorthInfoJPanel upperPanel;
    private ButtonJPanel buttonPanel;
    private ResourceBundle res;
    private CitiesTableModel tableModel;
    private TablePanel tablePanel;
    private JComboBox<Locale> localeCombo;
    public static boolean standart;
    public VisualJPanel visPanel;
    JPanel centreCardPanel;

    public MainJFrame(VisualJPanel visPanel, CitiesTableModel tableModel, PipedReader resultReader,PipedWriter cmdWriter, Locale[] locales) {
        super("Lab8");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.resultReader =  resultReader;
        this.tableModel = tableModel;
        this.cmdWriter = cmdWriter;
        this.visPanel = visPanel;
        localeCombo = new JComboBox<>(locales);
        int localeIndex = 0;
        for (int i = 0; i < locales.length; i++){
            if (Locale.getDefault().equals(locales[i])) localeIndex = i;
        }
        this.res = ResourceBundle.getBundle("resources.ProgramResources", locales[localeIndex]);
        tablePanel = new TablePanel(tableModel,cmdWriter, res);
        upperPanel = new NorthInfoJPanel(this,cmdWriter, res);
        buttonPanel = new ButtonJPanel(this, cmdWriter, res);
        cityReader = new ReadCity(cmdWriter,this, res);
        humanReader = new ReadHuman(cmdWriter,this, res);
        setCurrentLocale(locales[localeIndex]);

//        ScreenSize
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width*3/5,screen.height*3/5);
//        Adding Components
        setLayout(new BorderLayout());
        resultTextArea = new JTextArea(5,44);
        resultTextArea.setFont(new Font("Serif", Font.PLAIN,20));
        JScrollPane scroll = new JScrollPane(resultTextArea);

        add(scroll,BorderLayout.SOUTH);
        add(upperPanel, BorderLayout.NORTH);
         centreCardPanel = new JPanel(new CardLayout());
//
        centreCardPanel.add(tablePanel, "Таблица");
        centreCardPanel.add(visPanel,"Визуал");
//
        centreCardPanel.getLayout();
        add(centreCardPanel,BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        localeCombo.addActionListener(e -> {
            setCurrentLocale((Locale) localeCombo.getSelectedItem());
            validate();
        });
        upperPanel.add(localeCombo, BorderLayout.EAST);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateVisual();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
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
        tablePanel.updateTimeRender();
        tableModel.changeColumnNames(res);
        tableModel.fireTableStructureChanged();
        upperPanel.updateText(res);
        localeCombo.setSelectedItem(locale);
        buttonPanel.updateText(res);
        visPanel.setRes(res);
        cityReader.updateText(res);
        humanReader.updateText(res);
    }
    public VisualJPanel getVisPanel(){
        return visPanel;
    }
    public void updateVisual(){
        visPanel.updateVisual();
    }
    public JPanel getCard(){
        return centreCardPanel;
    }
}
