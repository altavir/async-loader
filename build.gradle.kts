import scientifik.useCoroutines

plugins {
    val toolsVersion = "0.5.2"
    id("scientifik.mpp") version toolsVersion
    id("scientifik.publish") version toolsVersion
}

useCoroutines()

group = "scientifik"
version = "0.0.1"

kotlin {
    sourceSets {
        val commonMain by getting{
            dependencies{
                implementation("io.github.microutils:kotlin-logging-common:1.8.3")
            }
        }
        val jvmMain by getting{
            dependencies{
                implementation("io.github.microutils:kotlin-logging:1.8.3")
            }
        }
        val jsMain by getting{
            dependencies {
                implementation("io.github.microutils:kotlin-logging-js:1.8.3")
                implementation(npm("fetch-inject","2.0.4"))
            }
        }
    }
}