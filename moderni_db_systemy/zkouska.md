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

![Datové předpoklady](/images/data_assumptions.png)
