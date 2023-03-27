dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/amarturelo/usersgithub-android-core")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_ACCESS_TOKEN")
            }
        }
    }
}

include(":followers")
if (System.getenv("IS_CICD") == null || System.getenv("IS_CICD") == "")
{
    println("logger: include module app")
    include (":app")
}

