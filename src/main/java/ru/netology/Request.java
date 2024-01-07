package ru.netology;

import java.util.List;

public class Request {
    private RequestLine requestLine;
    private final List<String> headers;
    private String body;


    public Request(RequestLine requestLine, List<String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;

    }

    public Request(RequestLine requestLine, List<String> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMethod() {
        return this.getRequestLine().getMethod();
    }

    public String getPath() {
        return this.getRequestLine().getPath();
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }

}

