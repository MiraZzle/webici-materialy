# Úvod do datového inženýrství (NDBI046) - poznámky

_Přednášející: Petr Škoda_

_Rok: 2024/25_

**Disclaimer**: `Vygenerováno Četem, za nic neruším - Mistrný pan M`

# 📚 Obsah

- [Towards Data Warehouse](#towards-data-warehouse)
- [Data Warehouse](#data-warehouse)
- [Data Modelling](#data-modelling)
- [Data (Pre)Processing](#data-preprocessing)
- [Data Catalogs & Metadata](#data-catalogs--metadata)
- [Controlled Vocabularies](#controlled-vocabularies)
- [Representation and Usage of Controlled Vocabularies](#representation-and-usage-of-controlled-vocabularies)
- [Ontologies](#ontologies)
- [Beyond Data Warehouse](#beyond-data-warehouse)
- [Data Management](#data-management)
- [Information Theory](#information-theory)
- [Building Trust – Kryptografie a zabezpečení dat](#building-trust--kryptografie-a-zabezpečení-dat)
- [Text Search](#text-search)

# Towards Data Warehouse

## 📈 Data in Business

### Business Process

- Modelováno pomocí **BPMN**
- **Operational DB systems**:

  - OLTP (On-line Transaction Processing)
  - Podpora každodenní operativy
  - Vysoká dostupnost, výkon, normalizované schéma
  - Malé read/write operace

---

## 🧠 DIKW Pyramida

| Vrstva      | Popis                                                  |
| ----------- | ------------------------------------------------------ |
| Data        | Symboly reprezentující vlastnosti objektů (pozorování) |
| Information | Přidání kontextu: klasifikace, agregace, výběr         |
| Knowledge   | Syntéza více informačních zdrojů v čase                |
| Wisdom      | Trendy, důvody, interpretace                           |

---

## 🧩 Typy dat v byznysu

### Data asset

- Data, která mají hodnotu (např. zákaznická, finanční)
- Kvalita dat určuje jejich hodnotu
- Cílem je podpora rozhodování, zlepšení výkonnosti

---

## 🧠 Business Intelligence (BI)

### Definice

- Analýza dat pro porozumění aktivitám a příležitostem
- Podpora rozhodování a strategie

### Kategorie

- **Querying/reporting**: statické přehledy (KPI)
- **Data mining**: prediktivní, explorativní (NDBI023)
- **Business analysis**: tabulky, dashboardy, EIS
- **Operational reporting**: krátkodobá rozhodnutí
- **Business Performance Mgmt (BPM)**: metriky a dlouhodobé strategie
- **Operational analytics**: near-real-time analýza pro provozní rozhodnutí

### Nástroje

- SAP BusinessObjects
- Microsoft Power BI
- Tableau (viz NPGR023)

---

## 🤝 BI z pohledu byznysu vs. IT

- Problémy:

  - Malá participace byznysu během návrhu/testování
  - Nerealistická očekávání
  - Slabý trénink a porozumění
  - Funkce bez smyslu, pomalé, nefunkční

---

## 🔄 OLTP vs. OLAP

| Vlastnost       | OLTP            | OLAP                     |
| --------------- | --------------- | ------------------------ |
| Cíl             | Operativa       | Podpora rozhodování      |
| Počet uživatelů | Tisíce          | Stovky                   |
| Operace         | R/W, transakční | Čtení, analytické dotazy |
| Architektury    | Normalizace     | MOLAP, ROLAP, HOLAP      |
| Výsledek dotazu | Záznam          | Matice (data cube)       |

---

## 📊 Multidimensional Data

- **Dimensions**: čas, produkt, atd.
- **Facts**: fakta (např. počet prodaných kusů)
- **Measures**: metriky (např. celkové tržby)

---

## 🔁 **Hierarchie dimenzí**

Hierarchie dimenzí znamená, že určitou dimenzi lze členit na více úrovní podrobnosti. Například:

- **Čas**: Rok → Čtvrtletí → Měsíc → Den
- **Geografie**: Stát → Kraj → Okres → Obec
- **Produkt**: Kategorie → Podkategorie → Produkt

Díky těmto hierarchiím lze s datovou kostkou provádět různé operace:

---

## 🔎 **Drill-down / Drill-up (navigace v hierarchii)**

- **Drill-down**: Jdeme do většího detailu (např. z kraje do okresu)

  - Příklad: Celkový prodej v roce → rozpad na jednotlivé čtvrtletí

- **Drill-up**: Agregujeme do vyšší úrovně (např. z obcí na úroveň krajů)

  - Příklad: Denní návštěvnost webu → měsíční souhrn

---

## ✂️ **Slice / Dice**

- **Slice**: Vybereme konkrétní „řez“ kostky, tj. jednu hodnotu určité dimenze

  - Příklad: Zobrazit pouze prodej v roce 2023 → získáme 2D tabulku (např. kraje × produktové kategorie)

- **Dice**: Vybereme konkrétní podmnožinu dat – více hodnot dimenzí najednou

  - Příklad: Prodej v letech 2022–2023, pro produkty „elektronika“ a kraje „Jihomoravský“ a „Praha“

---

## 🔄 **Pivot (Rotace)**

- Přetočení dimenzí – mění se osy analýzy

  - Příklad: Změníme analýzu z „kraje podle roku“ na „roky podle krajů“
  - Umožňuje jiný pohled na stejná data, často používané v tabulkách a grafech

---

## 🔁 **Drill-across / Drill-through**

- **Drill-across**: Přístup k jiné datové kostce se sdílenou dimenzí

  - Příklad: V jedné kostce vidíme prodeje, v druhé marketingové výdaje → můžeme je porovnat podle stejného kraje nebo období

- **Drill-through**: Z pohledu v kostce přejdeme k detailním řádkům ve faktové tabulce

  - Příklad: Celkový prodej v kraji = 1 000 000 Kč → kliknutím zobrazíme jednotlivé transakce, které to tvoří

---

## 🧮 Data Cube Vocabulary (RDF)

- **qb\:Observation**: pozorování
- **qb\:DataSet**: dataset
- **qb\:DimensionProperty**: dimenze (např. čas)
- **qb\:MeasureProperty**: měřená hodnota (např. unitsSold)
- **qb\:DataStructureDefinition**: struktura datové kostky

### Příklad RDF popisu pozorování:

```turtle
eg:measureMonHawaii a qb:Observation ;
    qb:dataset eg:DataSet ;
    eg:time eg:Monday ;
    eg:product eg:PizzaHawaii ;
    eg:unitsSold 4 .
```

---

## 📚 Metadata & standardy

- RDF model pro datové kostky – **W3C Data Cube Vocabulary** (2014)
- Vychází ze **SDMX**:

  - Technické standardy
  - Obsahové směrnice (Content-Oriented Guidelines)
  - URI:

    - `http://purl.org/linked-data/sdmx/2009/measure#`
    - `http://purl.org/linked-data/sdmx/2009/concept#`

---

# Data Warehouse

## 💡 Proč Data Warehouse?

- Primárním cílem je podpořit:

  - **operační funkce**
  - **požadavky na compliance**
  - **Business Intelligence aktivity**
    _(zdroj: DAMA DMBOK)_

---

## 🧱 Základní komponenty

```
Data Source -> Staging Area -> Data Warehouse
         |        Extract, Transform, Load       |
```

- **Data Warehouse ≠ OLTP databáze**
- **Není to datový skládka (Data Dump)**

---

## 👨‍💼 Role a rozsah

### Data Analyst

- **Data**: strukturovaná, statická, menší objem
- **Cíl**: zodpovědět konkrétní otázky, podpora rozhodování
- **Nástroje**: SQL, Excel, BI nástroje, Python/R
- **Dovednosti**: vizualizace, komunikace, základní statistika

### Data Scientist

- **Data**: semi/ne-strukturovaná, dynamická, velká
- **Cíl**: objevování nových příležitostí
- **Dovednosti**: pokročilé statistiky, NoSQL, ML, vizualizace

### Data Engineer

- **Cíl**: stavba a nasazení datových transformačních pipeline
- **Nástroje**: SQL, NoSQL, ETL, správa databází, infrastruktura
- **Bonus skill**: googlení

---

## 📜 Historie

- **1980s**: izolovaná data, distribuované DBMS
- **1983**: první relační DBMS (Teradata)
- **1988**: termín _Business Data Warehouse_ (Devlin & Murphy, IBM)
- **1993–1996**: Bill Inmon – definice DW jako:

  - _subject-oriented, integrated, time-variant, nonvolatile_

- **Ralph Kimball**: DW jako kopie transakčních dat pro analýzu

---

## ✅ Požadavky na DWH

- Snadná dostupnost informací
- Konzistentní a důvěryhodná prezentace
- Adaptabilita a odolnost vůči změnám
- Bezpečnost a řízený přístup
- Spolupráce s business oddělením
- Podpora rozhodování

---

## 🏗️ Architektura

### Enterprise Data Warehouse

- Komplexní a centralizované řešení

### Data Mart (DWH Lite)

- Omezený rozsah (konkrétní téma)
- Nižší náklady, rychlejší nasazení
- Může být zdrojován z hlavního DWH

### Deluxe / Supreme

- Distribuované, rozsáhlé prostředí

---

## 🔧 Vývojový proces DWH

1. **Požadavky**
2. **Návrh**
3. **Vývoj a testování**
4. **Nasazení**
5. **Údržba**

- Přístup:

  - **Top-down** (EDW model)
  - **Bottom-up** (menší projekty)
  - **Hybridní**

- Klíčové principy:

  - Zaměření na cíle
  - Definice rozsahu
  - Integrace dat
  - Metadata
  - Data governance a kvalita

---

## 🔐 Governance

- **Přijetí byznysem**: podpora modelu objevování a zpětné vazby
- **Metadata**: end-to-end, lineage, kvalita
- **Strategie reportingu**
- **SLA** (service level agreement) - smlouva o kvalitě, aktuálnosti, dostupnosti dat

---

## 📊 Technologie

### OLAP Varianty

- **ROLAP**: relační
- **MOLAP**: multidimenzionální DB
- **HOLAP**: hybridní
- **DOLAP**: klientská verze

### OLAP vs OLTP

| OLTP                   | OLAP                               |
| ---------------------- | ---------------------------------- |
| Transakční systém      | Analytické dotazy                  |
| Normalizace (3NF)      | Denormalizované/multidimenzionální |
| Optimalizace pro zápis | Optimalizace pro čtení             |

---

## 🧠 Rozšíření

- **Online Analytical Mining**
- **Enterprise Information Integration**

# Data Modelling

## 🧱 Přístupy k modelování

- **Normalizovaný model (Bill Inmon)**

  - Důraz na centralizovaný, konsolidovaný datový model

- **Denormalizovaný model (Ralph Kimball)**

  - Důraz na snadný přístup pro analytiku pomocí dimenzionálního modelu

---

## 📐 (Ralph) Kimball Dimensional Modeling Techniques

### 📊 Fáze návrhu

1. **Analýza**

   - Sběr business požadavků, KPI, analytických potřeb
   - Identifikace rozhodovacích procesů
   - Porozumění datové realitě

2. **Návrh (Dimensional Design Process)**

   - Vyber business proces
   - Urči grain (zrno = úroveň detailu záznamu)
   - Identifikuj dimenze
   - Identifikuj fakta

---

## 🌟 Star Schema

- **Faktová tabulka**: výsledek jednoho business procesu
- **Dimenzní tabulky**: atributy dané události

### 🧩 Grain

- Definuje, co představuje jeden řádek ve faktové tabulce
- Všechny fakta i dimenze musí odpovídat danému grain
- **Atomic grain**: nejnižší možná úroveň detailu

---

## 🔄 Rozšiřitelnost dimenzionálního modelu

- Přidání nových faktů = nové sloupce
- Přidání dimenzí = nové tabulky
- Přidání atributů = nové sloupce v dimenzi
- Zjemnění grain = přidání podrobnějších dat
- Zachovat konzistenci s grain!

---

## 📦 Typy faktových tabulek

- **Transaction Fact Table**
- **Periodic Snapshot**
- **Accumulating Snapshot**

### Fakta

- **Addivní**: lze sčítat napříč dimenzemi
- **Ne-addivní**: lze sčítat jen některými dimenzemi
- **Odvozená fakta**
- Nepoužívej `NULL` ve faktových tabulkách

---

## 🧭 Práce s dimenzemi

- **Casual dimension**
- **Degenerate dimension**: dim klíč, který nemá svoji dim tabulku
- **Conformed (master) dimensions**: sdílené více tabulkami
- **Drill-across**: přechod mezi různými fakta tabulkami

---

## 🏛️ Hierarchie dimenzí

- **Fixed Depth**: např. produkt → značka → kategorie
- **Variable Depth**:

  - Mírně nepravidelné: převod na fixed pomocí max hloubky
  - Ragged: rekurzivní závislosti (např. organizační struktura)

---

## ❄️ Snowflake a Galaxy modely

- **Snowflake model**: další normalizace dimenzí
- **Galaxy model**: více faktových tabulek sdílející dimenze (faktové konstelace)

---

## 🐢 Slowly Changing Dimensions (SCD)

| Typ | Popis                                                  |
| --- | ------------------------------------------------------ |
| 0   | Původní hodnota se nemění (statická dimenze)           |
| 1   | Přepsání staré hodnoty novou (bez historie)            |
| 2   | Přidání nového řádku (sledování historie)              |
| 3   | Přidání nového atributu („alternativní realita“)       |
| 4   | Oddělení rychle se měnících atributů do „mini-dimenze“ |

---

## 🔤 Sémantické datové modely

- Vyvinuty koncem 70. let jako abstraktní reprezentace dat
- Snazší porozumění a zapamatování než tabulková forma (Leitheiser)

# Data (Pre)Processing

## 🔁 ETL – Extract, Transform, Load

```
Data Source → Staging Area → Data Warehouse
         Extract      Transform      Load
```

- Základní fáze:

  - **Extract**: získání dat (full refresh, log capture, CDC, stream, …)
  - **Transform**: čistění, normalizace, integrace, redukce
  - **Load**: zápis do cílového úložiště (full/incremental load, MV)

---

## 🧰 Data Transformation

- **Selection**: `SELECT * FROM customer WHERE city = 'Prague';`
- **Projection**: `SELECT city, state FROM customer;`
- **Summarization**: sumy, průměry, mediány (distributive, algebraic, holistic)
- **Reduction**:

  - Agregace v kostkách
  - Výběr atributů
  - Dimenzionální redukce
  - Numerosity reduction (clustering, sampling, histogramy…)

---

## 🧹 Data Cleaning

- **Kvalita dat = schopnost splnit účel použití**
- Techniky:

  - Odstranění šumu, inkonzistencí
  - Deduplikace
  - Korekce typografických chyb
  - Imputace chybějících hodnot (KNN, průměr, rozhodovací strom…)
  - Normalizace, převody typů

- Checklist:

  - Odstranit irelevantní/duplicitní data
  - Standardizace formátů
  - Převody časových zón, typů, symbolů
  - Kontrola rozptylu (např. pomocí boxplotu)

---

## 🧱 Discretization & Hierarchies

- **Diskretizace**: binning, entropie, clustering, ruční dělení
- **Hierarchie konceptů**: ručně definované nebo automaticky vygenerované

  - Např. věk → infant, child, adolescent, adult

---

## 🔗 Data Integration

- Entity matching, schema matching
- Named-entity recognition
- **Lowering/Lifting**: převody mezi technickým a sémantickým pohledem

---

## 🔄 Data Shuffling & Performance

- JOIN, GROUP BY, ORDER BY mohou být náročné (zejména v cloudu)
- Optimalizace pomocí vhodné přípravy dat

---

## 🔍 Change Data Capture (CDC)

- Přenos pouze změněných dat (insert, update, delete)
- Možnosti:

  - log scraping
  - message queue monitoring
  - audit sloupce
  - time/index-based sledování

---

## 🧠 Metadata, Monitoring, Governance

- Kvalitní ETL obsahuje:

  - Monitoring, failovery, vizualizaci toků
  - Metadata (provenance, lineage, audit dimenze)
  - SLA a měření kvality

---

## 📋 ETL komponenty podle DW Toolkit

- **Extraction** (1–3): profilace, CDC
- **Cleaning & Conforming** (4–8): deduplikace, validace
- **Delivery** (9–21): SCD manager, surrogate key generator, …
- **Management** (22–34): job scheduler, backup, lineage analyzer

---

## 🔄 ETL varianty

| Přístup       | Popis                                        |
| ------------- | -------------------------------------------- |
| ETL (classic) | Pull na vyžádání / plánovaně                 |
| ELT           | Load před transformací (data lake přístup)   |
| ESB           | Push model, event-driven                     |
| No-ETL        | Web-first přístup bez potřeby klasického ETL |

---

## 🧾 Data Provenance & Lineage

- **Cíl**: dohledatelnost původu a způsobu vzniku dat
- Typy:

  - **Where lineage**: z jakých vstupů výstup vznikl
  - **How lineage**: jak byly vstupy zpracovány

- **PROV model (W3C)**:

  - `prov:Entity`, `prov:Activity`, `prov:Agent`, …
  - Relace: `wasGeneratedBy`, `wasAttributedTo`, `used`, …

---

## 📊 Data Quality

- **GIGO – Garbage In, Garbage Out**
- 47 % nových záznamů má kritickou chybu
- Jen 3 % dat splňují i nejměkčí standard kvality

### Důsledky

- Ztráta důvěry, nespokojenost, zhoršené rozhodování
- Nižší použitelnost pro ML

### Dimenze kvality

| Dimenze           | Definice / metriky                                          |
| ----------------- | ----------------------------------------------------------- |
| Accuracy          | Shoda s realitou, důvěryhodnost                             |
| Completeness      | Pokrytí všech potřebných entit a vlastností                 |
| Consistency       | Neexistence rozporů mezi systémy                            |
| Availability      | Dostupnost dat pro použití                                  |
| Licensing         | Práva k užití dat                                           |
| Timeliness        | Aktuálnost dat vzhledem k účelu                             |
| Understandability | Srozumitelnost pro lidského uživatele                       |
| Interoperability  | Propojitelnost dat napříč zdroji (např. přes stejné entity) |

---

## 🧼 Data Cleaning jako analýza

- Je to **kreativní a kritický proces**, ne jen „grunt work“
- Cílem je:

  - Opravit chyby, které kazí algoritmy
  - Snížit šum a zkreslení
  - Zajistit dokumentaci a opakovatelnost

# Data Catalogs & Metadata

## ❓ Proč metadata a datové katalogy?

Otázky, které si klademe:

- Jaká data máme?
- Kde je máme?
- O čem jsou?
- Kdo za ně odpovídá?
- Jak vznikla a jak se vyvíjela?
- Jak spolu souvisejí?
- Jak s nimi pracovat?

➡ Odpověď: **datový katalog**

---

## 🧩 Definice

- **Dataset**: kolekce dat publikovaná nebo spravovaná jedním zdrojem.
- **Data source**: systém, který uchovává data a poskytuje přístup spotřebitelům dat.
- **Data catalog**: nástroj pro správu metadat – inventarizuje a popisuje datové sady, poskytuje kontext.
- **Metadata**: „data o datech“ – pomáhají porozumět, najít a použít datová aktiva.

---

## 📚 Typy metadat

| Typ              | Popis                                        |
| ---------------- | -------------------------------------------- |
| **Technická**    | Kde a v jakém formátu lze dataset získat     |
| **Strukturální** | Struktura dat (např. JSON Schema, XSD, CSVW) |
| **Provenance**   | Původ dat: autor, verze, historie            |
| **Doménová**     | Význam dat: popis, klíčová slova, téma       |
| **Businessová**  | Podmínky užití, varování, přístupová práva   |

---

## 🧠 DCAT – Data Catalog Vocabulary

- **Cíl**: interoperabilita metadat o datasetech
- **Základní pojmy**:

  - `dcat:Resource`
  - `dcat:Dataset`
  - `dcat:Distribution`
  - `dcat:DataService`
  - `dcat:Catalog`

### ✨ Co je slovník?

- Formální model sémantických konceptů a jejich vztahů
- Každý koncept má identifikátor (strojový) + popis (lidský)

---

## 📜 DCAT příklad (RDF)

```turtle
<https://data.gotham-city.fiction/resource/dataset/bus-stops>
  a dcat:Dataset ;
  dct:title "Bus stops positions"@en ;
  dct:description "Positioning data about all bus stops in the Gotham city."@en ;
  dcat:keyword "bus stop"@en, "position"@en, "public transport"@en ;
  dcat:theme <http://eurovoc.europa.eu/4197>, <http://eurovoc.europa.eu/4531> ;
  dct:spatial <https://www.wikidata.org/wiki/Q732858> ;
  dct:temporal [
    a dct:PeriodOfTime ;
    dcat:startDate "2021-01-01"^^xsd:date
  ] ;
  dcat:distribution <.../distribution/geojson> .
```

---

## 🔗 Metadata vrstvy v DCAT

- **Technická metadata**

  - `accessURL`, `mediaType`, `format`

- **Strukturální metadata**

  - `dct:conformsTo` (odkaz na schéma)

- **Provenance metadata**

  - `creator`, `issued`, `modified`, `publisher`, `contactPoint`

- **Business metadata**

  - `accrualPeriodicity`, licencování, přístupová práva

---

## 🧠 DCAT význam (ilustrováno pohádkou)

Bez metadat dataset zůstává skrytý. Frodo vytvořil DCAT popis pro dataset _Fangorn’s Creatures_ → nyní je dostupný, opětovně využitelný a přináší hodnotu.

---

## 🛠 DCAT-AP a aplikace

- **DCAT-AP**: aplikovaný profil DCAT pro EU (používá se v NSWI144)
- **Použití**:

  - Národní katalog otevřených dat (Česko)
  - European Data Portal
  - [schema.org/Dataset](https://schema.org/Dataset)

---

## 🧭 Nástroje pro data katalogizaci

Příklady platforem:

- Atlan, Ataccama, Talend, data.world
- Google Cloud Data Catalog
- Airbnb (Dataportal), Uber (Databook), LinkedIn (Datahub)
- Spotify (Lexikon), Lyft (Amundsen)

---

# Controlled Vocabularies

## 🧠 Co jsou doménová metadata?

Popisují **význam** dat pomocí:

- `title` a `description`: volný text
- `keywords`: volná slova/fráze
- `themes`: koncepty z řízených slovníků
- `temporal` a `spatial` resolution

---

## ❌ Problémy s klíčovými slovy (keywords)

- Nejednotnost v úrovni detailu
- Synonyma, překlady, různé interpretace
- Nedostatečně strojově čitelná sémantika
- „Izolované ostrovy“ bez vazeb

### Příklad:

```
bus stop, public transport, Gotham, buses, JSON, map, vehicle traffic system
```

---

## ⚖️ Rovnováha: Svoboda vs. Vyjadřovací síla

| Přístup  | Svoboda výběru | Strojová sémantika |
| -------- | -------------- | ------------------ |
| Keywords | vysoká         | nízká              |
| Themes   | nižší          | vysoká             |

---

## 📚 Co je řízený slovník (controlled vocabulary)?

- **Standardizovaný seznam pojmů s definovaným významem**
- Pojmy mají vztahy: hierarchické i asociativní
- **Zvyšuje interoperabilitu a opakovatelnost dat**

---

## 🧱 Typy řízených slovníků

| Typ                       | Popis                                                         |
| ------------------------- | ------------------------------------------------------------- |
| **Controlled list**       | Ploché seznamy bez vztahů (např. jazykové ekvivalenty)        |
| **Taxonomy**              | Hierarchie (např. širší/užší pojmy)                           |
| **Thesaurus**             | Hierarchie + synonyma + asociativní vztahy                    |
| **Classification scheme** | Rozdělení objektů do skupin na základě vlastností             |
| **Ontology**              | Komplexní model pojmů a jejich relací (nebyl zde rozpracován) |

---

## 📌 Vztahy mezi koncepty

- **Hierarchické**:

  - broader / narrower
  - parent / child
  - whole / part
  - specializes / generalizes

- **Asociativní**:

  - related, has part, precedes, succeeds, causes, instantiates

---

## 🧾 Příklady

### Controlled List

- EU Subject Matter Authority Table – pro indexaci oznámení na EUR-Lex

### Taxonomy

- **NCBI Taxonomy** – klasifikace organismů
- **Common Procurement Vocabulary (CPV)** – pro veřejné zakázky

### Thesaurus

- **EuroVoc** – vícejazyčný tezaurus pro dokumenty EU

  - např. _machine learning_, _cybernetics_, _economic intelligence_

### Classification Scheme

- **NACE** – statistická klasifikace ekonomických činností v EU

---

# Representation and Usage of Controlled Vocabularies

## 🧩 SKOS – Simple Knowledge Organization System

- W3C standard (2009) pro reprezentaci řízených slovníků
- Model postavený na RDF (Resource Description Framework)
- Používá se pro **sdílení a publikaci** slovníků na webu

---

## 📚 Základní SKOS pojmy

| SKOS prvek         | Popis                                                            |
| ------------------ | ---------------------------------------------------------------- |
| `skos:Concept`     | Jednotka významu – abstraktní entita (myšlenka, pojem, událost…) |
| `skos:prefLabel`   | Preferovaný název konceptu (pro lidi)                            |
| `skos:altLabel`    | Alternativní název (synonyma, překlady)                          |
| `skos:hiddenLabel` | Název skrytý pro uživatele, ale použitelný při vyhledávání       |
| `skos:notation`    | Jedinečný identifikátor (např. kód)                              |

---

## 📖 Poznámky u konceptů

| SKOS prvek         | Význam                      |
| ------------------ | --------------------------- |
| `skos:note`        | Obecná poznámka             |
| `skos:scopeNote`   | Jak se má koncept použít    |
| `skos:definition`  | Úplná definice konceptu     |
| `skos:example`     | Příklad použití             |
| `skos:historyNote` | Historie nebo změny významu |

---

## 🧭 Vztahy mezi koncepty (semantic relations)

| Vztah           | Význam                     |
| --------------- | -------------------------- |
| `skos:broader`  | obecnější pojem (nahoru)   |
| `skos:narrower` | specifičtější pojem (dolů) |
| `skos:related`  | asociativní vztah          |

> Všechny vztahy mohou být kombinované a vícejazyčné.

---

## 🗂️ SKOS Concept Scheme

- Slovník jako celek je reprezentován jako `skos:ConceptScheme`
- Obsahuje:

  - `skos:inScheme` – určuje, kam koncept patří
  - `skos:hasTopConcept` – určuje hlavní (kořenové) pojmy

---

## 🌍 Otevřený svět v SKOS

- **Open World Assumption**:

  - Pokud něco není uvedeno, neznamená to, že to neexistuje
  - Slovníky lze rozšiřovat, kombinovat, provazovat

---

## 🔀 Propojování různých schémat

SKOS podporuje:

- `skos:closeMatch`, `skos:exactMatch`
- `skos:broadMatch`, `skos:narrowMatch`
- `skos:relatedMatch`

➡ Používá se k mapování mezi různými slovníky

---

## 🧠 Příklad RDF reprezentace

```turtle
@prefix skos: <http://www.w3.org/2004/02/skos/core#> .

<http://eurovoc.europa.eu/3030> a skos:Concept ;
    skos:prefLabel "artificial intelligence"@en ;
    skos:prefLabel "umělá inteligence"@cs ;
    skos:altLabel "expert system"@en ;
    skos:notation "3030" ;
    skos:broader <http://eurovoc.europa.eu/4486> ;
    skos:inScheme <http://eurovoc.europa.eu/100141> .
```

---

## 🧩 Použití řízených slovníků k popisu dat

- Dataset může být propojen s pojmy pomocí `dcat:theme`
- Téma datasetu se odkazuje na `skos:Concept` ve slovníku

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX skos: <http://www.w3.org/2004/02/skos/core#>

SELECT ?label WHERE {
  <.../dataset/bus-lines> dcat:theme ?concept .
  ?concept skos:prefLabel ?label .
  FILTER (LANG(?label) = "en")
}
```

---

## 🧪 Dotazování na popisy dat (SPARQL)

- Dotaz na `dcat:theme` a jeho `skos:prefLabel`
- Dotaz na hierarchicky širší témata (`skos:broader*`)
- Dotaz na synonymní výrazy (`skos:altLabel`)
- Dotaz na datasety dle zvoleného tématu

---

# Ontologies

## 🧠 Co je ontologie?

- **Sdílená konceptualizace reality** (abstraktní i konkrétní pojmy a jejich vztahy)
- Používá se pro formální reprezentaci znalostí pomocí propojených konceptů
- Nahrazuje izolovanou strukturu dat sémanticky bohatým propojeným modelem

---

## 🧩 Vztah k řízeným slovníkům

| Typ                   | Popis                                                         |
| --------------------- | ------------------------------------------------------------- |
| Controlled List       | Seznam termínů bez vztahů                                     |
| Taxonomy              | Hierarchické vztahy (např. broader/narrower)                  |
| Thesaurus             | Hierarchie + synonyma + asociace                              |
| Classification Scheme | Hierarchické třídění na základě vlastností                    |
| **Ontology**          | Sdílená formální reprezentace entit, typů, vztahů, vlastností |

---

## 🧱 Ontologické pojmy

| Typ                  | Popis                                                                                       |
| -------------------- | ------------------------------------------------------------------------------------------- |
| **Concept**          | Abstraktní nebo konkrétní pojem, který může být instanciován                                |
| **Universal**        | Typ věcí sdílejících vlastnosti – např. „Auto“, „Řidič“                                     |
| **Individual**       | Konkrétní instance – např. `:John`, `:JohnsCar`                                             |
| **Endurant**         | Entita, která **přetrvává v čase** – je plně přítomná v každém okamžiku své existence       |
| **Substantial**      | **Existenciálně nezávislý endurant** – existuje samostatně (např. auto, člověk)             |
| **Moment**           | **Existenciálně závislý endurant** – neexistuje bez hostitelské entity                      |
| **Intrinsic Moment** | Závislý na **jedné** entitě – např. barva auta, věk osoby                                   |
| **Relator**          | Závislý na **dvou nebo více** endurantech – např. vlastnictví, manželství                   |
| **Perdurant**        | **Události** nebo procesy, které se dějí v čase – mají temporalitu (např. jízda, přednáška) |

---

## 🧬 Ontologická struktura (gUFO / OWL)

Příkladové třídy:

```ttl
:Person a owl:Class ; rdfs:subClassOf gufo:Object .
:Driver a owl:Class ; rdfs:subClassOf :Person .
:Car a owl:Class ; rdfs:subClassOf gufo:Object .
:owns a owl:Class ; rdfs:subClassOf gufo:Relator .
```

Příkladové vlastnosti:

```ttl
:ownedCar a owl:ObjectProperty ; rdfs:subPropertyOf gufo:mediates ; rdfs:domain :owns ; rdfs:range :Car .
:owner a owl:ObjectProperty ; rdfs:subPropertyOf gufo:mediates ; rdfs:domain :owns ; rdfs:range :Driver .
```

---

## 🧪 Ontologické vztahy

| Vztah            | Popis                                              |
| ---------------- | -------------------------------------------------- |
| `inheresIn`      | Moment patří entitě (např. jméno patří osobě)      |
| `mediates`       | Relátor propojuje více entit (např. driver a auto) |
| `hasParticipant` | Událost závislá na účastnících                     |
| `instanceOf`     | Jedinec je instancí univerzálu                     |

---

## 🚗 Příklad modelu: měření rychlosti

- **Individuální instance**:

  - `:John`, `:JohnsCar`, `:SpeedMeasurement1`

- **Třídy**:

  - `:Driver`, `:Car`, `:Speeding`, `:Policemen`

- **Vztahy**:

  - `:owns` – relátor mezi `:Driver` a `:Car`
  - `:droveDuringSpeedMeasurement`, `:measuredDuringSpeedMeasurement`

---

## 📦 Ontologie v praxi

| Oblast            | Ontologie                  |
| ----------------- | -------------------------- |
| Kulturní dědictví | CIDOC CRM                  |
| Podniky           | gist, schema.org, IOF      |
| Vláda             | ISA program, EDM           |
| Vědy o životě     | OBO Foundry, ChEMBL, SWEET |
| Komunita          | Wikidata                   |

---

## 🔗 Ontologie v OWL + gUFO

gUFO – „lightweight“ implementace _Unified Foundational Ontology_ v OWL

- Definuje základní typy jako `gufo:Endurant`, `gufo:Relator`, `gufo:Event`
- Používá se jako nadstavba pro specializované modely

---

# Beyond Data Warehouse

## 🏗️ Architektura Data Warehouse

```
Data Source → Staging Area → Data Warehouse
             Extract    Load    Transform
```

---

## ❌ Když DWH selže

- **Data Dump**: nestrukturovaný a nepoužitelný skládka dat
- **Příčina**: nedostatečné plánování, správa kvality, přetížení systémem

---

## 🌊 Data Lake

- **Charakteristika**:

  - Velké objemy dat
  - Nízké náklady na zřízení
  - „Schema on read“ – schéma určováno až při čtení
  - Podpora strukturovaných i nestrukturovaných dat
  - Vhodné pro ML, explorativní analýzy, neznámé use-cases

- **Výhody**:

  - Rychlá změna
  - Snadný přístup k datům
  - Podpora ELT

- **Rizika**:

  - Chybějící vize
  - Slabé řízení dat (data governance)
  - Vznik **Data Swamp** – chaos místo přínosu

---

## 🧪 Data Lakehouse

- Hybrid mezi DWH a Data Lake
- Cíl: využít výhod obou přístupů (real-time + škálovatelnost + flexibilita)

---

## 🤝 Data Contracts

- **API-like dohody** mezi producenty a konzumenty dat
- Příklad šablony (PayPal):

  - Dataset a schéma
  - Kvalita dat
  - Cena
  - Role, SLA
  - Odpovědnosti a vlastnosti

---

## 🌐 Data Mesh

### 📌 Důvody pro vznik:

- Rozmanitost datových zdrojů a případů užití
- Nutnost rychlé reakce na změny

### 🎯 Principy:

- Doménově orientované vlastnictví dat (decentralizace)
- Data jako produkt
- Samoobslužná datová infrastruktura (platforma)
- Federativní governance

> „Další generace datových architektur spojuje doménově řízený design, samoobslužné platformy a produktové myšlení.“ – Zhamak Dehghani, 2019

---

## 📦 Data as a Product

- **Vlastnosti**:

  - Jasné vlastnictví
  - Objevitelná přes datový katalog
  - Důvěryhodná, přístupná
  - Definovaná sémantika i syntax
  - Interoperabilita a řízení přístupů
  - Jasné odpovědnosti týmů

---

## 🧵 Data Fabric _(volitelné téma)_

- Není rozvedeno, ale naznačeno jako další evoluce v řízení datových toků

---

# Data Management

## 📊 Co je Data Asset?

> „Asset je ekonomický zdroj, který může být vlastněn nebo řízen a který má nebo produkuje hodnotu.“ – _DAMA DMBOK_

### Hodnota dat:

- Rozdíl mezi náklady a přínosy:

  - Náklady: získání, uchování, obnova, zlepšení
  - Přínosy: prodej, inovativní využití, vyšší kvalita dat

---

## 🧠 Definice Data Managementu

> „Rozvoj, realizace a dohled nad plány, politikami, programy a praktikami, které spravují a zvyšují hodnotu dat v průběhu jejich životního cyklu.“ – _DAMA DMBOK_

---

## 👥 Role v Data Managementu

- **Technické**: DB administrátoři, síťoví administrátoři, vývojáři
- **Businessové**: datoví správci (Data Stewards), stratégové, CDOs, governance council

---

## 🧭 Strategie

> „Strategie je soubor rozhodnutí, který určuje směr pro dosažení vysoké úrovně cílů.“

---

## 🧱 Frameworky pro řízení dat

- Strategic Alignment Model (Henderson & Venkatraman)
- Amsterdam Information Model
- DAMA DMBOK (tzv. DAMA Wheel)
- Hexagon, Context Diagram
- Aikenova pyramida:

  1. Aplikace s DB
  2. Problémy s kvalitou
  3. Governance
  4. Profit

---

## 🔁 Data Life Cycle

Fáze správy dat (příklady):

- Sběr / vytvoření
- Uložení
- Údržba
- Syntéza
- Využití
- Publikace
- Archivace
- Smazání

> Např. IPCC: >80 PB, LSST: >500 PB, Černá díra: 4.5 PB

---

## 🏛️ Data Governance

> „Cvičení autority a kontroly (plánování, monitoring a prosazení) nad správou datových aktiv.“ – _DAMA DMBOK_

### Klíčové komponenty:

- Strategie, politiky, standardy
- Compliance, řešení problémů
- Projekty datového managementu
- Ocenění datových aktiv

### Oblasti rozhodování:

- Principy dat
- Kvalita dat
- Metadata
- Přístup k datům
- Životní cyklus dat

---

## 🏢 Organizace Data Governance

### Funkce:

| Funkce       | Popis                              |
| ------------ | ---------------------------------- |
| Legislativní | Politiky, standardy, architektura  |
| Soudní       | Řešení problémů a eskalace         |
| Výkonná      | Administrativa, ochrana, realizace |

### Účastníci:

- Řídící výbor (Steering Committee)
- Rada (Council)
- Týmy správců dat (Stewardship Teams)

  - **Data Stewardship** = zodpovědnost za kvalitu a kontrolu dat

### Modely řízení:

- Centralizovaný
- Federovaný

---

# Information Theory

## ❓ Hlavní otázky

- Jaká je minimální velikost reprezentace dat?
- Jak zabránit chybám při přenosu/ukládání dat?

---

## 📜 Historie

- **1840** Morse – elektromagnetická komunikace (patent)
- **1937** Shannon – magisterská práce (relační obvody)
- **1948** Shannon – _A Mathematical Theory of Communication_
- Zakladatel informační teorie

---

## 🔄 Model komunikace

```
Source → Transmitter → [Channel + Noise] → Receiver → Destination
```

---

## ℹ️ Shannon Entropy

- **Entropie H(X)** měří informaci/nejistotu
- Shannon's Source Coding Theorem:

  - _N_ výstupů lze komprimovat do \~N × H(X) bitů

---

## 📦 Kompresní metody

### Run-Length Encoding (RLE)

- Nahrazuje opakující se znaky: `AAAAA` → `5A`

### Huffmanovo kódování

- Frekvenční binární kódování, optimální pro známé pravděpodobnosti

### Lempel-Ziv 1977 (LZ77)

- Nahrazuje opakované výskyty zpětnými odkazy (pozice, délka)
- Nevyžaduje znalost statistik

### Lempel-Ziv-Welch (LZW)

- Slovníková metoda
- Rozšiřuje slovník během komprese

### Aritmetické kódování

- Kóduje celou zprávu jako reálné číslo v intervalu \[0,1)
- Efektivní pro zdroje s nerovnoměrnými pravděpodobnostmi

---

## 📡 Spolehlivý přenos dat

### Redundance

- Např. `0 → 000`, `1 → 111`
- Pravděpodobnost chyby u tříbitové replikace:

  ```
  P(error) = 3f² - 2f³
  ```

### Shannon’s Noisy-Channel Coding Theorem

- Je možné komunikovat s libovolně malou chybovostí, pokud je přenosová rychlost < kapacity kanálu

#### Kapacita Binary Symmetric Channel (BSC):

```math
C = 1 - H₂(f) = 1 - [f·log₂(1/f) + (1-f)·log₂(1/(1-f))]
```

---

## 🧮 Kódy pro detekci a opravu chyb

- **Hammingovy kódy**
- **Reed–Solomon**
- **Konvoluční kódování** (pro kontinuální přenos)

---

## ⚙️ Praktické nástroje

- **Apache Commons Compress** (Java)
- Podporované formáty: gzip, bzip2, lzma, zstd, xz, Brotli, Snappy, LZ4, tar, zip, 7z, …
- Vhodné pro:

  - Aktivní dokumenty
  - Archivaci
  - Konfigurace systémů

---

# Building Trust – Kryptografie a zabezpečení dat

## 🔐 Šifrování

### Caesar cipher

- Posun abecedy o pevný klíč (např. +5)
- Příklad: `theory` → `ymjwd`

---

### Symetrická vs. Asymetrická šifra

| Vlastnost | Symetrická       | Asymetrická            |
| --------- | ---------------- | ---------------------- |
| Klíč      | Jeden sdílený    | Privátní / veřejný pár |
| Rychlost  | Rychlé           | Pomalejší              |
| Příklad   | AES              | RSA, Diffie-Hellman    |
| Použití   | Šifrování obsahu | Výměna klíče           |

---

## 🧱 AES (Advanced Encryption Standard)

- **Symetrická šifra**
- Bloky 128 bitů, klíč: 128/192/256 bitů
- Kroky:

  - Padding
  - XOR s klíčem
  - SubBytes → ShiftRows → MixColumns → XOR (opakováno dle délky klíče)

---

## 🔑 Diffie-Hellman

- **Výpočet sdíleného tajného klíče bez jeho odeslání**
- Využívá operace s modulo exponenty
- Varianta: **Elliptic Curve Diffie-Hellman**

---

## 🧮 RSA (Rivest–Shamir–Adleman)

- Asymetrické šifrování
- Výběr prvočísel (p, q), výpočet:

  - `N = p * q`
  - φ(N), výběr `e` a výpočet `d` tak, aby `d * e mod φ(N) = 1`

- Šifrování a dešifrování pomocí modulární aritmetiky

---

## 🔍 Hashování

- Jednosměrná transformace (např. SHA256)
- Hash má fixní délku, i malé změny vstupu způsobí dramatické změny výstupu
- **Nelze zpětně dekódovat** ani snadno najít kolize

---

## ✍️ Digitální podpis

- Ověřuje:

  - **Integritu** dokumentu (hash)
  - **Autenticitu** (zdroj odpovídá držiteli privátního klíče)

- Založen na **asymetrickém šifrování**
- Vyžaduje právní podporu (např. EU směrnice 130/2011/ES)

---

## 🪪 JSON Web Tokens (JWT)

- **Průmyslový standard (RFC 7519)** pro přenos dat s integritou
- Struktura:

  ```json
  {header}.{payload}.{signature}
  ```

- Možno šifrovat nebo podepisovat

---

## 📄 Digitální certifikát (X.509)

- **Potvrzení identity** – veřejné potvrzení vlastnictví privátního klíče
- Obsahuje: veřejný klíč, jméno, platnost…
- **Slouží pro důvěru** mezi entitami (HTTPS apod.)

---

## 🏛 Public Key Infrastructure (PKI)

- **Infrastruktura důvěry**
- Obsahuje:

  - Certifikační autority (CA)
  - Seznamy odvolaných certifikátů (CRL)
  - Klíčové úschovy (key escrow)

---

## 🌐 HTTPS, SSL, TLS

- **HTTPS = HTTP + TLS**
- SSL je starší a nahrazeno TLS

---

## 🧰 Krypto API a nástroje

- **JavaScript (browser)**: `window.crypto`

  - Algoritmy: RSA-OAEP, AES-CTR, AES-GCM…

- **Python**: `cryptography` knihovna
- **OpenSSL příkazy**:

  - `openssl dgst -sha1 file`
  - `openssl req -newkey rsa:2048 …`
  - `openssl dgst -sha256 -sign …`

---

# Text Search

## 🔍 Základní přístupy

- **Textové vyhledávání**:

  - Hledání řetězce v dokumentu (např. "est")
  - Hledání celých slov (např. "restrictions")
  - Hledání frází (např. "we expect")

- **Relevance**:

  - Míra, jak moc odpověď odpovídá informační potřebě
  - Kontext je klíčový – relevance je subjektivní

---

## 📈 Metody hodnocení výsledků

- **Precision** = TP / (TP + FP) → kolik z nalezených je relevantních
- **Recall** = TP / (TP + FN) → kolik z relevantních bylo nalezeno
- Používá se **confusion matrix**

---

## 📚 Full-text search

- Algoritmické porovnávání dotazu s obsahem dokumentů
- Synonyma: **keyword search**, **probabilistic search**, **algorithmic search**

---

## 🗂 Indexace

### Term-Document Incidence Matrix

- Matice: dokumenty vs. výskyty termínů (0/1)

### Inverted Index

- Pro každý termín uchovává seznam dokumentů (posting list)

---

## 🔄 Pipeline vyhledávání

1. Sběr dokumentů
2. Tokenizace (slova, zkratky, formáty)
3. Předzpracování:

   - Lemmatizace, stemming
   - Odstranění stop-slov
   - Normalizace diakritiky

4. Vytvoření indexu

---

## 🔧 Apache Solr

- Full-text engine založený na Lucene

- **Konfigurace polí (schema.xml)**:

  ```xml
  <field name="price" type="float" indexed="true" stored="true"/>
  ```

- **Analyzéry**:

  - Tokenizéry: `standard`, `keyword`, `nGram`
  - Filtry: `lowercase`, `stem`, `ASCIIFolding`, `englishMinimalStem`

---

## ⚠️ Slabiny full-text vyhledávání

- **Synonym Problem** – "car" vs. "automobile"
- **Homonym Problem** – "bass" (ryba vs. hudba)
- **Aboutness Problem** – dokument je o tématu, ale klíčové slovo neobsahuje
- **Jazyková variabilita** – různé výrazy, jazyky

---

## 🔎 Metadata-enabled Search

- **Deterministické hledání** ve strukturovaných polích (např. autor, název, téma)
- Funguje dobře pro:

  - Knihovní katalogy
  - Filtrování podle předem známých atributů

---

## 🤖 Semantic Full-Text Search

- Využívá **doménové znalosti** a entity
- Cíl: porozumět významu dotazu a dokumentu
- Výzvy:

  - Rozpoznání entit
  - Spojení indexu a sémantiky
  - Kontext a uživatelské rozhraní

### Příklad (SQL Server):

- Full-text: hledáš _slova_
- Semantic: hledáš _význam_

---

## 📐 Dokumenty jako vektory

- Model vektorového prostoru:

  - Dokument = vektor
  - Dotaz = krátký dokument
  - Vyhledávání = měření podobnosti vektorů

- Nástroje:

  - **TF-IDF**
  - **Word2Vec**
  - **BERT** (v moderních systémech)
