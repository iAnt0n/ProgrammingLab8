import DB.CityDB;
import DB.ClientDB;
import DB.DBConnection;
import collection.CityCollection;
import collection.CollectionManager;
import commands.CommandInvoker;
import commands.CommandType;
import communication.*;
import exceptions.ConnectionCancelledException;

import java.io.*;
import java.net.BindException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Main {
    private static ExecutorService readRequest = Executors.newCachedThreadPool();
    private static ExecutorService getResponse = Executors.newCachedThreadPool();
    private static ForkJoinPool sendResponse = new ForkJoinPool();
    private static ExecutorService sendUpdate = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException,  SQLException {
        Logger log = Logger.getLogger(Main.class.getName());
        CommandInvoker ci = new CommandInvoker();
        CityCollection collection;
        ClientHandler clientHandler = null;

        try {
            clientHandler = new ClientHandler(Integer.parseInt(args[0]));
        } catch (BindException e) {
            log.warning("Порт уже используется. Укажите другой порт");
            System.exit(1);
        } catch (Exception e) {
            log.warning("Usage: java -jar server.jar <port>");
            System.exit(1);
        }

        DBConnection dbconnect = new DBConnection();

        CityDB cityDB = null;
        ClientDB clientDB = null;
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            cityDB = new CityDB(dbconnect.getConnect(
                    prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password")), "cities");
            clientDB = new ClientDB(dbconnect.getConnect(
                    prop.getProperty("db.url"), prop.getProperty("db.user"), prop.getProperty("db.password")), "users");
        } catch (IOException e) {
            System.out.println("Не удалось прочитать .properties файл");
            System.exit(1);
        }

        ClientHandler finalClientHandler = clientHandler;
        CityDB finalCityDB = cityDB;
        ClientDB finalClientDB = clientDB;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            finalCityDB.closeConnection();
            finalClientDB.closeConnection();
            finalClientHandler.closeServer();
        }));


        collection = new CityCollection(CityDB.loadMapFromDB());
        CollectionManager cm = new CollectionManager(collection);

        HashMap<SelectionKey, Future<TransferObject>> to = new HashMap<>();
        HashMap<SelectionKey, Future<TransferObject>> res = new HashMap<>();
        while (true) {
            clientHandler.getSelector().select();
            Iterator keys = clientHandler.getSelector().selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    clientHandler.acceptConnection();
                }

                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    try {
                        to.put(key, readRequest.submit(new RequestHandler(channel)));
                        key.interestOps(SelectionKey.OP_WRITE);
                    } catch (ConnectionCancelledException e) {
                        log.warning(e.getMessage());
                        continue;
                    }
                }

                if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                        try {
                            if (to.containsKey(key)&&to.get(key).isDone()){
                                res.put(key, getResponse.submit(new CommandHandler(to.get(key).get(), cm)));
                                to.remove(key);
                            }
                            if (res.containsKey(key)&&res.get(key).isDone()) {
                                TransferObject response = res.get(key).get();
                                sendResponse.execute(new ResponseHandler(channel, response));
                                if ((ci.getCommands().get(response.getName())).getType().equals(CommandType.MODIFY)) {
                                    sendUpdate.execute(new UpdateHandler(clientHandler, cm.getCollection().getCityMap()));
                                }
                                res.remove(key);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            log.warning(e.getMessage());
                            e.printStackTrace();
                        }
                }
            }
        }
    }
}