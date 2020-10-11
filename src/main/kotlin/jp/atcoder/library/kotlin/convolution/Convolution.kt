package jp.atcoder.library.kotlin.convolution

import jp.atcoder.library.kotlin.modInt.ModIntFactory.ModInt
import jp.atcoder.library.kotlin.modInt.ModIntFactory
import java.util.*
import java.util.stream.Collectors

/**
 * Convolution.
 *
 * convert from [AtCoderLibraryForJava - Convolution](https://github.com/NASU41/AtCoderLibraryForJava/blob/a7e73af20f0553b9d9c4a2e7c4194af817b12485/Convolution/Convolution.java#L10)
 */
class Convolution {
    /**
     * Find a primitive root.
     *
     * @param m A prime number.
     * @return Primitive root.
     */
    private fun primitiveRoot(m: Int): Int {
        if (m == 2) return 1
        if (m == 167772161) return 3
        if (m == 469762049) return 3
        if (m == 754974721) return 11
        if (m == 998244353) return 3
        val divs = IntArray(20)
        divs[0] = 2
        var cnt = 1
        var x = (m - 1) / 2
        while (x % 2 == 0) x /= 2
        var i = 3
        while (i.toLong() * i <= x) {
            if (x % i == 0) {
                divs[cnt++] = i
                while (x % i == 0) {
                    x /= i
                }
            }
            i += 2
        }
        if (x > 1) {
            divs[cnt++] = x
        }
        var g = 2
        while (true) {
            var ok = true
            for (i2 in 0 until cnt) {
                if (pow(g.toLong(), (m - 1) / divs[i2].toLong(), m) == 1L) {
                    ok = false
                    break
                }
            }
            if (ok) return g
            g++
        }
    }

    /**
     * Power.
     *
     * @param x Parameter x.
     * @param n Parameter n.
     * @param m Mod.
     * @return n-th power of x mod m.
     */
    private fun pow(x: Long, n: Long, m: Int): Long {
        var vn = n
        if (m == 1) return 0
        var r: Long = 1
        var y = x % m
        while (vn > 0) {
            if (vn and 1 != 0L) r = r * y % m
            y = y * y % m
            vn = vn shr 1
        }
        return r
    }

    /**
     * Ceil of power 2.
     *
     * @param n Value.
     * @return Ceil of power 2.
     */
    private fun ceilPow2(n: Int): Int {
        var x = 0
        while (1L shl x < n) x++
        return x
    }

    /**
     * Garner's algorithm.
     *
     * @param c    Mod convolution results.
     * @param mods Mods.
     * @return Result.
     */
    private fun garner(c: LongArray, mods: IntArray): Long {
        val n = c.size + 1
        val cnst = LongArray(n)
        val coef = LongArray(n)
        Arrays.fill(coef, 1)
        for (i in 0 until n - 1) {
            val m1 = mods[i]
            var v = (c[i] - cnst[i] + m1) % m1
            v = v * pow(coef[i], m1 - 2.toLong(), m1) % m1
            for (j in i + 1 until n) {
                val m2 = mods[j].toLong()
                cnst[j] = (cnst[j] + coef[j] * v) % m2
                coef[j] = coef[j] * m1 % m2
            }
        }
        return cnst[n - 1]
    }

    /**
     * Pre-calculation for NTT.
     *
     * @param mod NTT Prime.
     * @param g   Primitive root of mod.
     * @return Pre-calculation table.
     */
    private fun sumE(mod: Int, g: Int): LongArray {
        val sum_e = LongArray(30)
        val es = LongArray(30)
        val ies = LongArray(30)
        val cnt2 = Integer.numberOfTrailingZeros(mod - 1)
        var e = pow(g.toLong(), (mod - 1 shr cnt2.toLong().toInt()).toLong(), mod)
        var ie = pow(e, mod - 2.toLong(), mod)
        for (i in cnt2 downTo 2) {
            es[i - 2] = e
            ies[i - 2] = ie
            e = e * e % mod
            ie = ie * ie % mod
        }
        var now: Long = 1
        for (i in 0 until cnt2 - 2) {
            sum_e[i] = es[i] * now % mod
            now = now * ies[i] % mod
        }
        return sum_e
    }

    /**
     * Pre-calculation for inverse NTT.
     *
     * @param mod Mod.
     * @param g   Primitive root of mod.
     * @return Pre-calculation table.
     */
    private fun sumIE(mod: Int, g: Int): LongArray {
        val sum_ie = LongArray(30)
        val es = LongArray(30)
        val ies = LongArray(30)
        val cnt2 = Integer.numberOfTrailingZeros(mod - 1)
        var e = pow(g.toLong(), (mod - 1 shr cnt2.toLong().toInt()).toLong(), mod)
        var ie = pow(e, mod - 2.toLong(), mod)
        for (i in cnt2 downTo 2) {
            es[i - 2] = e
            ies[i - 2] = ie
            e = e * e % mod
            ie = ie * ie % mod
        }
        var now: Long = 1
        for (i in 0 until cnt2 - 2) {
            sum_ie[i] = ies[i] * now % mod
            now = now * es[i] % mod
        }
        return sum_ie
    }

