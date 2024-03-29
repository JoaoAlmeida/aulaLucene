package queries;

import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class WildcardQueryExample {

	public static void main(String[] args) throws Exception {

		String indexPath = "./indexedFiles";

		// Get directory reference
		Directory dir = FSDirectory.open(Paths.get(indexPath));

		// Index reader - an interface for accessing a point-in-time view of a lucene index
		IndexReader reader = DirectoryReader.open(dir);

		// Create lucene searcher. It search over a single IndexReader.
		IndexSearcher searcher = new IndexSearcher(reader);

		/**
		 * Wildcard "*" Example
		 */

		// Create wildcard query
		Query query = new WildcardQuery(new Term("contents", "ho*"));

		// Search the lucene documents
		TopDocs hits = searcher.search(query, 10, Sort.INDEXORDER);

		System.out.println("Search terms found in :: " + hits.totalHits + " files \n");

		for (ScoreDoc sd : hits.scoreDocs) {

			Document d = searcher.doc(sd.doc);

			System.out.println("Path : " + d.get("path"));
		}
	}
}
