## Otázky podle kategorií

### **RDF**
1. RDF:List  
   -> funguje jako spojový list 
   --- koukám do first (value), když chci druhou hodnotou, jdu do rest a zase first
   --- končí null (nil)

2. Co je to reifikace?  
   -- proces přidávání metadat RDF statementům 
   ```
   my:index.html my:createdBy "Jakub Klímek" .
   ```
    ```
   _:triple1 a rdf:Statement .
   _:triple1 rdf:subject	my:index.html	.
   _:triple1 rdf:predicate	my:createdBy	.
   _:triple1 rdf:object	"Jakub Klímek"	.
   ```

3. Popište RDF\*. Uveďte příklad.  
   --- řeší přidávání metadat k RDF statementům 
   ```
   my:index.html my:createdBy "Jakub Klímek" .
   ```
    ```
    << my:index.html my:createdBy "Jakub Klímek" >> 
     dcterms:source "https://x.y.z"^^xsd:anyURI ;
     dcterms:created "2020-04-23"^^xsd:date .
     ```

4. Jak se liší prefixovaná a relativní IRI v kontextu RDF?  
    ```
    @prefix foo: <http://example.org/ns#> . ---- HARDCODING
    <#document> foo: <https://jk.com> .
        ----- název instance 

    @base <http://newbase.com/> .           ---- změní "zákládní" IRI 
    <#document> foo: <https://jk.com> .
    ```
   

5. Co je Linked Data Vocabularies?  
   ONTOLOGIE.
   --- sbírka seskupující vlastnosti pro entity
   - inteoperabilita dat --- pevné standardy a pojmenovává vlastnosti
   

6. Co je Open World Assumption?  
    - když něco nemám v záznamu = nevím
    (uzavřený - ne)
   

7. Co je SERVICE pro SPARQL?  
   - Klauzule pro dotazování vzdálených SPARQL endpointů.  
   --- multiendpointové 

Klauzule `SERVICE` umožňuje dotazovat vzdálené SPARQL endpointy.

```sparql
SELECT ?name ?birthPlace
WHERE {
  ?person a dbo:Person ;
          foaf:name ?name .
  SERVICE <http://dbpedia.org/sparql> {
    ?person dbo:birthPlace ?birthPlace .
  }
}
```   

8. Vysvětlete SKOS:exactMatch a použijte na příkladu.  
    - možnost linkovat entity napříč slovníky (auťák = auto)
    - tranzitivní (A = B, B = C, tedy A = C)
    --- např. 
   
   
BONUS

9. prázdné uzly RDF
   - resource bez URI, pouze lokální scope
   -- když nevím IRI / nechci použít (chci ale dát data lokálně objektu) či reifikace
   -- použití jako konektory lokálně
   
11. Serializace RDF
    - N-triples, RDF Turtle, N-Quads (pojmenovaní grafu navíc)


### **Wikidata**
1. Vlastnosti tvrzení v Wikidatech?  
   (Statement)
   - property
   - value
   - rank 
   - qualifiers 
   - references 
   

2. Co je QID ve Wikidatech? 
    - identifikace nějaké itemu na Wikidatech (např. Douglas Adams je Q42)


### **Cypher & LPG**
1. Co je set v Cypher?  
    - keyword, kterým se modifikují property

2. V čem "exceluje" LPG?
    - vhodné pro popis metadat vztahů mezi entitami 
    - má grafové algoritmy 
    ```(:Person {name: "John", age: 30})-[:KNOWS]->(:Person {name: "Alice", age: 25}) 
    ```

### **XML/XPath/XSLT**
1. Co je mode v XSLT?  
   - atribut v templatu, ve kterém odlišuji zpracování stejně zacílených věcí 
   = vytvoření šablon 

```
   <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <!-- Šablona pro režim "mode1" -->
  <xsl:template match="item" mode="mode1">
    <xsl:value-of select="name"/> - Mode 1
  </xsl:template>
  
  <!-- Šablona pro režim "mode2" -->
  <xsl:template match="item" mode="mode2">
    <xsl:value-of select="name"/> - Mode 2
  </xsl:template>
  
  <xsl:template match="/">
    <html>
      <body>
        <h2>Items in Mode 1:</h2>
        <xsl:apply-templates select="items/item" mode="mode1"/>
        
        <h2>Items in Mode 2:</h2>
        <xsl:apply-templates select="items/item" mode="mode2"/>
      </body>
    </html>
  </xsl:template>

  </xsl:stylesheet>
  
```

   

