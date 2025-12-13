package template

import InputType
import readAllLines
import kotlin.time.measureTime


class Template(private val inputType: InputType) {

    private fun part1(): Long {
        return 0
    }

    private fun part2(): Long {
        return 0
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
    Template(InputType.SAMPLE).solvePart1()
    Template(InputType.ACTUAL).solvePart1()

    Template(InputType.SAMPLE).solvePart2()
    Template(InputType.ACTUAL).solvePart2()
}