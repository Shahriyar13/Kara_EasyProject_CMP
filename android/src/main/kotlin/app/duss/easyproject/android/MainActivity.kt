package app.duss.easyproject.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import app.duss.easyproject.presentation.ui.ContentView
import app.duss.easyproject.core.di.initKoin
import app.duss.easyproject.ui.root.RootComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin(
            enableNetworkLogs = app.duss.easyproject.android.BuildConfig.DEBUG
        ) {
            androidContext(applicationContext)
        }
        
        val rootComponent =
            RootComponent(
                componentContext = defaultComponentContext(),
                storeFactory = DefaultStoreFactory(),
            )

        setContent {
            ContentView(component = rootComponent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}