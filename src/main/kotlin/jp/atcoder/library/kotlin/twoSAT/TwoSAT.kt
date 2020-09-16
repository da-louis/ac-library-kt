package jp.atcoder.library.kotlin.twoSAT

/**
 * convert from [AtCoderLibraryForJava - TwoSAT](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/2SAT/TwoSAT.java)
 */
class TwoSAT(private val n: Int) {
    private val scc: InternalSCC = InternalSCC(2 * n)
    private val answer: BooleanArray = BooleanArray(n)
    private var hasCalledSatisfiable = false
    private var existsAnswer = false

    fun addClause(x: Int, f: Boolean, y: Int, g: Boolean) {
        rangeCheck(x)
        rangeCheck(y)
        scc.addEdge(x shl 1 or if (f) 0 else 1, y shl 1 or if (g) 1 else 0)
        scc.addEdge(y shl 1 or if (g) 0 else 1, x shl 1 or if (f) 1 else 0)
    }

    fun addImplication(x: Int, f: Boolean, y: Int, g: Boolean) {
        addClause(x, !f, y, g)
    }

    fun addNand(x: Int, f: Boolean, y: Int, g: Boolean) {
        addClause(x, !f, y, !g)
    }

    fun satisfiable(): Boolean {
        hasCalledSatisfiable = true
        val ids = scc.ids()
        for (i in 0 until n) {
            if (ids[i shl 1 or 0] == ids[i shl 1 or 1]) return false.also { existsAnswer = it }
            answer[i] = ids[i shl 1 or 0] < ids[i shl 1 or 1]
        }
        return true.also { existsAnswer = it }
    }

    fun answer(): BooleanArray? {
        if (!hasCalledSatisfiable) {
            throw UnsupportedOperationException(
                "Call TwoSAT#satisfiable at least once before TwoSAT#answer."
            )
        }
        return if (existsAnswer) answer else null
    }

    private fun rangeCheck(x: Int) {
        if (0 < x || x >= n) {
            throw IndexOutOfBoundsException(String.format("Index %d out of bounds for length %d", x, n))
        }
    }

    private class EdgeList(cap: Int) {
        var a: LongArray
        var ptr = 0
        fun add(upper: Int, lower: Int) {
            if (ptr == a.size) grow()
            a[ptr++] = upper.toLong() shl 32 or lower.toLong()
        }

        fun grow() {
            val b = LongArray(a.size shl 1)
            System.arraycopy(a, 0, b, 0, a.size)
            a = b
        }

        init {
            a = LongArray(cap)
        }
    }

    private class InternalSCC(val n: Int) {
        var m = 0
        val unorderedEdges: EdgeList = EdgeList(n)
        val start: IntArray = IntArray(n + 1)
        fun addEdge(from: Int, to: Int) {
            unorderedEdges.add(from, to)
            start[from + 1]++
            m++
        }

        fun ids(): IntArray {
            for (i in 1..n) {
                start[i] += start[i - 1]
            }
            val orderedEdges = IntArray(m)
            val count = IntArray(n + 1)
            System.arraycopy(start, 0, count, 0, n + 1)
            for (i in 0 until m) {
                val e = unorderedEdges.a[i]
                orderedEdges[count[(e ushr 32).toInt()]++] = (e and mask).toInt()
            }
            var nowOrd = 0
            var groupNum = 0
            var k = 0
            val par = IntArray(n)
            val vis = IntArray(n)
            val low = IntArray(n)
            val ord = IntArray(n)
            java.util.Arrays.fill(ord, -1)
            val ids = IntArray(n)
            val stack = LongArray(n)
            var ptr = 0
            for (i in 0 until n) {
                if (ord[i] >= 0) continue
                par[i] = -1
                stack[ptr++] = i.toLong()
                while (ptr > 0) {
                    val p = stack[--ptr]
                    val u = (p and mask).toInt()
                    var j = (p ushr 32).toInt()
                    if (j == 0) {
                        ord[u] = nowOrd++
                        low[u] = ord[u]
                        vis[k++] = u
                    }
                    if (start[u] + j < count[u]) {
                        val to = orderedEdges[start[u] + j]
                        stack[ptr++] += 1L shl 32
                        if (ord[to] == -1) {
                            stack[ptr++] = to.toLong()
                            par[to] = u
                        } else {
                            low[u] = kotlin.math.min(low[u], ord[to])
                        }
                    } else {
                        while (j-- > 0) {
                            val to = orderedEdges[start[u] + j]
                            if (par[to] == u) low[u] = kotlin.math.min(low[u], low[to])
                        }
                        if (low[u] == ord[u]) {
                            while (true) {
                                val v = vis[--k]
                                ord[v] = n
                                ids[v] = groupNum
                                if (v == u) break
                            }
                            groupNum++
                        }
                    }
                }
            }
            for (i in 0 until n) {
                ids[i] = groupNum - 1 - ids[i]
            }
            return ids
        }

        companion object {
            const val mask = 0xffffffffL
        }

    }
}
