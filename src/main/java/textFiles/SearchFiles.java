package textFiles;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class SearchFiles {

	public static void main(String[] args) throws Exception {

		FilesIndex index = new FilesIndex();

		// Create lucene searcher. It search over a single IndexReader.
		IndexSearcher searcher = index.createSearcher();

		// Search indexed contents using search term
		TopDocs foundDocs = index.searchInContent("house", searcher);

		// Total found documents
		System.out.println("Total Results :: " + foundDocs.totalHits);

		// Let's print out the path of files which have searched term
		for (ScoreDoc sd : foundDocs.scoreDocs) {

			Document d = searcher.doc(sd.doc);

			System.out.println("Path : " + d.get("path") + ", Score : " + sd.score);
		}
	}
}
