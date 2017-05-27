# Navod pro testery

## priprava
 -   v linuxu je zapotrebi doinstalovat snmpwalk
```
apt install snmp
```
ve windows se pouzije pribaleny snmpwalk z adresare snmp/win/

## testovani

 pro pridani zarizeni muzete pouzit nejake zarizeni ktere ma povolene cteni protokolu snmp
 - v pilsfree : pridame libovolny switch swkralovicka[cislo_popoisne] jako komunitu zadame public
 - jinde : pridame host minemax.cz s komunitou public
  
  vyzkousejte 
  - pridani do seznamu zarizeni
  - odebrat pridane zarizeni
  - expandovat strom zarizeni
  - proklikat si strom, najit smysl jednotlivych hodnot
  
  vypnuti programu ulozi seznam zarizeni do souboru .devicelist pri dalsim spusteni se znovu nacte
 
 