

import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import kotlinx.coroutines.experimental.javafx.JavaFx as UI


/**
 * Created by qwerty on 7. 5. 2017.
 */


class ConsoleWindow : Stage() {
    val borderPane = BorderPane()
    val textArea = TextArea()
    init {
        borderPane.center = textArea
        textArea.isEditable = false
        scene = Scene(borderPane,700.0,500.0)
    }

    fun runTaskWithOutput(program: List<String>, afterTaskJob: (output : String) -> Unit) {
        textArea.clear()
        title = program.joinToString(" ")
        async(CommonPool) {
            val pb = ProcessBuilder(program.toList())
            val process = pb.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            while (process.isAlive) {
                val string = reader.readLine()
                launch(UI) { textArea.appendText("${string}\n")}
            }
            launch(UI) {
                afterTaskJob(textArea.text)
            }
        }

    }

    class MyOutputStream(var textArea: TextArea) : OutputStream(){

        override fun write(b: Int) {
            textArea.appendText(b.toChar().toString())
        }
    }


}