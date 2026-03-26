package uk.gov.onelogin.criorchestrator.libraries.testing.time

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * Create a fixed clock for testing.
 */
fun testClock(
    year: Int = 2026,
    month: Int = 3,
    dayOfMonth: Int = 26,
    zoneId: ZoneId = ZoneOffset.UTC,
): Clock =
    Clock.fixed(
        LocalDate
            .of(year, month, dayOfMonth)
            .atStartOfDay(zoneId)
            .toInstant(),
        zoneId,
    )
