package com.dima.lab5.client;

import com.dima.lab5.server.service.ComplexWebService;
import com.dima.lab5.core.Complex;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientRunner {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:2020/complex");
            QName qname = new QName("http://service.server.lab5.dima.com/", "ComplexWebServiceImplService");
            Service service = Service.create(url, qname);
            ComplexWebService complexWebService = service.getPort(ComplexWebService.class);

            Complex a = new Complex(2.8, 3.5);
            Complex b = new Complex(2.2, 4.7);

            System.out.println("Первое число: " + a);
            System.out.println("Второе число: " + b);
            System.out.println("Сложение: " + complexWebService.add(a, b));
            System.out.println("Вычитание: " + complexWebService.sub(a, b));
            System.out.println("Умножение: " + complexWebService.multiply(a, b));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
