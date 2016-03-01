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

#Hadoop for your RAM

Apache Spark is an open-source, parallel, distributed, general-purpose cluster computing framework with distributed, in-memory data processing.
$$
$$
In contrast to Hadoop’s two-stage disk-based MapReduce processing engine, Spark’s multi-stage in-memory computing engine allows for running most computations in memory, and hence provides better performance for iterative applications, e.g. machine learning algorithms and interactive data mining.

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

#SMACK (2014)
![](img/smack-stack.jpg)

.notes:  Kafka takes care to event transport, Cassandra is used to persist and distribute events. While Spark and Akka can be combined to build various data analysis pipelines for both large data sets, event processing, in order to meet the required throughput, and latency constraints. Mesos serves as a task coordinator, facilitating the distribution of tasks and jobs in the cluster.

---

#Why Scala: Productivity


Spark is essentially distributed Scala (`sc.parallelize(0 to 100)`). Basic syntax and Collections API are all that's needed to become productive.
$$
$$
You use the same language (often the same code) for iterative exploration as you do for large jobs.

.notes:  Framework and language have converged. Scala Collections API, Spark API, Scalding API, Kafka API are almost identical. Python and R are second-class citizens.

---

#Why Scala: Ecosystem

Scala integrates well with the big data ecosystem, which is also JVM based.
$$
$$
In addition to Scala-native frameworks like Spark and Kafka, there are many successful projects on top of mixed Java / Scala frameworks:

.notes: Scalding (Cascading), Algebird / Summingbird (Scalding and Spark), Finagle (Akka), Scrunch (Crunch), Flink (Java and Scala)

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

Spark libraries also have frequent reference of category theory (eg monoids, monads, applicative functors etc) to guarantee the correctness of distributed operations.
$$
$$
Equipped with this knowledge it will be a lot easier to understand techniques like map-side joins and reduces.

---

#Hello World in Spark

    !scala
    val lines = sc.textFile( "1950.txt" )
    lines.flatMap( (l) => l.split(" ") )
     .map( (w) => (w,1) )
     .reduceByKey( _ + _ )
     .saveAsTextFile( "WordCount.txt" )

.notes: this code looks fairly unremarkable, as if map and reduce are simply part of the core language.

---


#Spark Stack
![](img/spark-ecosystem.png)

.notes: Spark can run over a variety of cluster managers, including Hadoop YARN, Apache Mesos, and a Standalone Scheduler. SparkSQL Spark’s package for working with structured data (DataFrames) and semi-structured data (DataSets). Spark Streaming is a Spark component that enables processing of live streams of data. GraphX is a library for manipulating graphs and performing graph-parallel computations (We will cover these in the Methods class). Spark's machine learning library (We will cover MLlib in the Models class). Spark Core contains the basic functionality of Spark: task scheduling, memory management, fault recovery, interacting with storage systems, etc. The RDD API will be our point of departure in this course.

---

#Benefits of Tight Integration

* Higher-level components in the stack benefit from improvements at the lower layers

* Organizational costs associated with running the stack are minimized

* Combine batch, interactive, and stream processing processing with a unified API

.notes: For example, when Spark’s core engine adds an optimization, SQL and machine learning libraries automatically speed up as well. These costs include deployment, maintenance, testing, support, and others. This also means that each time a new component is added to the Spark stack, every organization that uses Spark will immediately be able to try this new component. Spark’s design is also fairly simple and the Scala codebase is fairly small relative to the features it offers.

---

#Computation Model

Spark supports diverse workloads, but is optimized for low-latency iterative tasks.
$$
$$
These sorts of computation occur often in Machine Learning and graph algorithms.


.notes: Many Machine Learning algorithms require plenty of iterations before the result models get optimal, like logistic regression. The same applies to graph algorithms to traverse all the nodes and edges when needed. Iterative computations can increase their performance when the interim partial results are stored in memory. Spark can cache intermediate data in memory for faster model building and training.

---

Spark creates a *directed acyclic graph* (DAG) of computation stages to submit jobs to the cluster manager.
$$
$$
Spark uses a *lazy evaluation model* (i.e. postpones any processing until an action is performed).

---

    !scala
    val lines = sc.textFile("1950.txt") //creation
    val nyt = "Special to THE NEW YORK TIMES."
    val linesWithNYT = lines.filter {_ contains nyt} //transformation
    linesWithNYT.count() //action

---


#Spark and Hadoop

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

