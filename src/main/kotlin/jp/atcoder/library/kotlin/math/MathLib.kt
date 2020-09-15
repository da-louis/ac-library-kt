package jp.atcoder.library.kotlin.math

internal object MathLib {
    /**
     * xをmで割った余りを返す.
     */
    fun safeMod(x: Long, m: Long): Long {
        val x = x % m
        return if (x < 0) x + m else x
    }

    fun invGcd(a: Long, b: Long): LongArray {
        var a = a
        a = safeMod(a, b)
        if (a == 0L) return longArrayOf(b, 0)
        var s = b
        var t = a
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
    fun powMod(x: Long, n: Long, m: Long): Long {
        assert(n >= 0 && m >= 1)
        var x = x
        var n = n
        var ans = 1L
        while (n > 0) {
            if (n % 2 == 1L) ans = ans * x % m
            x = x * x % m
            n /= 2
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
        var a = a
        var b = b
        var ans: Long = 0
        if (a >= m) {
            ans += (n - 1) * n * (a / m) / 2
            a %= m
        }
        if (b >= m) {
            ans += n * (b / m)
            b %= m
        }
        val yMax = (a * n + b) / m
        val xMax = yMax * m - b
        if (yMax == 0L) return ans
        ans += (n - (xMax + a - 1) / a) * yMax
        ans += floorSum(yMax, a, m, (a - xMax % a) % a)
        return ans
    }
}