// verification-helper: PROBLEM https://judge.yosupo.jp/problem/convolution_mod
package jp.atcoder.library.kotlin.convolution

fun main() {
    fun readLongArray() = readLine()!!.split(' ').map(java.lang.Long::parseLong).toLongArray()

    val (n, m) = readLine()!!.split(' ').map(Integer::parseInt)
    val aArray = readLongArray()
    val bArray = readLongArray()
    val ansArray = Convolution().convolution(aArray, bArray, 998_244_353)
    println(ansArray.joinToString(" "))
}