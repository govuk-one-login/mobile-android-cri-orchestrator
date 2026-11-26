package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.provider.KoSourceSetProvider

private const val ANDROID_TEST = "androidTest"
private const val TEST = "test"

fun <T : KoSourceSetProvider> Iterable<T>.withTestSourceSet(): List<T> = filter {
    it.sourceSetName.contains(TEST, ignoreCase = true) &&
        !it.sourceSetName.contains(ANDROID_TEST, ignoreCase = true)
}

fun <T : KoSourceSetProvider> Iterable<T>.withAndroidTestSourceSet(): List<T> = filter {
    it.sourceSetName.contains(ANDROID_TEST, ignoreCase = true)
}
