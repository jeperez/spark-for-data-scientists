import com.datascience.example.Example
val poems = List("See Spot.  See Spot run.  Run Spot run.", "The little dog laughed.  To see such a sport. And the dish ran away with the spoon")
sentences = poems.flatMap(Example.getSentences)
val posTags = sentences.map(Example.getPartsOfSpeech)
print posTags
