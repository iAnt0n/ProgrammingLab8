package communication;

import collection.Climate;
import gui.ConnectFrame;
import utils.UserInterface;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalTime;

/**
 * Класс, отвечающий за соединение и связь с сервером
 */
public class Connector {
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private static PipedReader reader = new PipedReader();
    private static BufferedReader breader = new BufferedReader(reader);
    private static PipedWriter writer;

    static {
        try {
            writer = new PipedWriter(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ConnectFrame frame = null;
    static boolean retainsConnection;

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
                    JOptionPane.showMessageDialog(frame,"Server unavailable","Error",JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(frame,"Invalid Input","Error",JOptionPane.ERROR_MESSAGE);
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

    public TransferObject readResponse(UserInterface ui) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        int i = ois.read();
        return (TransferObject) ois.readObject();
    }
}
