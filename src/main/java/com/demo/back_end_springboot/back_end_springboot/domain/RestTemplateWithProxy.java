package com.demo.back_end_springboot.back_end_springboot.domain;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class RestTemplateWithProxy {
    static RestTemplate REST_TEMPLATE = new RestTemplate();
    static SimpleClientHttpRequestFactory FACTORY = new SimpleClientHttpRequestFactory();

    static {
        FACTORY.setConnectTimeout(15000);
        FACTORY.setReadTimeout(5000);
    }

    public static RestTemplate getRestTemplate() {
        FACTORY.setProxy(getRandomProxy());
        REST_TEMPLATE.setRequestFactory(FACTORY);
        return REST_TEMPLATE;
    }

    private static Proxy getRandomProxy() {
        String proxyIp = "";
        int port = 0;

        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8090));
    }
}
