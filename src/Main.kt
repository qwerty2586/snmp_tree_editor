
import javafx.application.Application
import javafx.collections.ListChangeListener
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.stage.Stage
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

/**
 * Application class
 */

val MAIN_TITLE = "Device Table"
var mainStage : Stage? = null;

class Main : Application() {


    val table = NetworkTable()
    val addressField = TextField()
    val communityField = TextField()

    val addButton = Button("Add")
    val removeButton = Button("Remove")
    val treeButton = Button("Tree")

    override fun start(primaryStage: Stage) {
        mainStage = primaryStage
        val root = BorderPane()
        root.center = table
        root.bottom = bottomGrid()
        primaryStage.title = MAIN_TITLE
        primaryStage.scene = Scene(root, 500.0, 275.0)
        primaryStage.show()
        loadFromFile(DEVICE_LIST_FILE)
    }

    /**
     * Creates view for adding new device
     */
    private fun bottomGrid() : GridPane {
        val grid = GridPane()

        grid.add(Label("dns/ip"),0,0)
        grid.add(addressField,0,1)

        grid.add(Label("community"),1,0)
        grid.add(communityField,1,1)

        grid.add(removeButton,2,0)
        grid.add(addButton,2,1)
        grid.add(treeButton,3,0,1,2)

        addButton.setOnAction { addItem() }
        removeButton.setOnAction { removeItem() }
        treeButton.setOnAction { expandTree() }

        treeButton.isDisable = true

        table.selectionModel.selectedItems.addListener { change: ListChangeListener.Change<*> ->
            treeButton.isDisable = (table.selectionModel.selectedItems.count() == 0)
        }

        addButton.minWidth = 100.0
        removeButton.minWidth = 100.0
        treeButton.minWidth = 100.0
        treeButton.minHeight = 55.0
        //GridPane.setFillHeight(treeButton,true)
        //grid.columnConstraints..hgrow = Priority.ALWAYS


        grid.alignment = Pos.CENTER
        grid.hgap = 5.0
        grid.vgap = 5.0
        grid.padding = Insets(5.0)

        return grid
    }

    private fun expandTree() {
        table.openTreeButtonClicked()
    }

    private fun removeItem() {
        if (table.selectionModel.selectedItems.count() < 1) {
            alert(Alert.AlertType.WARNING,"Nothing selected","Specify deletion","Please select ").showAndWait()
            return
        }
        val selected = table.selectionModel.selectedItems
        val names = selected.map { it.dns.value }.joinToString(separator = "\n" ,prefix = "\n",postfix = "\n");
        alert(Alert.AlertType.CONFIRMATION, "Realy delete?", "Realy delete?", "Do you realy want to delete these items from evice table?\nItems: ${names}")
                .showAndWait().filter { response -> response == ButtonType.OK }
                .ifPresent {
                    //table.items.remove(selected)
                    table.items.removeAll(selected)
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
        val address = addressField.text
        val community = communityField.text
        addressField.text = ""
        communityField.text = ""
        table.items.add(NetworkNode(address,community))
    }

    override fun stop() {
        saveToFile(DEVICE_LIST_FILE)
        super.stop()
    }

    /**
     * Saves device list from file
     */
    private fun  saveToFile(filename: String) {
        Files.write(Paths.get(filename), table.items.stream().map { node -> "${node.ip.value};${node.community.value}" }.collect(Collectors.joining("\n")).lines(), Charset.forName("UTF-8"))
    }

    /**
     * Loads device list from file
     */
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

    // program execution
    companion object {
        @JvmStatic fun main(vararg args: String) {
            Application.launch(Main::class.java, *args)
        }
    }
}
