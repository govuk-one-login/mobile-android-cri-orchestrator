package uk.gov.onelogin.criorchestrator.features.selectdoc.internal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.squareup.anvil.annotations.ContributesMultibinding
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.SelectPassportScreen
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocumentDestinations
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesMultibinding(CriOrchestratorScope::class)
class SelectDocumentNavGraphProvider
    @Inject
    constructor() : ProveYourIdentityNavGraphProvider {
        override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
            composable<SelectDocumentDestinations.Passport> {
                SelectPassportScreen()
            }

            composable<SelectDocumentDestinations.DrivingLicence> {
            }
        }
    }
