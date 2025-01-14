# RDF
### schema + data sample:
```
@prefix ex: <http://example.org/> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .

ex:Person a rdfs:Class ;
    rdfs:label "A person class" .

ex:name a rdf:Property ;
    rdfs:domain ex:Person ;
    rdfs:range xsd:string .

ex:Simon a ex:Person ;
    ex:name "Simon Juza" .
```

# LPG + Cypher 
### LPG 
```
{
  "nodes": [
    { "id": 1, "labels": ["Person"], "properties": { "name": "John Doe", "age": 30 } }
  ],
  "relationships": [
    { 
      "id": 10, 
      "type": "FRIEND", 
      "startNode": 1, 
      "endNode": 2, 
      "properties": { "since": 2020 }
    }
  ]
}
```
### Cypher
```
CREATE (p1:Person {name: "John Doe", age: 30});  
CREATE (p2:Person {name: "Jane Smith", age: 25});  
CREATE (p1)-[:FRIEND {since: 2020}]->(p2);  
```

# XML
### Schema - person.xsd 
```
<?xml version="1.0" encoding="utf-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="Person">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="Name" type="xs:string"/>
        <xs:element name="Age" type="xs:integer"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```
### XML - example 
```
<?xml version="1.0" encoding="utf-8"?>
<Person 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="person.xsd">
  <Name>John Doe</Name>
  <Age>30</Age>
</Person>
```

### XPath - example
```
//Person  

/Person/Name

```


# JSON
### Schema
```
{  
  "$schema": "http://json-schema.org/draft-07/schema#",  
  "type": "object",  
  "properties": {  
    "name": {  
      "type": "string"  
    },  
    "age": {  
      "type": "integer",  
      "minimum": 0  
    }  
  },  
  "required": ["name", "age"]  
}  
```

### JSON Example
```
{  
  "name": "John Doe",  
  "age": 30  
}  
```

# JSON-LD

### JSON-LD schema
```
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "@context": {
      "type": "object",
      "additionalProperties": {
        "type": ["string", "object"]
      }
    },
    "@type": {
      "type": ["string", "array"],
      "items": { "type": "string" }
    },
    "@id": {
      "type": "string",
      "format": "uri"
    },
    "properties": {
      "type": "object",
      "additionalProperties": {
        "type": ["string", "number", "array", "object", "boolean"]
      }
    }
  },
  "required": ["@context"],
  "additionalProperties": true
}
```
### JSON-LD example
```
{
  "@context": {
    "@vocab": "http://schema.org/",
    "name": "http://schema.org/name",
    "age": "http://schema.org/age"
  },
  "@type": "Person",
  "@id": "http://example.org/JohnDoe",
  "name": "John Doe",
  "age": 30,
  "knows": {
    "@type": "Person",
    "name": "Jane Smith"
  }
}
```

# CSV

### JSON-LD schema
```
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "@context": {
      "type": "object",
      "properties": {
        "@vocab": { "type": "string" }
      },
      "required": ["@vocab"]
    },
    "data": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "id": { "type": "integer" },
          "name": { "type": "string" },
          "age": { "type": "integer" },
          "type": { "type": "string" }
        },
        "required": ["id", "name", "age", "type"]
      }
    }
  },
  "required": ["@context", "data"]
}
```

### CSV
```
id,name,age,type
1,John Doe,30,Person
2,Jane Smith,25,Person
```




