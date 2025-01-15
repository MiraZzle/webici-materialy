# Exam 2015

1. 4V u BigData

-   Volume - dat přibývá a je jejich stále větší a větší objem, který exponenciálně roste
-   Variety - data jsou velmi různorodé, data mohou být (částečně) strukturovaná i nestrukturovaná, jsou různových datových formátů a modelů a struktur
-   Velocity - jsou velké požadavky na zpracování, analyzování a vyhodnocování dat, nejlépe real-time
-   Veracity - data jsou často neúplná, poškozená ať už to je kvůli aproximacím, zakrouhlování, latenci, neucelenosti atd.

2. Předvést výhody Map/Reduce na konkrétním příkladu + jak v tomto příkladě může pomoct metoda Combine
   Obecně metoda combine slouží jako "lokální reduce", tedy proběhne mezi map a reduce metodou. Jejím cílem je zkomprimovat data tak, aby se ušetřila práce jednotlivým reduce úlohám, tedy vlastně pro ně předpočítávají výsledky lokálně pro jednotlivé map operace.

    Pro úlohu bych zvolil např. knihkupectví, kde ke každé knize kterou nabízí znám její cenu a počet prodaných kusů. Cílem je spočítat obrat knihkupectví. V map fázi emitnu každou knihu a celkový příjem pro danou knihu. Následující combine fáze může pro stejný klíč (stejnou knihu) sečíst již rovnou dané hodnoty obratu, čímž snížím celkový počet úloh pro reducer. Reducer potom agreguje všechny podvýsledky a pro každou knihu vypíše celkové příjmy za danou knihu.

3. Rozdíl mezi scale-in a scale-out
   Scale-in (vertikální) je metoda škálování, kdy mám jeden stroj a ten daný stroj stále vylepšuji (lepší hw, ramky, paměť atd.). Nevýhodou tohoto přístupu je cena (drahé) a také fakt, že hw má stále určité limitace, tedy nemohu takto škálovat do nekonečna. Tuto metodu využívají relační databáze, zaručujue atomicitu.
   Scale-out (horizontální) je metoda škálování, která využívá více strojů a distribuuje data mezi nimi. Výhodou je, že lze používat celkem levný hw (komoditní) a lze to takhle dělat do poměrně velké míry. Tuto metodu využívají právě distribuované systémy.

4. Master-slave X peer-to-peer replikace
   Master-slave replikace má 2 typy uzlů, primární (master) a sekundární (slave). Master se většinou stará o zápisy a update samotných dat, slaves slouží hlavně pro replikaci, tedy kopírování datasetů a udržování jeho kopií na různých místech. Nevýhodou je bottleneck, který představuje master, jelikož má omezenou kapacitu a tedy dokáže zpracovávat omezený počet requestů. Master může kdykovliv selhat a pak systém není plně funkční do zvolení nového mastera.
   P2P replikace má uzly, z nich všechny dokáží komunikovat s klientem (read write requesty). Řeší mnoho problému M/S architektury, tedy není zde žádný master, není tak tvořen horní limit výkonu, lze přesměrovat request na jiný, méně vytížený uzel. Musí se ale řešit W/W konflity, tedy případ, kdy 2 uživatelé upraví stejný soubor. To lze řešit již pži zápisu (zabránění konfliktu pomocí zámků). Nebo se také lze doptat ostatních replik na validnost dat, pokud souhlasí dostatečný počet, tak se data odešlou uživateli.

    ### **Shrnutí rozdílů:**

    | Charakteristika           | Master-Slave                        | Peer-to-Peer                       |
    | ------------------------- | ----------------------------------- | ---------------------------------- |
    | **Architektura**          | Centralizovaná                      | Decentralizovaná                   |
    | **Typ uzlů**              | Master (zápis), Slaves (čtení)      | Všechny uzly rovnocenné            |
    | **Konzistence**           | Jednodušší, řízena masterem         | Řešení W/W konfliktů nutné         |
    | **Odolnost vůči selhání** | Nízká (selhání masteru je kritické) | Vysoká (jiné uzly převezmou práci) |
    | **Výkon**                 | Omezený masterem                    | Lépe škálovatelné                  |

5. Redis: proč není standardní key-value DB
   Redis není standarní key-value db, protože disponuje mnoha cache-like vlastnostmi, jako je nastavování time to live u klíčů.
   Dále podporuje mnoho pokročilejší datových struktur, ne skutečně jen nějakou simple key-value, např. list, hash, set, sorted set atd.
   Funguje in-memory, díky tomu může figurovat jako cache a je tedy extrémně rychlá. Disk se používá pro persistenci a obnovu dat po restartu.

