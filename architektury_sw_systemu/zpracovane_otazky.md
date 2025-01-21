# OTÁZKY ARCHITEKTURY
 
1) Jmenujte tři druhy architektonických struktur a stručně popište. / Uveďte dva architektonické styly a krátce je popište 
    - struktura
    - event-driven, layered, microservices (viz zápisky)

2) Co je to pohled v SW architektuře? 
    - co přesně? funkcionální vs technický?
    (stakeholder view?)
    - systémový inženýř
    - analytik
    - programátor
    - úroveň systému, co daný člověk chce 

    - aplikačně-centrický pohled 
    - 4+1 = logický, development, fyzický, procesový 

3) Performance QA - vysvětlete na příkladu a uveďte a vysvětlete 2 taktiky pro dosažení. 
    - naše projekty

4) Jaký je rozdíl mezi three-tier a domain-driven architekturou?
    - three-tier: rozdělené dle responsibilities (separated frontend and backend with application server and db server)
    - domain-driven: přístup založen na významu znalostí a spolupráci znalců a vývojářů (zaměřen na business logic, rozděleno do segmentů), bounded context = větší granularita

5) Co všechno vejde do úvahu při návrhu architektury SW systému? 
    - správně identifikovat problém 
    - stakeholders
    - právní a regulační požadavky
    - testování a kvalita
    - budget 
    - scalability nutná?
    - actors
    - performance based on requirements 
    - non-functional requirements
    - measurable requirements (QA)
    - responsibilities 

6) Co je to microkernel, popište části, nakreslete obrázek, k čemu je to dobré
    - je to monolitický architektonický styl
    - plugin architektura 

7) Co je to tight coupling, uveďte dva případy 
    - vysoký level coupling = tedy to nechceme, protože na sobě moduly až moc závisí
    a: Content Coupling (A přímo vkládám do B)
    b: Common Coupling (stejná global data mezi dvěma moduly)

8) Na příkladu vysvětlete QA security a uveďte aspoň dvě taktiky pro dosažení 
    - V L3 vrtvě mám EntryPoint, ten musí zamezit přístupy nechtěných uživatelů (hackers)

    Source of Stimulus: Hacker
    Stimulus: wants private data of users
    Artifact: Application Frontend Container (From L3 - EntryPoint to DTB Component)
    Environment: Run-time
    Response: the hacker is detected as a threat
    Measure: data is untouched


9) Co je to logical unit a uveďte příklad 
    - jednotka řešící celou responsibility
    - např. nějaké modul s komponenty, virt HW



### NAMALUJ C4 OTÁZKY, VOL. 1

4) (přibližně) Nakreslete libovolný dekompoziční model (například C4) pro systém monitorování 
lednic. Uživatel v mobilní aplikaci zadává požadavky, aby v lednici bylo nějaké množství 
(gramáž/počet) nějakých produktů. HW v lednici je schopen množství všech produktů 
monitorovat. Pokud nějaký produkt začne docházet, přijde uživateli do mobilní aplikace 
notifikace. Systém musí být schopen automaticky zboží dokoupit pomocí integrovaného 
obchodního systému, doručením se zabývá externí systém. 
Je také nutné splnit následující požadavky: 
a) Systém musí být schopen monitorovat tisíce lednic. 
b) Informace o obsahu lednice nesmí být centralizované. 
c) Notifikace uživateli přijdou v jakémkoliv případě do 30 minut nezávisle na tom, kde je 
uživatel. 
d) Systém musí být schopen umožnit jednoduché přidání nových online dodavatelů zboží. 
Uveďte konkrétně, jak dekompoziční model splňuje každý z požadavků (slovně/vyznačit v 
diagramu). Celá dekompozice za 2b, každý QA za další 2b. 

5) Uveďte jakých kvalitativních atributů se týkají požadavky a-d z otázky 4. (správná odpověď: 
usability, security, availability, modifiability) 

6) Napište strukturovaný scénař (jako byl probírán na přednášce) pro následující požadavek: 
Chceme stávající architekturu z úlohy 4 rozšířit tak, aby systém automaticky kontroloval všechno 
zboží, jestli není ve slevě, v takovém případě by systém poslal notifikaci uživateli. Určete také, o 
jaký QA jde. 


### NAMALUJ C4 OTÁZKY, VOL. 2
6) Navrhněte a pomocí C4 nakreslete následující systém AI_Examiner (přesně si to 
nepamatuju, zachovávám hrubý význam): 
 
Externí systémy vysokých škol (dále jen sisy; jsou externí a vy je nenavrhujete) vám do 
AI_E naposílají data o svých studentech a termínech zkoušek a kteří studenti na ty 
termíny jdou. AI_E si to uloží a nějak si spáruje svá data o zaregistrovaných uživatelích s 
daty z těch sisů. Učitelé ve webové aplikaci od AI_E napíšou otázky a bodové rozmezí a 
vzorové odpovědi. Před zkouškou si učitel nechá vygenerovat PDFko z těch otázek, 
stáhne si to z té webovky a sám už si to nějak vytiskne. 
 
Až skončí zkouška, učitel to zaklikne, studenti si otevřou appku AI_E na svých mobilech a 
nafotí své odpovědi. Systém přijme fotky a automaticky vyhodnotí. Učiteli přijde 
notifikace o hotovém automatickém vyhodnocení a má pak možnost ve webovce ručně 
zkontrolovat ohodnocení fotek a případně může hodnocení změnit pro jednotlivé 
studenty. Pak se známky pošlou do sisu. 
 
Návrh již zmíněného za 6 bodů. Následující požadavky každý za 2 body. V architektuře 
vyznačte, čím jste požadavek vyřešili. 
 
a) Studentovi je do minuty oznámeno, že jeho fotky byly přijaty (a ty fotky fakt musí být 
přijaty). 
b) Systém rozpozná, jestli student neposlal fotku řešení jiného studenta, aby nemohli 
podvádět. 
c) Pokud je fotka rozmazaná/nečitelná, bude to učiteli oznámeno do 5 minut od zjištění. 
 
7. Určete, jakých QA se týkají požadavky z otázky 6. (Absolutně jsme se neshodli na 
odpovědi, proto to sem radši nepíšu.) 
8. Uvažte requirement k otázce 6, že externí vysoké školy mohou využívat AI_E pro 
vyhodnocení svých zkoušek. Určete typ QA a napište scénář dle struktury z přednášky. 

### uncertain 
10. už si nepamatuju, ale asi to bylo něco o QA  ?????
