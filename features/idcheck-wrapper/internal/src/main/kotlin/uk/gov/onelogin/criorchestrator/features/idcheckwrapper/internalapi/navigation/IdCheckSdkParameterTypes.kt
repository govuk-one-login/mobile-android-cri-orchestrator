package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import uk.gov.idcheck.sdk.IdCheckSdkParameters

/**
 * This is required to serialize [IdCheckSdkParameters] into navigation arguments.
 */
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
