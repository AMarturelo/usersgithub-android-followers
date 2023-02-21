include(":followers")
if (System.getenv("IS_CICD") == null || System.getenv("IS_CICD") == "")
{
    println("logger: include module app")
    include (":app")
}
