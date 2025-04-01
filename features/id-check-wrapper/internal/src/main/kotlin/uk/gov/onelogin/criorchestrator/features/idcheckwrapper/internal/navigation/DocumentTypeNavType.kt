package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType

/**
 * This is required to serialize [DocumentType] into navigation arguments.
 */
internal object DocumentTypeNavType : NavType<DocumentType>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String,
    ): DocumentType? = bundle.getString(key)?.let { parseValue(it) }

    override fun put(
        bundle: Bundle,
        key: String,
        value: DocumentType,
    ) = bundle.putString(key, serializeAsValue(value))

    override fun parseValue(value: String): DocumentType = Json.decodeFromString<DocumentType>(value)

    override fun serializeAsValue(value: DocumentType): String = Json.encodeToString(value)
}
