import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import java.util.*

/**
 * Created by qwerty on 1. 5. 2017.
 */


class NetworkNode( _address:String,var _community:String) {
    var address = SimpleStringProperty()
        set(value) {
            ip = value
            dns = value
        }
    var ip = SimpleStringProperty()
  //      set(value) {address = value}
    var dns = SimpleStringProperty()
  //      set(value) {address = value}
    var community = SimpleStringProperty()
    init {
        address.value = _address
        community.value = _community
    }
}


val inserts = FXCollections.observableArrayList<NetworkNode>(arrayListOf(
        NetworkNode("swkralovicka43","public")
))



class NetworkTable:TableView<NetworkNode>() {

    val dnsColumn = TableColumn<NetworkNode,String>("DNS")
    val ipColumn = TableColumn<NetworkNode,String>("IP")

    init {


        dnsColumn.setCellValueFactory { v -> v.value.dns }
        ipColumn.setCellValueFactory { v -> v.value.ip }

        isEditable = true
        ipColumn.isEditable = true
        dnsColumn.isEditable = true

        columns.addAll(dnsColumn,ipColumn)

        items.addAll(inserts)
    }

}