package communication;

import collection.City;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

public class UpdateHandler implements Runnable {
    private ClientHandler clientHandler;
    private ConcurrentHashMap<String, City> map;

    public UpdateHandler(ClientHandler clientHandler, ConcurrentHashMap<String, City> map) {
        this.clientHandler = clientHandler;
        this.map = map;
    }

    @Override
    public void run() {
        for(SelectionKey key : clientHandler.getSelector().keys()) {
            if(key.isValid() && key.isWritable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(new TransferObject("update", null, map, null, null));
                    oos.flush();
                    channel.write(ByteBuffer.wrap(baos.toByteArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
