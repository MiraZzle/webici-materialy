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


| Technologie                       | **ACID Transakce** | **BASE**      | **Replikace**                          | **Sharding**        |
|------------------------------------|--------------------|---------------|----------------------------------------|---------------------|
| **Cassandra**                      | Ne (nepodporuje plné ACID) | Ano           | Master-Slave                          | Ano (Master-Slave)  |
| **MongoDB**                        | Ne (jednoduché transakce na úrovni dokumentu) | Ano           | Master-Slave                          | Ano (Master-Slave)  |
| **Neo4j**                          | Ano                | Ne            | Master-Slave                          | Ano (Master-Slave)  |
| **Riak**                           | Ne (nepodporuje plné ACID) | Ano           | Peer-to-Peer                          | Ano (Master-Slave)  |
| **PostgreSQL**                     | Ano                | Ne            | Master-Slave                          | Ano (Master-Slave)  |
| **Redis**                          | Ne (jednoduché transakce)  | Ano           | Master-Slave                          | Ano (Master-Slave)  |
| **Hadoop MapReduce**               | Ne (MapReduce není transakční systém) | Ne (nepoužívá BASE) | Není aplikován (součást HDFS)         | Ne (MapReduce není replikace) |
| **Hadoop HDFS**                    | Ne (souborový systém) | Ne            | Master-Slave                          | Ano (Master-Slave)  |
| **SciDB**                          | Ano (omezeně, pro operace na blocích) | Ne | Master-Slave                          | Ano (Master-Slave)  |
| **ElasticSearch**                  | Ne (transakce nejsou plně podporovány) | Ano           | Master-Slave a Peer-to-Peer (kombinace) | Ano (Master-Slave)  |
