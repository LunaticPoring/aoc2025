package day2

import InputType
import readAllFile


fun String.splitAtIndex(index : Int) = take(index) to substring(index)

class Day2(private val inputType: InputType) {



    private fun part1(): Long {
        val input = readAllFile(inputType, this::class.java.packageName)
        val ranges = input.split(',').map { range -> range.split('-').let { it.first().toLong()..it[1].toLong() } }
        return ranges.sumOf {
            var intermediateSum = 0L
            for(currentNumber in it) {
                val currentNumberAsString = currentNumber.toString()
                if (currentNumberAsString.length%2 == 0) {
                    val middle = currentNumberAsString.length / 2
                    val first = currentNumberAsString.substring(0, middle)
                    val second = currentNumberAsString.substring(middle)
                    if (first == second) {
                        intermediateSum += currentNumber
                    }
                }
            }
            intermediateSum
        }
    }

    fun divisors(n: Int): List<Int> {
        return (2..n).filter { n % it == 0 }
    }


    private fun part2(): Long {
        val input = readAllFile(inputType, this::class.java.packageName)
        val ranges = input.split(',').map { range -> range.split('-').let { it.first().toLong()..it[1].toLong() } }
        return ranges.sumOf {
            var intermediateSum = 0L
            for(currentNumber in it) {
                val currentNumberAsString = currentNumber.toString()
                val numberLength = currentNumberAsString.length

                val divisors = divisors(numberLength)

                if(divisors.any {
                    val blockSize = numberLength / it
                        var previousBlock = currentNumberAsString.substring(0, blockSize)
                        var i = blockSize
                        var hasDiffs = false;
                        while (i < currentNumberAsString.length) {
                            if (currentNumberAsString.substring(i, minOf(i + blockSize, currentNumberAsString.length)) != previousBlock) {
                                hasDiffs = true
                                break;
                            }
                            i += blockSize
                        }
                        !hasDiffs

                }) {
                    //println(currentNumber)
                    intermediateSum += currentNumber
                }

            }
            intermediateSum
        }
    }



    fun solvePart1() {
        println("Result for part 1 with input ${inputType.name}:  ${part1()}")
    }

    fun solvePart2() {
        val start= System.currentTimeMillis()
        println("Result for part 2 with input ${inputType.name}:  ${part2()}")
        val intermediate = System.currentTimeMillis()
        println("That took ${start - intermediate}ms")
    }


}


fun main() {
//    Day2(InputType.SAMPLE).solvePart1()
//   Day2(InputType.ACTUAL).solvePart1()
//
//    Day2(InputType.SAMPLE).solvePart2()
    Day2(InputType.ACTUAL).solvePart2()


}