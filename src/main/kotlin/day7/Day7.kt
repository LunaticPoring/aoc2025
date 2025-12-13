package day7

import InputType
import readAllLines
import kotlin.time.measureTime


class Day7(private val inputType: InputType) {


    sealed class CellContent {
        data object Start : CellContent()
        data object Splitter : CellContent()
        data object Empty : CellContent()

        companion object {
            fun fromChar(c: Char): CellContent = when (c) {
                'S' -> Start
                '^' -> Splitter
                '.' -> Empty
                else -> error("Unknown cell type: $c")
            }
        }
    }

    fun List<List<CellContent>>.safeGet(r: Int, c: Int): CellContent? =
        getOrNull(r)?.getOrNull(c)

    private fun part1(): Int {
        val lines = readAllLines(inputType, this::class.java.packageName)

        val matrix: List<List<CellContent>> =
            lines.map { line -> line.map(CellContent::fromChar) }

        val startPos: Pair<Int, Int> = matrix
            .withIndex()
            .firstNotNullOf { (rowIndex, row) ->
                row.withIndex()
                    .firstOrNull { (_, cell) -> cell == CellContent.Start }
                    ?.let { (colIndex, _) -> rowIndex to colIndex }
            }

        // Start just below S
        var beamsToHandle = mutableSetOf(startPos.first + 1 to startPos.second)

        var reachedSplitters = 0
        while (beamsToHandle.isNotEmpty()) {
            val maintainedBeams = mutableSetOf<Pair<Int, Int>>()
            val newSplitBeams = mutableSetOf<Pair<Int, Int>>()

            beamsToHandle.forEach { (row, column) ->
                val down = row + 1

                when (matrix.safeGet(down, column)) {
                    is CellContent.Splitter -> {
                        reachedSplitters++
                        if (matrix.safeGet(down, column - 1) is CellContent.Empty) {
                            newSplitBeams += (down to (column - 1))
                        }
                        if (matrix.safeGet(down, column + 1) is CellContent.Empty) {
                            newSplitBeams += (down to (column + 1))
                        }
                    }

                    is CellContent.Empty -> {
                        // continue downward
                        maintainedBeams += (down to column)
                    }

                    null, CellContent.Start -> { /* ignore */ }
                }
            }

            beamsToHandle = (maintainedBeams + newSplitBeams).toMutableSet()
        }
        return reachedSplitters
    }


    data class Graph(
        val edges: MutableMap<Node, MutableSet<Node>> = mutableMapOf()
    ) {
        fun addEdge(from: Node, to: Node) {
            edges.computeIfAbsent(from) { mutableSetOf() }.add(to)
        }
    }

    private fun buildGraph(): Graph {
        val lines = readAllLines(inputType, this::class.java.packageName)
        val matrix: List<List<CellContent>> =
            lines.map { line -> line.map(CellContent::fromChar) }

        val startPos: Node = matrix
            .withIndex()
            .firstNotNullOf { (rowIndex, row) ->
                row.withIndex()
                    .firstOrNull { (_, cell) -> cell == CellContent.Start }
                    ?.let { (colIndex, _) -> rowIndex to colIndex }
            }

        val graph = Graph()
        val visited = mutableSetOf<Node>()

        // We now start from the *start position itself*
        var frontier = mutableSetOf(startPos)

        while (frontier.isNotEmpty()) {
            val nextFrontier = mutableSetOf<Node>()

            for (node in frontier) {
                if (!visited.add(node)) continue

                val (row, col) = node
                val down = row + 1
                val below = matrix.safeGet(down, col)

                when (below) {
                    is CellContent.Empty -> {
                        val next = down to col
                        graph.addEdge(node, next)
                        nextFrontier += next
                    }

                    is CellContent.Splitter -> {
                        // left beam
                        if (matrix.safeGet(down, col - 1) is CellContent.Empty) {
                            val left = down to (col - 1)
                            graph.addEdge(node, left)
                            nextFrontier += left
                        }

                        // right beam
                        if (matrix.safeGet(down, col + 1) is CellContent.Empty) {
                            val right = down to (col + 1)
                            graph.addEdge(node, right)
                            nextFrontier += right
                        }
                    }

                    null -> { /* ignore */ }
                    CellContent.Start -> { /* start should not point into itself */ }
                }
            }

            frontier = nextFrontier
        }

        return graph
    }



    fun countPaths(graph: Graph, start: Node): Long {
        val memo = mutableMapOf<Node, Long>()

        fun dfs(node: Node): Long =
            memo.getOrPut(node) {
                val children = graph.edges[node]
                if (children == null || children.isEmpty()) {
                    1L                        // leaf = 1 path
                } else {
                    children.sumOf { dfs(it) } // sum of children’s paths
                }
            }

        return dfs(start)
    }


    private fun part2(): Long {
        val lines = readAllLines(inputType, this::class.java.packageName)

        val matrix: List<List<CellContent>> =
            lines.map { line -> line.map(CellContent::fromChar) }

        val startPos: Pair<Int, Int> = matrix
            .withIndex()
            .firstNotNullOf { (rowIndex, row) ->
                row.withIndex()
                    .firstOrNull { (_, cell) -> cell == CellContent.Start }
                    ?.let { (colIndex, _) -> rowIndex to colIndex }
            }

        val graph = buildGraph()

        val pathCount = countPaths(graph, startPos)
        return pathCount
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

typealias Node = Pair<Int, Int>


fun main() {
    Day7(InputType.SAMPLE).solvePart1()
    Day7(InputType.ACTUAL).solvePart1()

    Day7(InputType.SAMPLE).solvePart2()
    Day7(InputType.ACTUAL).solvePart2()
}