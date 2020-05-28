package gui;

import com.sun.scenario.effect.impl.sw.java.JSWBoxBlurPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedWriter;

public class ConnectFrame extends JFrame {
    private PipedWriter writer;
    private JTextField host;
    private JTextField port;
    public ConnectFrame(PipedWriter writer){
        this.writer = writer;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width/5,screen.height/5,screen.width/5,screen.height/5);
        KeyBoardListener keyListener = new KeyBoardListener();
//        Добавление на верх фрэйма надписи
        add(createLabelCenter("Введите хост и порт сервера"), BorderLayout.NORTH);
//        Панель в середине
        JPanel centrePanel = new JPanel(new GridLayout(2,2,0,10));
        centrePanel.add(createLabelCenter("Server's host"));
        centrePanel.add(host = new JTextField());
        host.addActionListener(keyListener);
        centrePanel.add(createLabelCenter("Port"));
        centrePanel.add(port = new JTextField());
        port.addActionListener(keyListener);
        port.setFont(new Font("Serif", Font.PLAIN,20));
        host.setFont(new Font("Serif", Font.PLAIN,20));
//        Добавляем эту панель в центр
        add(centrePanel,BorderLayout.CENTER);
//        Добавим кнопку
        JButton button = new JButton("Connect");
//        Установим слушателя
        button.addActionListener(e -> {
            try {
                writer.write(host.getText() + "\n");
                writer.write(port.getText() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
//        Добавим кнопку на панель
        add(button,BorderLayout.SOUTH);
    }

    private JLabel createLabelCenter(String name){
        JLabel label = new JLabel(name);
        label.setFont(new Font("Serif", Font.PLAIN,20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    public class KeyBoardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                writer.write(host.getText() + "\n");
                writer.write(port.getText() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
