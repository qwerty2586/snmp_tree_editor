import javafx.scene.control.TableColumn
import javafx.scene.control.TableView

/**
 * Created by qwerty on 1. 5. 2017.
 */


class NetworkNode( var address:String,var community:String)

class NetworkTable:TableView<NetworkNode>() {

    val dnsColumn = TableColumn<NetworkNode,String>("DNS")
    val ipColumn = TableColumn<NetworkNode,String>("IP")

    init {
        columns.addAll(dnsColumn,ipColumn)
    }

}