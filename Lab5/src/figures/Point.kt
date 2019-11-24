package figures

import kotlin.math.pow
import kotlin.math.sqrt

class Point(var x: Double, var y: Double) {

    fun distanceTo(another: Point): Double {
        return sqrt((x - another.x).pow(2.0) + (y - another.y).pow(2.0))
    }
}
