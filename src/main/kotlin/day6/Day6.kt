package day6

import InputType
import readAllLines
import kotlin.time.measureTime


class Day6(private val inputType: InputType) {

    inline fun <T> Iterable<T>.sumOfIndexed(selector: (index: Int, value: T) -> Long): Long {
        var sum = 0L
        var i = 0
        for (element in this) {
            sum += selector(i++, element)
        }
        return sum
    }

    fun String.splitWithMultipleSpaces() = this.trim().split(Regex("\\s+"))
    private fun part1(): Long {
        val lines = readAllLines(inputType, this::class.java.packageName)
        val horizontalOperands = lines.dropLast(1)
        val operations = lines.last().splitWithMultipleSpaces()

        val numberOfOperations = horizontalOperands.first().splitWithMultipleSpaces().size
        val operands = MutableList(numberOfOperations) { mutableListOf<Long>() }

        horizontalOperands.forEach {
            it.splitWithMultipleSpaces().forEachIndexed { index, operand ->
                operands[index] += operand.toLong()
            }
        }


        return operands.sumOfIndexed { index, operand ->
            val res = operand.drop(1).fold(operand.first()) { acc, element ->

                if (operations[index] == "*") acc * element
                else acc + element
            }
            res
        }
    }

    private fun part2(): Long {
        val lines = readAllLines(inputType, this::class.java.packageName)
        val horizontalOperands = lines.dropLast(1)
        val operations = lines.last().splitWithMultipleSpaces()


        val allChars = horizontalOperands.map { it.split("").filter { it.isNotEmpty() }.reversed() }

        var totalSum = 0L
        var currentOperationIndex = operations.size - 1
        var currentResult: Long? = null
        for (i in 0..< allChars.first().size) {
            val currentNumber = allChars.joinToString("") { it[i] }.trim().toLongOrNull()
            if (currentNumber == null) {
                totalSum += currentResult ?: 0
                currentOperationIndex--
                currentResult = null
            } else if (currentResult == null) {
                currentResult = currentNumber
            } else if (operations[currentOperationIndex] == "*") {
                currentResult *= currentNumber
            } else {
                currentResult += currentNumber
            }
        }
        totalSum += currentResult ?: 0

        return totalSum
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
    Day6(InputType.SAMPLE).solvePart1()
    Day6(InputType.ACTUAL).solvePart1()

    Day6(InputType.SAMPLE).solvePart2()
    Day6(InputType.ACTUAL).solvePart2()
}