package ru.netology;


import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private ExecutorService threadPool = Executors.newFixedThreadPool(64);
    private final Map<String, Map<String, Handler>> handlers = new ConcurrentHashMap<>();


    public void listen(int port) {
        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    var socket = serverSocket.accept();
                    var connectHandler = new ConnectHandler(socket, handlers);
                    threadPool.execute(connectHandler::handle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> pathHandlerMap = new ConcurrentHashMap<>();
        if (handlers.containsKey(method)) {
            pathHandlerMap = handlers.get(method);
        }
        pathHandlerMap.put(path, handler);
        handlers.put(method, pathHandlerMap);
    }


}

