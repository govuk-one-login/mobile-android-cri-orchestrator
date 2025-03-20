package uk.gov.onelogin.criorchestrator.libraries.analytics.resources

class FakeResourceProvider(
    val defaultEnglishString: String = "dummy string",
) : ResourceProvider {
    override fun getEnglishString(resId: Int): String = defaultEnglishString
}