<!---it is indeed possible to write concurrent programs in imperative and OO languages, but it's extremely painful. In essence, you have to translate functional methods to your language of choice and follow the functional paradigm religiously. A single slip and you're spending weeks tracking concurrency bugs.
The reason is that, in imperative languages, and OO in particular, the sharing of data is the default behavior. And sharing of data between threads leads to data races -- unless you follow a very strict regime of synchronization. No help from the compiler or the type system! All the methodologies of testing and bug reporting fail miserably in the face of data races. It's usually very hard to reproduce a non-trivial concurrency bug.
In strict functional languages, sharing and mutation are closely monitored through the type system. By default, all data is immutable, and therefore can be freely shared. Mutable data is encapsulated in a way that cannot be escaped. If a function does mutation, this fact is reflected in its type -- and in the type of all functions that call it.
Then there is the issue of STM (Software Transactional Memory) -- the most foolproof method of writing concurrent code. An atomic STM section of code is forbidden to perform any I/O because, if it's retried, it will perform it multiple times. In imperative languages, the burden of proof (that the atomic section does no I/O) is on the programmer. In Haskell, this behavior is forbidden by the type system: STM and IO monads don't mix. That's why STM works great in Haskell, while the attempts to introduce it in C++ have been, so far, unsuccessful.-->

---


