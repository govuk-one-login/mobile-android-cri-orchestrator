package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityNavGraphProvider
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject
import kotlin.reflect.typeOf

@ContributesMultibinding(CriOrchestratorScope::class)
class IdCheckNavGraphProvider
@Inject constructor() : ProveYourIdentityNavGraphProvider {
    override fun NavGraphBuilder.contributeToGraph(navController: NavController) {
        composable<IdCheckDestinations.SyncIdCheck>(
            typeMap = mapOf(typeOf<IdCheckSdkParameters>() to IdCheckSdkParameterType)
        ) {
            val args = it.toRoute<IdCheckDestinations.SyncIdCheck>()
            SyncIdCheckScreen(
                args.idCheckSdkParameters,
            )
        }
    }
}

sealed interface IdCheckDestinations : ProveYourIdentityDestinations {
    @Serializable
    data class SyncIdCheck(
        val idCheckSdkParameters: IdCheckSdkParameters
    ) : IdCheckDestinations
}

internal val IdCheckSdkParameterType = object : NavType<IdCheckSdkParameters>(
    isNullableAllowed = false,
) {
    override fun get(
        bundle: Bundle,
        key: String
    ): IdCheckSdkParameters? = bundle.getString(key)?.let { parseValue(it) }

    override fun put(
        bundle: Bundle,
        key: String,
        value: IdCheckSdkParameters
    ) = bundle.putString(key, serializeAsValue(value))

    override fun parseValue(value: String): IdCheckSdkParameters =
        Json.decodeFromString<IdCheckSdkParameters>(value)

    override fun serializeAsValue(value: IdCheckSdkParameters): String =
        Json.encodeToString(value)
}
