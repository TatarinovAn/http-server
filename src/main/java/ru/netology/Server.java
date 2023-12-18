package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;


public class Server implements Runnable {
    public final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
            "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
    private int port;

    public void Server(int port) {
        this.port = port;
    }


    private String requestListen(String requestLine) throws IOException {
        final var parts = requestLine.split(" ");
        if (parts.length != 3) {
            // just close socket
            return null;
        }
        final var path = parts[1];
        if (!validPaths.contains(path)) {
            return "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Length: 0\r\n" +
                    "Connection: close\r\n" +
                    "\r\n";

        }

        final var filePath = Path.of(".", "public", path);
        final var mimeType = Files.probeContentType(filePath);
        // special case for classic
        if (path.equals("/classic.html")) {
            final var template = Files.readString(filePath);
            final var content = template.replace(
                    "{time}",
                    LocalDateTime.now().toString()
            ).getBytes();
            return "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + mimeType + "\r\n" +
                    "Content-Length: " + content.length + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n";
        }

        final var length = Files.size(filePath);
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                "Content-Length: " + length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
    }

    @Override
    public void run() {
        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                try (
                        final var socket = serverSocket.accept();
                        final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        final var out = new BufferedOutputStream(socket.getOutputStream())
                ) {
                    String outServer = requestListen(in.readLine());
                    if (outServer == null) {
                        continue;
                    }
                    out.write((
                            outServer
                    ).getBytes());
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
