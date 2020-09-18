package jp.atcoder.library.kotlin.modInt

import org.junit.Assert.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class FenwickTreeTest {
    @Test
    fun modIntTest1e9_7() {
        val factory = ModIntFactory(1e9.toInt() + 7)
        val a = factory.create(107)
        val b = factory.create(109)

        // Simple multiplication.
        assertEquals(factory.create(109 * 107), a.mul(b))

        // Inverse.
        val c = a.mul(a.inv())
        assertEquals(factory.create(1), c)

        // Overflow test.
        assertTrue(a.pow(10000).value > 0)
    }
}

class ModIntFactoryTest {
    private val mod1000000007 = 1_000_000_007
    private lateinit var modFactory1000000007: ModIntFactory

    @Before
    fun setUp() {
        // 今の所immutableですが念の為ここでセット
        modFactory1000000007 = ModIntFactory(mod1000000007)
    }

    @Test
    fun createModIntWithPositiveValue() {
        assertThat(modFactory1000000007.create(2L * mod1000000007 + 5).value)
            .isEqualTo(5)
    }

    @Test
    fun createModIntWithNegativeValue() {
        assertThat(modFactory1000000007.create(-2L * mod1000000007 + 5L).value)
            .isEqualTo(5)
        assertThat(modFactory1000000007.create(-2L * mod1000000007).value)
            .isEqualTo(0)
    }
}

class ModIntTest {
    private val mod1000000007 = 1_000_000_007
    private lateinit var modFactory1000000007: ModIntFactory

    @Before
    fun setUp() {
        modFactory1000000007 = ModIntFactory(mod1000000007)
    }

    @Test
    fun haveMod() {
        assertThat(modFactory1000000007.create(2L * mod1000000007).mod)
            .isEqualTo(mod1000000007)
    }

    @Test
    fun haveValue() {
        assertThat(modFactory1000000007.create(2L * mod1000000007 + 5).value)
            .isEqualTo(5)
    }
}
