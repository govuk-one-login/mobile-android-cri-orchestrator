package uk.gov.onelogin.criorchestrator.features.handback.internal.novalidsession

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class NoValidSessionErrorProvider : PreviewParameterProvider<NoValidSessionErrorParameters> {
    override val values: Sequence<NoValidSessionErrorParameters> =
        sequenceOf(
            NoValidSessionErrorParameters(
                title = "We were not able to confirm your identity",
                para1 =
                    "This could be because you started proving your identity more than " +
                        "an hour ago.",
                para2 = "You need to start again.",
                para3 =
                    "Go back to the GOV.UK website. Find the service you need to use " +
                        "and try to prove your identity again.",
                isV1 = false,
            ),
            NoValidSessionErrorParameters(
                title = "Nid oeddem yn gallu cadarnhau eich hunaniaeth",
                para1 =
                    "Gall hyn fod oherwydd eich bod wedi dechrau profi eich hunaniaeth " +
                        "mwy nag awr yn ôl.",
                para2 = "Mae angen i chi ddechrau eto.",
                para3 =
                    "Ewch yn ôl i wefan GOV.UK. Dewch o hyd i\\'r gwasanaeth roeddech yn " +
                        "ceisio ei ddefnyddio a cheisiwch brofi eich hunaniaeth eto.",
                isV1 = false,
            ),
        )
}
