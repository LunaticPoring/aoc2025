package day1

import InputType
import readAllLines
import kotlin.math.floor


class Day1(private val inputType: InputType) {

    private fun part1(): Long {
        val lines = readAllLines(inputType, this::class.java.packageName)

        var currentLockNumber = 50L
        var zeroCount = 0L
        lines.forEach {
            val shift = it.substring(1).toLong() % 100
            if (it.first() == 'L') {
                //println("$currentLockNumber - $shift = ")
                currentLockNumber -= shift
                //println(currentLockNumber)
            } else {
                //println("$currentLockNumber + $shift = ")
                currentLockNumber += shift
                //println(currentLockNumber)
            }
            if (currentLockNumber < 0) {
                currentLockNumber = 100 + currentLockNumber
            } else if (currentLockNumber > 99) {
                currentLockNumber -= 100
            }
            println(currentLockNumber)
            if (currentLockNumber == 0L) {
                zeroCount++
            }
        }
        return zeroCount
    }

    private fun part2(): Long {
        val lines = readAllLines(inputType, this::class.java.packageName)

        var currentLockNumber = 50L
        var zeroCount = 0L
        lines.forEach {
            val daNumber = it.substring(1).toLong()
            val shift = daNumber % 100
            val numberOfZeros = floor(daNumber / 100.0)
            println("Number of zeros $numberOfZeros")
            zeroCount+=numberOfZeros.toLong()
            println("Actual shift is $shift")
            val wasOnZero = currentLockNumber == 0L
            if (it.first() == 'L') {
                //println("$currentLockNumber - $shift = ")
                currentLockNumber -= shift
                //println(currentLockNumber)
            } else {
                //println("$currentLockNumber + $shift = ")
                currentLockNumber += shift
                //println(currentLockNumber)
            }
            if (currentLockNumber < 0) {
                currentLockNumber = 100 + currentLockNumber
                if (!wasOnZero) {
                    zeroCount++
                }
            } else if (currentLockNumber > 99) {
                currentLockNumber -= 100
                if (!wasOnZero) {
                    zeroCount++
                }
            } else if (currentLockNumber == 0L) {
                zeroCount++
            }
            println(currentLockNumber)
        }
        return zeroCount
    }


    fun solvePart1() {
        println("Result for part 1 with input ${inputType.name}:  ${part1()}")
    }

    fun solvePart2() {
        println("Result for part 2 with input ${inputType.name}:  ${part2()}")
    }
}


fun main() {
//    Day1(InputType.SAMPLE).solvePart1()
//    Day1(InputType.ACTUAL).solvePart1()

    Day1(InputType.SAMPLE).solvePart2()
    Day1(InputType.ACTUAL).solvePart2()
}