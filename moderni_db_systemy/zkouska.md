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
