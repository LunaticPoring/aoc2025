package day8

import InputType
import readAllLines
import kotlin.math.sqrt
import kotlin.time.measureTime


class Day8(private val inputType: InputType) {

    data class Point3D(val x: Double, val y: Double, val z: Double)

    fun distance(p1: Point3D, p2: Point3D): Double {
        return sqrt(
            (p2.x - p1.x).let { it * it } +
                    (p2.y - p1.y).let { it * it } +
                    (p2.z - p1.z).let { it * it }
        )
    }

    data class DistanceEntry(
        val p1: Point3D,
        val p2: Point3D,
        val distance: Double
    )

    fun listUniqueDistances(points: List<Point3D>): List<DistanceEntry> {
        val result = mutableListOf<DistanceEntry>()

        for (i in points.indices) {
            for (j in i + 1 until points.size) {   
                val p1 = points[i]
                val p2 = points[j]
                val d = distance(p1, p2)
                result.add(DistanceEntry(p1, p2, d))
            }
        }

        return result.sortedBy { it.distance }
    }

    private fun part1(): Int {
        
        val lines = readAllLines(inputType, this::class.java.packageName)
        val points = lines.map {
            it.split(",").let { parts ->
                Point3D(parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble())
            }
        }

        var networks = mutableListOf<MutableSet<Point3D>>()

        val distances = listUniqueDistances(points)

        distances.take(if(inputType == InputType.SAMPLE) 10 else 1000).forEach { distance ->
            val p1 = distance.p1
            val p2 = distance.p2

            val networksToMerge = networks.filter { it.contains(p1) || it.contains(p2) }

            val networksWithout = networks.filterNot { it.contains(p1) || it.contains(p2) }

            val mergedSet = mutableSetOf<Point3D>()

            if (networksToMerge.isEmpty()) {
                mergedSet.add(p1)
                mergedSet.add(p2)
                networks.add(mergedSet)

            } else {
                mergedSet.add(p1)
                mergedSet.add(p2)

                networksToMerge.forEach { network ->
                    mergedSet.addAll(network)
                }

                val newNetworksList = networksWithout.toMutableList()
                newNetworksList.add(mergedSet)
                networks = newNetworksList
            }
        }

        return networks.map { it.size }.sortedDescending().take(3).reduce { acc, value -> acc * value }
    }

    data class Point3DInt(val x: Int, val y: Int, val z: Int)

    fun squaredDistance(p1: Point3DInt, p2: Point3DInt): Long {
        val dx = (p2.x - p1.x).toLong()
        val dy = (p2.y - p1.y).toLong()
        val dz = (p2.z - p1.z).toLong()
        return dx * dx + dy * dy + dz * dz
    }


    
    data class LastEdge(val x1: Int, val x2: Int)

    data class Edge(
        val index1: Int,
        val index2: Int,
        val weight: Long 
    )

    private fun part2(points: List<Point3DInt>): Long {
        val N = points.size

        

        
        
        val minConnectionWeight = LongArray(N) { Long.MAX_VALUE }

        
        val parentIndex = IntArray(N) { -1 }

        
        val inMST = BooleanArray(N) { false }

        
        minConnectionWeight[0] = 0L

        
        var maxMSTWeight = 0L
        var lastEdge: LastEdge? = null

        
        for (count in 0 until N) {
            
            var u = -1
            var minWeight = Long.MAX_VALUE

            for (v in 0 until N) {
                if (!inMST[v] && minConnectionWeight[v] < minWeight) {
                    minWeight = minConnectionWeight[v]
                    u = v
                }
            }

            
            if (u == -1) break

            
            inMST[u] = true
            
            if (minConnectionWeight[u] > maxMSTWeight) {
                maxMSTWeight = minConnectionWeight[u]
                
                val p1 = points[u]
                val p2 = points[parentIndex[u]]
                lastEdge = LastEdge(p1.x, p2.x)
            }

            

            
            for (v in 0 until N) {
                
                if (!inMST[v]) {
                    val weight = squaredDistance(points[u], points[v])

                    
                    if (weight < minConnectionWeight[v]) {
                        minConnectionWeight[v] = weight
                        parentIndex[v] = u
                    }
                }
            }
        }

        
        val finalEdge = lastEdge ?: throw IllegalStateException("MST could not be formed.")

        
        return finalEdge.x1.toLong() * finalEdge.x2.toLong()
    }

    
    private fun part2(): Long {
        
        val lines = readAllLines(inputType, this::class.java.packageName)
        val points = lines.map {
            it.split(",").let { parts ->
                Point3DInt(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            }
        }
        return part2(points)
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
    Day8(InputType.SAMPLE).solvePart1()
    Day8(InputType.ACTUAL).solvePart1()

    Day8(InputType.SAMPLE).solvePart2()
    Day8(InputType.ACTUAL).solvePart2()
}