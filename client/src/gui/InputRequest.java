package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.ResourceBundle;

class InputRequest extends JDialog {
    private JLabel northLabel = new JLabel();
    private JButton button = new JButton();
    private JTextField textField = new JTextField();
    private PipedWriter writer;
    private String cmd ="";
    private ResourceBundle res;

    InputRequest(JFrame owner, PipedWriter writer, ResourceBundle res){
        super(owner,res.getString("input"),true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.writer = writer;
        textField.setFont(new Font("Serif", Font.PLAIN,14));
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width/3,screen.height/5);
        northLabel.setFont(new Font("Serif", Font.PLAIN,20));
        setLayout(new BorderLayout());
        add(northLabel,BorderLayout.NORTH);
        add(textField,BorderLayout.CENTER);
        add(button,BorderLayout.SOUTH);
        button.setText(res.getString("confirm"));
        button.addActionListener(new butListener(this));
    }

    public void setCommand(String cmd){
        this.cmd = cmd;
        northLabel.setText(res.getString("requireSimple"));
        button.setText(res.getString("confirm"));
    }

    public void setRes(ResourceBundle res){
        this.res = res;
    }
    public class butListener implements ActionListener{
        Component comp;
        boolean send;
        butListener(Component comp){
            this.comp = comp;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            send = false;
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(comp, "Параметр не может быть пустым", "АШИБКА БЛЯТЬ", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    writer.write(cmd+" ");
                    if (cmd.equals("update ")) MainJFrame.standart=true;
                    writer.write(textField.getText()+ "\n");
                    writer.flush();
                    setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
