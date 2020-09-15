package jp.atcoder.library.kotlin.segTree

/**
 * Segment tree(0-indexed)
 */
class SegTree<S>(private val size: Int, private val e: S, private val op: (S, S) -> S) {
    private val innerSize: Int
    private val data: Array<S>

    constructor(dat: Array<S>, e: S, op: (S, S) -> S) : this(dat.size, e, op) {
        System.arraycopy(dat, 0, data, innerSize, dat.size)
        for (i in innerSize - 1 downTo 1) {
            data[i] = op(data[i shl 1 or 0], data[i shl 1 or 1])
        }
    }

    init {
        var k = 1
        while (k < size) k = k shl 1
        innerSize = k
        @Suppress("UNCHECKED_CAST")
        data = Array(innerSize shl 1) { e as Any } as Array<S>
    }

    operator fun set(p: Int, x: S) {
        var vp = p
        exclusiveRangeCheck(vp)
        data[innerSize.let { vp += it; vp }] = x
        vp = vp shr 1
        while (vp > 0) {
            data[vp] = op(data[vp shl 1 or 0], data[vp shl 1 or 1])
            vp = vp shr 1
        }
    }

    operator fun get(p: Int): S {
        exclusiveRangeCheck(p)
        return data[p + innerSize]
    }

    fun prod(l: Int, r: Int): S {
        var vl = l
        var vr = r
        require(vl <= vr) { String.format("Invalid range: [%d, %d)", vl, vr) }
        inclusiveRangeCheck(vl)
        inclusiveRangeCheck(vr)
        var sumLeft = e
        var sumRight = e
        vl += innerSize
        vr += innerSize
        while (vl < vr) {
            if (vl and 1 == 1) sumLeft = op(sumLeft, data[vl++])
            if (vr and 1 == 1) sumRight = op(data[--vr], sumRight)
            vl = vl shr 1
            vr = vr shr 1
        }
        return op(sumLeft, sumRight)
    }

    fun allProd(): S {
        return data[1]
    }

    fun maxRight(l: Int, f: (S) -> Boolean): Int {
        var vl = l
        inclusiveRangeCheck(vl)
        require(f(e)) { "Identity element must satisfy the condition." }
        if (vl == size) return size
        vl += innerSize
        var sum = e
        do {
            vl = vl shr Integer.numberOfTrailingZeros(vl)
            if (!f(op(sum, data[vl]))) {
                while (vl < innerSize) {
                    vl = vl shl 1
                    if (f(op(sum, data[vl]))) {
                        sum = op(sum, data[vl])
                        vl++
                    }
                }
                return vl - innerSize
            }
            sum = op(sum, data[vl])
            vl++
        } while (vl and -vl != vl)
        return size
    }

    fun minLeft(r: Int, f: (S) -> Boolean): Int {
        var vr = r
        inclusiveRangeCheck(vr)
        require(f(e)) { "Identity element must satisfy the condition." }
        if (vr == 0) return 0
        vr += innerSize
        var sum = e
        do {
            vr--
            while (vr > 1 && vr and 1 == 1) vr = vr shr 1
            if (!f(op(data[vr], sum))) {
                while (vr < innerSize) {
                    vr = vr shl 1 or 1
                    if (f(op(data[vr], sum))) {
                        sum = op(data[vr], sum)
                        vr--
                    }
                }
                return vr + 1 - innerSize
            }
            sum = op(data[vr], sum)
        } while (vr and -vr != vr)
        return 0
    }

    private fun exclusiveRangeCheck(p: Int) {
        if (p < 0 || p >= size) {
            throw IndexOutOfBoundsException("Index $p out of bounds for the range [0, $size).")
        }
    }

    private fun inclusiveRangeCheck(p: Int) {
        if (p < 0 || p > size) {
            throw IndexOutOfBoundsException("Index $p out of bounds for the range [0, $size].")
        }
    }
}
