# KIV/UUR - Snmp Tree Editor

## Zadání
Vaším úkolem bude vytvořit aplikaci s GUI, na kterém si vyzkoušíte využití většiny základních komponent JavaFX. Téma práce, tj. co bude vaše aplikace dělat, si zvolíte sami nejpozději do 4. týdne semestru. Zadání musí být schváleno cvičícím, přičemž aplikace musí splňovat následující podmínky:

 - Je nutné použít alespoň dvě z těchto komponent: Tabulka (TableView), Strom (TreeView), vlastní "ručně" vykreslená komponenta.
 - Aplikace musí mít alespoň 3 různá okna, která používá (okna nemusí být stále aktivní, tj. mohou mít např. charakter průvodce nebo konfigurace aplikace).

## Výběr tématu
 SNMP protokol umožňuje vzdálenou správu zařízení v síti. 
 Data jsou reprezentována pomocí stromové struktury a jejich změnou provádíme nastavení zařázení.
 
 Rozhodl jsem se zpracovat takovýto prohlížeč s podporou editace.
## Uživatelská příručka

### Instalace
Překlad programu můžeme použít gradle
```
gradle build jar
```
vytvoří v adresáři build/libs spustitelný jar soubor. Ten lze spustit dvojklikem.
#### Windows

Pro základní zobrazení stačí přibalené binárky snmpwalk, které musí být v adresáři snmp/win vzhledem k umístění jar souboru.

Pokud chceme komfortnější překlad číselných OID identifikátorů na názvy musíme doinstalovat [net-snmp](https://sourceforge.net/projects/net-snmp/). 

#### Linux

Pro základní funkcionalitu nainstalujeme základní balíček SNMP
```
apt install snmp 
```
Pokud chceme překlad OID na názvy postupujeme podle návodu https://wiki.debian.org/SNMP

### Ovládání

V hlavním okně lze přidávat, editovat a odebírat zařízení. 
Dvojklikem na řádek mužeme u zařízení spustit čtení souboru.
Tato akce otevře dvě okna. V jednom běží výstup konzole v druhé čeká na doběhnutí prvního a potom se v něm expanduje strom. 
Proklikáváním stromu lze číst hodnoty položek.

Program po si po vypnutí pamatuje přidaná zařízení a uloží je do souboru .devicelist

Pro vyzkoušení čtení snmp je na server minemax.cz spuštěn server pro čtení pod komunitou public.

## Programátorská příručka

Rozhodl jsem se pro implementaci využít jazyk kotlin, který je kompatibilní s JVM a tedy i JavaFX.
Tento jazyk jsem se rozhodl vyzkoušet po kladných zkušenostech s používáním tohot jazyka v
programování Androidích aplikací kde výrazně zkracuje a zpřehledňuje kód.

Pro běh konzole jsem zkusil využití korutin namísto vláken, 
které jsou experimentální vlastností jazyka kotlin.

Problémem, který se mi nepovedlo vyřešit je překlad na IP a DNS které kvůli double bindingu
blokují hlavní vlákno dokud se adresa neresolvne.

## Prezentace

[Prezentace 1](doc/pres1.md)

[Prezentace 2](doc/pres2.md)

## Testovaní

[Návod pro testery](doc/test_manual.md)

[Reporty testerů](doc/test_reports)

### Výsledky testů

Na základě zpráv jsem opravil následující:

 - na hlavní okno jsem přidal tlačítko pro spuštení stromu
 - umožnil jsem výběr více položek stromu
 - po přidání zařízení se obsah textového pole smaže a tím zabraní vícenásobnému kliknutí
 - při přidání, změně se změní záhlaví okna na resolving IP.../resolving DNS...
 - doplnil jsem se uživatelskou příručku o pasáž s instalací snmp-tools

## Závěr
Aplikace není ve finální podobě, chybí jí možnost editace. Přesto splňuje požadavky zadání(Tři okna, tabulka a Strom).
Na základě zpráv testerů jsem provedl úpravy v programu.