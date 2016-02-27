<div style="border-radius: 10px; background: #EEEEEE; padding: 20px; text-align: center; font-size: 1.5em">
  <big><b>Spark for Data Scientists</b></big> </br>
  </br>

  <code>{chris,jason}@datascience.com</code>
  <br/>
  <br/>

</div>

---

#Resources

* [Spark Project Docs](http://spark.apache.org/documentation.html)
* Karau et al's [Learning Spark: Lightning-Fast Big Data Analysis](http://shop.oreilly.com/product/0636920028512.do)
* This course's [Github Project](https://github.com/cem3394/spark-for-data-scientists/tree/gh-pages)

---

#Lecture 1: The Spark Ecosystem

---

#Matei Zaharia
![](img/matei_zaharia.jpg)
$$
$$
"One of the Spark project goals was to deliver a platform that supports a very wide array of diverse workflows - not only MapReduce batch jobs (there were available in Hadoop already at that time), but also iterative computations like graph algorithms or Machine Learning. And also different scales of workloads from sub-second interactive jobs to jobs that run for many hours."

---

#A Bit of History

![](img/history.png)

[https://medium.com/@markobonaci/the-history-of-hadoop-68984a11704](https://medium.com/@markobonaci/the-history-of-hadoop-68984a11704)

.notes: https://www.youtube.com/watch?v=SxAxAhn-BDU

---

#OpenMP (1997)
![](img/OpenMP.jpg)

.notes: started w/ Backus' Fortran 1.0 in 1997. parallel processing only done on expensive supercomputers. complex and not robust to node failures.

---

#MapReduce (2004)
![](img/mapreduce.png)

.notes: combined FP primitives with world-class cluster computing know-how. the simple MR framework (read Backus) meant that all of a sudden it became relatively simple and reliable to compute with large arrays of cheap nodes, any of which might fail. these guys are legends. they are also behind tensorFlow.

---

#Hadoop (2006)
![](img/hadoop-ecosystem.png)

.notes: open source MapReduce. became a top level apache project in 2006. batched processing. static pageRank anecdote. by 2006 google had already moved on to streaming.

---

#Kafka and Spark (2012)
![](img/kafka-spark.png)

.notes: Both Scala projects. Came out of LinkedIn and Berekley's AmpLabs respectively. Framework and language have converged. Scala Collections API, Spark API, Scalding API, Kafka API are almost identical.

---

Apache Spark is an open-source, parallel, distributed, general-purpose cluster computing framework with distributed, in-memory data processing.
$$
$$
In contrast to Hadoop’s two-stage disk-based MapReduce processing engine, Spark’s multi-stage in-memory computing engine allows for running most computations in memory, and hence provides better performance for iterative applications, e.g. machine learning algorithms and interactive data mining.

---

#Why Scala: Productivity


Basic syntax and Collections API are all that's needed to become productive with Spark.
$$
$$
You use the same language (often the same code) for iterative exploration as you do for large jobs.

.notes: Python and R are second-class citizens.

---

#Why Scala: Ecosystem

Scala integrates well with the big data eco-system, which is also JVM based.
$$
$$
In addition to Scala-native frameworks like Spark and Kafka, there are many frameworks on top of Java libraries like Scalding (Cascading), Algebird / Summingbird (Scalding and Storm), Scrunch (Crunch), and Flink (Java core with Scala API).

---

The Scala APIs are usually more flexible than say Hadoop streaming with Python/Perl, PySpark or Python/Ruby bolts in Storm, since you have direct access to the underlying API.
$$
$$
There are also a wide range of data storage solutions that are built for or work well with JVM like Cassandra, HBase, Voldemort and Datomic.

---

#Why Scala: Functional Paradigm

A third benefit is the functional paradigm which fits well within the Map/Reduce and big data model.
$$
$$
Batch processing works on top of immutable data, transforms with combinators, and generates new copies. Real time log streams are essentially lazy streams.

---

Most Scala data frameworks have the notion of some abstract data type that's extremely consistent with Scala's collection API.
$$
$$
Glance at `TypedPipe` in Scalding and `RDD` in Spark, and you'll see that they all have the same set of combinator methods, e.g. `map`, `flatMap`, `filter`, `reduce`, `fold` and `groupBy`.

---

Many libraries also have frequent reference of category theory (eg monoids, monads, applicative functors etc) to guarantee the correctness of distributed operations.
$$
$$
Equipped such knowledge it'll be a lot easier to understand techniques like map-side reduce.

---

#Spark Stack
![](img/spark-ecosystem.png)

---

#Spark Core

Basic functionality of Spark: task scheduling, memory management, fault recovery, interacting with storage systems, etc.
$$
$$
RDD API will be our point of departure in this course.

---

#SparkSQL

Spark’s package for working with structured data (DataFrames) and semi-structured data (DataSets).

---


#Spark Streaming & GraphX

Spark Streaming is a Spark component that enables processing of live streams of data.

GraphX is a library for manipulating graphs and performing graph-parallel computations.

.notes: We will cover these in the Methods class.

---


#MLLib

Spark's machine learning library.

.notes: We will cover MLlib in the Models class.

---

#Benefits of Tight Integration

* Higher-level components in the stack benefit from improvements at the lower layers

* Organizational costs associated with running the stack are minimized

* Combine batch, interactive, and stream processing processing with a unified API

.notes: For example, when Spark’s core engine adds an optimization, SQL and machine learning libraries automatically speed up as well. These costs include deployment, maintenance, testing, support, and others. This also means that each time a new component is added to the Spark stack, every organization that uses Spark will immediately be able to try this new component.

---

Spark’s design is also fairly simple and the Scala codebase is fairly small relative to the features it offers.

---


#Computation Model

Spark can run over a variety of cluster managers, including Hadoop YARN, Apache Mesos, and a Standalone Scheduler.
$$
$$
Spark creates a directed acyclic graph (DAG) of computation stages to submit jobs to the cluster manager.
$$
$$
Spark uses a lazy evaluation model (i.e. postpones any processing until really required for actions).


---

Spark supports diverse workloads, but is optimized for low-latency iterative tasks.
$$
$$
These sorts of computation occur often in Machine Learning and graph algorithms.


.notes: Many Machine Learning algorithms require plenty of iterations before the result models get optimal, like logistic regression. The same applies to graph algorithms to traverse all the nodes and edges when needed. Iterative computations can increase their performance when the interim partial results are stored in memory. Spark can cache intermediate data in memory for faster model building and training.

---


#Spark vs Hadoop (2012)


Duration of the first and later iterations in Hadoop, HadoopBinMem and Spark for logistic regression and k-means using 100 GB of data on a 100-node cluster.
[source](https://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf)
$$
$$
![](img/spark-vs-hadoop.png)

.notes: Hadoop: The Hadoop 0.20.2 stable release. HadoopBinMem: A Hadoop deployment that converts the input data into a low-overhead binary format in the first iteration to eliminate text parsing in later ones, and stores it in an in-memory HDFS instance.

---

#RDD vs Distributed Shared Memory

$$
$$

<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;margin:0px auto;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
.tg .tg-9hbo{font-weight:bold;vertical-align:top}
.tg .tg-yw4l{vertical-align:top}
</style>
<table class="tg">
  <tr>
    <th class="tg-9hbo"></th>
    <th class="tg-9hbo">RDD</th>
    <th class="tg-9hbo">DSM</th>
  </tr>
  <tr>
    <td class="tg-9hbo">Evaluation</td>
    <td class="tg-yw4l">lazy</td>
    <td class="tg-yw4l">strict</td>
  </tr>
  <tr>
    <td class="tg-9hbo">Writes</td>
    <td class="tg-yw4l">coarse grained</td>
    <td class="tg-yw4l">fine grained</td>
  </tr>
  <tr>
    <td class="tg-9hbo">Recovery</td>
    <td class="tg-yw4l">lineage</td>
    <td class="tg-yw4l">check point</td>
  </tr>
  <tr>
    <td class="tg-9hbo">Consistency</td>
    <td class="tg-yw4l">trivial / immutable</td>
    <td class="tg-yw4l">delegated</td>
  </tr>
</table>

---

#Spark Shell

You start Spark shell using spark-shell script (available in bin directory).

    $ ./bin/spark-shell
    Spark context available as sc.
    SQL context available as sqlContext.
    Welcome to
          ____              __
         / __/__  ___ _____/ /__
        _\ \/ _ \/ _ `/ __/  '_/
       /___/ .__/\_,_/_/ /_/\_\   version 1.6.0-SNAPSHOT
          /_/

---

#Spark Context

Spark shell gives you the `sc` value which is the [SparkContext](http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkContext) for the session.

    scala> sc
    res0: park.SparkContext = spark.SparkContext@2ac0cb64

---

There is also an `sqlContext` object to use with Spark SQL.


    scala> sqlContext
    res1: spark.sql.SQLContext = spark.sql.hive.HiveContext@60ae950f

---

To close Spark shell, you press Ctrl+D or type in `:q` (or any subset of `:quit`).

    scala> :quit

---

#Spark drivers

At a high level, every Spark application consists of a driver program that launches various parallel operations on a cluster.

The driver program contains your application’s main function and defines distributed datasets on the cluster, then applies operations to them.


.notes: In Zeppelin, the driver program was the Spark interpreter itself.

---

Driver programs access Spark through a `SparkContext` object, which represents a connection to a computing cluster.
$$
$$
`SparkContext` in turn requires a `SparkConf` object for configuration.
$$
$$
Refer to [Spark Configuration](http://spark.apache.org/docs/latest/configuration.html) for further discussion of how to configure Spark.

---

You can query for the values of Spark properties in Spark shell as follows:

    scala> sc.getConf.getOption("spark.local.dir")
    res1: Option[String] = None
    scala> sc.getConf.getOption("spark.app.name")
    res2: Option[String] = Some(Spark shell)


---



---


#Lecture 2: RDD's


---




#RDD - Resilient Distributed Dataset

Resilient Distributed Dataset (RDD) is the primary data abstraction in Spark. It is a distributed collection of items.

The original paper is a great read: [https://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf](https://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf)

---

At a high level, any Spark application creates RDDs out of some input, run (lazy) transformations of these RDDs to some other form (shape), and finally perform actions to collect or store data.

---

#Transformations

    !scala
    map(f : T => U) : RDD[T] => RDD[U]
    filter(f : T => Bool) : RDD[T] => RDD[T]
    flatMap(f : T => Seq[U]) : RDD[T] => RDD[U]
    sample(fraction : Float) : RDD[T] => RDD[T]
    groupByKey() : RDD[(K, V)] => RDD[(K, Seq[V])]
    reduceByKey(f : (V,V) => V) : RDD[(K, V)] => RDD[(K, V)]
    union() : (RDD[T],RDD[T]) => RDD[T]
    join() : (RDD[(K, V)],RDD[(K, W)]) => RDD[(K, (V, W))]
    cogroup() : (RDD[(K, V)],RDD[(K, W)]) => RDD[(K, (Seq[V], Seq[W]))]
    crossProduct() : (RDD[T],RDD[U]) => RDD[(T, U)]
    mapValues(f : V => W) : RDD[(K, V)] => RDD[(K, W)]
    sort(c : Comparator[K]) : RDD[(K, V)] => RDD[(K, V)]
    partitionBy(p : Partitioner[K]) : RDD[(K, V)] => RDD[(K, V)]


---
