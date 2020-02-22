package com.dima.lab5.server;

import com.dima.lab5.server.service.ComplexWebServiceImpl;

import javax.xml.ws.Endpoint;

public class ServerRunner {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:2020/complex", new ComplexWebServiceImpl());
    }
}
