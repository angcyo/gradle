import java.io.FileInputStream
import java.util.Locale
import java.util.Properties

/*plugins {
    id("com.android.application")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}*/
apply(plugin = "com.android.application")
apply(plugin = "dev.flutter.flutter-gradle-plugin")

if (Integer.parseInt(com.android.Version.ANDROID_GRADLE_PLUGIN_VERSION.split(".")[0]) < 9) {
    //apply plugin: "kotlin-android"
    apply(plugin = "kotlin-android")
}
apply("${project.findProperty("gradleHost")}/master/key.gradle")
apply("${project.findProperty("gradleHost")}/master/v7v8a.gradle")
apply("${project.findProperty("gradleHost")}/master/collectProduct.gradle")

// 3. Kotlin DSL 中获取 Flutter 动态注入属性的辅助函数
val flutterExt = extensions.findByName("flutter")
fun getFlutterProp(propName: String): String? =
    flutterExt?.withGroovyBuilder { getProperty(propName) }
        ?.toString()

//2026-4-11
println("-------------------------build.gradle.kts-------------------------")
println("gradleVersion: ${gradle.gradleVersion}")
println("AGPVersion: ${com.android.Version.ANDROID_GRADLE_PLUGIN_VERSION}")
println("flutter.targetSdkVersion: " + getFlutterProp("targetSdkVersion"))    //36
println("flutter.compileSdkVersion: " + getFlutterProp("compileSdkVersion"))  //36
println("flutter.ndkVersion: " + getFlutterProp("ndkVersion"))                //28.2.13676358
println("flutter.minSdkVersion: " + getFlutterProp("minSdkVersion"))          //24
println("flutter.versionCode: " + getFlutterProp("versionCode"))              //1
println("flutter.versionName: " + getFlutterProp("versionName"))              //1.0.0
println("------------------------------------------------------------------")

val isLinux = !System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")

// 是否是打包 aab
// - assembleRelease
// - bundleRelease
val isBundleTask =
    gradle.startParameter.taskNames.any { it.contains("bundleRelease", ignoreCase = true) }

configure<com.android.build.api.dsl.ApplicationExtension> {
    namespace = extra["appId"]!!.toString()
    compileSdk = getFlutterProp("compileSdkVersion")!!.toInt()
    ndkVersion = getFlutterProp("ndkVersion")!!

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = extra["appId"]!!.toString()
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
        minSdk = getFlutterProp("minSdkVersion")!!.toInt()
        targetSdk = getFlutterProp("targetSdkVersion")!!.toInt()
        versionCode = getFlutterProp("versionCode")!!.toInt()
        versionName = getFlutterProp("versionName")

        // https://developer.android.com/topic/performance/app-optimization/customize-which-resources-to-keep?hl=zh-cn
        // 只保留特定语言和屏幕密度。
        // resConfigs "zh", "en", "xhdpi", "xxhdpi"
        //resConfigs("zh", "en", "xhdpi", "xxhdpi")
        //resourceConfigurations.addAll(listOf("zh", "en", "xhdpi", "xxhdpi"))
    }

    /*androidResources {
        localeFilters += setOf("zh", "en")
    }*/

/*    signingConfigs {
        // 打包 apk 时的签名
        create("laserabc") {
            storeFile =
                file(keystoreProperties[if (isLinux) "key.fileMac" else "key.file"] as String)
            storePassword = keystoreProperties["password"] as String
            keyAlias = keystoreProperties["key.alias"] as String
            keyPassword = keystoreProperties["key.password"] as String
        }
        // 打包 aab 时的签名, 用于提交到 Google Play, 最后 Google Play 打包 apk 时, 还是要用上面的签名.
        create("laserabc_upload") {
            storeFile =
                file(uploadKeystoreProperties[if (isLinux) "key.fileMac" else "key.file"] as String)
            storePassword = uploadKeystoreProperties["password"] as String
            keyAlias = uploadKeystoreProperties["key.alias"] as String
            keyPassword = uploadKeystoreProperties["key.password"] as String
        }
    }*/

    buildTypes {
        release {
            // TODO: Add your own signing config for the release build.
            // Signing with the debug keys for now, so `flutter run --release` works.
            //signingConfig = signingConfigs.getByName("debug")
            /*signingConfig = if (isBundleTask) {
                println("检测到 AAB 打包任务，使用签名配置->laserabc_upload")
                signingConfigs.getByName("laserabc_upload")
            } else {
                println("检测到 APK 打包任务，使用签名配置->laserabc")
                signingConfigs.getByName("laserabc")
            }*/

            isMinifyEnabled = true   // 启用代码缩减与混淆
            isShrinkResources = true // 启用资源缩减
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // https://docs.flutter.dev/release/breaking-changes/default-abi-filters-android
            // Clear the automatically set filters.
            ndk.abiFilters.clear()
            // Set your custom filters.
            //ndk.abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a"))
            ndk.abiFilters.addAll(listOf("arm64-v8a"))
        }
    }

    // 核心技巧：根据 Task 名称动态修改签名
    /*gradle.taskGraph.whenReady { taskGraph ->
        if (taskGraph.hasTask(":app:bundleRelease")) {
            println "检测到 AAB 打包任务，切换签名配置..."
            project.android.buildTypes.release.signingConfig = signingConfigs.laserabc_upload
        } else {
            println "使用默认 APK 签名配置..."
            project.android.buildTypes.release.signingConfig = signingConfigs.laserabc
        }
    }*/
    /*gradle.taskGraph.whenReady {
        // 检查任务图中是否包含 bundleRelease 任务
        allTasks.forEach { task ->
            //it.name.contains("bundleRelease", ignoreCase = true)
            println("task->${task.name}")
            if (task.name.contains("assembleRelease", ignoreCase = true)) {
                println("检测到 APK 打包任务，切换签名配置...")
                //android.buildTypes.release.signingConfig = signingConfigs.getByName("laserabc")
                //task.signingConfig = signingConfigs.getByName("laserabc")
                //(this as? PackageApplication)?.signingConfig
            }
        }
    }*/
}

if (Integer.parseInt(com.android.Version.ANDROID_GRADLE_PLUGIN_VERSION.split(".")[0]) >= 9) {
    project.extensions.configure(org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension::class.java) {
        compilerOptions {
            // 修复点：将字符串 "17" 改为严格对应的 JvmTarget 强类型枚举
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

extensions.findByName("flutter")?.withGroovyBuilder {
    invokeMethod("source", "../..")
}

dependencies {
    // https://mvnrepository.com/artifact/androidx.activity/activity
    //implementation("androidx.activity:activity:1.13.0")
}
