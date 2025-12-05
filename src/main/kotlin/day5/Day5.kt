package day5

import InputType
import readAllFile
import readAllLines
import kotlin.system.measureNanoTime
import kotlin.time.measureTime


class Day5(private val inputType: InputType) {
    fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
        if (ranges.isEmpty()) return emptyList()

        val sorted = ranges.sortedBy { it.first }

        val result = mutableListOf<LongRange>()
        var current = sorted[0]

        for (range in sorted.drop(1)) {
            if (range.first <= current.last + 1) {
                current = current.first..maxOf(current.last, range.last)
            } else {
                result.add(current)
                current = range
            }
        }
        result.add(current)
        return result
    }

    private fun part1(): Int {
        val content = readAllFile(inputType, this::class.java.packageName)
        val (rangesBlock, ingredients) = content.split("\r\n\r\n")

        val ranges = mergeRanges(rangesBlock.lines().map { it.split("-").let { it[0].toLong()..it[1].toLong() } })

        return ingredients.lines().count { ingredient -> ranges.any { ingredient.toLong() in it } }

    }


    private fun part2(): Long {
        val content = readAllFile(inputType, this::class.java.packageName)
        val (rangesBlock) = content.split("\r\n\r\n")

        val baseRanges = rangesBlock.lines().map {
            it.split("-").let {
                it[0].toLong()..it[1].toLong()
            }
        }
        val mergedRanges = mergeRanges(baseRanges)

        return mergedRanges.sumOf { it.endInclusive - it.start + 1 }
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
    Day5(InputType.SAMPLE).solvePart1()
    Day5(InputType.ACTUAL).solvePart1()

    Day5(InputType.SAMPLE).solvePart2()
    Day5(InputType.ACTUAL).solvePart2()
}