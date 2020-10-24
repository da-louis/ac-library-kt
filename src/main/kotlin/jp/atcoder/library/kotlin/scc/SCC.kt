package jp.atcoder.library.kotlin.scc

/**
 * convert from [AtCoderLibraryForJava - SCC](https://github.com/NASU41/AtCoderLibraryForJava/blob/ee794a298f6d16ab24bd9316e7cae8a9155510e5/SCC/SCC.java)
 */
class SCC(private val n: Int) {
    internal class Edge(var from: Int, var to: Int)

    private var m = 0
    private val unorderedEdges: ArrayList<Edge> = ArrayList()
    private val start: IntArray = IntArray(n + 1)
    private val ids: IntArray = IntArray(n)
    private var hasBuilt = false

    fun addEdge(from: Int, to: Int) {
        rangeCheck(from)
        rangeCheck(to)
        unorderedEdges.add(Edge(from, to))
        start[from + 1]++
        m++
    }

    fun id(i: Int): Int {
        if (!hasBuilt) {
            throw UnsupportedOperationException(
                "Graph hasn't been built."
            )
        }
        rangeCheck(i)
        return ids[i]
    }

    fun build(): Array<IntArray> {
        for (i in 1..n) {
            start[i] += start[i - 1]
        }
        val orderedEdges = arrayOfNulls<Edge>(m)
        val count = IntArray(n + 1)
        System.arraycopy(start, 0, count, 0, n + 1)
        for (e in unorderedEdges) {
            orderedEdges[count[e.from]++] = e
        }
        var nowOrd = 0
        var groupNum = 0
        var k = 0
        // parent
        val par = IntArray(n)
        val vis = IntArray(n)
        val low = IntArray(n)
        val ord = IntArray(n)
        java.util.Arrays.fill(ord, -1)
        // u = lower32(stack[i]) : visiting vertex
        // j = upper32(stack[i]) : jth child
        val stack = LongArray(n)
        // size of stack
        var ptr = 0
        // non-recursional DFS
        for (i in 0 until n) {
            if (ord[i] >= 0) continue
            par[i] = -1
            // vertex i, 0th child.
            stack[ptr++] = 0L shl 32 or i.toLong()
            // stack is not empty
            while (ptr > 0) {
                // last element
                val p = stack[--ptr]
                // vertex
                val u = (p and 0xffffffffL).toInt()
                // jth child
                var j = (p ushr 32).toInt()
                if (j == 0) { // first visit
                    ord[u] = nowOrd++
                    low[u] = ord[u]
                    vis[k++] = u
                }
                if (start[u] + j < count[u]) { // there are more children
                    // jth child
                    val to = orderedEdges[start[u] + j]!!.to
                    // incr children counter
                    stack[ptr++] += 1L shl 32
                    if (ord[to] == -1) { // new vertex
                        stack[ptr++] = 0L shl 32 or to.toLong()
                        par[to] = u
                    } else { // backward edge
                        low[u] = kotlin.math.min(low[u], ord[to])
                    }
                } else { // no more children (leaving)
                    while (j-- > 0) {
                        val to = orderedEdges[start[u] + j]!!.to
                        // update lowlink
                        if (par[to] == u) low[u] = kotlin.math.min(low[u], low[to])
                    }
                    if (low[u] == ord[u]) { // root of a component
                        while (true) { // gathering verticies
                            val v = vis[--k]
                            ord[v] = n
                            ids[v] = groupNum
                            if (v == u) break
                        }
                        groupNum++ // incr the number of components
                    }
                }
            }
        }
        for (i in 0 until n) {
            ids[i] = groupNum - 1 - ids[i]
        }
        val counts = IntArray(groupNum)
        for (x in ids) counts[x]++
        val groups = Array(groupNum) { intArrayOf() }
        for (i in 0 until groupNum) {
            groups[i] = IntArray(counts[i])
        }
        for (i in 0 until n) {
            val cmp = ids[i]
            groups[cmp][--counts[cmp]] = i
        }
        hasBuilt = true
        return groups
    }

    private fun rangeCheck(i: Int) {
        if (i < 0 || i >= n) {
            throw IndexOutOfBoundsException(String.format("Index %d out of bounds for length %d", i, n))
        }
    }

}
