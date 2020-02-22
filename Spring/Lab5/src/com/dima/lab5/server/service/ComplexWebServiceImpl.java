package com.dima.lab5.server.service;

import com.dima.lab5.core.Complex;

import javax.jws.WebService;

@WebService(endpointInterface = "com.dima.lab5.server.service.ComplexWebService")
public class ComplexWebServiceImpl implements ComplexWebService {
    @Override
    public Complex add(Complex a, Complex b) {
        return a.add(b);
    }

    @Override
    public Complex sub(Complex a, Complex b) {
        return a.sub(b);
    }

    @Override
    public Complex multiply(Complex a, Complex b) {
        return a.mul(b);
    }
}