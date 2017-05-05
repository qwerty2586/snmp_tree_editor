
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.StringConverter
import java.net.InetAddress

/**
 * Created by qwerty on 1. 5. 2017.
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

    fun parseNewAddress(s: String) {
        parseValues = false
        ip.value = s
        dns.value = s
    }

}


fun getIP(s : String) : String {
    if (s == "") return ""
    try {
        return InetAddress.getByName(s).hostAddress
    } catch (e : Exception) {
        return s
    }
}

fun getDNS(s : String) : String {
    if (s == "") return ""
    try {
        return InetAddress.getByName(s).hostName
    } catch (e : Exception) {
        return s
    }
}


val inserts = FXCollections.observableArrayList<NetworkNode>(arrayListOf(
        NetworkNode("swkralovicka43", "public")
))


var parseValues = false

class NetworkTable : TableView<NetworkNode>() {


    val dnsColumn = TableColumn<NetworkNode, String>("DNS")
    val ipColumn = TableColumn<NetworkNode, String>("IP")

    init {


        dnsColumn.setCellValueFactory { v -> v.value.dns }
        ipColumn.setCellValueFactory { v -> v.value.ip }


        // dnsColumn.setCellFactory ( TextFieldTableCell.forTableColumn<NetworkNode, String> ()


        dnsColumn.cellFactory =TextFieldTableCell.forTableColumn()
        ipColumn.cellFactory =TextFieldTableCell.forTableColumn()

        isEditable = true
        ipColumn.isEditable = true
        dnsColumn.isEditable = true

        columns.addAll(dnsColumn, ipColumn)

        items.addAll(inserts)
    }

}