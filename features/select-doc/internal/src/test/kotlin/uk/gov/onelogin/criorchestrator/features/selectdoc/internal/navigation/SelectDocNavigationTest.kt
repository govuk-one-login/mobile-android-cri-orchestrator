package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.navigation

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.serialization.Serializable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verifyNoMoreInteractions
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.SelectDocNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.GetJourneyType
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.StubGetJourneyType
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.FakeResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.ResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.composeutils.goBack
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelFactoryBindings
import uk.gov.onelogin.criorchestrator.libraries.navigation.CompositeNavHost
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

@RunWith(AndroidJUnit4::class)
class SelectDocNavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val onFinish = mock<() -> Unit>()

    @Inject
    lateinit var navGraphProvider: SelectDocNavGraphProvider

    @Inject
    lateinit var metroVmf: MetroViewModelFactory

    @Before
    fun setUp() {
        createGraphFactory<TestGraph.Factory>().create().inject(this)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(onFinish)
    }

    @Test
    fun `select passport`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.Passport,
        )

        composeTestRule.clickStart()
        composeTestRule.assertSelectPassportIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `select driving licence`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.DrivingLicence,
        )

        composeTestRule.clickStart()
        composeTestRule.assertSelectDrivingLicenceIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `select brp`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.Brp,
        )

        composeTestRule.clickStart()
        composeTestRule.assertSelectBrpIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `confirm passport`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.ConfirmPassport,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmPassportIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `confirm driving licence`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.ConfirmDrivingLicence,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmDrivingLicenceIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `confirm brp`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.ConfirmBrp,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmBrpIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `confirm no non-chipped id`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.ConfirmNoNonChippedID,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmNoNonChippedIdIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `confirm no chipped id`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.ConfirmNoChippedID,
        )

        composeTestRule.clickStart()
        composeTestRule.assertConfirmNoChippedIdIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    @Test
    fun `types of photo id`() {
        composeTestRule.setNavGraphContent(
            startNavigatesTo = SelectDocDestinations.TypesOfPhotoID,
        )

        composeTestRule.clickStart()
        composeTestRule.assertTypesOfPhotoIdIsDisplayed()

        // Back navigation is enabled
        composeTestRule.goBack()
        composeTestRule.assertStartIsDisplayed()
    }

    private fun ComposeTestRule.assertStartIsDisplayed() =
        onNodeWithText(START_BUTTON)
            .assertIsDisplayed()

    private fun ComposeTestRule.clickStart() =
        onNodeWithText(START_BUTTON)
            .performClick()

    private fun ComposeTestRule.assertSelectPassportIsDisplayed() =
        onNodeWithText(context.getString(R.string.selectdocument_passport_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertSelectDrivingLicenceIsDisplayed() =
        onAllNodesWithText(context.getString(R.string.selectdocument_drivinglicence_title))
            .onFirst()
            .assertIsDisplayed()

    private fun ComposeTestRule.assertSelectBrpIsDisplayed() =
        onNodeWithText(context.getString(R.string.selectdocument_brp_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertConfirmPassportIsDisplayed() =
        onNodeWithText(context.getString(R.string.confirmdocument_passport_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertConfirmDrivingLicenceIsDisplayed() =
        onNodeWithText(context.getString(R.string.confirmdocument_drivinglicence_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertConfirmNoNonChippedIdIsDisplayed() =
        onNodeWithText(context.getString(R.string.confirm_nononchippedid_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertConfirmNoChippedIdIsDisplayed() =
        onNodeWithText(context.getString(R.string.confirm_nochippedid_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertConfirmBrpIsDisplayed() =
        onNodeWithText(context.getString(R.string.confirmdocument_brp_title))
            .assertIsDisplayed()

    private fun ComposeTestRule.assertTypesOfPhotoIdIsDisplayed() =
        onNodeWithText(context.getString(R.string.typesofphotoid_title))
            .assertIsDisplayed()

    private fun ComposeContentTestRule.setNavGraphContent(startNavigatesTo: NavigationDestination) =
        setContent {
            CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
                CompositeNavHost(
                    navGraphProviders =
                        persistentSetOf(
                            InitialNavGraphProvider(navigatesTo = startNavigatesTo),
                            navGraphProvider,
                        ),
                    startDestination = InitialNavGraphStart,
                    onFinish = onFinish,
                )
            }
        }
}

private const val START_BUTTON = "Start button"

@Serializable
internal data object InitialNavGraphStart : NavigationDestination

private class InitialNavGraphProvider(
    private val navigatesTo: NavigationDestination,
) : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(
        navController: NavController,
        onFinish: () -> Unit,
    ) {
        composable<InitialNavGraphStart> {
            Button(
                onClick = { navController.navigate(navigatesTo) },
            ) {
                Text(START_BUTTON)
            }
        }
    }
}

@DependencyGraph(
    scope = CriOrchestratorScope::class,
    bindingContainers = [ViewModelFactoryBindings::class],
)
interface TestGraph : ViewModelGraph {
    fun inject(test: SelectDocNavigationTest)

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides
            analytics: SelectDocAnalytics = mock(),
            @Provides
            getJourneyType: GetJourneyType = StubGetJourneyType(),
            @Provides
            nfcChecker: NfcChecker = NfcChecker { true },
            @Provides
            resourceProvider: ResourceProvider = FakeResourceProvider(),
        ): TestGraph
    }
}
