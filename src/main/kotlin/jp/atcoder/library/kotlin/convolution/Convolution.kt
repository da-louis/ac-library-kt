package jp.atcoder.library.kotlin.convolution

import jp.atcoder.library.kotlin.convolution.ModIntFactory.ModInt
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
        val mod = a[0].mod()
        val va = a.stream().mapToLong { it.value().toLong() }.toArray()
        val vb = b.stream().mapToLong { it.value().toLong() }.toArray()
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

class ModIntFactory(mod: Int) {
    private val ma: ModArithmetic
    private val mod: Int

    fun create(value: Long): ModInt {
        var v = value
        if (mod.also { v %= it } < 0) v += mod.toLong()
        return if (ma is ModArithmetic.ModArithmeticMontgomery) {
            ModInt(ma.generate(v))
        } else ModInt(v.toInt())
    }

    inner class ModInt(private var value: Int) {
        fun mod(): Int {
            return mod
        }

        fun value(): Int {
            return if (ma is ModArithmetic.ModArithmeticMontgomery) {
                ma.reduce(value.toLong())
            } else value
        }

        fun add(mi: ModInt): ModInt {
            return ModInt(ma.add(value, mi.value))
        }

        fun sub(mi: ModInt): ModInt {
            return ModInt(ma.sub(value, mi.value))
        }

        fun mul(mi: ModInt): ModInt {
            return ModInt(ma.mul(value, mi.value))
        }

        operator fun div(mi: ModInt): ModInt {
            return ModInt(ma.div(value, mi.value))
        }

        fun inv(): ModInt {
            return ModInt(ma.inv(value))
        }

        fun pow(b: Long): ModInt {
            return ModInt(ma.pow(value, b))
        }

        fun addAsg(mi: ModInt): ModInt {
            value = ma.add(value, mi.value)
            return this
        }

        fun subAsg(mi: ModInt): ModInt {
            value = ma.sub(value, mi.value)
            return this
        }

        fun mulAsg(mi: ModInt): ModInt {
            value = ma.mul(value, mi.value)
            return this
        }

        fun divAsg(mi: ModInt): ModInt {
            value = ma.div(value, mi.value)
            return this
        }

        override fun toString(): String {
            return value().toString()
        }

        override fun equals(other: Any?): Boolean {
            if (other is ModInt) {
                return mod() == other.mod() && value() == other.value()
            }
            return false
        }

        override fun hashCode(): Int {
            return (1 * 37 + mod()) * 37 + value()
        }
    }

    internal interface ModArithmetic {
        fun mod(): Int
        fun add(a: Int, b: Int): Int
        fun sub(a: Int, b: Int): Int
        fun mul(a: Int, b: Int): Int
        fun div(a: Int, b: Int): Int {
            return mul(a, inv(b))
        }

        fun inv(a: Int): Int
        fun pow(a: Int, b: Long): Int
        class ModArithmetic1 : ModArithmetic {
            override fun mod(): Int {
                return 1
            }

            override fun add(a: Int, b: Int): Int {
                return 0
            }

            override fun sub(a: Int, b: Int): Int {
                return 0
            }

            override fun mul(a: Int, b: Int): Int {
                return 0
            }

            override fun inv(a: Int): Int {
                throw ArithmeticException("divide by zero")
            }

            override fun pow(a: Int, b: Long): Int {
                return 0
            }
        }

        class ModArithmetic2 : ModArithmetic {
            override fun mod(): Int {
                return 2
            }

            override fun add(a: Int, b: Int): Int {
                return a xor b
            }

            override fun sub(a: Int, b: Int): Int {
                return a xor b
            }

            override fun mul(a: Int, b: Int): Int {
                return a and b
            }

            override fun inv(a: Int): Int {
                if (a == 0) throw ArithmeticException("divide by zero")
                return a
            }

            override fun pow(a: Int, b: Long): Int {
                return if (b == 0L) 1 else a
            }
        }

