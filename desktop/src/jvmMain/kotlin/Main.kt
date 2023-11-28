
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.duss.easyproject.core.di.initKoin
import app.duss.easyproject.presentation.ui.ContentView
import app.duss.easyproject.presentation.ui.root.RootComponent
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.mvikotlin.core.utils.setMainThreadId
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import javax.swing.SwingUtilities

fun main() {
    initKoin(enableNetworkLogs = false)

    val rootComponent = invokeOnAwtSync {

//        //TODO: Test on different machine and after find formula then use thread.getThreadID()
//
//        System.getProperty("java.version") //21.0.1
//        ManagementFactory.getRuntimeMXBean().vmVersion //21.0.1+12-29
//
//        //TEST Result on 21.0.1 machine
//        System.getProperty("java.version").split(".").first //21
//        ManagementFactory.getRuntimeMXBean().vmVersion.split(".").first //21

        setMainThreadId(Thread.currentThread().id)

        val lifecycle = LifecycleRegistry()

        val rootComponent = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            storeFactory = DefaultStoreFactory(),
        )

        lifecycle.resume()

        rootComponent
    }

    application {
        Window(
            title = "Easy Project",
            onCloseRequest = ::exitApplication
        ) {
            ContentView(
                component = rootComponent
            )
        }
    }
}

fun <T> invokeOnAwtSync(block: () -> T): T {
    var result: T? = null
    SwingUtilities.invokeAndWait { result = block() }

    @Suppress("UNCHECKED_CAST")
    return result as T
}