
import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

/**
 * Created by qwerty on 1. 5. 2017.
 */
class Main : Application() {


    val table = NetworkTable()
    val addressField = TextField()
    val communityField = TextField()

    val addButton = Button("Add")
    val removeButton = Button("Remove")

    override fun start(primaryStage: Stage) {
        val root = BorderPane()
        root.center = table
        root.bottom = bottomGrid()
        primaryStage.title = "Device Table"
        primaryStage.scene = Scene(root, 500.0, 275.0)
        primaryStage.show()
        loadFromFile(DEVICE_LIST_FILE)
    }

    private fun bottomGrid() : GridPane {
        val grid = GridPane()

        grid.add(Label("dns/ip"),0,0)
        grid.add(addressField,0,1)

        grid.add(Label("community"),1,0)
        grid.add(communityField,1,1)

        grid.add(removeButton,2,0)
        grid.add(addButton,2,1)
        addButton.setOnAction { addItem() }
        removeButton.setOnAction { removeItem() }
        addButton.minWidth = 100.0
        removeButton.minWidth = 100.0

        grid.alignment = Pos.CENTER
        grid.hgap = 5.0
        grid.vgap = 5.0
        grid.padding = Insets(5.0)

        return grid
    }

    private fun removeItem() {
        if (table.selectionModel.selectedItems.count() < 1) {
            alert(Alert.AlertType.WARNING,"NNothing selected","Specify deletion","Please select ").showAndWait()
            return
        }
        val selected = table.selectionModel.selectedItem
        alert(Alert.AlertType.CONFIRMATION, "Realy delete?", "Realy delete?", "Do you realy want to delete ${selected.dns.value} from device table?")
                .showAndWait().filter { response -> response == ButtonType.OK }
                .ifPresent {
                    table.items.remove(selected)
                }
    }

    private fun addItem() {
        if (addressField.text.isEmpty()) {
            alert(Alert.AlertType.WARNING,"PLease specify","Missing dsn or ip","Please insert dns or ip").showAndWait()
            return
        }
        if (communityField.text.isEmpty()) {
            alert(Alert.AlertType.WARNING,"PLease specify","Missing community","Please insert device community").showAndWait()
            return
        }
        table.items.add(NetworkNode(addressField.text,communityField.text))
    }

    override fun stop() {
        saveToFile(DEVICE_LIST_FILE)
        super.stop()
    }

    private fun  saveToFile(filename: String) {
        Files.write(Paths.get(filename), table.items.stream().map { node -> "${node.ip.value};${node.community.value}" }.collect(Collectors.joining("\n")).lines(), Charset.forName("UTF-8"))
    }

    private fun  loadFromFile(filename: String) {
        try {
            val lines = Files.readAllLines(Paths.get(filename))
            lines.forEach {
                val parts = it.split(";")
                table.items.add(NetworkNode(parts.first(), parts.last()))
            }
        } catch (e : Exception) {

        }
    }


    companion object {


        @JvmStatic fun main(vararg args: String) {
            Application.launch(Main::class.java, *args)
        }
    }
}
