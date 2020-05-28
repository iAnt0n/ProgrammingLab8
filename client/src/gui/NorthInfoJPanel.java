package gui;

import communication.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

public class NorthInfoJPanel extends JPanel {
    private PipedWriter writer;
    private JButton button;
    private static JLabel label = new JLabel();
    private static JLabel userLabel = new JLabel();
    MainJFrame frame;
    JButton button1;
    JButton button2;
    NorthInfoJPanel(MainJFrame frame,PipedWriter writer, ResourceBundle res){
        this.writer=writer;
        this.frame = frame;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(label);
        label.setText(res.getString("enteredAs"));
        panel.add(userLabel);
        JPanel bigPanel = new JPanel(new GridLayout(2,1));
        bigPanel.add(panel);
        button = new JButton(res.getString("changeUser"));
        button.addActionListener(e -> {
            try {
                User.setPermission(false);
                writer.write("exit \n");
                writer.flush();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });
        bigPanel.add(button);
        add(bigPanel,BorderLayout.WEST);
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new GridLayout(1,2));
        ButtListener listener = new ButtListener();
        button1 = new JButton("Таблица");
        button1.addActionListener(listener);
        panel2.add(button1);
        button2 = new JButton("Визуал");
        button2.addActionListener(listener);
        panel2.add(button2);
        panel1.add(panel2,FlowLayout.LEFT);
        add(panel1,BorderLayout.CENTER);
    }

    public static void setText(String user){
        userLabel.setText(user);
    }

    public void updateText(ResourceBundle res){
        label.setText(res.getString("enteredAs"));
        button.setText(res.getString("changeUser"));
    }
    class ButtListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout layout = (CardLayout)(frame.getCard().getLayout());
            layout.show(frame.centreCardPanel, e.getActionCommand());
        }
    }
}
