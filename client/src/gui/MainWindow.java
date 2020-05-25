package gui;

import collection.City;
import commands.CommandBuilder;
import communication.Connector;
import utils.UserInterface;

import javax.swing.*;
import java.util.concurrent.ConcurrentHashMap;

public class MainWindow extends JFrame {
    private final Connector cnct;
    private final UserInterface ui;
    private final CommandBuilder cb;

    public MainWindow(String header, Connector cnct, UserInterface ui, CommandBuilder cb) {
        super(header);
        this.cnct = cnct;
        this.ui = ui;
        this.cb = cb;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
