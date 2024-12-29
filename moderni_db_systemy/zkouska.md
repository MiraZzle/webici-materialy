# **Materiály ke zkoušce z Moderních databázových systémů**

[**Stránky předmětu**](https://www.ksi.mff.cuni.cz/~holubova/NDBI040/)

## Osnova

- [Vrstvy databázových modelů](#vrstvy-databázových-modelů)
- [Big Data](#big-data)
  - [Definice](#definice-podle-gartneru---společnost-pro-it-analýzy-a-poradenství)
  - [Zdroje Big Data](#zdroje-big-data)
  - [Hlavní charakteristiky Big Data](#hlavní-charakteristiky-big-data)
  - [Zpracování Big Data](#zpracování-big-data)
- [Výhody NoSQL databází](#výhody-nosql-databází)
- [MapReduce](#mapreduce)
- [Apache Spark](#apache-spark)
  - [RDD operace](#rdd-operace)
    - [Transformace](#rdd-transformace)
    - [Akce](#rdd-akce)
- [Distribuční modely](#distribucni-modely)
- [Cloud computing](#cloud-computing)

---

# **Vrstvy databázových modelů**

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

# Apache Spark

- data analytics engine
- narozdil od Hadoop MapReduce je rychlejsi diky praci in memory narozdil od disk I/O, ktery zpomaloval MapReduce
- podporuje taky Spark SQL

## Driver program

- Spark Aplikace = driver program
- Obsahuje uzivatelem specifikovany kod pro dany problem a provadi orchestraci
- koordinuje paralelni zpracovani
- nasloucha executorum (worker nodes)
- mela by bezet blizko worker nodes (idealni v jedne LAN)
- Je spravovan skrze Web UI (ukazoval nam Yaghob v Cloudu)

## SparkContext

- centralni entita v driver programu
- koordinace mezi driverem a cluste managerem
- ridi resourcy a provadeni tasku

## ClusterManager

- spark si muze vybrat Cluster Managera
- externi system, ktery alokuje resourcy
- Priklady: Kubernetes, YARN (Resource Manager Hadoopu), Apache Mesos

## Resilient Distributed Dataset (RDD)

- immutabilni
- kolekce elementu rozdelenych mezi uzly v clusteru
- automaticka recovery
- lze persistovat v pameti (volani `persist` nebo `cache` funkci)
- paralelni zpracovani

### Jak vytvorit RDD

- paralelizujeme existujici kolekci v driver programu

```Java
List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
JavaRDD<Integer> distData = sc.parallelize(data);
```

- reference na externi dataset (treba v HDFS nebo Local file system) podporovany **Hadoopem**

```Java
JavaRDD<String> distFile = sc.textFile("data.txt");
```

## RDD operace

- delime na Transformace a Akce

## RDD Transformace

- vytvoreni noveho datasetu z existujiciho
- prakticky `map` funkce

### **1. `map(func)`**

- **Popis:** Vytvoří nové rozdělené dataset (`RDD`), kde každý prvek původního datasetu je transformován funkcí `func`.

```java
// Příklad: Zvětší každý prvek o 1
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
JavaRDD<Integer> mappedRDD = rdd.map(x -> x + 1);
```

---

### **2. `union(otherDataset)`**

- **Popis:** Spojí dva dataset (`RDD`) a vrátí nový dataset, který obsahuje všechny prvky z obou.

```java
// Příklad: Spojení dvou datasetů
JavaRDD<Integer> rdd1 = sc.parallelize(Arrays.asList(1, 2, 3));
JavaRDD<Integer> rdd2 = sc.parallelize(Arrays.asList(4, 5, 6));
JavaRDD<Integer> unionRDD = rdd1.union(rdd2);
```

---

### **3. `filter(func)`**

- **Popis:** Vrátí nový dataset, který obsahuje pouze prvky, na kterých funkce `func` vrátí `true`.

```java
// Příklad: Filtrace sudých čísel
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
JavaRDD<Integer> filteredRDD = rdd.filter(x -> x % 2 == 0);
```

---

### **4. `reduceByKey(func, [numPartitions])`**

- **Popis:** Pokud pracujete s páry `(K, V)`, agreguje hodnoty pro každý klíč pomocí funkce `func`. Volitelně můžete nastavit počet oddílů.

```java
// Příklad: Sčítání hodnot podle klíče
JavaPairRDD<String, Integer> pairs = sc.parallelizePairs(Arrays.asList(
    new Tuple2<>("a", 1),
    new Tuple2<>("b", 2),
    new Tuple2<>("a", 3)
));
JavaPairRDD<String, Integer> reducedRDD = pairs.reduceByKey((x, y) -> x + y);
```

---

### **5. `sortByKey([ascending], [numPartitions])`**

- **Popis:** Seřadí páry `(K, V)` podle klíče. Můžete zvolit vzestupné (`true`) nebo sestupné (`false`) řazení.

```java
// Příklad: Seřazení párů podle klíče vzestupně
JavaPairRDD<String, Integer> pairs = sc.parallelizePairs(Arrays.asList(
    new Tuple2<>("b", 2),
    new Tuple2<>("a", 1),
    new Tuple2<>("c", 3)
));
JavaPairRDD<String, Integer> sortedRDD = pairs.sortByKey(true);
```

---

### Další funkce:

- **`intersection`**: Vrátí dataset s prvky, které se nachází v obou vstupech.
- **`distinct`**: Vrátí dataset obsahující pouze unikátní prvky.

```java
// Příklad: Unikátní prvky
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 2, 3, 4, 4));
JavaRDD<Integer> distinctRDD = rdd.distinct();
```

## RDD Akce

- vratime hodnotu do driveru po nejaky vypoctu nad datasetem
- prakticky `reduce` funkce

### **1. `reduce(func)`**

- **Popis:** Agreguje prvky datasetu pomocí funkce `func`. Funkce musí být **komutativní** a **asociativní**, aby výpočet mohl probíhat paralelně.

```java
// Příklad: Součet všech čísel v datasetu
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
Integer sum = rdd.reduce((x, y) -> x + y);
```

---

### **2. `count()`**

- **Popis:** Vrátí počet prvků v datasetu.

```java
// Příklad: Spočítání prvků v datasetu
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
long count = rdd.count();
```

---

### **3. `first()`**

- **Popis:** Vrátí první prvek datasetu.

```java
// Příklad: Získání prvního prvku
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
Integer firstElement = rdd.first();
```

---

### **4. `take(n)`**

- **Popis:** Vrátí pole s prvních `n` prvky datasetu.

```java
// Příklad: Získání prvních 2 prvků
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(1, 2, 3, 4));
List<Integer> firstTwo = rdd.take(2);
```

---

### **5. `takeOrdered(n, [ordering])`**

- **Popis:** Vrátí prvních `n` prvků datasetu, seřazených buď podle jejich přirozeného pořadí, nebo podle vlastní komparace.

```java
// Příklad: Získání 2 nejmenších prvků
JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(4, 2, 3, 1));
List<Integer> smallestTwo = rdd.takeOrdered(2); // Výsledek: [1, 2]

// Příklad s vlastním pořadím (sestupně)
List<Integer> largestTwo = rdd.takeOrdered(2, Comparator.reverseOrder()); // Výsledek: [4, 3]
```

## Shuffle Operace

- nektere akce spousti shuffle
- shuffle = mechanismus pro redistribuci dat pres partitions
- nutnost kopirovani dat mezi executory a stroji

### Priklad Shuffle Operace: ReduceByKey

- Problém: Hodnoty stejného klíče mohou být v různých partitions nebo na různých strojích v clusteru
- Řešení (Shuffle): Spark načte hodnoty stejného klíče ze všech paritions, spojí je dohromady a spočítá finální výsledek

## RDD vs DataFrame vs Dataset

- RDD = primarni API ve Sparku, neobsahuje optimaliyace jako DataFrame nebo Dataset
- DataFrame = data jsou organizovana do pojmenovanych sloupcu
  - Weak typing: `Dataset<Row>`
  - Kolekce generických objektů
  - Jednodussi prace s daty (jako tabulka v SQL)
- Dataset = distribuovana kolekce dat
  - Strong typing: `Dataset<T>`, kde `T` je definice tridy
  - Neco jako RDD s podporou Spark SQL zaroven

> Spark 2.0 sjednotil DataFrame a Dataset do jedné struktury s dvěma API:

> Nepřísně typované API (DataFrame): Pro jednoduchost a SQL-like operace.
> Silně typované API (Dataset): Pro typovou bezpečnost a práci s konkrétními třídami.

![alt](./images/spark_api.png)

# Principy MDBS

- vzdáme se něketerých ACID vlastností
- Ze silné konzistence -> slabá konzistence

## Scalability

### Vertikalni scaling (scaling up)

- v historii preferovano
- zarucovalo strong consistency (protoze stacil jen jeden stroj)
- Vendor lock-in
- drahe
- stale existuje limit pro silu a kapacitu jednoho stroje

### Horizontalni scaling (scaling out)

- system distribuujeme pres vice stroju / uzlu
- staci komoditni hardware

### Klamy (fallacies) horizontalniho scalingu

- Sit je spolehliva
- Nulova letence
- Nekonecny bandwidth
- Sit je bezpecna
- Topologie se nemeni
- Mame pouze jednoho spravce
- Nulova cena transportu
- Sit je homogenni

## ACID

- typicke vlastnosti ocekavane u relacnich DBMS
- Databzova transakce = jednotka prace (sekvence operaci) v DBMS
- tyto vlastnosti jsou ale prilis drahe v distribuovanych systemech
- Atomicity - vse nebo nic, jedna selhana cast transakce = cela transakce selhala
- Consistency - databaze se presouva pouze mezi konzistentnimi stavy
- Isolation - efekty nedokonceene transakce (v prubehu, failed) nejsou viditelne zvenku
- Durability - po commitu transakce zustane transkace commited (i pres vypadek elektriny, errory)

## CAP Theorem

- CAP ma 3 casti
- Obecne ale nedava smysl, protoze definice nejsou dostatecne presne (napr. pouze CP by naznacovalo nikdy available)

**Theorem:** `Only 2 of the 3 guarantees can be given in a “shared-data” system`

![alt](./images/cap_theorem.png)

### Consistency

- po zmene dat, vsechny cteni maji videt stejna data
- vsechny uzly maji vzdy obsahovat stejna data

### Availability

- vsechny dotazy (cteni, zapisy) dostanou vzdy odpoved
- nazavisle na vypadcich

### Partition Tolerance

- system funguje i po izolovani podcasti systemu
- problemy s pripojenim neshodi system pokud je system fault tolerantni

## BASE

- lepe skalovatelny
- sada principu jako ACID

### Basically Available

- system funguje prakticky vetsinu casu
- castecne vypadky se deji ale bez selhani celeho systemu

### Soft State

- system se neustale meni
- stav systemu je nedeterministicky (kontrast vuci consistency v ACIDu, kde vzdy mame nejaky pevny stav)

### Eventual Consistency

- nekdy v budoucnu bude system konzistentni (az treba vsechny stroje budou synced)

## ACID vs BASE

- ACID garantuje _Consistency_ a _Availability_
  - pesimisticky pristup
  - toto dovoluje DB pouze na jednom stroji
- BASE garantuje _Availability_ a _Partition
  tolerance_
  - optimisticke
  - distribuovane databaze
- samostatny system je `CA` system

## Silna konzistence

![alt](./images/strong_consistency.png)

## Eventualni konzistence

![alt](./images/eventual_consistency.png)

# Distribucni modely

- Horizontalni scaling = databaze bezi na clusteru serveru
- Mame dva ortogonalni pristupy (= pristupy, ktere mohou byt aplikovany zaroven. Jsou v jinych dimenzich / pohledech na vec)
  - **Replikace** - kopirovani stejnych dat pres uzly (master-slave nebo peer-to-peer)
  - **Sharding** - jina data na jinych uzlech

## Single server

- bez jakekoliv distribuce
- DB pouze na tomto stroji
- Dobre treba pro Grafove DB -> slozita distribuce

## Sharding

- davame ruzna data na ruzne uzly
- idealne chceme pohromade data, ke kterym pristupujeme casto dohromady
- selhani uzlu -> jeho data jsou nedostupna (proto casto kombinujeme s replikaci)

### Rozmisteni uzlu

<ol type="A">
  <li>Jeden uživatel bere data z jednoho serveru</li>
  <li>Fyzická poloha</li>
  <li>Distribuujeme rovnoměrně přes uzly</li>
</ol>

## Master-slave Replikace

- jeden uzel je primarni (master), zbytek sekundarni (slaves)
- master zodpovida za zpracovani a update dat
- master limituje svoji schopnosti zpracovani updatu
- Problemy:
  - skalovatelnost zapisu (master je bottleneck)
  - nechrani pred selhanim mastera

### Volba mastera

- Manualni: user-defined
- Auomaticka: cluster-elected

## Peer-to-peer replikace

- resi mnoho problemu master-slave replikace
- bez mastera
- Problem: konzistence
  - zapisem na 2 mista vznika write-write konflikt
- Reseni:
  - pri zapisu dat repliky koordinuji pro zabraneni konfliktu
  - vsechny repliky nemusi souhlasit, staci vetsina

## Kombinace Shardingu a Replikace

### Master-slave replikace a sharding

- vice masteru, ale master je pouze pro nejaky dany datovy item
- uzel muze byt master pro nejaka data a slave pro jina

### Peer-to-peer replikace a sharding

- casta strategie pro sloupcove DB
- idealne replikacni faktor 3, takze kazdy shard je na 3 uzlech

### Konzistence

#### **Write Consistency (Konzistence zápisu)**

- **Problém:** Dva uživatelé chtějí upravit stejný záznam (write-write konflikt).
- **Důsledek:**
  - Ztracený update: Druhá transakce přepíše hodnotu z první transakce.
  - Ostatní transakce čtou nesprávnou hodnotu a vrací špatné výsledky.
- **Řešení:**
  - **Pesimistické:** Zabraňuje konfliktům (např. write lock).
  - **Optimistické:** Konflikty se nechají proběhnout, ale následně se detekují a řeší (např. podmíněný update nebo uložení obou hodnot jako konflikt).

#### **Read Consistency (Konzistence čtení)**

- **Problém:** Jeden uživatel čte, zatímco druhý zapisuje (read-write konflikt).
- **Důsledek:**
  - Nekonzistentní čtení: Hodnota objektu se mezi dvěma čteními změní.
  - Transakce, které čtou starou hodnotu, mohou vracet chybné výsledky.
- **Databáze:**
  - **Relační databáze:** Podporují ACID transakce (zajišťují konzistenci).
  - **NoSQL databáze:** Často podporují atomické operace jen v rámci jedné "agregace".
    - Pokud je update napříč více agregacemi, může dojít k **oknu nekonzistence**.
- **Další problém:** Konzistence replikace
  - Zajistit, aby všechny repliky měly stejnou hodnotu při čtení.

#### **Quorums (Kvóra)**

- **Otázka:** Kolik uzlů je potřeba zapojit pro zajištění silné konzistence?
- **Write quorum:** Počet uzlů potvrzujících zápis musí být:
  $$ W > \frac{N}{2} $$

  - \( N \) = počet uzlů v replikaci (replikační faktor).
  - \( W \) = počet uzlů zapojených do zápisu.

- **Read quorum:** Počet uzlů nutných pro čtení:
  $$ R + W > N $$
  - \( R \) = počet uzlů kontaktovaných pro čtení.
- **Princip:**
  - Zápisy s konflikty: Pouze jeden může získat většinu.
  - Pro zajištění aktuální hodnoty musíme kontaktovat dostatečný počet uzlů.

# Zpracovani Big Data

## Priklady ukolu pro Big Data

- analyza
- visualizace
- agregace
- manipulace a uprava dat

## Cloud computing

- Pronajem hw/sw (servery, data, software...) poptavce
- [Virtualizace a cloud computing předmět](https://is.cuni.cz/studium/predmety/index.php?do=predmet&kod=NSWI150)

### Modely cloudových služeb:

- **Software as a Service (SaaS):**

  - Primo hotovy sw produkt
  - zoom, shopify, slack

- **Platform as a Service (PaaS):**

  - Prostredi pro devs pro nasazeni a vyvoj vcetne HW
  - Nastavena DB, security, data security, hosting
  - Microsoft Azure, AWS Lambda

- **Infrastructure as a Service (IaaS):**
  - primo hw a infrastruktura (nejnizsi model)

![alt](./images/cloud_models.png)

### Spojeni Cloud computingu a Big Data

- nemusime resit drahy HW, instalaci a udrzbu
- jednoducha skalovatelnost
- nevyhoda je vendor lock-in

# Key-value databaze

- prakticky hash table
- hodnota je BLOB (nespecifikovany typ a struktura - muze byt cokoliv)

## Priklady

- Riak
- Redis
- MemcachedDB

## Vhodna vyuziti

- ### Session info
  - klicem je `session_id`
  - k ulozeni session staci `put` a pro dotaz jednoduchy `get`
- ### Nakupni kosiky
  - podobne jako Session info
- ### User preference

## Nevhodna vyuziti

- ### Vztahy mezi daty
- ### Transakce s vice operacemi
  - Ukladame vice klicu -> jedno selhani -> zadny z klicu v transakci se neulozi (revert / roll back)
- ### Dotazy na obsah dat

## Dotazovani

- dotazujeme se pomoci klice
- pomoci obsahu dat neni mozne (BLOB -> data nemusi byt jakkoliv definovana)
- klice jsou generovany nejakym algoritmem (auto incremenet), user generated nebo treba time stamps

# Riak (Key-value)

- open source

## Terminologie

- ### `bucket` = namespace pro klice
  - lze pro bucket nastavit replikacni faktor `n_val`
  - `allow_mult` - konkurentni updaty
  - `/riak/<bucket>/<key>`
- ### `ring`
- ### `hinted handoff`
- ### `gossiping`

## Principy

- klice jsou ukladany do bucketů (= namespaces)
- default interface je `HTTP`

## Riak Links

- umoznuji tvorit vztahy mezi objekty
- tvori se pridanim `Link` headeru k objektu (pres HTTP)

## Riak Search

- fulltext search engine
- podpora dotazovani na textova data
- pouziti pro hledani zaznamu podle obsahu

### Dotazování v Riak Search

- **Typy dotazů:**
  - **Zástupné znaky:** `Bus*`, `Bus?`
  - **Rozsahy:**
    - `[red TO rum]`: zahrnuje "red", "rum" a všechny mezi nimi
    - `{red TO rum}`: zahrnuje pouze slova mezi "red" a "rum"
  - **Logické operátory:** `(red OR blue) AND NOT yellow`
  - **Prefixové shody:** Vyhledávání podle počátečních písmen
  - **Blízkost:**
    - `"See spot run"~20`: slova v rámci 20 slov

### Proces indexace dat v Riak Search:

1. Načtení dokumentu
2. Rozdělení na pole
3. Rozdělení polí na termíny
4. Normalizace termínů
5. Zápis `{Field, Term, DocumentID}` do indexu

**Indexování:**

```
index <INDEX> <PATH>
```

**Vyhledávání:**

```
search <INDEX> <QUERY>
```

## Priklady Riaku

### **Příklady použití Riaku**

#### **Práce s Buckets:**

1. **Seznam všech buckets:**

   ```bash
   curl http://localhost:10011/riak?buckets=true
   ```

2. **Získání vlastností bucketu `foo`:**

   ```bash
   curl http://localhost:10011/riak/foo/
   ```

3. **Změna vlastností bucketu `foo`:**
   ```bash
   curl -X PUT http://localhost:10011/riak/foo -H "Content-Type: application/json" -d '{"props" : { "n_val" : 2 } }'
   ```

---

#### **Práce s daty:**

1. **Uložení prostého textu do bucketu `foo`:**

   ```bash
   curl -i -H "Content-Type: plain/text" -d "My text" http://localhost:10011/riak/foo/
   ```

2. **Uložení JSON souboru do bucketu `artists` s klíčem `Bruce`:**

   ```bash
   curl -i -H "Content-Type: application/json" -d '{"name":"Bruce"}' http://localhost:10011/riak/artists/Bruce
   ```

3. **Získání objektu:**

   ```bash
   curl http://localhost:10011/riak/artists/Bruce
   ```

4. **Aktualizace objektu:**

   ```bash
   curl -i -X PUT -H "Content-Type: application/json" -d '{"name":"Bruce", "nickname":"The Boss"}' http://localhost:10011/riak/artists/Bruce
   ```

5. **Smazání objektu:**
   ```bash
   curl -i -X DELETE http://localhost:10011/riak/artists/Bruce
   ```

---

#### **Práce s Riak Links:**

1. **Přidání alba a propojení s performerem:**

   ```bash
   curl -H "Content-Type: text/plain" -H 'Link: </riak/artists/Bruce>; riaktag="performer"' -d "The River" http://localhost:10011/riak/albums/TheRiver
   ```

2. **Najít umělce, který provedl album `The River`:**

   ```bash
   curl -i http://localhost:10011/riak/albums/TheRiver/artists,performer,1
   ```

3. **Najít umělce, kteří spolupracovali s tím, kdo provedl `The River`:**
   ```bash
   curl -i http://localhost:10011/riak/albums/TheRiver/artists,_,0/artists,collaborator,1
   ```

## Interni mechanismy Riaku

- `BASE` principy
- pouziva `quora`

  - `N` = replikacni faktor (default = `3`)
  - Zapis: data musi byt zapsana aspon na `W` uzlech
  - Cteni: data musi byt nalezena aspon na `R` uzlech
  - `W` a `R` muzeme nastavit pro kazdou operaci

- Plati tyto nerovnosti:
  $$ W > \frac{N}{2} $$
  $$ R + W > N $$

- **Příklad:**
  - Cluster Riaku má:
    - **`N = 5`** (počet replik)
    - **`W = 3`** (minimální počet uzlů pro potvrzení zápisu)
  - **Zápis je úspěšný, pokud**:
    - Data jsou úspěšně zapsána na více než `3` uzlech
  - **Tolerované výpadky při zápisu:**
    - Cluster zvládne výpadek až **`N - W = 2`** uzlů a stále může provádět zápisy

## Clustering v Riaku

- bez mastera -> kazdy uzel muze obslouzit jakykoliv dotaz

- ### Konzistentni hashovani

  - hashovaci funkce mapuje klice do kruhu
  - kazdy uzel zodpovedny za interval hashu (= `slot`) na kruhu
  - prumerne remapujeme jen `k / n` klicu, kde `k` = pocet klicu a `n` = pocet slotu

## Riak Ring

- stred kazdeho clusteru
- `160-bitovy` prostor celych cisel rozdeleny na rovnomerne intervaly
- ### Kazdy `fyzicky uzel` ma `virtualni uzly` (= vnodes)

  - virtualni uzel je zodpovedny za cast klicu
  - kazdy fyzicky uzel ma na starost `1/ (pocet fyzickych uzlu)` ringu
  - #### Pocet vnodes na kazdem uzlu:

$$
\text{|vnodes\_na\_1\_uzlu|} = \frac{|partitions|}{\text{|fyzicke\_uzly|}}
$$

- ### Priklad:
  - Ring s `32` paritions
  - `4` fyzicke uzly
  - `8` vnodes na fyzicky uzel

![alt text](./images/riak_ring_e1.png)

## Replikace v Riaku

- nastavujeme `N value` (default = 3)
- objekty dedi `N value` z jejich bucketu

### Hinted kandoff

- resi selhani uzlu
- funguje diky replikaci
- Zajistuje high availability Riaku

1. **Selhání uzlu:** Pokud uzel v klastru selže, sousední uzly dočasně převezmou jeho úlohu.
2. **Dočasné převzetí:** Sousední uzly zpracovávají čtení a zápisy, aby zajistily dostupnost systému.
3. **Obnova:** Po návratu selhaného uzlu sousední uzly předají všechny mezitímní změny zpět.

**Výhoda:** Systém zůstává dostupný a data nejsou ztracena.

![alt](./images/hinted_handoff.png)

## Gossip protokol

- robustni sireni informaci
- `Gossiping` = posilani informaci nahodnemu uzlu
  - aktualizuje info a clusteru
- kazdy uzel gossipuje
  - periodicky
  - pri zmene na ringu

## Vector clocks

- kazdy uzel muze zpracovavat dotaz -> jaka verze hodnoty je ale aktualni?
- reseni: vector clocks
- kazda ulozena hodnota je tagged vector clockem
- ulozeno v headeru objektu
- pri kazdem updatu je hodnota vector clocku aktualizovana

## Riak siblings

- `siblings` = vicero objektu pod jednim klicem
- aktivovano `allow_mult = true` priznakem
- mohou vzniknout pri konkurentnim zapisu, starych vector clocks, neexistujicich vector clocks

## Koordinující uzel (vnode) v Riaku

1. Najde **vnode** pro klíč pomocí hashovací funkce.
2. Určí další **N-1 vnodes** pro repliky.
3. Odešle požadavek na všechny vybrané **vnodes**.
4. Čeká, dokud dostatečný počet odpovědí nesplní **kvórum** (pro čtení/zápis).
5. Vrátí výsledek klientovi.

# Redis (Key-value + multi-model)

- spise dokumentova multi-model databaze s podporou key-value

## Terminologie

## Principy

- klice jsou `binary safe` -> jakakoliv bina rni posloupnost muze byt klicem (tedy neni omezeni na text nebo citelny obsah)
- hodnota muze byt jakykoliv objekt (string, hash, list, set...)
- podpora pro mnozinove operace (range, diff, union, intersection)

### In-Memory Data Set

- data jsou primarne ulozena v pameti
- persistence je resena dumpingem datasetu na disk / pridanim prikazu do logu

### Publish/subscribe

### Cache-like chovani

- klice mohou mit nastaveny `TTL`
- pak jsou automaticky vymazany -> cache charackteristika

## Datove typy Redisu

### **String**

- **Binary safe:** Klíč může obsahovat libovolnou binární sekvenci.
- **Maximální velikost:** 512 MB.
- **Operace:**
  - Nastavení a načtení: `SET`, `GET`.
  - Modifikace: `APPEND`, `STRLEN`, `SETRANGE`.
  - Operace s čísly: `INCR`, `DECR`, `INCRBY`, `DECRBY`.
  - Bitové operace: `GETBIT`, `SETBIT`, `BITCOUNT`.

**Příklad:**

```plaintext
> SET count 10
OK
> INCR count
(integer) 11
> GET count
"11"
> DEL count
(integer) 1
```

---

### **List**

- **Seřazený seznam řetězců:** Prvky jsou uspořádány podle pořadí vložení.
- **Maximální délka:** Více než 4 miliardy prvků.
- **Operace:**
  - Přidání: `LPUSH` (hlava), `RPUSH` (konec), `LINSERT`.
  - Odebrání: `LPOP`, `RPOP`, `LREM`.
  - Přístup k prvkům: `LRANGE`, `LINDEX`.
  - Délka seznamu: `LLEN`.

**Příklad:**

```plaintext
> LPUSH animals cat
(integer) 1
> RPUSH animals dog
(integer) 2
> LRANGE animals 0 -1
1) "cat"
2) "dog"
```

---

### **Set**

- **Neuspořádaná kolekce unikátních řetězců.**
- **Maximální velikost:** Více než 4 miliardy prvků.
- **Operace:**
  - Přidání/Odebrání: `SADD`, `SREM`.
  - Test členství: `SISMEMBER`.
  - Množinové operace: `SUNION`, `SINTER`, `SDIFF`.

**Příklad:**

```plaintext
> SADD colors red green blue
(integer) 3
> SINTER colors:1 colors:2
1) "green"
```

---

### **Sorted Set**

- **Seřazená kolekce s hodnotami přiřazenými skóre.**
- **Operace:**
  - Přidání/Odebrání: `ZADD`, `ZREM`.
  - Počítání: `ZCARD`, `ZCOUNT`.
  - Získání prvků podle skóre: `ZRANGEBYSCORE`.

**Příklad:**

```plaintext
> ZADD scores 10 Anna 20 John
(integer) 2
> ZRANGE scores 0 -1
1) "Anna"
2) "John"
```

---

### **Hash**

- **Mapa mezi poli a hodnotami řetězců.**
- **Operace:**
  - Nastavení/Načtení: `HSET`, `HGET`, `HMSET`.
  - Všechny hodnoty/pole: `HGETALL`, `HKEYS`, `HVALS`.
  - Smazání: `HDEL`.

**Příklad:**

```plaintext
> HSET user:id name Sara age 25
(integer) 1
> HGET user:id name
"Sara"
> HGETALL user:id
1) "name"
2) "Sara"
3) "age"
4) "25"
```

## Transakce v Redisu

- kazdy prikaz je atomicky
- podporuje transakce pri pouziti vice prikazu (zachova poradi) -> vse v jedne atomicke operaci
- bez roll backu

```plaintext
> MULTI // start definice transakce
OK
> INCR foo
QUEUED
> INCR bar
QUEUED
> EXEC // provedeni transakce
1) (integer) 1
2) (integer) 1
```

## Replikace v Redisu (master-slave)

- master-slave
  - master ma vice slavu
  - uzel muze byt master a slave zaroven
- replikace je `neblokujici` na strane `mastera`
  - pri syncu slavu master pracuje dal
- replikace je `neblokujici` na strane `slavu`
- pri syncu slavu slave pracuje dal

## Synchronizace v Redisu

1. Po připojení k masteru slave odešle příkaz **SYNC**.
2. Master spustí **background saving** a začne ukládat nové příkazy do bufferu.
3. Po dokončení uložení master přenese celý soubor databáze na slave.
4. Slave uloží soubor na disk a načte jej do paměti.
5. Master pošle slave také všechny **bufferované příkazy**.

## Sharding v Redisu

### Redis Cluster (od verze 3.1)

- **Nepoužívá konzistentní hashování.**
- Klíče jsou přiřazeny do **16384 hash slotů** (CRC16 klíče modulo 16384).
- **Každý master uzel spravuje subset hash slotů.**

**Příklad:**

- 3 uzly:
  - **Node A:** Hash sloty 0–5500
  - **Node B:** Hash sloty 5501–11000
  - **Node C:** Hash sloty 11001–16383
- Přidání uzlu **D:** Některé sloty z A, B, C se přesunou na D.
- Odebrání uzlu **A:** Jeho sloty se přesunou na B a C.

**Bez přerušení provozu:** Přesun hash slotů probíhá bez nutnosti zastavit systém.

## Redis sentinel

- system pro managing Redis instanci
- monitorovani, notifikace, automaticky failover

# Sloupcove databaze

# Dokumentove databaze

# Grafove databaze

# Multimodel databaze

# Polystores

# Advanced
