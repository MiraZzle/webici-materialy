# Priprava na prednasku 4

1.  What is the task of the query parser?

    Translating a desclarative query (SQL) into algebraic expression (something like Relational algebra, usually somewhat enriched). We should not lose any information during the translation step.

2.  What is the high-level task of the query optimizer?

    Query optimizer takes some algebraic expression (from query parser for example) and uses domain specific knowledge for optimization and translates it into _executable program_

3.  What is the relationship between the WHAT and HOW aspects of query planning?

    The what is taking a declarative sql query (what we translate) and how means coming up with an exectuable program at the end.

4.  What kind of information does the catalog contain?

    DB catalog contains all metadata of database (names of tables, schemas) and statistics about the data.

5.  What is the relationship between query optimization and programming language compilation?

    Standart programming lang compiler takes some language (cpp, java) and translates it into executable code. This is the high level idea. The query optimizer does something similar, however since query optimizer is specialized, we can use some domain specific optimizations. Turing language is usually not turing complete.

6.  What does a single optimization rule do?

    Applied to DAG transforms it into another DAG. We can imagine the query is a tree and as such as a DAG.

    input: query evaluation plan
    output: different query eval plan that is equivalent

    A single optimization rule can be for example:

    ```
    /select_{r and q} R <=> /select_{q} (/select_{r} R)
    ```

7.  What is the advantage of breaking up conjunctions of selections?

    It always leads to the same result. The order does not matter. It is a prep step for next rules. We can choose the simpler selection first - this is called pushing down the rule (we evaluate it earlier since we evaluate the tree from bottom up).

8.  Why does it make sense to push down selections?

    Tuples that are not required are filtered earlier - we decrease the amount of comparisons. We have few tuples (rows) that enter the selection evaluation. The number of comparisons goes down.

9.  Why would we prefer joins over cross products whenever possible?

    Cross products have O(n^2) complexity - unacceptable for big sets. Joins have complexity of O(nlogn), they are quicker.

    ![unacceptable](https://i.pinimg.com/736x/6d/a2/8d/6da28dc629fb40a24d6c63a8df5285c9.jpg)

10. When is it possible to introduce a join?

    When we have the required attributes. See 9.

11. When is it possible to push down projections?

    When we add another attribute to our projection that will be needed in joins further up the tree. We basically introduce new projections.

12. What may be the effect of picking the wrong join order?

    There may be a drastic between the amount of tuples left to process as a result of the join.

13. Is the join order problem only relevant for joins?

    The order of operations is relevant to all operations that are commutative and associative. These operations include unions, intersections (search engines) and cross products.

14. What is a left-deep tree?

    Type of query execution plan where all joins are performed in a linear sequence. The left child of each join operation is always a base table or the result of another join, while the right child is always a base table. Joins are executed one at a time, typically from left to right, and each result feeds into the next join in sequence.

15. What is a bushy tree?

    A bushy tree is a more general form of join execution plan, where:

    Both the left and right children of any join can themselves be joins, not just base tables. This means that multiple joins can be performed in parallel, and intermediate results from joins can be joined together at higher levels of the tree.
