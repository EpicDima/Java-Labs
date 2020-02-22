package com.dima.lab5.core;

import java.util.Objects;

public final class Complex {
    private double real;
    private double img;

    public Complex(double real, double img) {
        this.real = real;
        this.img = img;
    }

    public Complex() {
        this(0.0, 0.0);
    }

    public Complex add(Complex another) {
        return new Complex(real + another.real, img + another.img);
    }

    public Complex sub(Complex another) {
        return new Complex(real - another.real, img - another.img);
    }

    public Complex mul(Complex another) {
        return new Complex(real * another.real - img * another.img,
                real * another.img + img * another.real);
    }

    public double getReal() {
        return real;
    }

    public double getImg() {
        return img;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImg(double img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.real, real) == 0 &&
                Double.compare(complex.img, img) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, img);
    }

    @Override
    public String toString() {
        return String.format("%f%+fj", real, img);
    }
}
