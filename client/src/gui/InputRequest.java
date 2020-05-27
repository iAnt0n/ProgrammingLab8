package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;

class InputRequest extends JDialog {
    private JLabel northLabel = new JLabel();
    private JButton button = new JButton("Подтвердить");
    private JTextField textField = new JTextField();
    private PipedWriter writer;
    private String cmd ="";
    InputRequest(JFrame owner, PipedWriter writer){
        super(owner,"ОКНО ЕБАТЬ",true);
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
        button.addActionListener(new butListener(this));
    }
    public void setCommand(String cmd){
        this.cmd = cmd;
        northLabel.setText("Для команды "+cmd+" необходим дополнительный параметр");
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
