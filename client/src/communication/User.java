package communication;

import gui.LogRegJFrame;
import gui.NorthInfoJPanel;
import jdk.nashorn.internal.scripts.JO;
import utils.UserInterface;

import javax.swing.*;
import java.io.*;

public class User {
    private String login;
    private char[] password;
    private static LogRegJFrame frame;

    private User(String login, char[] password){
        this.login=login;
        this.password=password;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    public static User getNewUser(UserInterface inter, Connector cnct) throws IOException {
        PipedReader reader = new PipedReader();
        BufferedReader ui = new BufferedReader(reader);
        PipedWriter writer  = new PipedWriter(reader);
        if (frame == null) {
            frame = new LogRegJFrame();
        }
        LogRegJFrame.ui =writer;
        boolean hasPermission = false;
        String login = "";
        char[] password = null;
            while (!hasPermission) {
                frame.setVisible(true);
                while(!ui.ready()){}
                frame.setVisible(false);
                String action = ui.readLine();
                System.out.println(action);
                if (action.equals("login")) {
                    login = ui.readLine();
                    NorthInfoJPanel.setText(login);
                    password = ui.readLine().toCharArray();
//                password = console.readPassword("Введите пароль: ");
                    cnct.sendTO(new TransferObject("login", null, null, login, password));
                } else if (action.equals("register")) {
                    login = ui.readLine();
                    if (login.isEmpty()) throw new IOException("Имя пользователя не может быть пустой строкой");
//                password = console.readPassword("Введите пароль: ");
                    password = ui.readLine().toCharArray();
                    cnct.sendTO(new TransferObject("register", null, null, login, password));
                } else if (action.equals("exit")) {
                    System.exit(0);
                } else {
//                    ui.writeln("Неверная опция");
                    continue;
                }
                System.out.println("1234");
                String responseString = cnct.readResponse(inter).getSimpleArgs()[0];
                if (responseString.equals("Вход произошел успешно")) {
                    hasPermission = true;
                }
                System.out.println(responseString);
                if (responseString.equals("Неверное имя пользователя или пароль") | responseString.equals("Пользователь с таким именем уже зарегистрирован")) {
                    JOptionPane.showMessageDialog(frame,responseString,"Произошла ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        return new User(login, password);
    }
}
