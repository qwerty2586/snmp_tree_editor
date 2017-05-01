
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

/**
 * Created by qwerty on 1. 5. 2017.
 */
class Main : Application() {


    override fun start(primaryStage: Stage) {
        val root = BorderPane()



        root.center = NetworkTable()
        root.bottom = Label("hello")
        primaryStage.title = "Hello World!!!"
        primaryStage.scene = Scene(root, 300.0, 275.0)
        primaryStage.show()
    }

    companion object {


        @JvmStatic fun main(vararg args: String) {
            Application.launch(Main::class.java, *args)
        }
    }
}
