package jp.atcoder.library.kotlin.maxFlow

/**
 * convert from [AtCoderLibraryForJava - MaxFlow](https://github.com/NASU41/AtCoderLibraryForJava/blob/3d5e128641057adbce8b4a727bba4079b8fa2c02/MinCostFlow/MinCostFlow.java)
 */
class MaxFlow(private val n: Int) {
    inner class CapEdge internal constructor(val from: Int, val to: Int, var cap: Long, val rev: Int)

    private var m = 0
    private val edges: ArrayList<CapEdge> = ArrayList()
    private val count: IntArray = IntArray(n)
    private val g: Array<Array<CapEdge?>> = Array(n) { emptyArray<CapEdge?>() }

    fun addEdge(from: Int, to: Int, cap: Long): Int {
        rangeCheck(from, 0, n)
        rangeCheck(to, 0, n)
        nonNegativeCheck(cap, "Capacity")
        val e = CapEdge(from, to, cap, count[to])
        count[from]++
        count[to]++
        edges.add(e)
        return m++
    }

    fun getEdge(i: Int): CapEdge {
        rangeCheck(i, 0, m)
        return edges[i]
    }

    fun changeEdge(i: Int, newCap: Long, newFlow: Long) {
        rangeCheck(i, 0, m)
        nonNegativeCheck(newCap, "Capacity")
        require(newFlow <= newCap) { String.format("Flow %d is greater than capacity %d.", newCap, newFlow) }
        val e = edges[i]
        val er = g[e.to][e.rev]
        e.cap = newCap - newFlow
        er!!.cap = newFlow
    }

    private fun buildGraph() {
        for (i in 0 until n) {
            g[i] = arrayOfNulls(count[i])
        }
        val idx = IntArray(n)
        for (e in edges) {
            g[e.to][idx[e.to]++] = CapEdge(e.to, e.from, 0, idx[e.from])
            g[e.from][idx[e.from]++] = e
        }
    }

    fun maxFlow(s: Int, t: Int): Long {
        return flow(s, t, INF)
    }

    fun flow(s: Int, t: Int, flowLimit: Long): Long {
        rangeCheck(s, 0, n)
        rangeCheck(t, 0, n)
        buildGraph()
        var flow: Long = 0
        val level = IntArray(n)
        val que = IntArray(n)
        val iter = IntArray(n)
        while (true) {
            java.util.Arrays.fill(level, -1)
            dinicBFS(s, t, level, que)
            if (level[t] < 0) return flow
            java.util.Arrays.fill(iter, 0)
            while (true) {
                val d = dinicDFS(t, s, flowLimit - flow, iter, level)
                if (d <= 0) break
                flow += d
            }
        }
    }

    private fun dinicBFS(s: Int, t: Int, level: IntArray, que: IntArray) {
        var hd = 0
        var tl = 0
        que[tl++] = s
        level[s] = 0
        while (tl > hd) {
            val u = que[hd++]
            for (e in g[u]) {
                val v = e!!.to
                if (e.cap <= 0 || level[v] >= 0) continue
                level[v] = level[u] + 1
                if (v == t) return
                que[tl++] = v
            }
        }
    }

    private fun dinicDFS(cur: Int, s: Int, f: Long, iter: IntArray, level: IntArray): Long {
        if (cur == s) return f
        var res: Long = 0
        while (iter[cur] < count[cur]) {
            val er = g[cur][iter[cur]++]
            val u = er!!.to
            val e = g[u][er.rev]
            if (level[u] >= level[cur] || e!!.cap <= 0) continue
            val d = dinicDFS(u, s, kotlin.math.min(f - res, e.cap), iter, level)
            if (d <= 0) continue
            e.cap -= d
            er.cap += d
            res += d
            if (res == f) break
        }
        return res
    }

    fun fordFulkersonMaxFlow(s: Int, t: Int): Long {
        return fordFulkersonFlow(s, t, INF)
    }

    fun fordFulkersonFlow(s: Int, t: Int, flowLimit: Long): Long {
        rangeCheck(s, 0, n)
        rangeCheck(t, 0, n)
        buildGraph()
        val used = BooleanArray(n)
        var flow: Long = 0
        while (true) {
            java.util.Arrays.fill(used, false)
            val f = fordFulkersonDFS(s, t, flowLimit - flow, used)
            if (f <= 0) return flow
            flow += f
        }
    }

    private fun fordFulkersonDFS(cur: Int, t: Int, f: Long, used: BooleanArray): Long {
        if (cur == t) return f
        used[cur] = true
        for (e in g[cur]) {
            if (used[e!!.to] || e.cap <= 0) continue
            val d = fordFulkersonDFS(e.to, t, kotlin.math.min(f, e.cap), used)
            if (d <= 0) continue
            e.cap -= d
            g[e.to][e.rev]!!.cap += d
            return d
        }
        return 0
    }

    fun minCut(s: Int): BooleanArray {
        rangeCheck(s, 0, n)
        val reachable = BooleanArray(n)
        val stack = IntArray(n)
        var ptr = 0
        stack[ptr++] = s
        reachable[s] = true
        while (ptr > 0) {
            val u = stack[--ptr]
            for (e in g[u]) {
                val v = e!!.to
                if (reachable[v] || e.cap <= 0) continue
                reachable[v] = true
                stack[ptr++] = v
            }
        }
        return reachable
    }

    private fun rangeCheck(i: Int, minInclusive: Int, maxExclusive: Int) {
        if (i < minInclusive || i >= maxExclusive) {
            throw IndexOutOfBoundsException(String.format("Index %d out of bounds for length %d", i, maxExclusive))
        }
    }

    private fun nonNegativeCheck(cap: Long, attribute: String) {
        require(cap >= 0) { String.format("%s %d is negative.", attribute, cap) }
    }

    companion object {
        private const val INF = Long.MAX_VALUE
    }
}
