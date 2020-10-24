package jp.atcoder.library.kotlin.stringAlgorithm

/**
 * StringAlgorithm
 *
 * convert from [AtCoderLibraryForJava - StringAlgorithm](https://github.com/NASU41/AtCoderLibraryForJava/blob/df25ae43276dff808c3fff91baa6dab5be372c00/StringAlgorithm/StringAlgorithm.java)
 */
object StringAlgorithm {
    private fun saNaive(s: IntArray): IntArray {
        val n = s.size
        val sa = IntArray(n)
        for (i in 0 until n) {
            sa[i] = i
        }
        insertionsortUsingComparator(sa, java.util.function.IntBinaryOperator { l: Int, r: Int ->
            var vl = l
            var vr = r
            while (vl < n && vr < n) {
                if (s[vl] != s[vr]) return@IntBinaryOperator s[vl] - s[vr]
                vl++
                vr++
            }
            -(vl - vr)
        })
        return sa
    }

    private fun saDoubling(s: IntArray): IntArray {
        val n = s.size
        val sa = IntArray(n)
        for (i in 0 until n) {
            sa[i] = i
        }
        var rnk = s.copyOf(n)
        var tmp = IntArray(n)
        var k = 1
        while (k < n) {
            val vk = k
            val vrnk = rnk
            val cmp = java.util.function.IntBinaryOperator { x: Int, y: Int ->
                if (vrnk[x] != vrnk[y]) return@IntBinaryOperator vrnk[x] - vrnk[y]
                val rx = if (x + vk < n) vrnk[x + vk] else -1
                val ry = if (y + vk < n) vrnk[y + vk] else -1
                rx - ry
            }
            mergesortUsingComparator(sa, cmp)
            tmp[sa[0]] = 0
            for (i in 1 until n) {
                tmp[sa[i]] = tmp[sa[i - 1]] + if (cmp.applyAsInt(sa[i - 1], sa[i]) < 0) 1 else 0
            }
            val buf = tmp
            tmp = rnk
            rnk = buf
            k *= 2
        }
        return sa
    }

    private fun insertionsortUsingComparator(a: IntArray, comparator: java.util.function.IntBinaryOperator) {
        val n = a.size
        for (i in 1 until n) {
            val tmp = a[i]
            if (comparator.applyAsInt(a[i - 1], tmp) > 0) {
                var j = i
                do {
                    a[j] = a[j - 1]
                    j--
                } while (j > 0 && comparator.applyAsInt(a[j - 1], tmp) > 0)
                a[j] = tmp
            }
        }
    }

    private fun mergesortUsingComparator(a: IntArray, comparator: java.util.function.IntBinaryOperator) {
        val n = a.size
        val work = IntArray(n)
        var block = 1
        while (block <= n) {
            val block2 = block shl 1
            var l = 0
            val max = n - block
            while (l < max) {
                val m = l + block
                val r = kotlin.math.min(l + block2, n)
                System.arraycopy(a, l, work, 0, block)
                var i = l
                var wi = 0
                var ti = m
                while (true) {
                    if (ti == r) {
                        System.arraycopy(work, wi, a, i, block - wi)
                        break
                    }
                    if (comparator.applyAsInt(work[wi], a[ti]) > 0) {
                        a[i] = a[ti++]
                    } else {
                        a[i] = work[wi++]
                        if (wi == block) break
                    }
                    i++
                }
                l += block2
            }
            block = block shl 1
        }
    }

    private const val THRESHOLD_NAIVE = 50
    private const val THRESHOLD_DOUBLING = 0

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
        //		if (n < THRESHOLD_DOUBLING) {
        //			return saDoubling(s);
        //		}
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
        val induce = java.util.function.Consumer { lms: IntArray ->
            java.util.Arrays.fill(sa, -1)
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
        java.util.Arrays.fill(lmsMap, -1)
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
        val vals = s.copyOf(n)
        java.util.Arrays.sort(vals)
        var p = 1
        for (i in 1 until n) {
            if (vals[i] != vals[i - 1]) {
                vals[p++] = vals[i]
            }
        }
        val s2 = IntArray(n)
        for (i in 0 until n) {
            s2[i] = java.util.Arrays.binarySearch(vals, 0, p, s[i])
        }
        return sais(s2, p)
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
            var k = if (j + z[j] <= i) 0 else kotlin.math.min(j + z[j] - i, z[i - j])
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
            var k = if (j + z[j] <= i) 0 else kotlin.math.min(j + z[j] - i, z[i - j])
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
