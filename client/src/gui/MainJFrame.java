package gui;

import collection.City;
import commands.CommandBuilder;
import communication.Connector;
import communication.User;
import utils.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.ConcurrentHashMap;

public class MainJFrame extends JFrame {
    PipedReader resultReader;
    PipedWriter cmdWriter;
    public static JTextArea resultTextArea;

    public MainJFrame(String header, TablePanel tablePanel,PipedReader resultReader,PipedWriter cmdWriter) {
        super(header);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.resultReader =resultReader;
        this.cmdWriter = cmdWriter;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width*3/5,screen.height*3/5);
        setResizable(false);
        setLayout(new BorderLayout());
        add(tablePanel,BorderLayout.CENTER);
        add(new ButtonJPanel(this,cmdWriter),BorderLayout.WEST);
        resultTextArea = new JTextArea(5,44);
        resultTextArea.setFont(new Font("Serif", Font.PLAIN,20));
        JScrollPane scroll = new JScrollPane(resultTextArea);
        add(scroll,BorderLayout.SOUTH);
    }
}
