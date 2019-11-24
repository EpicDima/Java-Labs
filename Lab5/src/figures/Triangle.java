package figures;

public class Triangle extends Figure {

    private Point a, b, c;
    private double line1, line2, line3;

    public Triangle(Point a, Point b, Point c) throws IllegalArgumentException {

        line1 = a.distanceTo(b);
        line2 = a.distanceTo(c);
        line3 = b.distanceTo(c);

        if (((line1 + line2) <= line3) || ((line1 + line3) <= line2) || ((line2 + line3) <= line1)) {
            throw new IllegalArgumentException("The triangle with the same coordinates may not exist");
        }

        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String getName() {
        return "Треугольник";
    }

    @Override
    public double getArea() {
        double p = (line1 + line2 + line3) / 2;
        return Math.sqrt(p * (p - line1) * (p - line2) * (p - line3));
    }

    @Override
    public Point[] getPoints() {
        return new Point[]{a, b, c};
    }
}
