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
 * Window with value tree
 * Constructor have one parameter which is used as root element of tree
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

    /**
     * Create descpription based on current selected
     */
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

    /**
     * Parse snmpwalk output and put Nodes to tree structure
     */
    fun insertNodesFromString(consoleOutput: String) {
        // split by \n but only if outside of quotes
        val lines = consoleOutput.split(Regex("(?=(?:(?:[^\"]*\"){2})*[^\"]*$)\\n"))
        for (line in lines) {
            if ("\"" == line || line.isEmpty()) continue
            val firstEqualsSign = line.indexOf('=');
            if (firstEqualsSign == -1) continue
            val leftPart = line.substring(0,firstEqualsSign)
            val rightPart = line.substring(firstEqualsSign+1)

            val path = leftPart.trim().replace("::", ".")
            val valueparts = rightPart.trim().split(":")
            val valueType = valueparts.first().trim()

            val value = valueparts.last().trim()
            val pathParts = path.split(".")
            val locPath = pathParts.last()

            // leafnode will be added to tree after finding apropriate parent in tree
            val leafNode = SnmpNode(path, true, locPath, value, valueType)

            var node = root
            var level = 0
            var pathPrefix = ""
            for (part in pathParts) {
                level++
                pathPrefix = "$pathPrefix${if (level>1) "." else ""}$part"
                if (level == pathParts.count()) {
                    node.children.add(TreeItem(leafNode))
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
                    // didnt find node -> creating
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

    /**
     * Recursively join nodes with only one child, simplifiing tree in process
     */
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
}
class SnmpTreeCell : TreeCell<SnmpNode>() {
    override fun updateItem(item: SnmpNode?, empty: Boolean) {
        super.updateItem(item, empty)
        text = if (empty) null else item!!.locPath
    }
}

/**
 * Data representation of single Node
 */
class SnmpNode(var path: String, var leaf: Boolean, var locPath: String, var value: String, var type: String)

