package jp.atcoder.library.kotlin.modInt

/**
 * ModInt を生成するためのファクトリ.
 *
 * convert from [AtCoderLibraryForJava - FenwickTree](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/FenwickTree/FenwickTree.java)
 */
class ModIntFactory(private val mod: Int) {
    private val ma = ModArithmetic.of(mod)

    /**
     * ModInt を生成する.
     * 生成された ModInt は、[value] をファクトリ生成時に指定した mod で割った余りを持つ.
     */
    fun create(value: Long): ModInt {
        val v = (value % mod).let { if (it < 0) it + mod else it }
        return if (ma is ModArithmetic.ModArithmeticMontgomery) {
            ModInt(ma.generate(v))
        } else {
            ModInt(v.toInt())
        }
    }

    inner class ModInt(private var rawValue: Int) {
        val mod = this@ModIntFactory.mod

        val value: Int
            get() = if (ma is ModArithmetic.ModArithmeticMontgomery) {
                ma.reduce(rawValue.toLong())
            } else {
                rawValue
            }

        operator fun plus(mi: ModInt) = ModInt(ma.add(rawValue, mi.rawValue))
        operator fun minus(mi: ModInt) = ModInt(ma.sub(rawValue, mi.rawValue))
        operator fun times(mi: ModInt) = ModInt(ma.mul(rawValue, mi.rawValue))
        operator fun div(mi: ModInt) = ModInt(ma.div(rawValue, mi.rawValue))

        /** (this * inv) % mod = 1 を満たすような inv を ModInt として生成して返す. */
        fun inv() = ModInt(ma.inv(rawValue))

        /** (this ^ [b]) % mod の結果を ModInt として生成して返す. */
        fun pow(b: Long) = ModInt(ma.pow(rawValue, b))

        /** this を this + [mi] に変更する */
        fun addAsg(mi: ModInt): ModInt {
            rawValue = ma.add(rawValue, mi.rawValue)
            return this
        }

        /** this を this - [mi] に変更する */
        fun subAsg(mi: ModInt): ModInt {
            rawValue = ma.sub(rawValue, mi.rawValue)
            return this
        }

        /** this を this * [mi] に変更する */
        fun mulAsg(mi: ModInt): ModInt {
            rawValue = ma.mul(rawValue, mi.rawValue)
            return this
        }

        /** this を this / [mi] に変更する */
        fun divAsg(mi: ModInt): ModInt {
            rawValue = ma.div(rawValue, mi.rawValue)
            return this
        }

        override fun toString(): String {
            return value.toString()
        }

        override fun equals(other: Any?): Boolean {
            if (other is ModInt) {
                return mod == other.mod && value == other.value
            }
            return false
        }

        override fun hashCode(): Int {
            return (1 * 37 + mod) * 37 + value
        }
    }

    private interface ModArithmetic {
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
}
