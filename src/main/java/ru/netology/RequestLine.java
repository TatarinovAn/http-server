package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestLine {
    private final String method;
    private final String path;
    private final String version;
    private List<NameValuePair> queryList;
    private String query;
    private URLEncodedUtils URLEncorydedUtils;


    public RequestLine(String method, String path, String version) {
        this.method = method;
        if (path.contains("?")) {
            this.query = path.substring(path.indexOf("?") + 1, path.length() - 1);
            getQueryParam(query);
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

    private void getQueryParam(String name) {

        this.queryList = URLEncorydedUtils.parse(name, StandardCharsets.UTF_8);
    }

    public Map<String, String> getQueryParams() {
        if (query != null) {
            Map<String, String> listParam = new HashMap<>();
            for (NameValuePair param : queryList) {
                listParam.put(param.getName(), param.getValue());
            }

            return listParam;
        }
        return null;
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
