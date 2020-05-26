package communication;

import gui.ConnectFrame;
import utils.UserInterface;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.time.LocalTime;

/**
 * Класс, отвечающий за соединение и связь с сервером
 */
public class Connector {
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    static PipedReader reader = new PipedReader();
    static BufferedReader breader = new BufferedReader(reader);
    static PipedWriter writer;

    static {
        try {
            writer = new PipedWriter(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectFrame frame = null;
    public static boolean retainsConnection;

    /**
     * Конструктор, создающий новый сокет и получающий связанные с ним потоки
     *
     * @param host адрес хоста сервера
     * @param port номер порта
     * @throws IOException при ошибке получения потоков, связанных с сокетом
     */
    private Connector(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
        retainsConnection = true;
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }

    public static Connector connectToServ() {
        LocalTime first = LocalTime.now();
        if (frame == null){frame = new ConnectFrame(writer);}
        while (!retainsConnection) {
            try {
                frame.setVisible(true);
                while (!breader.ready()){};
                frame.setVisible(false);
                return new Connector(breader.readLine(),Integer.parseInt(breader.readLine()));
            } catch (IOException e) {
                LocalTime second = LocalTime.now();
                if (second.getSecond() - first.getSecond() > 10) {
                    JOptionPane.showMessageDialog(frame,"Соединение с сервером не может быть установлено, простите","Проверьте сервер",JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(frame,"Не удалось соединиться с сервером, проверьте введенные данные","Произошла ошибка",JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public void sendTO(TransferObject TO) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(TO);
            oos.flush();
            out.write(baos.toByteArray());
        }
    }

    public TransferObject readResponse(UserInterface ui) {
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            return (TransferObject) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            ui.writeln("Не удалось прочитать ответ сервера");
            return null;
        }
    }
}
