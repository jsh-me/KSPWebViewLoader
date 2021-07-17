package com.jshme.kspsample

import android.app.Activity
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*

object WebViewLoader {
    fun onInitialize(activity: Activity) {

        val parserName = "WebViewBuilder"
        val kClass = Class.forName("${activity.packageName}.$parserName").kotlin
        val instance = kClass.objectInstance ?: kClass.java.newInstance()

        kClass.memberProperties
            .filterIsInstance<KMutableProperty<*>>()
            .firstOrNull { kProperty -> kProperty.name == "context" }
            .run { this?.setter?.call(instance, activity) }

        kClass.memberFunctions
            .firstOrNull { kFunction -> kFunction.name == "init" }
            .run { this?.call(instance) }
    }
}
