enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("${rootProject.projectDir}/build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(
            "https://maven.pkg.github.com/govuk-one-login/mobile-android-ui",
            setupGithubCredentials()
        )
    }
}

fun setupGithubCredentials(): MavenArtifactRepository.() -> Unit =
    {
        val (credUser, credToken) = fetchGithubCredentials()
        credentials {
            username = credUser
            password = credToken
        }
    }

fun fetchGithubCredentials(): Pair<String, String> {
    val gprUser = System.getenv("GITHUB_ACTOR")
    val gprToken = System.getenv("GITHUB_TOKEN")

    if (!gprUser.isNullOrEmpty() && !gprToken.isNullOrEmpty()) {
        return Pair(gprUser, gprToken)
    }

    val gprUserProperty = providers.gradleProperty("gpr.user")
    val gprTokenProperty = providers.gradleProperty("gpr.token")

    return gprUserProperty.get() to gprTokenProperty.get()
}

rootProject.name = "mobile-android-cri-orchestrator"

include(
    ":features:resume:internal",
    ":features:resume:internal-api",
    ":features:resume:public-api",
    ":features:session:internal",
    ":features:session:internal-api",
    ":konsist-test",
    ":libraries:android-utils",
    ":libraries:di",
    ":libraries:navigation",
    ":libraries:screenshot-testing",
    ":sdk:internal",
    ":sdk:public-api",
    ":sdk:shared-api",
    ":test-wrapper",
)
