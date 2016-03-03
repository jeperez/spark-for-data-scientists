package com.datascience.example

object Example {

lazy val segmenter = epic.preprocess.MLSentenceSegmenter.bundled().get
lazy val tagger = epic.models.PosTagSelector.loadTagger("en").get
lazy val tokenizer = new epic.preprocess.TreebankTokenizer()
        def getSentences(paragraph: String) = {
                segmenter(paragraph).map(tokenizer).toIndexedSeq
	}
	def getPartsOfSpeech(indexedSentence: IndexedSeq[String]) = {
		tagger.bestSequence(indexedSentence)
	}
}
