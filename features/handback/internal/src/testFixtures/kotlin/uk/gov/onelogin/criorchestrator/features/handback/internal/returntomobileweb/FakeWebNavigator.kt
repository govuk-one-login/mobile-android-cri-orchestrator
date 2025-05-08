package uk.gov.onelogin.criorchestrator.features.handback.internal.returntomobileweb

import uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb.WebNavigator

class FakeWebNavigator : WebNavigator {
    var openUrl: String? = null

    override fun openWebPage(url: String) {
        openUrl = url
    }
}
