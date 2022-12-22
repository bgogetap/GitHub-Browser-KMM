pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    // KMM modules download Kotlin/Native compiler on their own. Any other repositoriesMode will
    // fail (for now).
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.51.0"
}

rootProject.name = "GitHubBrowser"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

val modules = mutableSetOf<String>()
val ignoredDirectories = setOf("build", "buildSrc", "gradle", ".idea")

fun fillModules(files: List<File>, targetPath: String) {
    files.forEach { file ->
        if (file.isFile) {
            if (file.name == "build.gradle" || file.name == "build.gradle.kts") {
                modules.add(targetPath)
            }
        } else {
            if (ignoredDirectories.contains(file.name)) {
                return@forEach
            }
            val updatedParentPath = "$targetPath:${file.name}"
            val childFiles =
                file.listFiles()?.filter { it.isDirectory || it.name.contains(".gradle") }
            if (childFiles != null) {
                fillModules(childFiles, updatedParentPath)
            }
        }
    }
}

val projectDirectories = checkNotNull(
    rootProject.projectDir.listFiles()
        ?.filter { it.isDirectory }
) { "No directories in project" }

val now = System.currentTimeMillis()
fillModules(
    files = projectDirectories, targetPath = ""
)
println("Finding ${modules.size} modules took ${System.currentTimeMillis() - now}ms")
modules.forEach { include(it) }
