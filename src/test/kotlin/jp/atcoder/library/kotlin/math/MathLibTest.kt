package jp.atcoder.library.kotlin.math

import org.junit.Assert.*
import org.junit.Test

class MathLibTest {
    // SafeModTest
    @Test
    fun positiveXMod() = assertEquals(1, MathLib.safeMod(1_000_000_008, 1_000_000_007))

    @Test
    fun negativeXMod() = assertEquals(1, MathLib.safeMod(-1_000_000_006, 1_000_000_007))

    // リファクタ時にバグらせやすい気がするので記載
    // x % mが0のとき、if (x < 0) (x % m) + m else x % mのようにすると0ではなくmになる
    @Test
    fun negativeXModMIsZero() = assertEquals(0, MathLib.safeMod(-2 * 1_000_000_007, 1_000_000_007))




    // PowModTest
    @Test
    fun nIsPositive() = assertEquals(73741817, MathLib.powMod(2, 30, 1_000_000_007))

    @Test
    fun nIszero() = assertEquals(1, MathLib.powMod(100, 0, 1_000_000_007))
}