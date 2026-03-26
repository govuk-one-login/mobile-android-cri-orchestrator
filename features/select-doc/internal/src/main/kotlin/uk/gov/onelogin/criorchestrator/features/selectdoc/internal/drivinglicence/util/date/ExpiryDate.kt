package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.util.date

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val gdsDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

/**
 * To be implemented in GOV.UK Design System: https://github.com/govuk-one-login/mobile-android-ui/pull/405
 */
fun LocalDate.formatGdsDate(): String = format(gdsDateFormatter)