6. Riak ring
   Riak ring je základní struktura pro distribuovatelnost v riak clusteru. Jedná se o 160 bitový prostor čísel, který je rozdělený na rovnoměrné části podle počtu fyzických uzlů a ty se dále také dělí na virtual nodes, také rovnoměrně. Slouží k replikaci dat, tedy jako každý vnode je zodpovědný za nějakou část klíčů, která se nachází v jeho části riak ringu. Riak požívá konzistentní hashování k replikaci dat, tedy hashovací fce mapuje klíč do kruhu a daná data jsou pak uložena do příslušné části v ringu.

7. Read a write quorum
   Write quorum je problém, který řeší případ, na kolik uzlů musí být zapsáno a by byl zápis považován za úšpěšný, stejně tak u read quorum.
   Tento koncept se používá v distribuovaných systémech. Často si uživatel může nastavit replikační faktor, který pak figuruje ve výpočtu. Replikační faktor říká počet replikovaných uzlů pro každou položku dat.

8. Popsat Mongo DB Replica set a vysvetlit pojmy primary, secondary, elections, arbiter
   MongoDB má master-slave architekturu pro každý replica set. Replica set je set uzlů, který spravuje nějakou část datasetu, obsahuje právě jeden primární uzel a jeho ostatní uzly jsou slaves.
   Při dotázání na nějaká data, se najde daný replika set a kontaktuje se primární uzel. Primary a secondary jsou tady názvy uzlů, kde primary je master a slave je secondary. Pro každý replica set si lze nastavit, jestli skutečně write requesty může přijímat jen primární uzel, nebo jestli i sekundární apod. nebo i nejbližší uzel (kvůli latenci).
   Elections jsou proces vybírání primary uzlu. Mohou vzniknout při step downu dosavadního primárního uzlu, nebo jiný uzel má vyšší prioritu, primární uzel vypadne kvůli hw chybě apod.
   V elections hraje roli heartbeat (tedy jaké uzly jsou aktivní a mohou být součástí volby) a priorita.
   Arbiter je speciální uzel, který nemůže být zvolen jako primární a nenese žádný dataset (nereplikuje). Slouží pouze k tomu, aby volil v případě, že v repliace setu je sudý počet uzlů (volí podle stejných pravidel, jako všichni ostatní).

9. Sloupcová X klasická DB

10. Write & delete v Cassandře

11. Amdahlův zákon

12. Gremlin X Cypher, rozdíly, příklady

# Exam 25. 5. 2022 + 8. 6. 2023

1. Rozdiel medzi Master slave a P2P replikáciou

2. Cassandra Write a Delete + popísať súvisiace pojmy ako memtable, sstable, tombstone, …

3. Rozdiel medzi relačnou dbs a array dbs + uviesť príklad kedy je array lepšia

4. Amhdal’s law a jeho dopady v praxi + príklad

5. Máme autorov, knihy, publisherov, navrhnite ako v grafovej dbs (neo4j) reprezentovať také dáta a vzťahy medzi nimi a na vami zvolenej reprezentácii spravte dotaz ktorý nájde publishers of co-workers of co-workers od Dan Brown

6. Máme zmiešané dáta o študentoch - časť relačná a časť JSON. Ako by ste také dáta reprezentovali v multimodelovej db (PostgreSQL) a ako by ste sa na oba druhy dát dotazovali pomocou 1 dotazu?

7. Hadoop MapReduce framework - popísať hlavné fázy, kde sú uložené dáta a aký je rozdiel medzi JobTrackerom a TaskTrackerom

8. Máme dáta o knihách - názov, ISBN, …, ku každej knihe máme aj zoznam jej autorov, o autoroch vieme ich meno, priezvisko, vek, krajinu pôvodu. Ako by ste takéto dáta reprezentovali v MongoDB? Popíšte vami zvolený model a vysvetlite jeho výhody a demonštrujte ako by ste nad danou reprezentáciou našli knihy ktoré boli spolunapísané Danom Brownom alebo Robertom Neviemakým 😅

9. Popíšte čo je ti Riak ring, aké má výhody a vysvetlite súvisiace pojmy

10. Máme CSV soubor s daty o knihách - titul, autor, cena, vydavatel, ... Popište MapReduce execution která vypočte celkovou a průměrnou cenu knih každého vydavatele + vysvětlete zda můžeme použít funkci Combine (+ jak / proč ne)

11. Vysvětlene rozšíření databáze Redis v kontextu tradičních key-value databázi a uveďte příklady.

Poznámka autora:
Na zkoušku jsou 2.5 hodiny, což se zdá jako spousta času, ale je to taky hodně psaní (každá otázka má vlastní A4), takže za něj budete ještě rádi.
