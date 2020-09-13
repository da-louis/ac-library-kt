package jp.atcoder.library.kotlin.stringAlgorithm

import java.lang.Integer.min
import java.util.*
import java.util.function.Consumer

object StringAlgorithm {
    private fun saNaive(s: IntArray): IntArray {
        val n = s.size
        val sb = arrayOfNulls<Int>(n)
        for (i in 0 until n) {
            sb[i] = i
        }
        Arrays.sort(sb) { l: Int?, r: Int? ->
            var vl = l
            var vr = r
            while (vl!! < n && vr!! < n) {
                if (s[vl] != s[vr]) return@sort s[vl] - s[vr]
                vl++
                vr++
            }
            -(vl - vr!!)
        }
        val sa = IntArray(n)
        for (i in 0 until n) {
            sa[i] = sb[i]!!
        }
        return sa
    }

    private fun saDoubling(s: IntArray): IntArray {
        val n = s.size
        val sb = arrayOfNulls<Int>(n)
        for (i in 0 until n) {
            sb[i] = i
        }
        var rnk = s
        var tmp = IntArray(n)
        var k = 1
        while (k < n) {
            val vk = k
            val vrnk = rnk
            val cmp = Comparator { x: Int?, y: Int? ->
                if (vrnk[x!!] != vrnk[y!!]) {
                    vrnk[x] - vrnk[y]
                } else {
                    val rx = if (x + vk < n) vrnk[x + vk] else -1
                    val ry = if (y + vk < n) vrnk[y + vk] else -1
                    rx - ry
                }
            }
            Arrays.sort(sb, cmp)
            tmp[sb[0]!!] = 0
            for (i in 1 until n) {
                tmp[sb[i]!!] = tmp[sb[i - 1]!!] + if (cmp.compare(sb[i - 1], sb[i]) < 0) 1 else 0
            }
            val buf = tmp
            tmp = rnk
            rnk = buf
            k *= 2
        }
        val sa = IntArray(n)
        for (i in 0 until n) {
            sa[i] = sb[i]!!
        }
        return sa
    }

    private const val THRESHOLD_NAIVE = 10
    private const val THRESHOLD_DOUBLING = 40
    private fun sais(s: IntArray, upper: Int): IntArray {
        val n = s.size
        if (n == 0) return IntArray(0)
        if (n == 1) return intArrayOf(0)
        if (n == 2) {
            return if (s[0] < s[1]) {
                intArrayOf(0, 1)
            } else {
                intArrayOf(1, 0)
            }
        }
        if (n < THRESHOLD_NAIVE) {
            return saNaive(s)
        }
        if (n < THRESHOLD_DOUBLING) {
            return saDoubling(s)
        }
        val sa = IntArray(n)
        val ls = BooleanArray(n)
        for (i in n - 2 downTo 0) {
            ls[i] = if (s[i] == s[i + 1]) ls[i + 1] else s[i] < s[i + 1]
        }
        val sumL = IntArray(upper + 1)
        val sumS = IntArray(upper + 1)
        for (i in 0 until n) {
            if (ls[i]) {
                sumL[s[i] + 1]++
            } else {
                sumS[s[i]]++
            }
        }
        for (i in 0..upper) {
            sumS[i] += sumL[i]
            if (i < upper) sumL[i + 1] += sumS[i]
        }
        val induce = Consumer { lms: IntArray ->
            Arrays.fill(sa, -1)
            val buf = IntArray(upper + 1)
            System.arraycopy(sumS, 0, buf, 0, upper + 1)
            for (d in lms) {
                if (d == n) continue
                sa[buf[s[d]]++] = d
            }
            System.arraycopy(sumL, 0, buf, 0, upper + 1)
            sa[buf[s[n - 1]]++] = n - 1
            for (i in 0 until n) {
                val v = sa[i]
                if (v >= 1 && !ls[v - 1]) {
                    sa[buf[s[v - 1]]++] = v - 1
                }
            }
            System.arraycopy(sumL, 0, buf, 0, upper + 1)
            for (i in n - 1 downTo 0) {
                val v = sa[i]
                if (v >= 1 && ls[v - 1]) {
                    sa[--buf[s[v - 1] + 1]] = v - 1
                }
            }
        }
        val lmsMap = IntArray(n + 1)
        Arrays.fill(lmsMap, -1)
        var m = 0
        for (i in 1 until n) {
            if (!ls[i - 1] && ls[i]) {
                lmsMap[i] = m++
            }
        }
        val lms = IntArray(m)
        run {
            var p = 0
            for (i in 1 until n) {
                if (!ls[i - 1] && ls[i]) {
                    lms[p++] = i
                }
            }
        }
        induce.accept(lms)
        if (m > 0) {
            val sortedLms = IntArray(m)
            run {
                var p = 0
                for (v in sa) {
                    if (lmsMap[v] != -1) {
                        sortedLms[p++] = v
                    }
                }
            }
            val recS = IntArray(m)
            var recUpper = 0
            recS[lmsMap[sortedLms[0]]] = 0
            for (i in 1 until m) {
                var l = sortedLms[i - 1]
                var r = sortedLms[i]
                val endL = if (lmsMap[l] + 1 < m) lms[lmsMap[l] + 1] else n
                val endR = if (lmsMap[r] + 1 < m) lms[lmsMap[r] + 1] else n
                var same = true
                if (endL - l != endR - r) {
                    same = false
                } else {
                    while (l < endL && s[l] == s[r]) {
                        l++
                        r++
                    }
                    if (l == n || s[l] != s[r]) same = false
                }
                if (!same) {
                    recUpper++
                }
                recS[lmsMap[sortedLms[i]]] = recUpper
            }
            val recSA = sais(recS, recUpper)
            for (i in 0 until m) {
                sortedLms[i] = lms[recSA[i]]
            }
            induce.accept(sortedLms)
        }
        return sa
    }

