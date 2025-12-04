package day4

import InputType
import day4.Day4.CellContent.PaperStack
import readAllLines


class Day4(private val inputType: InputType) {

    sealed class CellContent {
        class PaperStack(val x: Int, val y: Int, var toCheck: Boolean = true) : CellContent() {
            fun isAccessible(grid: List<List<CellContent>>): Boolean {
                val directions = listOf(
                    Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
                    Pair(0, -1),              Pair(0, 1),
                    Pair(1, -1),  Pair(1, 0), Pair(1, 1)
                )

                var count = 0

                for ((dx, dy) in directions) {
                    val nx = x + dx
                    val ny = y + dy

                    if (nx in grid.indices && ny in grid[0].indices) {
                        if (grid[nx][ny] is PaperStack) {
                            count++
                        }
                    }
                }

                //println("$x $y has $count adjacents")
                return count < 4

            }
            fun adjacentPaperStacks(grid: List<List<CellContent>>): List<PaperStack> {
                val directions = listOf(
                    Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
                    Pair(0, -1),              Pair(0, 1),
                    Pair(1, -1),  Pair(1, 0), Pair(1, 1)
                )

                val result = mutableListOf<PaperStack>()

                for ((dx, dy) in directions) {
                    val nx = x + dx
                    val ny = y + dy

                    if (nx in grid.indices && ny in grid[nx].indices) {
                        val cell = grid[nx][ny]
                        if (cell is PaperStack) {
                            result += cell
                        }
                    }
                }
                return result
            }
        }

        data object Empty : CellContent()
    }

    private fun part1(): Int {
        val grid = parseGrid()
        return   grid.sumOf {
            it.filter {
                it is PaperStack && it.isAccessible(grid)
            }.size
        }
    }

    private fun parseGrid(): MutableList<MutableList<CellContent>> {
        val grid = mutableListOf<MutableList<CellContent>>()
        val lines = readAllLines(inputType, this::class.java.packageName)
        lines.forEachIndexed { lineIndex, line ->
            val row = mutableListOf<CellContent>()
            line.forEachIndexed { rowIndex, cell ->
                if (cell == '@') {
                    row += PaperStack(lineIndex, rowIndex)
                } else if (cell == '.') {
                    row += CellContent.Empty
                }
            }
            grid += row
        }
        return grid
    }

    private fun part2(): Int {
        val grid = parseGrid()
        val allPaperStacks = grid.flatten()
        var paperStacksToCheck = allPaperStacks.filterIsInstance<PaperStack>()
        var removedPaperStack = 0

        while (paperStacksToCheck.isNotEmpty()) {
            val newlyPaperStacksToRemove = paperStacksToCheck.filter {
                it.isAccessible(grid)
            }
            removedPaperStack += newlyPaperStacksToRemove.size
            newlyPaperStacksToRemove.forEach {
                grid[it.x][it.y] = CellContent.Empty
            }
            paperStacksToCheck = newlyPaperStacksToRemove.flatMap { it.adjacentPaperStacks(grid) }.distinct()

//            println(grid.forEach {
//                println(it.map { if (it is PaperStack) '@' else '.' }.joinToString(""))
//            })

        }
        return removedPaperStack
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
    Day4(InputType.SAMPLE).solvePart1()
    Day4(InputType.ACTUAL).solvePart1()

    Day4(InputType.SAMPLE).solvePart2()
    Day4(InputType.ACTUAL).solvePart2()
}