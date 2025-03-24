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
            url = uri("https://maven.pkg.github.com/govuk-one-login/*"),
            setupGithubCredentials()
        )
        maven(
            // imposter maven repository
            "https://s3-eu-west-1.amazonaws.com/gatehillsoftware-maven/releases/"
        )
        maven("https://raw.githubusercontent.com/iProov/android/master/maven/")
        maven("https://jitpack.io")
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
    ":features:config:internal",
    ":features:config:public-api",
    ":features:dev:internal",
    ":features:dev:internal-api",
    ":features:dev:public-api",
    ":features:resume:internal",
    ":features:resume:internal-api",
    ":features:resume:public-api",
    ":features:session:internal",
    ":features:session:internal-api",
    ":features:select-doc:internal",
    ":features:select-doc:internal-api",
    ":konsist-test",
    ":libraries:analytics",
    ":libraries:compose-utils",
    ":libraries:kotlin-utils",
    ":libraries:di",
    ":libraries:navigation",
    ":libraries:screenshot-testing",
    ":libraries:testing",
    ":sdk:internal",
    ":sdk:public-api",
    ":sdk:shared-api",
    ":test-wrapper",
    ":libraries:idchecksdk"
)