        class ModArithmetic998244353 : ModArithmetic {
            private val mod = 998244353
            override fun mod(): Int {
                return mod
            }

            override fun add(a: Int, b: Int): Int {
                val res = a + b
                return if (res >= mod) res - mod else res
            }

            override fun sub(a: Int, b: Int): Int {
                val res = a - b
                return if (res < 0) res + mod else res
            }

            override fun mul(a: Int, b: Int): Int {
                return (a.toLong() * b % mod).toInt()
            }

            override fun inv(a: Int): Int {
                var va = a
                var b = mod
                var u: Long = 1
                var v: Long = 0
                while (b >= 1) {
                    val t = (va / b).toLong()
                    va -= (t * b).toInt()
                    val tmp1 = va
                    va = b
                    b = tmp1
                    u -= t * v
                    val tmp2 = u
                    u = v
                    v = tmp2
                }
                u %= mod.toLong()
                if (va != 1) {
                    throw ArithmeticException("divide by zero")
                }
                return (if (u < 0) u + mod else u).toInt()
            }

            override fun pow(a: Int, b: Long): Int {
                var vb = b
                if (vb < 0) throw ArithmeticException("negative power")
                var res: Long = 1
                var pow2 = a.toLong()
                var idx: Long = 1
                while (vb > 0) {
                    val lsb = vb and -vb
                    while (lsb != idx) {
                        pow2 = pow2 * pow2 % mod
                        idx = idx shl 1
                    }
                    res = res * pow2 % mod
                    vb = vb xor lsb
                }
                return res.toInt()
            }
        }

        class ModArithmetic1000000007 : ModArithmetic {
            private val mod = 1000000007
            override fun mod(): Int {
                return mod
            }

            override fun add(a: Int, b: Int): Int {
                val res = a + b
                return if (res >= mod) res - mod else res
            }

            override fun sub(a: Int, b: Int): Int {
                val res = a - b
                return if (res < 0) res + mod else res
            }

            override fun mul(a: Int, b: Int): Int {
                return (a.toLong() * b % mod).toInt()
            }

            override fun div(a: Int, b: Int): Int {
                return mul(a, inv(b))
            }

            override fun inv(a: Int): Int {
                var va = a
                var b = mod
                var u: Long = 1
                var v: Long = 0
                while (b >= 1) {
                    val t = (va / b).toLong()
                    va -= (t * b).toInt()
                    val tmp1 = va
                    va = b
                    b = tmp1
                    u -= t * v
                    val tmp2 = u
                    u = v
                    v = tmp2
                }
                u %= mod.toLong()
                if (va != 1) {
                    throw ArithmeticException("divide by zero")
                }
                return (if (u < 0) u + mod else u).toInt()
            }

            override fun pow(a: Int, b: Long): Int {
                var vb = b
                if (vb < 0) throw ArithmeticException("negative power")
                var res: Long = 1
                var pow2 = a.toLong()
                var idx: Long = 1
                while (vb > 0) {
                    val lsb = vb and -vb
                    while (lsb != idx) {
                        pow2 = pow2 * pow2 % mod
                        idx = idx shl 1
                    }
                    res = res * pow2 % mod
                    vb = vb xor lsb
                }
                return res.toInt()
            }
        }

        class ModArithmeticMontgomery(mod: Int) : ModArithmeticDynamic(mod) {
            private val negInv: Long
            private val r2: Long
            private val r3: Long
            fun generate(x: Long): Int {
                return reduce(x * r2)
            }

            fun reduce(x: Long): Int {
                var vx = x
                vx = vx + (vx * negInv and 0xffffffffL) * mod ushr 32
                return (if (vx < mod) vx else vx - mod).toInt()
            }

            override fun mul(a: Int, b: Int): Int {
                return reduce(a.toLong() * b)
            }

            override fun inv(a: Int): Int {
                var va = a
                va = super.inv(va)
                return reduce(va * r3)
            }

