package figures;

public class PolyAngle extends Figure {

    private Point[] points;
    private double[] lines;

    public PolyAngle(Point... points) throws IllegalArgumentException {
        if (points.length <= 3) {
            throw new IllegalArgumentException("A polygon must consist of 4 or more vertices");
        }

        checkForConvex(points);

        double[] temp = new double[points.length];
        for (int i = 1; i < points.length; i++) {
            temp[i] = points[i].distanceTo(points[i - 1]);
        }
        temp[0] = points[0].distanceTo(points[points.length - 1]);

        this.points = points;
        this.lines = temp;
    }

    private void checkForConvex(Point[] points) throws IllegalArgumentException {
        double first = checkPoints(points[points.length - 1], points[0], points[1]);
        for (int i = 1; i < points.length - 1; i++) {
            if (Double.compare(Math.signum(checkPoints(points[i - 1], points[i], points[i + 1])), Math.signum(first)) != 0) {
                throw new IllegalArgumentException("The polygon must be convex");
            }
        }
    }

    private double checkPoints(Point a, Point b, Point c) {
        Point ab = new Point(b.getX() - a.getX(),
                             b.getY() - a.getY());
        Point bc = new Point(c.getX() - b.getX(),
                             c.getY() - b.getY());
        return ab.getX() * bc.getY() - ab.getY() * bc.getX();
    }

    @Override
    public String getName() {
        return "Многоугольник";
    }

    @Override
    public double getArea() {
        double area = 0;
        for (int i = 0; i < points.length - 1; i++) {
            area += (points[i].getX() + points[i + 1].getX())
                    * (points[i].getY() - points[i + 1].getY());
        }
        area += (points[points.length - 1].getX() + points[0].getX())
                * (points[points.length - 1].getY() - points[0].getY());
        return 0.5 * Math.abs(area);
    }

    @Override
    public Point[] getPoints() {
        return points;
    }
}