2. Uveďte příklad osy v XPath.  
   - descendant-or-self (vrstva, ze které se ptám a všechny další níže splňující dotaz)
   

3. Vysvětlete XPath osu attribute::.  
   - atributy dle osy (např. pokud jsem osu nedefinoval, jsem v child ose)
   

4. (a) Vysvětlete rozdíl mezi jednoduchým a complex typem v XML Schema.  
   simpleType:
        - nemá text content
        - nemá atributy
        - nemá subelementy

    complex je má (dle 4b).

4. (b) Simple a complex Content XML Schema.
    - jedná se o "podvýběr" complexType 
    simpleContent:
        - nemá subelementy (+ atributy subelementů)
        - narozdíl od simpleType má text content a atributy 
   

5. Co je validní XML?  
   - mám XML, má XML schéma
   - když je XML validní vůči XML schématu, je to validní XML dokument + well-formed (splňuje XML syntax rules)
   

6. Popište XML DOM.  
    - způsob zpracovávání XML --- celé se načte do paměti
    - random access ideální
    porovnání: 

(BONUS)
7. SAX 
    - parsování po elementu, zpracovávám vždy celý dokument 
    - push-based

8. STAX 
    - parsování po elementu
    - jen dokud nenajdu hledaný element
    - pull-based 

9. Co jsou CDATA sekce?
    - možnost mít string se special znaky, které se neinterpretují 
```
<example>
  <![CDATA[
    <html>
      <body>
        <p>This is a paragraph with <strong>HTML</strong> tags.</p>
      </body>
    </html>
  ]]>
    </example>
```

### **JSON/JSON-LD**
1. Co je keyword aliasing v JSON-LD?  
   - definujeme v contextu
```
{
  "@context": {
    "schema": "http://schema.org/",
    "name": "schema:name",
    "Person": "schema:Person",
    "PEPÍK": "@id"
  },
  "@type": "Person",
  "name": "John Doe",
  "PEPÍK": "http://example.org/person/1"
}
```

2. Uveďte 3 klíčová slova ze JSON-LD.  
   -- @context
   -- @id
   -- @type
   

3. Jakými třemi způsoby můžeme na JSON přidat JSON-LD kontext?  

Přímý kontext 
```   {
  "@context": {
    "ex": "http://example.org/"
  },
  "@type": "Person",
  "ex:name": "John Doe"
} 
```

Externí URL kontext 
```{
  "@context": "http://schema.org",
  "@type": "Person",
  "name": "John Doe"
}
```

Kombinovaný - SCOPED - kontext (interní a externí)
```{
  "@context": "http://schema.org",
  "name": "Matěj Foukal",
  "description": "Webový vývojář",
  "address": {
    "@context": {
      "postalCode": "http://schema.org/postalCode",
      "addressLocality": "http://schema.org/addressLocality"
    },
    "postalCode": "11000",
    "addressLocality": "Praha"
  }
}
```
   

5. Popište hlavní způsoby validace polí v JSON Schema.  
   - list validation (definuji si typ pro celé pole)
   - tuple validation (různé typy, ordered)
   

6. Na příkladu vysvětlete, jak lze v JSON-LD zachovat pořadí hodnot v poli.  
   - @list 

pomocí klíčového slova @list.
Příklad:

```
{
  "@context": {
    "ex": "http://example.org/"
  },
  "ex:steps": {
    "@list": [
      "Step 1: Open the file",
      "Step 2: Edit the content",
      "Step 3: Save the file"
    ]
  }
}
```
   

7. Popište 3 validační klíčová slova v JSON Schema.  
    - type
    - required
    - enum

7. Popište 3 schématická klíčová slova v JSON Schema.  
    - $schema
    - $id 
    - $ref 


8. Jak lze specifikovat RDF zdroj v JSON-LD?  

```
    {
  "@context": {
    "ex": "http://example.org/"
  },
  "@id": "http://example.org/person/123",
  "@type": "ex:Person",
  "ex:name": "John Doe",
  "ex:age": 30
}
```

@id --- IRI RDF

   

### **CSV**
1. Popište CSV podle přesného RFC.  
    - comma-separated, CRLF zakončení, RFC4180
    - utf-8 (bez BOMU)
    - escapování: "
    - hlavička dobrovolná, ale doporučená 
    - record = samostatný řádek

2. Jak pomoci URI zacílit na různé části CSV?  
   - fragmentový identifikátor "#"
   ```
   http://example.org/data.csv#cell=5,2
   http://example.org/data.csv#row=5
   http://example.org/data.csv#column=3
   ```

