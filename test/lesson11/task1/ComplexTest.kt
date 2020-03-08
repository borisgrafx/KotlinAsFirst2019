package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ComplexTest {

    private fun assertApproxEquals(expected: Complex, actual: Complex, eps: Double) {
        assertEquals(expected.re, actual.re, eps)
        assertEquals(expected.im, actual.im, eps)
    }

    @Test
    fun plus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("7-10i"), Complex("2-3i") + Complex("5-7i"), 1e-10)
    }

    @Test
    operator fun unaryMinus() {
        assertApproxEquals(Complex(1.0, -2.0), -Complex(-1.0, 2.0), 1e-10)
        assertApproxEquals(Complex(0.0, 0.0), -Complex(0.0, 0.0), 1e-10)
    }

    @Test
    fun minus() {
        assertApproxEquals(Complex("-2+6i"), Complex("1+2i") - Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("-5+7i"), Complex("0-0i") - Complex("5-7i"), 1e-10)
    }

    @Test
    fun times() {
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("-11-29i"), Complex("2-3i") * Complex("5-7i"), 1e-10)
    }

    @Test
    fun div() {
        assertApproxEquals(Complex("1+1i"), Complex("-1+3i") / Complex("1+2i"), 1e-10)
        assertApproxEquals(Complex("-0.75+0.25i"), Complex("-1+2i") / Complex("2-2i"), 1e-10)
    }

    @Test
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), Complex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
        assertEquals("1+2i", Complex("1+2i").toString())
        assertEquals("11-8i", Complex("11-8i").toString())
        assertEquals("1+0i", Complex(1.0).toString())
    }
}