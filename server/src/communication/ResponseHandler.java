package communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class ResponseHandler extends RecursiveAction{
    private TransferObject response;
    private SocketChannel channel;
    private Logger log = Logger.getLogger(ResponseHandler.class.getName());

    public ResponseHandler(SocketChannel channel, TransferObject response){
        this.channel=channel;
        this.response=response;
    }


    @Override
    public void compute() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(response);
            oos.flush();
            channel.write(ByteBuffer.wrap(baos.toByteArray()));
            log.info("Ответ отправлен клиенту "+channel.getRemoteAddress());
        } catch (IOException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}