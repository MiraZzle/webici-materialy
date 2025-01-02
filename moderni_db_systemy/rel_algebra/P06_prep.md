# Priprava na prednasku 6

1. In MapReduce processing, what is a prerequisite for using a Combiner?

The reduce operation must be both commutative and associative (like addition of 1s in Word count)

2. If you use a Combiner, does that mean you won’t need a Reducer?

No. Reducer still needs to combine the outputs coming from map tasks.

3. How many Map tasks are usually created?

One map task for every chunk of the input file.

4. Why is the number of Reduce tasks typically configured to be lower than the number of Map tasks?

For each map task we need to create to create intermediate files for each reduce task -> so if we set the number of reduce tasks too high, the the number of intermediate files will be too big.

5. In the extended relational algebra, what is the operator symbol for the grouping operator?

γ - gamma

We denote a grouping-and-aggregation operation on
a relation R by γX(R), where X is a list of elements that are each either
(a) A grouping attribute, or
(b) An expression θ(A), where θ is one of the five aggregation operations such as SUM, and A is an attribute not among the grouping
attributes.

6. What is the communication cost of an algorithm?

The sum of communication costs of all tasks implementing the algorithn. The communication cost of a task is the size of its input - usually measured in tuples.

7. When can communication cost not be used to measure the efficiency of an algorithm?

Communication cost may not be a suitable measure of efficiency when the execution time of individual tasks dominates the overall running time. But such cases are quite rare.

8. What is the justification for focusing on communication costs in (acyclic) MapReduce workflows?

The algorithm executed is usually very simple. The typical compute node can do a lot of work on received elements in time it takes to receive the input.

The network speed is usually much slower than processing speed.

The main cost is usually loading data from disks, the execution itself is usually quite fast.

9.  What are the two reasons why we do not consider the output size?

A. If the output of task A is the input for task B, then the size of A's input is already accounted for in B's input.

B. In practive, the algorithm output is usually not large when compared to the input or immediate algorithm data.

10. Why is wall-clock time also an important criterion?

wall-clock time: the time it takes a parallel algorithm to finish

If we were to minimize the communication cost, we could feed the entire input to just one node, thus minimizing the communication cost but increasing the overall time it takes for the algorithm to finish.

11. What is the communication cost of computing R(A, B) ▷◁ S(B,C) with MapReduce?

`O(|R| + |S|)` where `|R|` is the size of relation `R` - the same goes for `|S|`
