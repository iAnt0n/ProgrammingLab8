package communication;

import gui.LogRegJFrame;
import gui.NorthInfoJPanel;
import utils.UserInterface;

import javax.swing.*;
import java.io.*;

public class User {
    private static String login;
    private static char[] password;
    private static LogRegJFrame frame;
    private static boolean permission = false;

    public static String getLogin() {
        return login;
    }

    public static char[] getPassword() {
        return password;
    }

    public static synchronized boolean hasPermission(){
        return permission;
    }

    public static synchronized void setPermission(boolean b){
        permission = b;
    }

    public static void getNewUser(UserInterface inter, Connector cnct) throws IOException {
        PipedReader reader = new PipedReader();
        BufferedReader ui = new BufferedReader(reader);
        PipedWriter writer  = new PipedWriter(reader);
        if (frame == null) {
            frame = new LogRegJFrame();
        }
        LogRegJFrame.ui =writer;

        String login = "";
        char[] password = null;
        synchronized (User.class) {
            while (!hasPermission()) {
                frame.setVisible(true);
                while (!ui.ready()) {
                }
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
                }
                try {
                    User.class.wait();
                }
                catch (InterruptedException ignored){}
            }
        }
        User.login=login;
        User.password=password;
    }

    public static void showError(String responseString){
        JOptionPane.showMessageDialog(frame,responseString,"Произошла ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
