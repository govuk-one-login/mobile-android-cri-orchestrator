package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import uk.gov.onelogin.criorchestrator.libraries.screenshottesting.createMoleculePaparazziCompatRule

class ProveYourIdentityModalStateTest {
    @get:Rule
    val moleculePaparazziCompatRule = createMoleculePaparazziCompatRule()

    @Test
    fun `given initially allowed to show, initial state is allowed to show`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                val state =
                    rememberProveYourIdentityModalState(
                        initiallyAllowedToShow = true,
                    )
                state to state.allowedToShow
            }.test {
                awaitItem().also { (_, allowedToShow) ->
                    assertEquals(allowedToShow, true)
                }
            }
        }

    @Test
    fun `given not initially allowed to show, initial state is not allowed to show`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                val state =
                    rememberProveYourIdentityModalState(
                        initiallyAllowedToShow = false,
                    )
                state to state.allowedToShow
            }.test {
                awaitItem().also { (_, allowedToShow) ->
                    assertEquals(allowedToShow, false)
                }
            }
        }

    @Test
    fun `when dismissed, it is not allowed to show`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                val state =
                    rememberProveYourIdentityModalState(
                        initiallyAllowedToShow = true,
                    )
                state to state.allowedToShow
            }.test {
                awaitItem().also { (state, _) ->
                    state.onDismissRequest()
                }

                awaitItem().also { (_, allowedToShow) ->
                    assertEquals(allowedToShow, false)
                }
            }
        }

    @Test
    fun `when dismissed and then shown, it is allowed to show`() =
        runTest {
            moleculeFlow(RecompositionMode.Immediate) {
                val state =
                    rememberProveYourIdentityModalState(
                        initiallyAllowedToShow = true,
                    )
                state to state.allowedToShow
            }.test {
                awaitItem().also { (state, _) -> state.onDismissRequest() }
                awaitItem().also { (state, _) -> state.allowToShow() }

                awaitItem().also { (_, allowedToShow) ->
                    assertEquals(allowedToShow, true)
                }
            }
        }
}
