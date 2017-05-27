

import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.experimental.javafx.JavaFx as UI


/**
 * Console window, contains text area, where is redirected program output
 */


class ConsoleWindow : Stage() {
    val borderPane = BorderPane()
    val textArea = TextArea()
    init {
        borderPane.center = textArea
        textArea.isEditable = false
        scene = Scene(borderPane,700.0,500.0)
    }

    fun runTaskWithOutput(program: List<String>, afterTaskJob: (output : String?) -> Unit) {
        textArea.clear()
        title = "Console Window - " + program.joinToString(" ")
        async(CommonPool) {
            val pb = ProcessBuilder(program.toList())
            val process = pb.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            while (process.isAlive) {
                val string = reader.readLine()
                launch(UI) { if (string!=null) textArea.appendText("${string}\n")}
            }

            // zpracuje zbytek retezce
            do {
                val string = reader.readLine()
                launch(UI) {
                    if (string != null && string.isNotEmpty()) textArea.appendText("${string}\n")

                }
            } while (string != null);
            launch(UI) {
                afterTaskJob(textArea.text)
            }


        }

    }

}