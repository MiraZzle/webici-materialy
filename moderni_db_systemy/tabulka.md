HDFS
- master-slave 

Riak (key-value)
- clustering nemá mastera (jakýkoliv uzel může řešit data)

Redis (key-value)
-> master-slave replikace, master-hash slots sharding 

Cassandra (column)
- peer-to-peer 
-> request zpracovává jakýkoliv uzel (koordinátor)

Mongo (document)
-> master-slave replikace (replica set, každý má svou kopii)
= primární s oplogem a sekundární uzly (maximálně 1 primární -- volby0)

Neo4j (graph)
-> uzel + vztahy

SciDB (array)
-> multidimenzionální sorted pole 

ElasticSearch (full-text search engine)
-> indexované 