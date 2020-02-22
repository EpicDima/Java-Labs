package com.dima.lab5.server.service;

import com.dima.lab5.core.Complex;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ComplexWebService {
    @WebMethod
    Complex add(Complex a, Complex b);

    @WebMethod
    Complex sub(Complex a, Complex b);

    @WebMethod
    Complex multiply(Complex a, Complex b);
}
