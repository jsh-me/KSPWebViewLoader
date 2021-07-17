package com.jshme.ksp

import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import java.io.OutputStream
import kotlin.math.log

class WebViewBuilderProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
) : SymbolProcessor {


    private var propertyUrl: MutableMap<KSPropertyDeclaration, String> = mutableMapOf()
    private var propertyBridgeClass: MutableMap<KSPropertyDeclaration, String> = mutableMapOf()
    private var propertyAutoSet: MutableMap<KSPropertyDeclaration, Boolean> = mutableMapOf()

    private var parentClassDeclaration: KSDeclaration? = null

    private lateinit var packageName: String
    private lateinit var fileName: String

    fun init() {
        packageName = "com.jshme.kspsample"
        fileName = "WebViewBuilder"
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(WebViewBuilder::class.java.canonicalName)

        val ret = symbols.filterNot { it.validate() }.toList()

        symbols
            .filter { (it is KSPropertyDeclaration || it is KSClassDeclaration || it is KSFunctionDeclaration) && it.validate() }
            .forEach {
                it.accept(PropertyVisitor(), Unit)
            }

        return ret
    }

    override fun finish() {
        val outputStream = codeGenerator.createNewFile(
            dependencies = Dependencies(true),
            packageName = packageName,
            fileName = fileName
        )

        outputStream.appendText(
            """
                |package $packageName
                |
                |import android.webkit.WebView
                |import android.util.Log
                |import android.content.Context
                |
                |object $fileName {
                |    var context: Context? = null
                |
                |    fun init() {
            """.trimMargin()
        )

        propertyUrl.map { variable ->
            outputStream.appendText("\t\t(context as? ${parentClassDeclaration?.simpleName?.asString()})?.${variable.key}?.loadUrl(\"${variable.value}\")")
        }

        propertyAutoSet
            .filter { property -> property.value }
            .map { variable ->
                outputStream.appendText(
                    """
                    |        (context as? ${parentClassDeclaration?.simpleName?.asString()})?.${variable.key}?.settings?.apply {
                    |            javaScriptEnabled = true
                    |            domStorageEnabled = true
                    |            useWideViewPort = true
                    |            loadWithOverviewMode = true
                    |            setSupportMultipleWindows(true)
                    |        }
                """.trimMargin()
                )
            }

        outputStream.appendText(
            """
            |    }
            |}
            """.trimMargin()
        )

        outputStream.close()
        logger.warn("finish")
    }

    override fun onError() {
        super.onError()
    }


    inner class PropertyVisitor : KSVisitorVoid() {

        override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
            logger.warn("visite Property Declearation : ${property} and ${property.parentDeclaration}")
            parentClassDeclaration = property.parentDeclaration

            //@todo
            val parent = property.parentDeclaration as? KSClassDeclaration

            property.annotations.firstOrNull { annotation ->
                annotation.shortName.asString() == "WebViewBuilder"
            }?.arguments?.map { argument ->
                if (argument.name?.asString() == "url") propertyUrl[property] = argument.value.toString()
                if (argument.name?.asString() == "bridge") propertyBridgeClass[property] = argument.value.toString()
                if (argument.name?.asString() == "autoSet") propertyAutoSet[property] = argument.value as Boolean
            }

            logger.warn("size ${propertyUrl.size}")

            logger.warn("$fileName 생성완료")
        }
    }

    fun OutputStream.appendText(str: String) {
        this.write("$str\n".toByteArray())
    }
}
