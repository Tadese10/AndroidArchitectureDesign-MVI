package dependencies

object Dependencies{

    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val core_ktx = "androidx.core:core-ktx:${Versions.ktx_core}"

    // -- Retrofit2
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"

    // -- Lifecycle Components (ViewModel, LiveData and ReactiveStreams)
    val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_version}"
    val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}"

    // -- Coroutines
    val kotlinx_coroutines_core =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.caroutines_version}"
    val  kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.caroutines_version}"

    // RecyclerView
    val  recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview_version}"

    //Material
    val material = "com.google.android.material:material:${Versions.material_version}"

    //glide
    val glide =  "com.github.bumptech.glide:glide:${Versions.glide_version}"
    val glide_compiler =  "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    // Leak Canary (detecting memory leaks)
    val  leakcanary_android  = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary_version}"

    val swipe_refresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_version}"
}