package communication;

import java.io.*;

/**
 * Класс, служащий для передачи команд с клиента на сервер и ответов с сервера на клиент
 */
public class TransferObject implements Serializable {
    private String name;
    private String[] simpleArgs;
    private Object complexArgs;
    private String login;
    private char[] password;
    private static final long serialVersionUID = 123456789L;

    public TransferObject(String name, String[] simpleArgs, Object complexArgs, String login, char[] password) {
        this.name = name;
        this.simpleArgs = simpleArgs;
        this.complexArgs = complexArgs;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Object getComplexArgs() {
        return complexArgs;
    }

    public String[] getSimpleArgs() {
        return simpleArgs;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setSimpleArgs(String[] s){
        simpleArgs=s;
    }

    public void setComplexArgs(Object o){
        complexArgs=o;
    }
}