package jp.atcoder.library.kotlin.minCostFlow

/**
 * convert from [AtCoderLibraryForJava - MinCostFlow](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/MinCostFlow/MinCostFlow.java)
 */
class MinCostFlow(private val n: Int) {
    inner class WeightedCapEdge internal constructor(
        val from: Int,
        val to: Int,
        var cap: Long,
        var cost: Long,
        val rev: Int
    )

    private var m = 0
    private val edges: ArrayList<WeightedCapEdge> = ArrayList()
    private val count: IntArray = IntArray(n)
    private val g: Array<Array<WeightedCapEdge?>> = Array(n) { emptyArray<WeightedCapEdge?>() }
    private val potential: LongArray = LongArray(n)
    private val dist: LongArray = LongArray(n)
    private val prev: Array<WeightedCapEdge?> = arrayOfNulls(n)

    fun addEdge(from: Int, to: Int, cap: Long, cost: Long): Int {
        rangeCheck(from, 0, n)
        rangeCheck(to, 0, n)
        nonNegativeCheck(cap, "Capacity")
        nonNegativeCheck(cost, "Cost")
        val e = WeightedCapEdge(from, to, cap, cost, count[to])
        count[from]++
        count[to]++
        edges.add(e)
        return m++
    }

    private fun buildGraph() {
        for (i in 0 until n) {
            g[i] = arrayOfNulls(count[i])
        }
        val idx = IntArray(n)
        for (e in edges) {
            g[e.to][idx[e.to]++] = WeightedCapEdge(e.to, e.from, 0, -e.cost, idx[e.from])
            g[e.from][idx[e.from]++] = e
        }
    }

    private var addFlow: Long = 0
    private var addCost: Long = 0
    fun minCostMaxFlow(s: Int, t: Int): LongArray {
        return minCostFlow(s, t, INF)
    }

    fun minCostFlow(s: Int, t: Int, flowLimit: Long): LongArray {
        rangeCheck(s, 0, n)
        rangeCheck(t, 0, n)
        require(s != t) { String.format("s = t = %d", s) }
        nonNegativeCheck(flowLimit, "Flow")
        buildGraph()
        var flow: Long = 0
        var cost: Long = 0
        while (true) {
            dijkstra(s, t, flowLimit - flow)
            if (addFlow == 0L) break
            flow += addFlow
            cost += addFlow * addCost
        }
        return longArrayOf(flow, cost)
    }

    @JvmOverloads
    fun minCostSlope(s: Int, t: Int, flowLimit: Long = INF): ArrayList<LongArray> {
        rangeCheck(s, 0, n)
        rangeCheck(t, 0, n)
        require(s != t) { String.format("s = t = %d", s) }
        nonNegativeCheck(flowLimit, "Flow")
        buildGraph()
        val slope = ArrayList<LongArray>()
        var prevCost: Long = -1
        var flow: Long = 0
        var cost: Long = 0
        while (true) {
            slope.add(longArrayOf(flow, cost))
            dijkstra(s, t, flowLimit - flow)
            if (addFlow == 0L) return slope
            flow += addFlow
            cost += addFlow * addCost
            if (addCost == prevCost) {
                slope.removeAt(slope.size - 1)
            }
            prevCost = addCost
        }
    }

    private fun dijkstra(s: Int, t: Int, maxFlow: Long) {
        class State(val v: Int, val d: Long) : Comparable<State> {
            override operator fun compareTo(other: State): Int {
                return if (d == other.d) v - other.v else if (d > other.d) 1 else -1
            }
        }
        java.util.Arrays.fill(dist, INF)
        dist[s] = 0
        val pq = java.util.PriorityQueue<State>()
        pq.add(State(s, 0L))
        while (pq.size > 0) {
            val st = pq.poll()
            val u = st.v
            if (st.d != dist[u]) continue
            for (e in g[u]) {
                if (e!!.cap <= 0) continue
                val v = e.to
                val nextCost = dist[u] + e.cost + potential[u] - potential[v]
                if (nextCost < dist[v]) {
                    dist[v] = nextCost
                    prev[v] = e
                    pq.add(State(v, dist[v]))
                }
            }
        }
        if (dist[t] == INF) {
            addFlow = 0
            addCost = INF
            return
        }
        for (i in 0 until n) {
            potential[i] += dist[i]
        }
        addCost = 0
        addFlow = maxFlow
        run {
            var v = t
            while (v != s) {
                val e = prev[v]
                addCost += e!!.cost
                addFlow = Math.min(addFlow, e.cap)
                v = e.from
            }
        }
        var v = t
        while (v != s) {
            val e = prev[v]
            e!!.cap -= addFlow
            g[v][e!!.rev]!!.cap += addFlow
            v = e!!.from
        }
    }

    fun clearFlow() {
        java.util.Arrays.fill(potential, 0)
        for (e in edges) {
            val flow = e.cap
            e.cap += flow
            g[e.to][e.rev]!!.cap -= flow
        }
    }

    fun getEdge(i: Int): WeightedCapEdge {
        rangeCheck(i, 0, m)
        return edges[i]
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
