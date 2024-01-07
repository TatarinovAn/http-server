package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestLine {
    private final String method;
    private final String path;
    private final String version;
    private String query;

    public RequestLine(String method, String path, String version) {
        this.method = method;
        if (path.contains("?")) {
            this.query = path.substring(path.indexOf("?") + 1, path.length() - 1);

            path = path.substring(0, path.indexOf("?"));

            this.path = path;
        } else {
            this.path = path;
        }

        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public List<NameValuePair> getQueryParam(String name) {
        return URLEncodedUtils.parse(name, StandardCharsets.UTF_8);
    }

    public void getQueryParams() {
        if (query != null) {

            for (final NameValuePair param : getQueryParam(query)) {
                System.out.println(param.getName() + " : " + param.getValue());
            }
        }
    }


    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