    /**
     * Inverse NTT.
     *
     * @param a     Target array.
     * @param sumIE Pre-calculation table.
     * @param mod   NTT Prime.
     */
    private fun butterflyInv(a: LongArray, sumIE: LongArray, mod: Int) {
        val n = a.size
        val h = ceilPow2(n)
        for (ph in h downTo 1) {
            val w = 1 shl ph - 1
            val p = 1 shl h - ph
            var inow: Long = 1
            for (s in 0 until w) {
                val offset = s shl h - ph + 1
                for (i in 0 until p) {
                    val l = a[i + offset]
                    val r = a[i + offset + p]
                    a[i + offset] = (l + r) % mod
                    a[i + offset + p] = (mod + l - r) * inow % mod
                }
                val x = Integer.numberOfTrailingZeros(s.inv())
                inow = inow * sumIE[x] % mod
            }
        }
    }

    /**
     * Inverse NTT.
     *
     * @param a    Target array.
     * @param sumE Pre-calculation table.
     * @param mod  NTT Prime.
     */
    private fun butterfly(a: LongArray, sumE: LongArray, mod: Int) {
        val n = a.size
        val h = ceilPow2(n)
        for (ph in 1..h) {
            val w = 1 shl ph - 1
            val p = 1 shl h - ph
            var now: Long = 1
            for (s in 0 until w) {
                val offset = s shl h - ph + 1
                for (i in 0 until p) {
                    val l = a[i + offset]
                    val r = a[i + offset + p] * now % mod
                    a[i + offset] = (l + r) % mod
                    a[i + offset + p] = (l - r + mod) % mod
                }
                val x = Integer.numberOfTrailingZeros(s.inv())
                now = now * sumE[x] % mod
            }
        }
    }

    /**
     * Convolution.
     *
     * @param a   Target array 1.
     * @param b   Target array 2.
     * @param mod NTT Prime.
     * @return Answer.
     */
    fun convolution(a: LongArray, b: LongArray, mod: Int): LongArray {
        var va = a
        var vb = b
        val n = va.size
        val m = vb.size
        if (n == 0 || m == 0) return LongArray(0)
        val z = 1 shl ceilPow2(n + m - 1)
        run {
            val na = LongArray(z)
            val nb = LongArray(z)
            System.arraycopy(va, 0, na, 0, n)
            System.arraycopy(vb, 0, nb, 0, m)
            va = na
            vb = nb
        }
        val g = primitiveRoot(mod)
        val sume = sumE(mod, g)
        val sumie = sumIE(mod, g)
        butterfly(va, sume, mod)
        butterfly(vb, sume, mod)
        for (i in 0 until z) {
            va[i] = va[i] * vb[i] % mod
        }
        butterflyInv(va, sumie, mod)
        va = Arrays.copyOf(va, n + m - 1)
        val iz = pow(z.toLong(), mod - 2.toLong(), mod)
        for (i in 0 until n + m - 1) va[i] = va[i] * iz % mod
        return va
    }

    /**
     * Convolution.
     *
     * @param a   Target array 1.
     * @param b   Target array 2.
     * @param mod Any mod.
     * @return Answer.
     */
    fun convolutionLL(a: LongArray, b: LongArray, mod: Int): LongArray {
        val n = a.size
        val m = b.size
        if (n == 0 || m == 0) return LongArray(0)
        val mod1 = 754974721
        val mod2 = 167772161
        val mod3 = 469762049
        val c1 = convolution(a, b, mod1)
        val c2 = convolution(a, b, mod2)
        val c3 = convolution(a, b, mod3)
        val retSize = c1.size
        val ret = LongArray(retSize)
        val mods = intArrayOf(mod1, mod2, mod3, mod)
        for (i in 0 until retSize) {
            ret[i] = garner(longArrayOf(c1[i], c2[i], c3[i]), mods)
        }
        return ret
    }

    /**
     * Convolution by ModInt.
     *
     * @param a Target array 1.
     * @param b Target array 2.
     * @return Answer.
     */
    fun convolution(
        a: List<ModInt>,
        b: List<ModInt>
    ): List<ModInt> {
        val mod = a[0].mod
        val va = a.stream().mapToLong { it.value.toLong() }.toArray()
        val vb = b.stream().mapToLong { it.value.toLong() }.toArray()
        val c = convolutionLL(va, vb, mod)
        val factory = ModIntFactory(mod)
        return Arrays.stream(c).mapToObj { value: Long -> factory.create(value) }.collect(Collectors.toList())
    }

    /**
     * Naive convolution. (Complexity is O(N^2)!!)
     *
     * @param a   Target array 1.
     * @param b   Target array 2.
     * @param mod Mod.
     * @return Answer.
     */
    fun convolutionNaive(a: LongArray, b: LongArray, mod: Int): LongArray {
        val n = a.size
        val m = b.size
        val k = n + m - 1
        val ret = LongArray(k)
        for (i in 0 until n) {
            for (j in 0 until m) {
                ret[i + j] += a[i] * b[j] % mod
                ret[i + j] %= mod.toLong()
            }
        }
        return ret
    }
}
