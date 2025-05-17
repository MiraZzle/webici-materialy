# 🧾 Přehled dělení a typologií (Úvod do datového inženýrství)

---

## 📚 Metadata – typy

```markdown
- **Technická** – formát, endpoint, velikost
- **Strukturální** – schéma, datové typy
- **Provenance** – původ, autor, verze, úpravy
- **Doménová** – význam polí, obchodní kontext
- **Businessová / právní** – licence, oprávnění
```

---

## 📦 Typy dimenzí

```markdown
- **Conformed** – sdílené více fakty (např. zákazník)
- **Degenerate** – bez atributů, např. číslo faktury
- **Role-playing** – stejná tabulka, více významů (např. datum objednávky vs. odeslání)
```

---

## 🧱 Typy faktových tabulek

```markdown
- **Transaction** – 1 řádek = 1 událost (objednávka)
- **Periodic snapshot** – stav v čase (denní tržby)
- **Accumulating snapshot** – proces přes fáze (např. nákupní funnel)
```

---

## 🌀 Typy SCD (Slowly Changing Dimensions)

```markdown
- **Type 0** – bez změn (immutable)
- **Type 1** – přepsání hodnoty
- **Type 2** – nový řádek pro každou verzi
- **Type 3** – nový sloupec pro historickou hodnotu
- **Type 4** – oddělená tabulka (mini-dimenze)
```

---

## 📚 Typy řízených slovníků

```markdown
- **Controlled list** – plochý seznam hodnot (např. kódy jazyků)
- **Taxonomy** – stromová hierarchie (např. „Zvíře > Savec > Pes“)
- **Thesaurus** – hierarchie + synonyma + vztahy (např. EuroVoc)
- **Classification scheme** – vícerozměrná kategorizace (např. NACE)
- **Ontology** – plně formální model pojmů a vztahů (např. schema.org, Wikidata)
```

---

## 📖 SKOS – typy vztahů

```markdown
- **skos:broader** – nadřazený pojem
- **skos:narrower** – podřazený pojem
- **skos:related** – asociovaný pojem
- **skos:altLabel** – synonymum
- **skos:prefLabel** – preferovaný název
- **skos:exactMatch** – ekvivalent v jiném slovníku
- **skos:closeMatch**, **broadMatch**, **narrowMatch**
```

---

## 🏗️ Typy datových architektur

```markdown
- **Data Warehouse** – centralizovaný, strukturovaný
- **Data Lake** – surová data, schema-on-read
- **Lakehouse** – kombinace struktury a flexibility
- **Data Mesh** – decentralizovaná doménová odpovědnost
- **Data Fabric** – inteligentní propojování dat napříč systémy
```

---

## 🤝 Data Mesh – 4 principy

```markdown
1. **Doménové vlastnictví**
2. **Data jako produkt**
3. **Samoobslužná platforma**
4. **Federovaná governance**
```

---

## 📃 Data Contract – hlavní části

```markdown
- Název a popis datasetu
- Definice schématu (JSON Schema, Avro…)
- Povolené hodnoty (validace)
- SLA (frekvence, dostupnost)
- Vlastník
- Odpovědnost při porušení
```

---

## 📉 Typy komprese podle entropie

```markdown
- **Bezztrátová** – ZIP, PNG (blíží se Shannonově entropii)
- **Ztrátová** – MP3, JPEG (odstraňuje nadbytečné informace)
```

---

## 🔐 Typy kryptografie

```markdown
- **Symetrická** – 1 klíč (AES, DES)
- **Asymetrická** – veřejný/soukromý klíč (RSA, ECC)
- **Hybridní** – kombinace (např. TLS)
```

---

## 🔏 Kryptografie – funkce

```markdown
- **Šifrování** – důvěrnost
- **Digitální podpis** – autenticita + integrita
- **Hashování** – detekce změn
- **Certifikáty / PKI** – důvěra mezi stranami
```

---

## 🔍 Typy vyhledávání

```markdown
- **Boolean** – přesné shody (AND, OR, NOT)
- **Fulltext** – relevance pomocí TF-IDF, BM25
- **Vektorové** – sémantické vyhledávání přes embeddings
- **Filtrování** – striktní podmínky (rok ≥ 2020, jazyk = "cs")
```

---

## 🧪 Vyhledávání – hlavní skórovací metody

```markdown
- **TF-IDF**
- **BM25**
- **Kosinová podobnost**
- **Embeddings + ANN (např. FAISS, Qdrant)**
```

---

## 🧠 Kvalita dat – rozlišované dimenze

```markdown
- **Accuracy**
- **Completeness**
- **Consistency**
- **Timeliness**
- **Validity**
- **Uniqueness**
- **Understandability**
- **Interoperability**
```
