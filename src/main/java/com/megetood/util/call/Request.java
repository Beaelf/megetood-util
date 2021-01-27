package com.megetood.util.call;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Iterator;
import java.util.Map;

/**
 * todo
 *
 * @author Lei Chengdong
 * @date 2020/12/29
 */
public class Request {
    private RequestMethod method;
    private String url;
    private MediaType contentType;
    private Object body;
    private Map<String, Object> params;

    public Request(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.contentType = builder.contentType;
        this.body = builder.body;
        this.params = builder.params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RequestMethod method;
        private String url;
        private MediaType contentType;
        private Object body;
        private Map<String, Object> params;

        public Builder() {
        }

        public Builder url(String url) {
            if (url == null) {
                throw new NullPointerException("url == null");
            }
            this.url = url;
            return this;
        }

        public Builder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public Builder get() {
            return method(RequestMethod.GET, null, null);
        }

        public Builder post(Object body) {
            return this.post(MediaType.parseMediaType("application/json; charset=utf-8"), body);
        }

        public Builder post(MediaType contentType, Object body) {
            return this.method(RequestMethod.POST, contentType, body);
        }

        private Builder method(RequestMethod method, MediaType contentType, Object body) {
            this.method = method;
            this.contentType = contentType;
            this.body = body;
            return this;
        }

        public Request build() {
            if (this.url == null) {
                throw new IllegalStateException("url == null");
            }
            if (this.method == null) {
                throw new IllegalStateException("method == null");
            }

            // build url with params
            if (params != null) {
                StringBuilder urlParams = new StringBuilder();
                Iterator<Map.Entry<String, Object>> ite = params.entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, Object> cur = ite.next();
                    urlParams.append("&").append(cur.getKey()).append("=").append(cur.getValue());
                }

                if (this.url.indexOf("?") > 0) {
                    if (this.url.endsWith("&")) {
                        // if url ends like "?key=value&"
                        this.url = String.format("%s%s", this.url, urlParams.deleteCharAt(0).toString());
                    } else {
                        // if url ends like "?key=value"
                        this.url = String.format("%s%s", this.url, urlParams.toString());
                    }
                } else {
                    // if url ends with nothing
                    this.url = String.format("%s?%s", this.url, urlParams.deleteCharAt(0).toString());
                }
            }

            return new Request(this);
        }
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
