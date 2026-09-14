package uk.gov.onelogin.criorchestrator.testwrapper

/** Class that solely exists to provide a matching test that triggers the
 * sonar code coverage calculation **/
class DummyClass {
    fun double(value: Int): Int = value.times(DOUBLE)
}

private const val DOUBLE = 2
