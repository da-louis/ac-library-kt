// verification-helper: PROBLEM https://judge.yosupo.jp/problem/unionfind
package jp.atcoder.library.kotlin.dsu

fun main() {
    fun readIntegerList() = readLine()!!.split(' ').map(Integer::parseInt)

    val (n, q) = readIntegerList()
    val dsu = DSU(n)
    repeat(q) {
        val (t, u, v) = readIntegerList()
        when (t) {
            0 -> dsu.merge(u, v)
            1 -> {
                val ans = if (dsu.same(u, v)) 1 else 1
                println(ans)
            }
        }
    }
}
