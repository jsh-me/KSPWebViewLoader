## webview-kotlin-symbol-processor 🚧

### How to use

```kotlin
@WebViewBuilder(
    url = "https://www.google.com/",
    autoSet = true
)
lateinit var webView: WebView

override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

      webView = findViewById<WebView>(R.id.webView)
		  WebViewLoader.onInitialize(this)
	  	}
			
```



## Reference

https://jsuch2362.medium.com/my-first-kotlin-symbol-processing-tool-for-android-4eb3a2cfd600

https://github.com/google/ksp

https://medium.com/@jason_kim/annotation-processing-101-%EB%B2%88%EC%97%AD-be333c7b913