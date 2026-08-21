plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.sonarsource.scanner.gradle" &&
            requested.name == "sonarqube-gradle-plugin"
        ) {
            useVersion("7.4.0.8496")
        }
    }
}

dependencies {
    listOf(
        libs.android.build.tool,
        libs.kotlin.gradle.plugin,
        libs.ksp.gradle.plugin,
        libs.metro.gradle.plugin,
        libs.uk.gov.pipelines.plugins,
    ).forEach {
        implementation(it)
    }

    //https://github.com/gradle/gradle/issues/15383
    implementation(files((libs as Any).javaClass.superclass.protectionDomain.codeSource.location))
}