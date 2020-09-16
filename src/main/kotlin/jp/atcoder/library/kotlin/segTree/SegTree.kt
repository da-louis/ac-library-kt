package jp.atcoder.library.kotlin.segTree

/**
 * Segment tree(0-indexed)
 */
internal class SegTree<S>(n: Int, op: java.util.function.BinaryOperator<S>, e: S) {
    private val max: Int = n
    private val n: Int
    private val op: java.util.function.BinaryOperator<S>
    private val e: S
    private val data: Array<S>

    constructor(dat: Array<S>, op: java.util.function.BinaryOperator<S>, e: S) : this(dat.size, op, e) {
        build(dat)
    }

    private fun build(dat: Array<S>) {
        val l = dat.size
        System.arraycopy(dat, 0, data, n, l)
        for (i in n - 1 downTo 1) {
            data[i] = op.apply(data[i shl 1 or 0], data[i shl 1 or 1])
        }
    }

    operator fun set(p: Int, x: S) {
        var vp = p
        exclusiveRangeCheck(vp)
        data[n.let { vp += it; vp }] = x
        vp = vp shr 1
        while (vp > 0) {
            data[vp] = op.apply(data[vp shl 1 or 0], data[vp shl 1 or 1])
            vp = vp shr 1
        }
    }

    operator fun get(p: Int): S {
        exclusiveRangeCheck(p)
        return data[p + n]
    }

    fun prod(l: Int, r: Int): S {
        var vl = l
        var vr = r
        require(vl <= vr) { String.format("Invalid range: [%d, %d)", vl, vr) }
        inclusiveRangeCheck(vl)
        inclusiveRangeCheck(vr)
        var sumLeft = e
        var sumRight = e
        vl += n
        vr += n
        while (vl < vr) {
            if (vl and 1 == 1) sumLeft = op.apply(sumLeft, data[vl++])
            if (vr and 1 == 1) sumRight = op.apply(data[--vr], sumRight)
            vl = vl shr 1
            vr = vr shr 1
        }
        return op.apply(sumLeft, sumRight)
    }

    fun allProd(): S {
        return data[1]
    }

    fun maxRight(l: Int, f: java.util.function.Predicate<S>): Int {
        var vl = l
        inclusiveRangeCheck(vl)
        require(f.test(e)) { "Identity element must satisfy the condition." }
        if (vl == max) return max
        vl += n
        var sum = e
        do {
            vl = vl shr Integer.numberOfTrailingZeros(vl)
            if (!f.test(op.apply(sum, data[vl]))) {
                while (vl < n) {
                    vl = vl shl 1
                    if (f.test(op.apply(sum, data[vl]))) {
                        sum = op.apply(sum, data[vl])
                        vl++
                    }
                }
                return vl - n
            }
            sum = op.apply(sum, data[vl])
            vl++
        } while (vl and -vl != vl)
        return max
    }

    fun minLeft(r: Int, f: java.util.function.Predicate<S>): Int {
        var vr = r
        inclusiveRangeCheck(vr)
        require(f.test(e)) { "Identity element must satisfy the condition." }
        if (vr == 0) return 0
        vr += n
        var sum = e
        do {
            vr--
            while (vr > 1 && vr and 1 == 1) vr = vr shr 1
            if (!f.test(op.apply(data[vr], sum))) {
                while (vr < n) {
                    vr = vr shl 1 or 1
                    if (f.test(op.apply(data[vr], sum))) {
                        sum = op.apply(data[vr], sum)
                        vr--
                    }
                }
                return vr + 1 - n
            }
            sum = op.apply(data[vr], sum)
        } while (vr and -vr != vr)
        return 0
    }

    private fun exclusiveRangeCheck(p: Int) {
        if (p < 0 || p >= max) {
            throw IndexOutOfBoundsException(String.format("Index %d out of bounds for the range [%d, %d).", p, 0, max))
        }
    }

    private fun inclusiveRangeCheck(p: Int) {
        if (p < 0 || p > max) {
            throw IndexOutOfBoundsException(String.format("Index %d out of bounds for the range [%d, %d].", p, 0, max))
        }
    }

    init {
        var k = 1
        while (k < n) k = k shl 1
        this.n = k
        this.e = e
        this.op = op
        @Suppress("UNCHECKED_CAST")
        data = Array(this.n shl 1) { e as Any } as Array<S>
        java.util.Arrays.fill(data, this.e)
    }
}
