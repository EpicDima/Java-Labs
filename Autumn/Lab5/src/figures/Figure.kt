package figures

abstract class Figure {
    abstract fun getName(): String
    abstract fun getArea(): Double
    abstract fun getPoints(): Array<Point>
}