    fun suffixArray(s: IntArray, upper: Int): IntArray {
        assert(0 <= upper)
        for (d in s) {
            assert(d in 0..upper)
        }
        return sais(s, upper)
    }

    fun suffixArray(s: IntArray): IntArray {
        val n = s.size
        val idx = arrayOfNulls<Int>(n)
        for (i in 0 until n) {
            idx[i] = i
        }
        Arrays.sort(idx) { l: Int?, r: Int? -> s[l!!] - s[r!!] }
        val s2 = IntArray(n)
        var now = 0
        for (i in 0 until n) {
            if (i > 0 && s[idx[i - 1]!!] != s[idx[i]!!]) {
                now++
            }
            s2[idx[i]!!] = now
        }
        return sais(s2, now)
    }

    fun suffixArray(s: CharArray): IntArray {
        val n = s.size
        val s2 = IntArray(n)
        for (i in 0 until n) {
            s2[i] = s[i].toInt()
        }
        return sais(s2, 255)
    }

    fun suffixArray(s: String): IntArray {
        return suffixArray(s.toCharArray())
    }

    fun lcpArray(s: IntArray, sa: IntArray): IntArray {
        val n = s.size
        assert(n >= 1)
        val rnk = IntArray(n)
        for (i in 0 until n) {
            rnk[sa[i]] = i
        }
        val lcp = IntArray(n - 1)
        var h = 0
        for (i in 0 until n) {
            if (h > 0) h--
            if (rnk[i] == 0) {
                continue
            }
            val j = sa[rnk[i] - 1]
            while (j + h < n && i + h < n) {
                if (s[j + h] != s[i + h]) break
                h++
            }
            lcp[rnk[i] - 1] = h
        }
        return lcp
    }

    fun lcpArray(s: CharArray, sa: IntArray): IntArray {
        val n = s.size
        val s2 = IntArray(n)
        for (i in 0 until n) {
            s2[i] = s[i].toInt()
        }
        return lcpArray(s2, sa)
    }

    fun lcpArray(s: String, sa: IntArray): IntArray {
        return lcpArray(s.toCharArray(), sa)
    }

    fun zAlgorithm(s: IntArray): IntArray {
        val n = s.size
        if (n == 0) return IntArray(0)
        val z = IntArray(n)
        var i = 1
        var j = 0
        while (i < n) {
            var k = if (j + z[j] <= i) 0 else min(j + z[j] - i, z[i - j])
            while (i + k < n && s[k] == s[i + k]) k++
            z[i] = k
            if (j + z[j] < i + z[i]) j = i
            i++
        }
        z[0] = n
        return z
    }

    fun zAlgorithm(s: CharArray): IntArray {
        val n = s.size
        if (n == 0) return IntArray(0)
        val z = IntArray(n)
        var i = 1
        var j = 0
        while (i < n) {
            var k = if (j + z[j] <= i) 0 else min(j + z[j] - i, z[i - j])
            while (i + k < n && s[k] == s[i + k]) k++
            z[i] = k
            if (j + z[j] < i + z[i]) j = i
            i++
        }
        z[0] = n
        return z
    }

    fun zAlgorithm(s: String): IntArray {
        return zAlgorithm(s.toCharArray())
    }
}