3. Co je relační datový model v kontextu CSV on the Web?  
    - stavební jednotka: tabulka - sloupce, primární klíče
    - v kontextu CSV je tabulka samostatný soubor s popsaným (volitelně) sloupečky
    - v jsonld nadefinuji primary a foreign keys + vzájemné vztahy

### **Formáty** (1ka neexistuje)
2. Uveďte příklady otevřených a licencovaných formátů videa.  
   - l (uzavřené): mp4, mpeg
   - o (otevřené): vp8, vp9
   

3. Uveďte formát vhodný pro archivaci dokumentů. Popište jeho vlastnosti. Proč je vhodný pro archivaci?  
    - PDF 
    - PDF/A
    - šifrování (elektronické podpisy), zákaz nežádaných funkcí, zamknutí dokumentu (nepřepisovatelné)
    - musí se přiložit externí zdroje
   
   

4. Co je Well-Known Text? Uveďte příklad.  
   POINT (20 30)
    - způsob reprezentace geometrie v datech 
    - pro geometrické / geografické informace
   

5. Uveďte příklad textu v Markdownu. Jaký je hlavní cíl Markdownu?  
   ```
   ### příklad 
   ```
   - srozumitelnost, strukturovanost, jednoduché formátování 
   - editační formát (HTML je publikační formát pro MD)

6. Popište formát INI a jeho použití.  
   - key-value konfigurační formát používaný ve Windows
   - encoding .properties 
   - nemá specifikaci 
   ```
   ;komentářek   
   [owner]
   name=John Doe
   organization=Acme Widgets Inc.
   ```
   

7. Co je TOML? Uveďte příklad.  
   - podobné INI, unicode encoding 
   - 
   ```
   #komentářek   
   [owner]
   name = "John Doe"
   organization = "Acme Widgets Inc."
   ```
   
   

8. Co je to textový formát? Uveďte příklady.  
   - zápis běžného textu s nějakým kódováním znaku 
   - lidsky čitelný text
   - .txt, .md, 602
   

9. Co je multimediální formát? Uveďte příklad.  
   - container spojující více druhu formátů s METADATY 
   (multimediální kontejner)
   
   

### **Grafika a multimédia**
1. Co je dithering?  
   - nemám barvu, domíchám barvu (chybějící barva v tiskárně)
   - snížení efektivního rozlišení 
   

2. Popište pixel/dot density. Čím je reprezentovaný?  
   - PPI -> pixel per inch (digitální metrika)
   - DPI -> dot per inch (tisková metrika)
   

3. Popište ztrátové a bezeztrátové kompresní metody pro rastrovou grafiku. Uveďte konkrétní formáty.  
   - bezt.: blockwise, Huffman, quadtree, run length (indexování podle políček stejné barvy)
   - zt.: DCT -- kvantizace, diskrétní kosinová transformace, upravujeme jas YcbCR (jas, modrá komponenta, červená komponenta)
   

4. Popište RGBA barevný model.  
   - Red, green, blue, alpha (průhlednost)
   - aditivní barevný model = "jak moc daná barva svítí?"
   

5. Popište CMYK.  
   - subtraktivní barevný model (jak moc danou barvu využiji - míchání)
   - Cyan, Magenta, Yellow, Black --- tiskárny
   

6. Co je diskrétní kosinová transformace?  
   - viz 3. (DCT)


### **Zvuk**
1. Popište Pulse-Code Modulation v kontextu digitálního zvuku.  
   - PCM, digitální zpracováíní analogové amplitudy pomocí regulárních intervalů (sampling rate, frekvence)
   - kvantizováno na nejbližší hodnotu v oblasti digitálních "kroků" (bit depth, sample size)
   


### **Obecné technologie a koncepty**
1. Co je Graph Query Language? GQL  
   MATCH (n) RETURN n

2. Co je souřadnicový systém?  
   --- WGS-84?
   

3. Proč vznikl TeX?  
   - ideální prostředí pro odborné texty obsahující vzorce a matematické výpočty
   - v zásadě "word" s lepší funkcionalitou a formátováním pro vědce 
   



### **POSLEDNÍ PŘÍKLADY**
    --- datový formát, schéma, příklad data samplu

1. Airline (- name, - number of employees)
1 -------(owns)------- 0...n
Airplane (- name, capacity, number of engines)

    ----> výměna dat mezi veřejnou administrací skrze webové services
    -- běžné queries: 
    počet motorů (number of engines) vlastněný Airline
    poče Airplanes na employee 



2. Navrhněte datový model, kde zaměstnanec pracuje v budově a může mít jiného zaměstnance jako nadřízeného.  
    
