package template

import InputType
import readAllLines


class Template(private val inputType: InputType) {

    private fun part1(): Long {
        return 0
    }

    private fun part2(): Long {
        return 0
    }

    private fun parsedLines(): Pair<List<Long>, List<Long>> {
        val lines = readAllLines(inputType, this::class.java.packageName)
        val (list1, list2) = lines.map { it.split("   ") }
            .map { it.first().toLong() to it[1].toLong() }
            .unzip()
        return list1 to list2
    }

    fun solvePart1() {
        val start = System.currentTimeMillis()
        println("Result for part 1 with input ${inputType.name}:  ${part1()} in ${System.currentTimeMillis() - start}ms")
    }

    fun solvePart2() {
        val start = System.currentTimeMillis()
        println("Result for part 2 with input ${inputType.name}:  ${part2()} in ${System.currentTimeMillis() - start}ms")
    }
}


fun main() {
    Template(InputType.SAMPLE).solvePart1()
    Template(InputType.ACTUAL).solvePart1()

    Template(InputType.SAMPLE).solvePart2()
    Template(InputType.ACTUAL).solvePart2()
}