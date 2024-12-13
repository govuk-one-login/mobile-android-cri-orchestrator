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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
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