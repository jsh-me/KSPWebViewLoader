package com.jshme.ksp

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.LOCAL_VARIABLE)
annotation class WebViewBuilder(
    val url: String,
    val autoSet: Boolean
)
