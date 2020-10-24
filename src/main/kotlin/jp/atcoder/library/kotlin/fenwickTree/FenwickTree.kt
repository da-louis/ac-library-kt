package jp.atcoder.library.kotlin.fenwickTree

/**
 * Fenwick tree(0-indexed).
 *
 * convert from [AtCoderLibraryForJava - FenwickTree](https://github.com/NASU41/AtCoderLibraryForJava/blob/b0702e6e27f0e2df657df55231a2441c506db5a6/FenwickTree/FenwickTree.java)
 */
class FenwickTree(private val n: Int) {
    private val data: LongArray = LongArray(n)

    /**
     * Fenwick tree constructor.
     */
    constructor(data: LongArray) : this(data.size) {
        build(data)
    }

    /**
     * Add value x to p.
     */
    fun add(p: Int, x: Long) {
        var vp = p
        assert(vp in 0 until n)
        vp++
        while (vp <= n) {
            data[vp - 1] += x
            vp += vp and -vp
        }
    }

    /**
     * Calc sum [l, r].
     */
    fun sum(l: Int, r: Int): Long {
        assert(l in 0..r && r <= n)
        return sum(r) - sum(l)
    }

    private fun sum(r: Int): Long {
        var vr = r
        var s: Long = 0
        while (vr > 0) {
            s += data[vr - 1]
            vr -= vr and -vr
        }
        return s
    }

    private fun build(dat: LongArray) {
        System.arraycopy(dat, 0, data, 0, n)
        for (i in 1..n) {
            val p = i + (i and -i)
            if (p <= n) {
                data[p - 1] += data[i - 1]
            }
        }
    }
}
