## webview-kotlin-symbol-processor 🚧

### how to use

```
@WebViewBuilder(
    url = "https://www.google.com/",
    autoSet = true
)
lateinit var webView: WebView

WebViewLoader.onInitialize(this)
```

