# **Materiály ke zkoušce z Moderních databázových systémů**

[**Stránky předmětu**](https://www.ksi.mff.cuni.cz/~holubova/NDBI040/)

---

## **Vrstvy databázových modelů**

### **Konceptuální vrstva**

- Modelování domény (reálné věci ze světa).
- Vysoká míra abstrakce.
- Používají se modely jako ER, UML.

### **Logická vrstva**

- Datové struktury pro uložení dat.
- Typy: relační, objektová, XML, grafová apod.

### **Fyzická vrstva**

- Skutečná implementace datových struktur z logické vrstvy.
- Obsahuje indexaci, datové soubory a jejich dělení.

---

# **Big Data**

### **Definice (podle Gartneru - společnost pro IT analýzy a poradenství):**

- **Volume** – velké objemy dat.
- **Variety** – různorodost dat.
- **Velocity** – rychlost generování a zpracování dat.

---

### **Zdroje Big Data**

- Sociální sítě.
- Vědecké nástroje.
- Mobilní zařízení.
- Senzory.

---

### **Hlavní charakteristiky Big Data**

#### **1. Volume (objem)**

- Velikost dat neustále exponenciálně roste.

#### **2. Variety (různorodost)**

- Podpora různých datových formátů, typů a struktur.
- Data mohou být strukturovaná, částečně strukturovaná, nebo zcela bez struktury.

#### **3. Velocity (rychlost)**

- Data jsou generována a musí být zpracována v reálném čase.

#### **4. Veracity (věrohodnost)**

- Řešení problémů jako inkonzistence, latence a neucelenost dat.

---

## **Zpracování Big Data**

### **OLTP: Online Transaction Processing**

- Používá se v DBMS a databázových aplikacích.
- Slouží k ukládání, dotazování a přístupu k datům.

### **OLAP: Online Analytical Processing**

- Multi-dimenzionální analytické dotazy.
- Patří do sekce BI (Business Intelligence).

### **RTAP: Real-Time Analytic Processing**

- Data jsou zpracovávána jako stream v reálném čase.
- Klíčové pro moderní Big Data architektury.

---

# **Výhody NoSQL databází**

### **1. Škálování**

- Horizontální škálování ("scaling out") oproti tradičnímu vertikálnímu škálování ("scaling up").

### **2. Big Data podpora**

- RDBMS nezvládají nové velké objemy dat.

### **3. Administrace**

- Automatické opravy, distribuovanost, a self-tuning.

### **4. Náklady**

- Nižší cena za transakci díky horizontálnímu škálování a využití [komoditního hardwaru](https://www.techtarget.com/whatis/definition/commodity-hardware).

### **5. Flexibilita**

- Absence pevného schématu umožňuje snadné strukturální změny bez vysokých nákladů.

---

## **Výzvy NoSQL databází**

- Nižší vyspělost technologií.
- Omezená podpora analytických nástrojů a BI.
- Nedostatek odborníků.

---

## **Datové předpoklady**

![Datové předpoklady](./images/data_assumptions.png)

---

# HDFS

- Hadoop Distributed File System
- Škálovatelný a opensource

## Apache Hadoop

- opensource framework s nástroji pro zpracování Big Data
- vyšel z Google MapReducs a GFS (Google File System)
- nástroje (komponenty) Hadoopu:
  - HDFS - distribuovaný file systémů
  - Hadoop YARN - scheduling a resource management clusterů
  - Hadoop MapReduce - paralelní zpracování dat

## Fault tolerance v HDFS

- _"failure is the norm rather than exception"_
- očekáváme, že vždy nějaká část nefunguje
- detekce chyb a auto recovery

## Typ dat v HDFS

- data proudí ve streamu
- automatický batch Processing
- write-once / read-many

## Nodes v HDFS

- Master / Slave archikektura
- HDFS využivá celý FS namespace
- Soubory jsou děleny na bloky (64MB, 128MB apod)

### NameNode

- master server
- řídí namespace
- regulace přístupu klientů
- řeší operace se soubory a adresáři
- určuje mapování bloků na DataNodes
- přijímá **HeartBeat** a **BlockReport** od DataNodes

#### Jak funguje NameNode

- Používá transakční log - **EditLog**
  - Zaznamenává všechny změny v meta datech FS (tvorba souboru, změna repliačního faktoru)
  - je uložen ve FS NameNodu
- **FsImage** - celý FS namespace s mapováním bloků na soubory
  - opět uložen v lokálním FS NameNodu
  - Je načten do paměti NameNodu (4GB RAM stačí)

#### Proces zapnutí systému z pohledu NameNode:

1. NameNode přečte FsImage a EditLog z disku
2. Aplikuje všechny operace v EditLogu na FsImage reprezentaci
3. Udělá **CheckPoint** - Flush out této verze systému do nového FsImage
4. Zkrátí EditLog

### DataNode

- provádí r/w requesty
- práce s bloky - tvorba, mazání a replikace na příkaz NameNode

#### Jak funguje DataNode

- Ukládá data do souborů ve vlastním FS
- Každý blok HDFS je samostatný soubor
- Netvoří všechny soubory ve stejném adresáři (využívá nějakou heuristiku)

#### Proces zapnutí systému z pohledu DataNode:

1. Generace seznamu všech svých HDFS bloků = **BlockReport**
2. Odešle **BlockReport** NameNodu

![alt text](./images/hdfs_arch.png)

## Namespace

- Hierarchický FS
- CRUD operace
- NameNode je správcem FS - zaznamenává změny
- Aplikace si specifkuje replikační faktor a to je uloženo v NameNode

## Replikace

- soubor je rozdělen na posloupnost bloků
- bloky mají stejnou velikost až na poslední
- velikost bloku lze nastavit
- replikace je nastavitelná
- zajišťuje fault toleranci

## Umístění replik

### Rack-aware

- bereme v potaz fyzickou lokaci uzly
- uzly jsou děleny do racků
- racky mezi sebou komunikují skrze switche
- NameNode určí rack id pro každou DataNode
- jednoduchý přístup: umístíme uzly do jiných racků => drahé zápisy

#### Standartní přístup

- replikační faktor je 3
- Repliky jsou umístěný následovně:
  - Jedna v uzlu na lokálním racku
  - Jedna na jiném uzlu v lokálním racku
  - Jedna na uzlu v jiném racku

## Chyby

### Network Failure

- ztráta připojení DataNodes k NameNode
- DataNodes přestanou posílat heartbeat a jsou NameNode označeny
- DataNode k nim neposílá I/O požadavky

### DataNode Failure

- může klesnout počet replik pod replikační faktor => nutnost re-replikace

# MapReduce

- Využívá paradigma **Rozděl a panuj**

## Faze Map

- Input: key/value páry
- Output: množina dočasných key/value párů - typicky jiná doména než input
- formálně: `(k_1, v_1) -> list(k_2, v_2)`

## Faze Reduce

- Input: Dočasný klíč a množina všech hodnot pro ten klíč
- Output: Menší množina hodnot ve stejné doméně
- formálně: `(k_2, list(v_2)) -> list(k_2, possibly smaller list(v_2))`

## Příklady MapReduce:

### Word Frequency

```
map(String key, String value):
    // key: document name
    // value: document contents
    for each word w in value:
        EmitIntermediate(w, "1");
```

```
reduce(String key, Iterator values):
    // key: a word
    // values: a list of counts
    int result = 0;
    for each v in values:
        result += ParseInt(v);
    Emit(key, AsString(result));
```

## Části MapReduce

### Input reader

- dělí vstup na části => kazda cast nalezi jedne map funkci
- generuje key / value pary

### Map funkce

- uzivatelem specifkovane zpracovani key / value paru

### Partition funkce

- dostane klice z map funkce a pocet reduceru
- vrati index reduceru, ktery se ma pouzit
- typicky zaheshujeme klic a modulime poctem reduceru

### Compare funkce

- setridi vstup do reduce funkce

### Reduce funkce

- uzivatelem specifkovane zpracovani key / values

### Output writer

- zapise vysledek reduce funkce do stabilniho uloziste

## Prubeh mapreduce

1. MapReduce knihovna rozdeli vstupni soubory do M casti (16MB - 64MB na cast)
2. Start kopii mapreduce na clusteru
3. Mame M map tasku a R reduce tasku
4. Master vybere IDLE workera a priradi mu jednu map nebo reduce tasku

![alt](./images/mapreduce_e1.png)

5. Worker s Map taskem precete obsah splitu na inputu
6. Naparsuje key/value pary ze vstupu
7. Preda pary do uzivatelem specifikovane Map funkce
8. Jsou vytvoreny docasne key/value pary a ulozeny do pameti

![alt](./images/mapreduce_e2.png)

9. Key / value pary z pameti jsou periodicky zapsany na lokalni disk
10. Poloha paru na disku je forwardovana masterovi

![alt](./images/mapreduce_e3.png)

11. Master notifikuje Reduce workery o poloze dat na disku
12. Reduce worker pouzije RPC k ziskani dat z lokalniho disku map workeru
13. Az ma reduce worker vsechna docasna data, setridi je podle docasnych klicu

![alt](./images/mapreduce_e4.png)

14. Reduce worker iteruje pres setrizena docasna data
15. Pro kazdy docasny klic pripneme jehp hodnotu do output filu pro tuto reduce partition

![alt](./images/mapreduce_e5.png)

## Combine funkce

- uzivatelem specifikovana funkce
- neco jako reduce funkce ale jeste v map fazi
- bezi pres lokalni data v mapperu
- slouzi ke kompresi posilaneho souboru

## Counters

- uzivatel muze priradit pocitadlo k jakekoliv akci mapperi / reduceru

## Fault Tolerance

### Worker Failure

- master pinguje workery
- pokud worker neodpovida, je oznacen za failed
- vsechny jeho tasky jsou naplanovany zpet do puvodniho idle stavu
- jeho tasky si pak rozeberou od zacatku jini workeri

### Master Failure

- 2 strategie

#### Strategie A

- master si dela periodicke checkpointy master datovych struktur
- pokud master zemre, nova kopie je nastartovana z pozice posledniho checkpointu

#### Strategie B

- Jeden master -> jeho selhani je malo pravdepodobne
- Pokud selze, cely prubeh MapReduce se zahodi

## Stragglers

- "struggler"
- stroj, ktery je pomaly -> nektere casti mu trvaji nezvykle dlouho

### Reseni straggleru:

- tesne pred dokoncenim MapReduce operace master naplanuje backup executions zbylych zapocatych tasku
- task je oznacena za hotovou, pokud jeji primarni nebo backup vykonavani je dokonceno

## Granularita tasku

- M casti Map faze
- R casti Reduce faze
- Master provede `O(M + R)` scheduling rozhodnuti
- Master uchovava `O(M * R)` status informaci v pameti
- R je typicky omezeno uzivatelem

# Hadoop MapReduce

- HDFS + JobTracker (master) + TaskTracker (slave)

## JobTracker

- Master
- pracuje jako scheduler - deleguje praci do TaskTrackeru
- komunikuje s NameNode (HDFS master) a vyhleda TaskTracker (Hadoop client) pobliz dat
- presune skutecnou praci do TaskTracker uzlu

## TaskTracker

- client
- Prijima tasky od JobTrackeru
- Map, Reduce, Combine
- Input a output cesty
- Ma omezeny pocet task slotu
- Pro kazdy task vyobi novou instanci JVM (Java Virtual Machine)
- Pocet volnych slotu posila pres heartbeat JobTrackeru

![alt](./images/mapreduce_hdfs.png)
