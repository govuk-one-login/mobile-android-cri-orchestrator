package uk.gov.onelogin.criorchestrator.libraries.screenshottesting

import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode.SHRINK
import org.junit.rules.TestRule

/**
 * Creates a test rule that works around a compatibility issue between Molecule and Paparazzi.
 *
 * Use this test rule in tests using Molecule that live alongside Paparazzi screenshot tests.
 *
 * This is implemented as a JUnit 4 rule because it relies on Paparazzi which only supports
 * JUnit 4. The consequence is that Molecule tests needing to apply the rule also need to use
 * JUnit 4.
 *
 * When this rule is no longer needed, upgrade Molecule tests to JUnit 5.
 *
 * ## Related issues
 *
 * - https://github.com/cashapp/molecule/issues/321
 * - https://github.com/cashapp/paparazzi/issues/1149
 * - https://issuetracker.google.com/issues/375395107#comment2
 */
fun createMoleculePaparazziCompatRule(): TestRule =
    Paparazzi(
        renderingMode = SHRINK,
        showSystemUi = false,
    )
