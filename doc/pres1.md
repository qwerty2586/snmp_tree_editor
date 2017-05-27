## SNMP Tree Editor



SNMP tree editor bude program vytvoreny jako semestralni prace na predmet UUR

bude umoznovat

- ogranizovat seznam SNMP klientu
- prochazet stromovou strukturou snmp
- editovat koncovou polozku 

### zdrojova data
Program bude spustet linuxovou utilitu **snmpwalk**, jejiz vystup bude parsovat.

Priklad zdrojovych dat SNMP

<!-- language: lang-none -->
    SNMPv2-SMI::mib-2.16.1.1.1.4.101 = Counter32: 3554559276
    SNMPv2-SMI::mib-2.16.1.1.1.4.102 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.103 = Counter32: 2762020914
    SNMPv2-SMI::mib-2.16.1.1.1.4.104 = Counter32: 4079222245
    SNMPv2-SMI::mib-2.16.1.1.1.4.105 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.106 = Counter32: 1710537022
    SNMPv2-SMI::mib-2.16.1.1.1.4.107 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.108 = Counter32: 3629237285
    SNMPv2-SMI::mib-2.16.1.1.1.4.109 = Counter32: 1662158079
    SNMPv2-SMI::mib-2.16.1.1.1.4.110 = Counter32: 1285152538
    SNMPv2-SMI::mib-2.16.1.1.1.4.111 = Counter32: 3628416265
    SNMPv2-SMI::mib-2.16.1.1.1.4.112 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.113 = Counter32: 2587116291
    SNMPv2-SMI::mib-2.16.1.1.1.4.114 = Counter32: 1765697143
    SNMPv2-SMI::mib-2.16.1.1.1.4.115 = Counter32: 1821125809
    SNMPv2-SMI::mib-2.16.1.1.1.4.116 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.117 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.118 = Counter32: 611562470
    SNMPv2-SMI::mib-2.16.1.1.1.4.119 = Counter32: 1890574375
    SNMPv2-SMI::mib-2.16.1.1.1.4.120 = Counter32: 53408982
    SNMPv2-SMI::mib-2.16.1.1.1.4.121 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.122 = Counter32: 0
    SNMPv2-SMI::mib-2.16.1.1.1.4.123 = Counter32: 2502604481
    SNMPv2-SMI::mib-2.16.1.1.1.4.124 = Counter32: 3926596339

jiny priklad


<!-- language: lang-none -->
    IF-MIB::ifName.1 = STRING: IP Interface
    IF-MIB::ifName.101 = STRING: Port #1
    IF-MIB::ifName.102 = STRING: Port #2
    IF-MIB::ifName.103 = STRING: Port #3
    IF-MIB::ifName.104 = STRING: Port #4
    IF-MIB::ifName.105 = STRING: Port #5
    IF-MIB::ifName.106 = STRING: Port #6
    IF-MIB::ifName.107 = STRING: Port #7
    IF-MIB::ifName.108 = STRING: Port #8
    IF-MIB::ifName.109 = STRING: Port #9
    IF-MIB::ifName.110 = STRING: Port #10
    IF-MIB::ifName.111 = STRING: Port #11
    IF-MIB::ifName.112 = STRING: Port #12
    IF-MIB::ifName.113 = STRING: Port #13
    IF-MIB::ifName.114 = STRING: Port #14
    IF-MIB::ifName.115 = STRING: Port #15
    IF-MIB::ifName.116 = STRING: Port #16
    IF-MIB::ifName.117 = STRING: Port #17
    IF-MIB::ifName.118 = STRING: Port #18
    IF-MIB::ifName.119 = STRING: Port #19
    IF-MIB::ifName.120 = STRING: Port #20
    IF-MIB::ifName.121 = STRING: Port #21
    IF-MIB::ifName.122 = STRING: Port #22
    IF-MIB::ifName.123 = STRING: Port #23
    IF-MIB::ifName.124 = STRING: Port #24



## Layout

### Hlavni okno
V hlavnim okne muzeme sledovat aktivitu zarizeni muzeme pridavat mazat editovat a zobrazit

![obrazek hlavniho okna](./doc/hlavni_okno.png)


### Okno se stromem

umozni prochazet stromovou strukturou

![obrazek okna_se_stromem](./doc/okno_se_stromem.png)

### Editacni okno

umozni menit koncovou hodnotu

![obrazek okna_se_stromem](./doc/editacni_okno.png)




