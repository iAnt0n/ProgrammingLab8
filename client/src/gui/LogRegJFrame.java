package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;

public class LogRegJFrame extends JFrame {
    public static PipedWriter ui;
    private JTextField login;
    private JPasswordField password;
    public LogRegJFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        KeyBoardListener keyList = new KeyBoardListener();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setTitle("Окно настройки");
        setResizable(false);
        setBounds(screen.width/5,screen.height/5,screen.width/4,screen.height/5);
//        Надпись сверху
        add(createLabelCenter("Нажмите login для входа или register для регистрации"), BorderLayout.NORTH);
//        Панель в середине
        JPanel centrePanel = new JPanel(new GridLayout(2,2,0,10));
        centrePanel.add(createLabelCenter("Login"));
        centrePanel.add(login = new JTextField());
        centrePanel.add(createLabelCenter("Password"));
        centrePanel.add(password = new JPasswordField());
        login.setFont(new Font("Serif", Font.PLAIN,20));
        login.addActionListener(keyList);
        password.addActionListener(keyList);
//        Добавляем эту панель в центр
        add(centrePanel,BorderLayout.CENTER);
//        Панель с кнопками
        ButtonListener listener = new ButtonListener();
        JPanel southPan = new JPanel(new GridLayout(1,2));
        JButton login = new JButton("login");
        login.addActionListener(listener);
        southPan.add(login);
        JButton register = new JButton("register");
        register.addActionListener(listener);
        southPan.add(register);
//        Добавляем панель с кнопками в соновной слой
        add(southPan,BorderLayout.SOUTH);
    }
    private JLabel createLabelCenter(String name){
        JLabel label = new JLabel(name);
        label.setFont(new Font("Serif", Font.PLAIN,20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ui.write(e.getActionCommand()+"\n");
                ui.write(login.getText()+"\n");
                ui.write(passToString(password.getPassword())+"\n");
                ui.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private String passToString(char[] chars){
        StringBuilder sb = new StringBuilder();
        for (char ch:chars){
            sb.append(ch);
        }
        return sb.toString();
    }
    public class KeyBoardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ui.write("login\n");
                ui.write(login.getText()+"\n");
                ui.write(passToString(password.getPassword())+"\n");
                ui.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
