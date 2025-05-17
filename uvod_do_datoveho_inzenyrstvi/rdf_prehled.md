## 🌐 RDF, sémantika a ontologie – přehled tříd, vlastností a prefixů

### 📦 Základní technologie

```markdown
- **RDF (Resource Description Framework)** – základní model pro vyjadřování dat ve formě trojic (subjekt–predikát–objekt)
- **RDFS (RDF Schema)** – nástroj pro definici tříd a hierarchií v RDF grafech
- **OWL (Web Ontology Language)** – rozšíření RDF pro logické modelování ontologií (třídy, vztahy, kardinální omezení, inference)
```

---

### 🧭 Prefixy a slovníky – přehled

| Prefix  | Název                         | Původ / Použití                                         |
| ------- | ----------------------------- | ------------------------------------------------------- |
| `rdf:`  | RDF Core                      | Základní datové typy a struktura RDF (`rdf:type`)       |
| `rdfs:` | RDF Schema                    | Třídy, nadřazenost, domény (`rdfs:Class`, `subClassOf`) |
| `owl:`  | Web Ontology Language         | Pokročilé vztahy, logika, ontologické vlastnosti        |
| `skos:` | Simple Knowledge Organization | Modelování thesaurů, taxonomií                          |
| `dcat:` | Data Catalog Vocabulary       | Popis datových katalogů a datasetů (DCAT)               |
| `dct:`  | Dublin Core Terms             | Obecná metadata (`title`, `creator`, `issued`)          |
| `prov:` | PROV-O                        | Modelování provenience (původu) dat                     |
| `gufo:` | gUFO (lightweight UFO)        | Ontologický model tříd, rolí a relací                   |
| `xsd:`  | XML Schema Datatypes          | Datové typy (`xsd:string`, `xsd:dateTime`)              |

---

### 🧩 DCAT – popis datových katalogů

```markdown
- `dcat:Catalog` – samotný katalog
- `dcat:Dataset` – datová sada (např. „Doprava v ČR 2022“)
- `dcat:Distribution` – konkrétní forma dat (CSV, API)
- `dcat:DataService` – API nad daty
- `dcat:theme` – odkaz na řízený slovník (např. EuroVoc)
```

---

### 🧠 SKOS – modelování pojmů a slovníků

```markdown
- `skos:Concept` – reprezentuje jeden pojem (např. „Doprava“)
- `skos:prefLabel` – hlavní název (např. „umělá inteligence“)
- `skos:altLabel` – synonymum
- `skos:notation` – kód pojmu (např. „3030“)
- `skos:broader` / `narrower` – hierarchické vztahy
- `skos:related` – asociativní vztahy
- `skos:inScheme` – odkaz na `skos:ConceptScheme`
- `skos:exactMatch`, `closeMatch` – pro mapování mezi slovníky
```

---

### 🔗 PROV-O – modelování provenience dat

```markdown
- `prov:Entity` – výsledek zpracování (např. datový soubor)
- `prov:Activity` – akce, která vytvořila entitu (např. skript)
- `prov:Agent` – aktér (člověk, software), který provedl aktivitu
- `prov:wasGeneratedBy` – entita vznikla aktivitou
- `prov:wasAttributedTo` – entita patří agentovi
- `prov:used` – aktivita použila jinou entitu
```

➡️ Umožňuje **rekonstruovat historii vzniku dat**.

---

### 🧬 gUFO – ontologický základ (UFO Light)

```markdown
- `gufo:Object` – stabilní objekt (např. auto, osoba)
- `gufo:Event` – událost v čase (např. přechod, měření)
- `gufo:Situation` – kombinace stavů v čase
- `gufo:Relator` – vazba mezi entitami (např. „vlastní“)
- `gufo:inheresIn` – vlastnost náleží entitě
- `gufo:mediates` – relátor propojuje entity
```

➡️ Skvělé pro modelování světa pomocí tříd, událostí, vztahů.

---

### 🧠 OWL – logické vztahy v ontologiích

```markdown
- `owl:Class` – definice třídy
- `owl:ObjectProperty` – vlastnost mezi objekty
- `owl:DatatypeProperty` – vlastnost s datovým typem
- `owl:sameAs` – identita napříč grafy
- `owl:FunctionalProperty`, `owl:InverseFunctionalProperty` – kardinalita
- `owl:Restriction` – pravidla pro vlastnosti (např. „max 1 majitel“)
```

➡️ Umožňuje **inferenci, dedukci a kontrolu konzistence**.

---

### 📃 Dublin Core – základní popisné metadata

```markdown
- `dct:title` – název
- `dct:description` – popis
- `dct:creator` – autor
- `dct:issued` – datum vydání
- `dct:modified` – poslední úprava
- `dct:license` – odkaz na licenci
```

Používá se napříč DCAT, SKOS, běžně i mimo RDF (např. v JSON-LD).
