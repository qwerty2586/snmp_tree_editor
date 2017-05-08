import javafx.collections.ListChangeListener
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import kotlinx.coroutines.experimental.javafx.JavaFx as UI

/**
 * Created by qwerty on 7. 5. 2017.
 */


class TreeWindow(deviceName: String) : Stage() {
    val borderPane = BorderPane()
    val tree = TreeView<SnmpNode>()
    val root = TreeItem<SnmpNode>(SnmpNode("", false, deviceName, "", ""))
    val ta = TextArea()

    init {
        borderPane.left = tree
        tree.setCellFactory({ SnmpTreeCell() })
        tree.root = root
        borderPane.center = ta

        tree.selectionModel.selectedItems.addListener({ change: ListChangeListener.Change<*> -> ta.text = createDescription() })

        title = deviceName
        scene = Scene(borderPane, 700.0, 500.0)
    }

    private fun  createDescription(): String? {
        val selection = tree.selectionModel.selectedItem?.value

        var output = ""
        if (selection!=null) {
            output = "Path: ${selection.path}\nName: ${selection.locPath}\nLeaf: ${selection.leaf}"
            if (selection.leaf) {
                output = "$output\nValue: ${selection.value}\nType: ${selection.type}"
            }
        }
        return output
    }

    fun insertNodesFromString(consoleOutput: String) {
        val lines = consoleOutput.split("\n")
        for (line in lines) {
            val parts = line.split("=")
            val path = parts.first().trim().replace("::", ".")
            val valueparts = parts.last().trim().split(":")
            val valueType = valueparts.first().trim()
            println(line)
            val value = valueparts.last().trim()
            val pathParts = path.split(".")
            val locPath = pathParts.last()

            val snmpNode = SnmpNode(path, true, locPath, value, valueType)

            var node = root


            var level = 0
            var pathPrefix = ""
            for (part in pathParts) {
                level++
                pathPrefix = "$pathPrefix${if (level>1) "." else ""}$part"
                if (level == pathParts.count()) {
                    node.children.add(TreeItem(snmpNode))
                } else {
                    val nodes = node.children
                    var isThere = false
                    for (testNode in nodes) {
                        if (testNode.value.locPath == part) {
                            isThere = true
                            node = testNode
                            break
                        }
                    }
                    if (!isThere) {
                        val newNode = TreeItem(SnmpNode(pathPrefix, false, part, "", ""))
                        node.children.add(newNode)
                        node = newNode
                    }
                }
            }
        }
        optimizeItem(root)
        root.isExpanded = true
    }

    private fun optimizeItem(node: TreeItem<SnmpNode>) {
        while (node.children.count() == 1) {
            val item = node.children[0].value
            item.locPath = "${node.value.locPath}.${node.children[0].value.locPath}"
            val backup = node.children[0].children
            node.value = item
            node.children.remove(node.children[0])
            node.children.addAll(backup)
        }

        node.children.forEach { optimizeItem(it) }
    }

    private fun addSnmpNode(snmpNode: SnmpNode) {

    }
}

class SnmpTreeCell : TreeCell<SnmpNode>() {
    override fun updateItem(item: SnmpNode?, empty: Boolean) {
        super.updateItem(item, empty)
        text = if (empty) null else item!!.locPath
    }
}

class SnmpNode(var path: String, var leaf: Boolean, var locPath: String, var value: String, var type: String) {

}

