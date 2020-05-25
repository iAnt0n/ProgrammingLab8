package communication;

import utils.UserInterface;

import java.io.Console;
import java.io.IOException;

public class User {
    private String login;
    private char[] password;

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

    public static User getNewUser(UserInterface ui, Connector cnct) throws IOException {
        boolean hasPermission = false;
        String login = "";
        char[] password = null;
        Console console = System.console();
        while (!hasPermission) {
            String action = ui.readLineWithMessage("Введите login для входа или register для регистрации или же exit для выхода: ");
            if (action.equals("login")) {
                login = ui.readLineWithMessage("Введите имя пользователя: ");
                password = ui.readLineWithMessage("pass").toCharArray();
//                password = console.readPassword("Введите пароль: ");
                cnct.sendTO(new TransferObject("login", null, null, login, password), ui);
            } else if (action.equals("register")) {
                login="";
                while (login.isEmpty()) {
                    login = ui.readLineWithMessage("Введите имя пользователя: ");
                    if (login.isEmpty()) ui.writeln("Имя пользователя не может быть пустой строкой");
                }
//                password = console.readPassword("Введите пароль: ");
                password = ui.readLineWithMessage("pass").toCharArray();
                cnct.sendTO(new TransferObject("register", null, null, login, password), ui);
            } else if (action.equals("exit")) {
                System.exit(0);
            } else {
                ui.writeln("Неверная опция");
                continue;
            }
            String responseString = cnct.readResponse(ui).getSimpleArgs()[0];
            if (responseString.equals("Вход произошел успешно")) {
                hasPermission = true;
            }
            ui.writeln(responseString);
        }
        ui.writeln("Введите help для просмотра доступных команд");
        return new User(login, password);
    }
}
