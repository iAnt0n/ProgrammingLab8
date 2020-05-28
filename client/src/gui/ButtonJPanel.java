package gui;

import commands.Command;
import commands.CommandBuilder;
import javafx.beans.property.adapter.JavaBeanDoubleProperty;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.*;

public class ButtonJPanel extends JPanel {
    private PipedWriter writer;
    private JFrame owner;
    private InputRequest requestJDialog;
    private SimpleListener simpleListener;
    private NotSimpleListener notSimpleListener;
    private HashMap<String, JButton> buttons = new HashMap<>();
    ButtonJPanel(JFrame owner,PipedWriter writer){
        this.writer= writer;
        this.owner=owner;
        requestJDialog = new InputRequest(owner,writer);
        simpleListener = new SimpleListener();
        notSimpleListener= new NotSimpleListener();
        setLayout(new GridLayout(CommandBuilder.getInstance().getCmdMap().size(),1,3,5));
        for(Command cmd : CommandBuilder.getInstance().getCmdMap().values()) {
            if (cmd.getSimpleArgLen() == 1) {
                addButton(cmd.getName(), notSimpleListener);
            } else {
                addButton(cmd.getName(), simpleListener);
            }
        }
        setVisible(true);
    }
    private void addButton(String name, ActionListener listener){
        JButton button = new JButton(name);
        button.addActionListener(listener);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        buttons.put(name, button);
        add(button);
    }

    public void updateText(ResourceBundle res){
        for (Map.Entry<String, JButton> e: buttons.entrySet()){
            e.getValue().setText(res.getString(e.getKey()));
        }
    }
    class SimpleListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ResourceBundle res = ResourceBundle.getBundle("resources.ButtonResources");
                writer.write(res.getString(e.getActionCommand())+"\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    class NotSimpleListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ResourceBundle res = ResourceBundle.getBundle("resources.ButtonResources");
            requestJDialog.setCommand(res.getString(e.getActionCommand()) + " ");
            requestJDialog.setVisible(true);
        }
    }
}
