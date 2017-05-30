
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.*
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.Callback
import javafx.util.StringConverter
import java.net.InetAddress

/**
 * Class representing one device, ip and dns value are binded
 */
class NetworkNode(_address: String, var _community: String) {
    // var address = SimpleStringProperty()


    var ip = SimpleStringProperty()
    var dns = SimpleStringProperty()
    var community = SimpleStringProperty()

    init {
        parseNewAddress(_address)
        community.value = _community

        ip.bindBidirectional(dns, object :StringConverter<String>() {
            override fun toString(string: String?): String {
                if (string==null) return ""
                return getIP(string)
            }

            override fun fromString(string: String?): String {
                if (string==null) return ""
                return getDNS(string)
            }

        })
        dns.bindBidirectional(ip, object :StringConverter<String>() {
            override fun toString(string: String?): String {
                if (string==null) return ""
                return getDNS(string)
            }

            override fun fromString(string: String?): String {
                if (string==null) return ""
                return getIP(string)
            }

        })
    }
    // changing values
    fun parseNewAddress(s: String) {
        ip.value = s
        dns.value = s
    }

}


fun getIP(s : String) : String {
    if (s == "") return ""
    try {
        // informing user because it can take some time
        mainStage?.title = "Resolving IP from $s"
        val r = InetAddress.getByName(s).hostAddress
        mainStage?.title = MAIN_TITLE
        return r
    } catch (e : Exception) {
        mainStage?.title = MAIN_TITLE
        return s
    }
}

fun getDNS(s : String) : String {
    if (s == "") return ""
    try {
        // informing user because it can take some time
        mainStage?.title = "Resolving DNS from $s"
        val r = InetAddress.getByName(s).hostName
        mainStage?.title = MAIN_TITLE
        return r
    } catch (e : Exception) {
        mainStage?.title = MAIN_TITLE
        return s
    }
}

// initial values
val inserts = FXCollections.observableArrayList<NetworkNode>(arrayListOf())

class NetworkTable : TableView<NetworkNode>() {


    val dnsColumn = TableColumn<NetworkNode, String>("DNS")
    val ipColumn = TableColumn<NetworkNode, String>("IP")
    val communityColumn = TableColumn<NetworkNode, String>("Community")

    init {


        dnsColumn.setCellValueFactory { it.value.dns }
        ipColumn.setCellValueFactory { it.value.ip }
        communityColumn.setCellValueFactory { it.value.community }

        dnsColumn.cellFactory =TextFieldTableCell.forTableColumn()
        ipColumn.cellFactory =TextFieldTableCell.forTableColumn()
        communityColumn.cellFactory =  TextFieldTableCell.forTableColumn()

        isEditable = true
        ipColumn.isEditable = true
        dnsColumn.isEditable = true

        columns.addAll(dnsColumn, ipColumn,communityColumn)

        items.addAll(inserts)

        selectionModel.selectionMode = SelectionMode.MULTIPLE

        rowFactory = Callback<TableView<NetworkNode>, TableRow<NetworkNode>> {
            val row = TableRow<NetworkNode>()
            val contextMenu = ContextMenu()
            val removeMenuItem = MenuItem("Remove")
            removeMenuItem.setOnAction {
                items.removeAll(selectionModel.selectedItems)
            }
            val openTreeItem = MenuItem("Tree")
            openTreeItem.setOnAction {
                openTree(selectionModel.selectedItem.ip.value,selectionModel.selectedItem.community.value )
            }

            contextMenu.getItems().addAll(openTreeItem,removeMenuItem)
            row.contextMenuProperty().bind(
                    Bindings.`when`(row.emptyProperty()) //  when je v kotlinu klicove slovo
                            .then(null as ContextMenu?)
                            .otherwise(contextMenu)
            )
            row.setOnMousePressed { event ->
                if (event.isPrimaryButtonDown && event.clickCount == 2) {
                    openTree(row.item?.ip?.value,row.item?.community?.value)
                }
            }

            row
        }

    }

    // action of opening
    private fun  openTree(address: String?, community: String?) {
        if (address==null) return
        val treeWindow = TreeWindow(address)
        treeWindow.show()
        //treeWindow.insertNodesFromString(java.lang.String.join("\n", Files.readAllLines(Paths.get("doc/swkralovicka43_snmpwalk_v2.txt"))))
        val console = ConsoleWindow()
        console.scene.window.x = treeWindow.scene.window.x + 200
        console.scene.window.y = treeWindow.scene.window.y + 200
        // windows doesnt strat on top of each other
        console.show()
        console.runTaskWithOutput(listOf(SNMP_WALK_BINARY_PATH, "-v", "2c", "-c", community!!, address),{
            console.close()
            if (it == null || it.isEmpty()) {
                alert(Alert.AlertType.ERROR,"Error processing SNMP","","Target device doesn't support snmp or is unavalaible").showAndWait()
                treeWindow.close()
            } else {
                treeWindow.insertNodesFromString(it)
            }
        })
    }

    fun openTreeButtonClicked() {
        selectionModel.selectedItems.forEach {
            openTree(it?.ip?.value,it?.community?.value)
        }
    }


}