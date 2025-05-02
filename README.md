# mobile-android-cri-orchestrator

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mobile-android-cri-orchestrator&metric=coverage)](https://sonarcloud.io/summary/new_code?id=mobile-android-cri-orchestrator)

## About

The Credential Issuer (CRI) Orchestrator coordinates identity proofing capability for the [GOV.UK One Login app](https://github.com/govuk-one-login/mobile-android-one-login-app).
It builds on the [ID Check SDK](https://github.com/govuk-one-login/mobile-id-check-android-sdk) used in the [GOV.UK ID Check app](https://github.com/govuk-one-login/mobile-id-check-android) with additional functionality including REST API based web-to-app handoff and document selection.

Currently the only CRI supported within the SDK is the [Document Checking CRI (DCMAW)](https://github.com/govuk-one-login/mobile-id-check-async), but this component could be extended in the future to allow for additional app-based CRI‍s.
In particular, adding support for the Address-Check and Fraud CRI‍s into the app would enable a complete end-to-end identity proofing journey within the GOV.UK One Login app.

See the [Mobile App Integration](https://github.com/govuk-one-login/architecture/blob/main/adr/0178-mobile-app-integration.md) ADR for more details.

## Installation

Add the Maven repositories to your project:

```kt
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven(
            url = uri("https://maven.pkg.github.com/govuk-one-login/*"),
            credentials {
                // Github credentials with permissions to read packages.
                // Don't hardcode these.
                username = providers.gradleProperty("gpr.user") 
                password = providers.gradleProperty("gpr.token")
            }
        )
        maven("https://raw.githubusercontent.com/iProov/android/master/maven/")
    }
}
```

Add the SDK dependency to your project:

```toml
# gradle/libs.versions.toml
[versions]
criorchestrator = "" # https://github.com/govuk-one-login/mobile-android-cri-orchestrator/tags

[libraries]
criorchestrator = { group = "uk.gov.onelogin.criorchestrator.sdk", name = "sdk", version.ref = "criorchestrator" }
```


```kt
// build.gradle.kts
implementation(libs.criorchestrator)
```

## Usage

You can allow users to continue to prove their identity by placing the `ProveYourIdentityCard` on screen. Providing the user has an active session, it will display and launch the full-screen "You can now continue your identity check" modal.

See the [Orchestration of ID Check SDK in One Login app tech design](https://govukverify.atlassian.net/wiki/spaces/DCMAW/pages/3800006819/Orchestration+of+ID+Check+SDK+in+One+Login+app) for more details.

Since the ID Check SDK launched by the CRI Orchestrator uses view binding and data binding, consumers of the CRI Orchestrator will need to enable view binding and data binding in their app's `build.gradle` file:

```kt
// build.gradle.kts
android {
    buildFeatures {
        databinding = true
        viewbinding = true
    }
}
```

See the `test-wrapper` module for an example of how to set this up.

## Development

Clone the repository, including the Android pipelines submodule:
```bash
git clone --recurse-submodules git@github.com:govuk-one-login/mobile-android-cri-orchestrator.git
```
Learn more about working with [Git submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules).
