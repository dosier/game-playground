package world.gregs.game.playground.pathfinding.astar


import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import java.lang.reflect.Array
import java.util.*
import kotlin.math.floor
import kotlin.math.ln

class BinaryHashHeap<T : Comparable<T>>(type: Class<T>, maxSize: Int) : Queue<T> {

    private val heap = Array.newInstance(type, maxSize + 1) as kotlin.Array<T>
    private var heapCount: Int = 0
    private val hash: Object2IntOpenHashMap<T> = Object2IntOpenHashMap(maxSize)

    override val size: Int
        get() = heapCount

    override fun isEmpty(): Boolean {
        return heapCount == 0
    }

    override fun contains(element: T): Boolean {
        for (i in 1..heapCount / 2) {
            if (heap[i] == element)
                return true
            if (heap[heapCount - i] == element)
                return true
        }
        return heap[heapCount] == element
    }

    override fun clear() {
        heapCount = 0
    }

    override fun add(element: T): Boolean {
        heapCount++

        heap[heapCount] = element

        hash[element] = heapCount
        sortUp(heapCount)
        return true
    }

    override fun offer(e: T): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun remove(): T {
        val item = heap[1]
        heap[1] = heap[heapCount]
        hash[heap[1]] = 1

        sortDown()

        heapCount--
        return item
    }

    override fun poll(): T? {
        // TODO Auto-generated method stub
        return null
    }

    override fun element(): T? {
        // TODO Auto-generated method stub
        return null
    }

    override fun peek(): T {
        return heap[1]
    }

    private fun sortUp(index: Int) {
        var index = index

        while (index > 1) {
            val c = heap[index].compareTo(heap[index / 2])

            if (c < 0) {
                val temp = heap[index / 2]
                heap[index / 2] = heap[index]
                hash[heap[index / 2]] = index
                heap[index] = temp
                hash[temp] = index
                index /= 2
            } else {
                break
            }
        }

    }

    private fun sortDown() {
        var i = 1

        while (true) {
            val u = i

            if (u * 2 + 1 <= heapCount) {
                if (heap[u] >= heap[u * 2])
                    i = 2 * u
                if (heap[i] >= heap[2 * u + 1])
                    i = 2 * u + 1
            } else if (u * 2 <= heapCount) {
                if (heap[u] >= heap[u * 2])
                    i = 2 * u
            }

            if (u != i) {
                val temp = heap[u]
                heap[u] = heap[i]
                hash[heap[i]] = u
                heap[i] = temp
                hash[temp] = i
            } else {
                break
            }
        }

    }

    fun update(e: T) {
        if (heap[hash.getInt(e)] == e) {
            sortUp(hash.getInt(e))
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        var depth = 0
        var width = 20
        for (i in 1..heapCount) {
            if (depth(i) > depth) {
                sb.append('\n')
                depth = depth(i)
                width /= 2
            }
            sb.append(pad(width)).append(heap[i]).append(pad(width))
        }

        return sb.toString()
    }

    private fun pad(length: Int): String {
        val sb = StringBuilder()
        for (i in 0 until length)
            sb.append(' ')
        return sb.toString()
    }

    private fun depth(index: Int): Int {
        return floor(log2(index)).toInt()
    }

    companion object {

        fun log2(n: Int): Double {
            return ln(n.toDouble()) / ln(2.0)
        }
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(elements: Collection<T>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): MutableIterator<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

