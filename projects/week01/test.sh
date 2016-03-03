#!/bin/sh
exec scala -J-Xmx2G -classpath epic_wrapper_assembly/target/scala-2.10/Example-assembly-1.0.jar "$0" "$@"
!#
object TestExample extends App {
import com.datascience.example.Example
val poems = List("See Spot.  See Spot run.  Run Spot run.", "The little dog laughed.  To see such a sport. And the dish ran away with the spoon")
val sentences = poems.flatMap(Example.getSentences)
val posTags = sentences.map(Example.getPartsOfSpeech)
print posTags

}


TestExample.main(args)
