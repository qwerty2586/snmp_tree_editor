import javafx.scene.control.Alert

/**
 * Created by qwerty on 8. 5. 2017.
 */

val DEVICE_LIST_FILE ="./.devicelist"

val SNMP_WALK_BINARY_PATH =
        if (com.sun.javafx.util.Utils.isWindows()) "snmp/win/snmpwalk.exe"
        else if (com.sun.javafx.util.Utils.isUnix()) "snmpwalk"
        else ""

val SNMP_SET_BINARY_PATH =
        if (com.sun.javafx.util.Utils.isWindows()) "snmp/win/snmpset.exe"
        else if (com.sun.javafx.util.Utils.isUnix()) "snmpset"
        else ""

fun alert(type: Alert.AlertType, title: String = "", headerText: String = "", text: String = ""): Alert {
        val a = Alert(type)
        a.contentText = text
        a.title = title
        a.headerText = headerText
        return a
}