package jp.atcoder.library.kotlin.dsu

import org.junit.Assert.*
import org.junit.Test

class DSUTest {
    @Test
    fun dsuTest() {
        val dsu = DSU(10)
        dsu.merge(2, 3)
        dsu.merge(4, 5)

        // 2-3, 4-5 connected.
        assertFalse(dsu.same(2, 5))
        dsu.merge(3, 4)

        // 2-3-4-5 connected.
        assertTrue(dsu.same(2, 5))
        assertEquals(4, dsu.size(2))

        // leader assertion.
        assertEquals(dsu.leader(2), dsu.leader(3))
        assertEquals(dsu.leader(2), dsu.leader(4))
        assertEquals(dsu.leader(2), dsu.leader(5))
        assertNotEquals(dsu.leader(0), dsu.leader(2))

        // groups assertion.
        val buckets = dsu.groups()
        assertEquals(7, buckets.size)
    }
}
