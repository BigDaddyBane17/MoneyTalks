pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MoneyTalks"
include(":app")
include(":core")
include(":core_ui")
include(":feature_account")
include(":feature_categories")
include(":feature_settings")
include(":feature_account:data")
include(":feature_account:domain")
include(":feature_categories:data")
include(":feature_categories:domain")
include(":feature_transactions:data")
include(":feature_transactions:domain")
include(":feature_transactions")

