package ru.netology;

import java.io.BufferedOutputStream;

@FunctionalInterface
public interface Handler {
    public void handler(Request request, BufferedOutputStream bufferedOutputStream);
}