            override fun pow(a: Int, b: Long): Int {
                return generate(super.pow(a, b).toLong())
            }

            init {
                var inv: Long = 0
                var s: Long = 1
                var t: Long = 0
                for (i in 0..31) {
                    if (t and 1 == 0L) {
                        t += mod.toLong()
                        inv += s
                    }
                    t = t shr 1
                    s = s shl 1
                }
                val r = (1L shl 32) % mod
                negInv = inv
                r2 = r * r % mod
                r3 = r2 * r % mod
            }
        }

        class ModArithmeticBarrett(mod: Int) : ModArithmeticDynamic(mod) {
            private val mh: Long
            private val ml: Long
            private fun reduce(x: Long): Int {
                var vx = x
                var z = (vx and mask) * ml
                z = (vx and mask) * mh + (vx ushr 32) * ml + (z ushr 32)
                z = (vx ushr 32) * mh + (z ushr 32)
                vx -= z * mod
                return (if (vx < mod) vx else vx - mod).toInt()
            }

            override fun mul(a: Int, b: Int): Int {
                return reduce(a.toLong() * b)
            }

            companion object {
                private const val mask = 0xffffffffL
            }

            init {
                /**
                 * m = floor(2^64/mod)
                 * 2^64 = p*mod + q, 2^32 = a*mod + b
                 * => (a*mod + b)^2 = p*mod + q
                 * => p = mod*a^2 + 2ab + floor(b^2/mod)
                 */
                val a = (1L shl 32) / mod
                val b = (1L shl 32) % mod
                val m = a * a * mod + 2 * a * b + b * b / mod
                mh = m ushr 32
                ml = m and mask
            }
        }

        open class ModArithmeticDynamic(val mod: Int) : ModArithmetic {
            override fun mod(): Int {
                return mod
            }

            override fun add(a: Int, b: Int): Int {
                val sum = a + b
                return if (sum >= mod) sum - mod else sum
            }

            override fun sub(a: Int, b: Int): Int {
                val sum = a - b
                return if (sum < 0) sum + mod else sum
            }

            override fun mul(a: Int, b: Int): Int {
                return (a.toLong() * b % mod).toInt()
            }

            override fun inv(a: Int): Int {
                var va = a
                var b = mod
                var u: Long = 1
                var v: Long = 0
                while (b >= 1) {
                    val t = (va / b).toLong()
                    va -= (t * b).toInt()
                    val tmp1 = va
                    va = b
                    b = tmp1
                    u -= t * v
                    val tmp2 = u
                    u = v
                    v = tmp2
                }
                u %= mod.toLong()
                if (va != 1) {
                    throw ArithmeticException("divide by zero")
                }
                return (if (u < 0) u + mod else u).toInt()
            }

            override fun pow(a: Int, b: Long): Int {
                var vb = b
                if (vb < 0) throw ArithmeticException("negative power")
                var res = 1
                var pow2 = a
                var idx: Long = 1
                while (vb > 0) {
                    val lsb = vb and -vb
                    while (lsb != idx) {
                        pow2 = mul(pow2, pow2)
                        idx = idx shl 1
                    }
                    res = mul(res, pow2)
                    vb = vb xor lsb
                }
                return res
            }
        }

        companion object {
            fun of(mod: Int): ModArithmetic {
                return when {
                    mod <= 0 -> throw IllegalArgumentException()
                    mod == 1 -> ModArithmetic1()
                    mod == 2 -> ModArithmetic2()
                    mod == 998244353 -> ModArithmetic998244353()
                    mod == 1000000007 -> ModArithmetic1000000007()
                    mod and 1 == 1 -> ModArithmeticMontgomery(mod)
                    else -> ModArithmeticBarrett(mod)
                }
            }
        }
    }

    init {
        ma = ModArithmetic.of(mod)
        this.mod = mod
    }
}
