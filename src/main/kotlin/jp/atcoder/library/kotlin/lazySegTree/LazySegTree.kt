package jp.atcoder.library.kotlin.lazySegTree

import java.util.*
import java.util.function.BiFunction
import java.util.function.BinaryOperator
import java.util.function.Predicate

/**
 * Lazy Segment tree(0-indexed).
 *
 * convert from [AtCoderLibraryForJava - LazySegTree](https://github.com/NASU41/AtCoderLibraryForJava/blob/e7d9874fb6baac4f566a6af471e80941a55b22b2/LazySegTree/LazySegTree.java)
 */
internal class LazySegTree<S, F>(
    val MAX: Int,
    op: BinaryOperator<S>,
    e: S,
    mapping: BiFunction<F, S, S>,
    composition: BinaryOperator<F>,
    id: F
) {
    val N: Int
    val Log: Int
    val Op: BinaryOperator<S>
    val E: S
    val Mapping: BiFunction<F, S, S>
    val Composition: BinaryOperator<F>
    val Id: F
    val Dat: Array<S>
    val Laz: Array<F>

    constructor(
        dat: Array<S>,
        op: BinaryOperator<S>,
        e: S,
        mapping: BiFunction<F, S, S>,
        composition: BinaryOperator<F>,
        id: F
    ) : this(dat.size, op, e, mapping, composition, id) {
        build(dat)
    }

    private fun build(dat: Array<S>) {
        val l = dat.size
        System.arraycopy(dat, 0, Dat, N, l)
        for (i in N - 1 downTo 1) {
            Dat[i] = Op.apply(Dat[i shl 1], Dat[i shl 1 or 1])
        }
    }

    private fun push(k: Int) {
        if (Laz[k] === Id) return
        val lk = k shl 1
        val rk = k shl 1 or 1
        Dat[lk] = Mapping.apply(Laz[k], Dat[lk])
        Dat[rk] = Mapping.apply(Laz[k], Dat[rk])
        if (lk < N) Laz[lk] = Composition.apply(Laz[k], Laz[lk])
        if (rk < N) Laz[rk] = Composition.apply(Laz[k], Laz[rk])
        Laz[k] = Id
    }

    private fun pushTo(k: Int) {
        for (i in Log downTo 1) push(k shr i)
    }

    private fun pushTo(lk: Int, rk: Int) {
        for (i in Log downTo 1) {
            if (lk shr i shl i != lk) push(lk shr i)
            if (rk shr i shl i != rk) push(rk shr i)
        }
    }

    private fun updateFrom(k: Int) {
        var vk = k
        vk = vk shr 1
        while (vk > 0) {
            Dat[vk] = Op.apply(Dat[vk shl 1], Dat[vk shl 1 or 1])
            vk = vk shr 1
        }
    }

    private fun updateFrom(lk: Int, rk: Int) {
        for (i in 1..Log) {
            if (lk shr i shl i != lk) {
                val lki = lk shr i
                Dat[lki] = Op.apply(Dat[lki shl 1], Dat[lki shl 1 or 1])
            }
            if (rk shr i shl i != rk) {
                val rki = rk - 1 shr i
                Dat[rki] = Op.apply(Dat[rki shl 1], Dat[rki shl 1 or 1])
            }
        }
    }

    operator fun set(p: Int, x: S) {
        var vp = p
        exclusiveRangeCheck(vp)
        vp += N
        pushTo(vp)
        Dat[vp] = x
        updateFrom(vp)
    }

    operator fun get(p: Int): S {
        var vp = p
        exclusiveRangeCheck(vp)
        vp += N
        pushTo(vp)
        return Dat[vp]
    }

    fun prod(l: Int, r: Int): S {
        var vl = l
        var vr = r
        require(vl <= vr) { String.format("Invalid range: [%d, %d)", vl, vr) }
        inclusiveRangeCheck(vl)
        inclusiveRangeCheck(vr)
        if (vl == vr) return E
        vl += N
        vr += N
        pushTo(vl, vr)
        var sumLeft = E
        var sumRight = E
        while (vl < vr) {
            if (vl and 1 == 1) sumLeft = Op.apply(sumLeft, Dat[vl++])
            if (vr and 1 == 1) sumRight = Op.apply(Dat[--vr], sumRight)
            vl = vl shr 1
            vr = vr shr 1
        }
        return Op.apply(sumLeft, sumRight)
    }

    fun allProd(): S {
        return Dat[1]
    }

    fun apply(p: Int, f: F) {
        var vp = p
        exclusiveRangeCheck(vp)
        vp += N
        pushTo(vp)
        Dat[vp] = Mapping.apply(f, Dat[vp])
        updateFrom(vp)
    }

    fun apply(l: Int, r: Int, f: F) {
        var vl = l
        var vr = r
        require(vl <= vr) { String.format("Invalid range: [%d, %d)", vl, vr) }
        inclusiveRangeCheck(vl)
        inclusiveRangeCheck(vr)
        if (vl == vr) return
        vl += N
        vr += N
        pushTo(vl, vr)
        var l2 = vl
        var r2 = vr
        while (l2 < r2) {
            if (l2 and 1 == 1) {
                Dat[l2] = Mapping.apply(f, Dat[l2])
                if (l2 < N) Laz[l2] = Composition.apply(f, Laz[l2])
                l2++
            }
            if (r2 and 1 == 1) {
                r2--
                Dat[r2] = Mapping.apply(f, Dat[r2])
                if (r2 < N) Laz[r2] = Composition.apply(f, Laz[r2])
            }
            l2 = l2 shr 1
            r2 = r2 shr 1
        }
        updateFrom(vl, vr)
    }

    fun maxRight(l: Int, g: Predicate<S>): Int {
        var vl = l
        inclusiveRangeCheck(vl)
        require(g.test(E)) { "Identity element must satisfy the condition." }
        if (vl == MAX) return MAX
        vl += N
        pushTo(vl)
        var sum = E
        do {
            vl = vl shr Integer.numberOfTrailingZeros(vl)
            if (!g.test(Op.apply(sum, Dat[vl]))) {
                while (vl < N) {
                    push(vl)
                    vl = vl shl 1
                    if (g.test(Op.apply(sum, Dat[vl]))) {
                        sum = Op.apply(sum, Dat[vl])
                        vl++
                    }
                }
                return vl - N
            }
            sum = Op.apply(sum, Dat[vl])
            vl++
        } while (vl and -vl != vl)
        return MAX
    }

    fun minLeft(r: Int, g: Predicate<S>): Int {
        var vr = r
        inclusiveRangeCheck(vr)
        require(g.test(E)) { "Identity element must satisfy the condition." }
        if (vr == 0) return 0
        vr += N
        pushTo(vr - 1)
        var sum = E
        do {
            vr--
            while (vr > 1 && vr and 1 == 1) vr = vr shr 1
            if (!g.test(Op.apply(Dat[vr], sum))) {
                while (vr < N) {
                    push(vr)
                    vr = vr shl 1 or 1
                    if (g.test(Op.apply(Dat[vr], sum))) {
                        sum = Op.apply(Dat[vr], sum)
                        vr--
                    }
                }
                return vr + 1 - N
            }
            sum = Op.apply(Dat[vr], sum)
        } while (vr and -vr != vr)
        return 0
    }

    private fun exclusiveRangeCheck(p: Int) {
        if (p < 0 || p >= MAX) {
            throw IndexOutOfBoundsException(String.format("Index %d is not in [%d, %d).", p, 0, MAX))
        }
    }

    private fun inclusiveRangeCheck(p: Int) {
        if (p < 0 || p > MAX) {
            throw IndexOutOfBoundsException(String.format("Index %d is not in [%d, %d].", p, 0, MAX))
        }
    }

    // **************** DEBUG **************** //
    private var indent = 6
    fun setIndent(newIndent: Int) {
        indent = newIndent
    }

    override fun toString(): String {
        return toSimpleString()
    }

    private fun simulatePushAll(): Array<S> {
        val simDat = Arrays.copyOf(Dat, 2 * N)
        val simLaz = Arrays.copyOf(Laz, 2 * N)
        for (k in 1 until N) {
            if (simLaz[k] === Id) continue
            val lk = k shl 1
            val rk = k shl 1 or 1
            simDat[lk] = Mapping.apply(simLaz[k], simDat[lk])
            simDat[rk] = Mapping.apply(simLaz[k], simDat[rk])
            if (lk < N) simLaz[lk] = Composition.apply(simLaz[k], simLaz[lk])
            if (rk < N) simLaz[rk] = Composition.apply(simLaz[k], simLaz[rk])
            simLaz[k] = Id
        }
        return simDat
    }

    fun toDetailedString(): String {
        return toDetailedString(1, 0, simulatePushAll())
    }

    private fun toDetailedString(k: Int, sp: Int, dat: Array<S>): String {
        if (k >= N) return indent(sp) + dat[k]
        var s = ""
        s += toDetailedString(k shl 1 or 1, sp + indent, dat)
        s += "\n"
        s += indent(sp) + dat[k]
        s += "\n"
        s += toDetailedString(k shl 1, sp + indent, dat)
        return s
    }

    fun toSimpleString(): String {
        val dat = simulatePushAll()
        val sb = StringBuilder()
        sb.append('[')
        for (i in 0 until N) {
            sb.append(dat[i + N])
            if (i < N - 1) sb.append(',').append(' ')
        }
        sb.append(']')
        return sb.toString()
    }

    companion object {
        private fun indent(n: Int): String {
            var vn = n
            val sb = StringBuilder()
            while (vn-- > 0) sb.append(' ')
            return sb.toString()
        }
    }

    init {
        var k = 1
        while (k < MAX) k = k shl 1
        N = k
        Log = Integer.numberOfTrailingZeros(N)
        Op = op
        E = e
        Mapping = mapping
        Composition = composition
        Id = id
        @Suppress("UNCHECKED_CAST")
        Dat = Array(N shl 1) { e as Any } as Array<S>
        @Suppress("UNCHECKED_CAST")
        Laz = Array(N) { id as Any } as Array<F>
        Arrays.fill(Dat, E)
        Arrays.fill(Laz, Id)
    }
}
