package jp.atcoder.library.kotlin.segTree

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.function.BinaryOperator
import java.util.function.Predicate
import kotlin.math.max

internal class SegTreeTest {
    @Test
    fun segTreeSum() {
        SegTree(arrayOf(1, 2, 3), BinaryOperator { t, u -> t + u }, 0).run {
            // [1, 2, 3]
            assertThat(prod(0, 0)).isEqualTo(0)
            assertThat(prod(0, 1)).isEqualTo(1)
            assertThat(prod(0, 3)).isEqualTo(6)
            assertThat(prod(3, 3)).isEqualTo(0)
            assertThat(allProd()).isEqualTo(6)

            // [4, 2, 3]
            this[0] = 4

            assertThat(prod(0, 0)).isEqualTo(0)
            assertThat(prod(0, 1)).isEqualTo(4)
            assertThat(prod(0, 3)).isEqualTo(9)
            assertThat(prod(3, 3)).isEqualTo(0)
            assertThat(allProd()).isEqualTo(9)
        }
    }

    @Test
    fun segTreeMax() {
        SegTree(arrayOf(1, 2, 3), BinaryOperator { t, u -> max(t, u) }, -1).run {
            // [1, 2, 3]
            assertThat(prod(0, 0)).isEqualTo(-1)
            assertThat(prod(0, 1)).isEqualTo(1)
            assertThat(prod(0, 3)).isEqualTo(3)
            assertThat(prod(3, 3)).isEqualTo(-1)
            assertThat(allProd()).isEqualTo(3)

            assertThat(minLeft(0, Predicate { it < 2 })).isEqualTo(0)
            assertThat(minLeft(1, Predicate { it < 2 })).isEqualTo(0)
            assertThat(minLeft(2, Predicate { it < 2 })).isEqualTo(2)
            assertThat(minLeft(3, Predicate { it < 2 })).isEqualTo(3)

            assertThat(maxRight(0, Predicate { it < 2 })).isEqualTo(1)
            assertThat(maxRight(1, Predicate { it < 2 })).isEqualTo(1)
            assertThat(maxRight(2, Predicate { it < 2 })).isEqualTo(2)
            assertThat(maxRight(3, Predicate { it < 2 })).isEqualTo(3)

            // [4, 2, 3]
            this[0] = 4

            assertThat(prod(0, 0)).isEqualTo(-1)
            assertThat(prod(0, 1)).isEqualTo(4)
            assertThat(prod(0, 3)).isEqualTo(4)
            assertThat(prod(3, 3)).isEqualTo(-1)
            assertThat(allProd()).isEqualTo(4)
        }
    }
}

