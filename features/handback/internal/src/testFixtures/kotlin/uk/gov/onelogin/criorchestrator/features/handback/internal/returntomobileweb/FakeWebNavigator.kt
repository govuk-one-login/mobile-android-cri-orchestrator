package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

class FakeWebNavigator : WebNavigator {
    var openUrl: String? = null

    override fun openWebPage(url: String) {
        openUrl = url
    }
}
