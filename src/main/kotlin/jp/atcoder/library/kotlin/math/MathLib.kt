package jp.atcoder.library.kotlin.math

/**
 * MathLib.
 *
 * convert from [AtCoderLibraryForJava - MathLib](https://github.com/NASU41/AtCoderLibraryForJava/blob/132179385293fd6c0d522d73f3f48435967fffcb/Math/MathLib.java#L4)
 */
object MathLib {
    /**
     * xをmで割った余りを返す.
     */
    fun safeMod(x: Long, m: Long): Long {
        val vx = x % m
        return if (vx < 0) vx + m else vx
    }

    fun invGcd(a: Long, b: Long): LongArray {
        var va = a
        va = safeMod(va, b)
        if (va == 0L) return longArrayOf(b, 0)
        var s = b
        var t = va
        var m0: Long = 0
        var m1: Long = 1
        while (t > 0) {
            val u = s / t
            s -= t * u
            m0 -= m1 * u
            var tmp = s
            s = t
            t = tmp
            tmp = m0
            m0 = m1
            m1 = tmp
        }
        if (m0 < 0) m0 += b / s
        return longArrayOf(s, m0)
    }

    /**
     * xのn乗をmで割った余りを返す.
     */
    fun powMod(x: Long, n: Long, m: Int): Long {
        var vx = x
        var vn = n
        assert(vn >= 0)
        assert(m >= 1)
        if (m == 1) return 0L
        vx = safeMod(vx, m.toLong())
        var ans = 1L
        while (vn > 0) {
            if (vn and 1 == 1L) ans = ans * vx % m
            vx = vx * vx % m
            vn = vn ushr 1
        }
        return ans
    }

    fun crt(r: LongArray, m: LongArray): LongArray {
        assert(r.size == m.size)
        val n = r.size
        var r0: Long = 0
        var m0: Long = 1
        for (i in 0 until n) {
            assert(1 <= m[i])
            var r1 = safeMod(r[i], m[i])
            var m1 = m[i]
            if (m0 < m1) {
                var tmp = r0
                r0 = r1
                r1 = tmp
                tmp = m0
                m0 = m1
                m1 = tmp
            }
            if (m0 % m1 == 0L) {
                if (r0 % m1 != r1) return longArrayOf(0, 0)
                continue
            }
            val ig = invGcd(m0, m1)
            val g = ig[0]
            val im = ig[1]
            val u1 = m1 / g
            if ((r1 - r0) % g != 0L) return longArrayOf(0, 0)
            val x = (r1 - r0) / g % u1 * im % u1
            r0 += x * m0
            m0 *= u1
            if (r0 < 0) r0 += m0
            //System.err.printf("%d %d\n", r0, m0);
        }
        return longArrayOf(r0, m0)
    }

    fun floorSum(n: Long, m: Long, a: Long, b: Long): Long {
        var va = a
        var vb = b
        var ans: Long = 0
        if (va >= m) {
            ans += (n - 1) * n * (va / m) / 2
            va %= m
        }
        if (vb >= m) {
            ans += n * (vb / m)
            vb %= m
        }
        val yMax = (va * n + vb) / m
        val xMax = yMax * m - vb
        if (yMax == 0L) return ans
        ans += (n - (xMax + va - 1) / va) * yMax
        ans += floorSum(yMax, va, m, (va - xMax % va) % va)
        return ans
    }
}
