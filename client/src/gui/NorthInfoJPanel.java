package gui;

import communication.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class NorthInfoJPanel extends JPanel {
    private PipedWriter writer;
    private JButton button;
    private static JLabel label = new JLabel();
    private static JLabel userLabel = new JLabel();
    NorthInfoJPanel(PipedWriter writer, ResourceBundle res){
        this.writer=writer;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(label);
        panel.add(userLabel);
        button = new JButton(res.getString("changeUser"));
        label.setText(res.getString("enteredAs"));
        button.addActionListener(e -> {
            try {
                User.setPermission(false);
                writer.write("exit \n");
                writer.flush();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });
        panel.add(button);
        add(panel,BorderLayout.WEST);
    }

    public static void setText(String user){
        userLabel.setText(user);
    }

    public void updateText(ResourceBundle res){
        label.setText(res.getString("enteredAs"));
        button.setText(res.getString("changeUser"));
    }
}
