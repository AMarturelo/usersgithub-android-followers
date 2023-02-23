dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://localhost:8081/repository/maven-snapshots/")
        }
    }
}

include(":followers")
if (System.getenv("IS_CICD") == null || System.getenv("IS_CICD") == "")
{
    println("logger: include module app")
    include (":app")
}

