# Priprava na prednasku 7

1. What is the benefit of merging a two-job-chain of a Map-only job and a traditional MapReduce job?

Communication cost: Saving time by not needing IO for temorary data. Reducing the amount of data movement in the MapReduce pipeline -> saves time.
Fewer tasks are started -> less task management

2. What is a downside?

- Possibly having to alter some code.
- When merging the results, we lose the intermetidate reuslts -> more difficult debugging.
- More specific code -> less reusability

3. Explain this example of chain folding:

We optimize the original chain (top) by folding the map + map reduce into a more complex map with reduce -> saves time.

4. Why should you avoid to merge jobs that use a lot of main memory?

Because the total memory usage may exceed the amount of memory dedicated for the task.