![](img/spark-vs-hadoop.png)
$$
$$
Duration of the first and later iterations in Hadoop, HadoopBinMem and Spark for logistic regression and k-means using 100 GB of data on a 100-node cluster.
[source](https://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf)

.notes: Hadoop: The Hadoop 0.20.2 stable release. HadoopBinMem: A Hadoop deployment that converts the input data into a low-overhead binary format in the first iteration to eliminate text parsing in later ones, and stores it in an in-memory HDFS instance.

---

---

#Spark Context

At a high level, every Spark application consists of a driver that launches various parallel operations on a cluster.
$$
$$
The driver program contains your application’s main function and defines distributed datasets on the cluster, then applies operations to them.

---

Driver programs access Spark through a `SparkContext` object, which represents a connection to a computing cluster.
$$
$$
A Spark context is essentially a client of Spark’s execution environment and acts as the director of your Spark application.

---

`SparkContext` in turn requires a `SparkConf` object for configuration.
$$
$$
Refer to [Spark Configuration](http://spark.apache.org/docs/latest/configuration.html) for further discussion of how to configure Spark.

---

SparkContext offers a lot of functionality, for example:

* Creating / deleting RDDs
* Setting # of RDD partitions
* Creating accumulators
* Creating broadcast variables
* Distributing JARs to workers
* Accessing services, e.g. Task Scheduler, DAGScheduler, Listener Bus, Block Manager, Shuffle Manager.
* Closure Cleaning
* Running jobs


---

#Running jobs

All RDD actions in Spark launch jobs (that are run on one or many partitions of the RDD) using `SparkContext.runJob(rdd: RDD[T], func: Iterator[T] => U): Array[U]`.
$$
$$
Running a job is essentially executing a function on all or a subset of partitions in an RDD and returning the result as an array (with elements being the results per partition).

---

#Challenge Question

Run a job using `runJob` on the `lines` RDD with a function that returns 1 for every partition.
$$
$$
What can you say about the number of partitions of the lines RDD?



---

---

Now run the following code. Is your result different than before? Why?

    !scala
    val foo = sc.parallelize(0 to 10)
    sc.runJob(foo, (t: TaskContext, i: Iterator[Int]) => 1)

---

A job  is a top-level work item (computation) submitted to `DAGScheduler` to compute the result of an action.
$$
$$
Computing a job is equivalent to computing the partitions of the RDD the action has been executed upon.

---

#WebUI

Spark comes with Web UI (aka webUI) to inspect job executions using a browser.

Every SparkContext launches its own instance of Web UI, available at [http://[master]:4040](http://localhost:4040/) by default (the port can be changed using `spark.ui.port`).

---

It offers tabs with the following information:

* Jobs
* Stages
* Storage (with RDD size and memory use)
* Environment
* Executors
* SQL

You can view the web UI after the fact after setting spark.eventLog.enabled to true before starting the application.

---

For standalone applications you must create your own Spark context.

    !scala
    import org.apache.spark.SparkConf
    import org.apache.spark.SparkContext
    import org.apache.spark.SparkContext._
    val conf = new SparkConf().setMaster("local")
                              .setAppName("My App")
    val sc = new SparkContext(conf)


.notes: In Zeppelin, the driver program is the Spark interpreter itself.

---

The cluster URL tells Spark how to connect to a cluster. local is a special value that runs Spark on one thread on the local machine, without connecting to a cluster.
$$
$$
The application name identifies your application to the cluster manager.
$$
$$
Note that only one SparkContext may be running in a single JVM (check out [SPARK-2243 Support multiple SparkContexts in the same JVM](https://issues.apache.org/jira/browse/SPARK-2243))

---

#Building and Submitting Executables

We can build applications using Scala / JVM tools like Gradle, Maven, or `sbt`:

    !scala
    name := "hello-world"
    version := "0.0.1"
    scalaVersion := "2.10.4"
    // additional libraries
    libraryDependencies ++= Seq(
     "org.apache.spark" %% "spark-core" % "1.2.0" % "provided"
    )

.notes: We'll learn about how to do this using sbt in lab.

---

#`spark-submit`

Once you have your build defined, you can use `spark-submit` to submit it to a Spark deployment environment.
$$
$$
You'll find `spark-submit` in `bin` directory of the Spark distribution.

---

#Spark Shell

Spark shell is an interactive shell for learning about Apache Spark, ad-hoc queries and developing Spark applications.
$$
$$
It is a very convenient tool to explore Spark and one of the many reasons why Spark is so helpful even for very simple tasks.
$$
$$
There are two main variants: `spark-shell` for Scala and `pyspark` for Python.

---

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

When you execute spark-shell it executes Spark submit as follows:
$$
$$
`org.apache.spark.deploy.SparkSubmit --class org.apache.spark.repl.Main --name Spark shell spark-shell`
$$
$$
Set `SPARK_PRINT_LAUNCH_COMMAND` to see the entire command to be executed.

---

Spark shell gives you the `sc` value which is the [SparkContext](http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.SparkContext) for the session.

    scala> sc
    res0: spark.SparkContext = spark.SparkContext@2ac0cb64

---

You can query for the values of Spark properties in Spark shell as follows:

    scala> sc.getConf.getOption("spark.local.dir")
    res1: Option[String] = None
    scala> sc.getConf.getOption("spark.app.name")
    res2: Option[String] = Some(Spark shell)


---

There is also an `sqlContext` object to use with Spark SQL.


    scala> sqlContext
    res1: spark.sql.SQLContext = spark.sql.hive.HiveContext@60ae950f

---

To close Spark shell, you press Ctrl+D or type in `:q` (or any subset of `:quit`).

    scala> :quit

---


#Lecture 2: RDD's


---

#RDD - Resilient Distributed Dataset

Resilient Distributed Dataset (RDD) is the primary data abstraction in Spark. It is a distributed collection of items.

The original paper is a great read: [https://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf](https://www.cs.berkeley.edu/~matei/papers/2012/nsdi_spark.pdf)

---

You use a Spark context to create RDDs.
$$
$$
When an RDD is created, it belongs to and is completely owned by the Spark context it originated from. RDDs can’t by design be shared between SparkContexts.

---
At a high level, any Spark application creates RDDs out of some input, run (lazy) transformations of these RDDs to some other form (shape), and finally perform actions to collect or store data.

---

#Transformations

    !scala
    def map[U]( f:(T) => U ) : RDD[U]
    def flatMap[U]( f:(T) => Seq[U] ) : RDD[U]
    def filter( f:(T) => Boolean ) : RDD[T]
    def keyBy[K]( f:(T) => K ) : RDD[(K,T)]
    def groupBy[K]( f:(T) => K ) : RDD[(K,Seq[T])]
    def sortBy[K]( f:(T) => K ) : RDD[T]
    def distinct( ) : RDD[T]
    def intersection( rdd:RDD[T] ) : RDD[T]
    def subtract( rdd:RDD[T] ) : RDD[T]
    def union( rdd:RDD[T] ) : RDD[T]
    def cartesian[U]( rdd:RDD[U] ) : RDD[(T,U)]
    def zip[U]( rdd:RDD[U] ) : RDD[(T,U))
    def sample( r:Boolean, f:Double, s:Long ): RDD[T]
    def pipe(command: String): RDD[String]

---

#Actions

    !scala
    // Trigger execution of DAG.
    def foreach( f:(T) => Unit ) : Unit    
    def reduce( f:(T,T) => T ) : T
    def fold(z:T)( f:(T,T) => T ) : T
    def min() : T
    def max() : T
    def first() : T
    def count() : Long
    def countByKey() : Map[K,Long]
    def collect( ) : Array[T]
    def top( n:Int ) : Array[T]
    def take( n:Int ) : Array[T]
    def takeOrdered( n:Int ) : Array[T]
    def takeSample( r:Boolean, n:Int, s:Long ) : Array[T]
    def aggregate[U](z: U)(seq: (U, T) => U, comb: (U, U) => U): U

---

As we mentioned last time, RDD actions in Spark launch jobs.
$$
$$
The number of partitions in a job depends on the type of a stage - `ResultStage` or `ShuffleMapStage`.
$$
$$
A job starts with a single target RDD, but can ultimately include other RDDs that are all part of the target RDD’s lineage graph.

---
