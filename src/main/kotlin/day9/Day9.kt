package day9

import InputType
import readAllLines
import kotlin.time.measureTime
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun <T> List<T>.combinations(size: Int): List<List<T>> {
    if (size <= 0) return listOf(emptyList())
    if (size > this.size) return emptyList()
    if (size == this.size) return listOf(this)
    if (size == 1) return this.map { listOf(it) }

    val combinations = mutableListOf<List<T>>()
    val rest = this.drop(1)
    rest.combinations(size - 1).forEach { combination ->
        combinations.add(listOf(this[0]) + combination)
    }
    combinations += rest.combinations(size)
    return combinations
}


class Day9(private val inputType: InputType) {


    data class Point(var x: Int, var y: Int) : Comparable<Point> {
        constructor(x: Number, y: Number) : this(x.toInt(), y.toInt())

        override operator fun compareTo(other: Point) = compareValuesBy(this, other, Point::x, Point::y)

        companion object {
            val ORIGIN = Point(0, 0)
            fun of(input: String): Point {
                val (x, y) = input.split(',', '-', ' ').map { it.trim().toInt() }
                return Point(x, y)
            }
        }
    }

    fun part1(): Long {
        val lines = readAllLines(inputType, this::class.java.packageName)
        return lines.map { Point.of(it) }.combinations(2).maxOf { (a, b) -> area(a, b) }
    }

    private fun area(a: Point, b: Point) = ((a.x - b.x).absoluteValue + 1).toLong() * ((a.y - b.y).absoluteValue + 1)

    fun intersects(
        a: Point, b: Point, segments: List<Pair<Point, Point>>
    ): Boolean {
        val minX = min(a.x, b.x)
        val maxX = max(a.x, b.x)
        val minY = min(a.y, b.y)
        val maxY = max(a.y, b.y)

        for (seg in segments) {
            val (s, e) = seg
            if (s.x == e.x && s.x in (minX + 1)..<maxX && maxOf(minOf(s.y, e.y), minY) < minOf(
                    maxOf(s.y, e.y), maxY
                ) || s.y == e.y && s.y in (minY + 1)..<maxY && maxOf(minOf(s.x, e.x), minX) < minOf(
                    maxOf(s.x, e.x), maxX
                )
            ) return true
        }

        return false
    }

    fun part2(): Any {
        val lines = readAllLines(inputType, this::class.java.packageName)
        val points = lines.map { Point.of(it) }
        val segments = points.windowed(2).map { it[0] to it[1] }
        return points.combinations(2).filter { (a, b) -> !intersects(a, b, segments) }.maxOf { (a, b) -> area(a, b) }
    }


    fun solvePart1() {
        val duration = measureTime {
            print("Result for part 1 with input ${inputType.name}:  ${part1()}")
        }
        println(" in : $duration")
    }

    fun solvePart2() {
        val duration = measureTime {
            print("Result for part 2 with input ${inputType.name}:  ${part2()}")
        }
        println(" in : $duration")
    }
}

fun main() {
    Day9(InputType.SAMPLE).solvePart1()
    Day9(InputType.ACTUAL).solvePart1()
    Day9(InputType.SAMPLE).solvePart2()
    Day9(InputType.ACTUAL).solvePart2()
}