package gui;

import commands.Command;
import commands.CommandBuilder;
import javafx.beans.property.adapter.JavaBeanDoubleProperty;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;

public class ButtonJPanel extends JPanel {
    PipedWriter writer;
    JFrame owner;
    InputRequest requestJDialog;
    SimpleListener simpleListener;
    NotSimpleListener notSimpleListener;
    ButtonJPanel(JFrame owner,PipedWriter writer){
        this.writer= writer;
        this.owner=owner;
        requestJDialog = new InputRequest(owner,writer);
        simpleListener = new SimpleListener();
        notSimpleListener= new NotSimpleListener();
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        for(Command cmd : CommandBuilder.getInstance().getCmdMap().values()){
            try {
                if (cmd.getSimpleArgLen() == 1) {
                    addButton(cmd.getName(), notSimpleListener);
                }
                if (cmd.buildArgs(null, null) == null && cmd.getSimpleArgLen()==0) {
                    addButton(cmd.getName(),simpleListener);
                }
            }catch (NullPointerException e){
            }
        }
        setVisible(true);
    }
    public void addButton(String name, ActionListener listener){
        JButton button = new JButton(name);
        button.addActionListener(listener);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setContentAreaFilled(true);
        add(button);
    }
    class SimpleListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                writer.write(e.getActionCommand()+"\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    class NotSimpleListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                requestJDialog.setCommand(e.getActionCommand()+ " ");
                requestJDialog.setVisible(true);
        }
    }
}
