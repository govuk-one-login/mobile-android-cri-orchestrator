import uk.gov.pipelines.config.ApkConfig

plugins {
    id("uk.gov.pipelines.android-lib-config")
}

android {
    buildFeatures {
        buildConfig = true
    }

    val apkConfig: ApkConfig by project.rootProject.extra
    defaultConfig {
        namespace = apkConfig.applicationId + ".api"
        compileSdk = apkConfig.sdkVersions.compile
        minSdk = apkConfig.sdkVersions.minimum
        testInstrumentationRunner = "uk.gov.criorchestrator.sdk.InstrumentationTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    listOf(
        libs.androidx.espresso.core,
        libs.androidx.test.espresso.accessibility,
        libs.androidx.junit,
        libs.hilt.android.testing,
    ).forEach {
        androidTestImplementation(it)
    }

    androidTestUtil(libs.androidx.test.orchestrator)

    listOf(
        libs.androidx.appcompat,
        libs.androidx.core.ktx,
        libs.hilt.android,
        libs.material,
    ).forEach {
        implementation(it)
    }

    listOf(
        libs.junit
    ).forEach {
        testImplementation(it)
    }
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "CRI Orchestrator SDK",
        )
        description.set(
            """
            Contains logic, screens, interfaces, use cases and implementations core to the CRI Orchestrator.
            """.trimIndent(),
        )
    }
}
