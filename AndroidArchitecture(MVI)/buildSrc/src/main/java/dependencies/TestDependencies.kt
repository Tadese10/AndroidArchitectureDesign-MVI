package dependencies

object TestDependencies{
    val test_ext = "androidx.test.ext:junit:${Versions.junit_test_ext}"
    val junit = "junit:junit:${Versions.junit_version}"
    val junit_test_implementation = "junit:junit:${Versions.junit_test_implementation_version}"
    val runner = "androidx.test:runner:${Versions.android_runner_version}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"
    val android_junit_instrumentation = "androidx.test.runner.AndroidJUnitRunner"
}