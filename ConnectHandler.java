package ru.netology;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public class ConnectHandler {
    private final Socket socket;
    private final Map<String, Map<String, Handler>> handlers;

    public ConnectHandler(Socket socket, Map<String, Map<String, Handler>> handlers) {
        this.socket = socket;
        this.handlers = handlers;
    }

    public void handle() {
        try (
                socket;
                final var in = new BufferedInputStream(socket.getInputStream());
                final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            Optional<Request> optionalRequest = RequestParse.parseRequest(in, out);
            Request request = null;
            System.out.println("start");

            if (optionalRequest.isPresent()) {
                request = optionalRequest.get();
            }
            if (request != null) {
                Handler handler = handlers.get(request.getMethod()).get(request.getPath());
                if (handler != null) {
                    handler.handler(request, out);

                } else {
                    Response.responseNotFound(out);
                }
            } else {
                Response.responseBad(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

