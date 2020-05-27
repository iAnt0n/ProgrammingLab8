package gui;

import communication.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;

public class NorthInfoJPanel extends JPanel {
    private PipedWriter writer;
    private JButton button;
    private static JLabel label = new JLabel();
    NorthInfoJPanel(PipedWriter writer){
        this.writer=writer;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(label);
        button = new JButton("Сменить пользователя");
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
        label.setText("Вы вошли в систему как: "+user);
    }
}
