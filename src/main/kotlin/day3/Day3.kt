package day3

import InputType
import readAllLines


class Day3(private val inputType: InputType) {

    private fun part1(): Long {
        val batteryBanks =  readAllLines(inputType, this::class.java.packageName)
        return batteryBanks.sumOf { batteryBank ->
            var biggest : Int? = null
            var secondBiggest : Int? = null
            batteryBank.forEachIndexed {  index, batteryJoltage ->
                if (index < batteryBank.length - 1 && (biggest == null || batteryJoltage.digitToInt() > biggest!!)) {
                    if (biggest != null) {
                        secondBiggest = null
                    }
                    biggest = batteryJoltage.digitToInt()
                } else  if (secondBiggest == null || batteryJoltage.digitToInt() > secondBiggest!!) {
                    secondBiggest = batteryJoltage.digitToInt()
                }
            }
            (biggest.toString() + secondBiggest.toString()).toLong()
        }
    }

    private fun part2(): Long {
        val batteryBanks =  readAllLines(inputType, this::class.java.packageName)
        return batteryBanks.sumOf { batteryBank ->
            val biggests = mutableListOf<Int?>(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,)
            batteryBank.forEachIndexed {  index, batteryJoltage ->

                val currentJoltage = batteryJoltage.digitToInt()

                for ((biggestIndex, biggest) in biggests.withIndex()) {
                    if (batteryBank.length - index >=  biggests.size - biggestIndex &&
                        (biggest == null || currentJoltage > biggest)) {

                        biggests[biggestIndex] = currentJoltage
                        for (i in biggestIndex + 1..<biggests.size) {
                            biggests[i] = null
                        }
                        break
                    }
                }
            }
            val lineVoltage = biggests.joinToString("").toLong()
            lineVoltage
        }
    }


    fun solvePart1() {
        val start = System.currentTimeMillis()
        println("Result for part 1 with input ${inputType.name}:  ${part1()} in ${System.currentTimeMillis() - start}ms")
    }

    fun solvePart2() {
        val start = System.currentTimeMillis()
        println("Result for part 2 with input ${inputType.name}:  ${part2()} in ${System.currentTimeMillis() - start }ms")
    }
}


fun main() {
    Day3(InputType.SAMPLE).solvePart1()
    Day3(InputType.ACTUAL).solvePart1()

    Day3(InputType.SAMPLE).solvePart2()
    Day3(InputType.ACTUAL).solvePart2()
}