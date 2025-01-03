# Priprava pro prednasku 3

1. What does it mean if a query language is “compositional”? Why is this a desirable
   property?

When asking a query over a set of relations, we get a relation as a result. Compositionality = the ability to run a query over the result of a previous query. We can work with query results and nest queries.

It is directly used in VIEWS in SQL

2. SQL relies on multiset semantics, relational algebra on set semantics. What is an im-
   portant consequence in query evaluation?

SQL is based on relational algebra. SQL allows preservation of duplicates. Relational algebra (set sementics) enforces uniqueness - may not be as efficient.

3. What is the result of evaluating the relational algebra query

123 Amy
677 Amy

4. What is the result of evaluating the relational algebra query

Amy

Because relational algebra uses set semantics (i.e. eliminates duplicates)

5. How many tuples are in the result of evaluating Student × Apply?

4 x 8 = 32

6. How many tuples are in the result of evaluating Student ▷◁ Apply?

Amy - 4
Bob - 1
Craig - 3

= 8

7. Is it true that Student ▷◁ (Apply ▷◁ College) = (Student ▷◁ Apply) ▷◁ College?

Yes - natural join can be rewritten as cartesian product with selection - implies associativity

8. Is it true that Apply ▷◁ College = College ▷◁ Apply?
   Yes, natural join is commutative

Why do we care?

Optimizers need to know how to translate the query

9. Is it true that Apply ▷◁ Student = σApply.sID=Student.sID(Apply × Student)?

No - natural join eliminates the redundant attribute, however cartesian product keeps all attributes (even when using selection)

10. Is it true that Apply ▷◁ Student = Apply ▷◁Apply.sID=Student.sID Student?

no - 9
Ten pravy natural join je stejny jako u 9 - zustaly by 2 sloupce

11. What does it mean when we say that the ▷◁ operator is not part of the core relational
    algebra?

The operator of natural join can be expressed using the core relational algebra operators - cartesian product with selection

12. What does it mean when we say that SQL is a declarative language?

We write queries about what we want. We do not need to specify how we get the data or any low level specifics

13. How are basic SQL statements and relational algebra connected?

SQL is based on relational algebra, however SQL uses multiset paradigm

CANONICAL TRANSLATION:

- SQL query is translated into relational algebra by the compiler
