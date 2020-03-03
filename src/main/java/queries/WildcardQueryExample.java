package queries;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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

	 public static void main(String[] args) throws Exception 
	    {
		 
		 String indexPath = "./indexedFiles";
		 
	        //Get directory reference
	        Directory dir = FSDirectory.open(Paths.get(indexPath));
	         
	        //Index reader - an interface for accessing a point-in-time view of a lucene index
	        IndexReader reader = DirectoryReader.open(dir);
	         
	        //Create lucene searcher. It search over a single IndexReader.
	        IndexSearcher searcher = new IndexSearcher(reader);
	         
	        //analyzer with the default stop words
	        Analyzer analyzer = new StandardAnalyzer();
	         
	         
	        /**
	         * Wildcard "*" Example
	         * */
	         
	        //Create wildcard query
	        Query query = new WildcardQuery(new Term("contents", "ho*"));
	         
	        //Search the lucene documents
	        TopDocs hits = searcher.search(query, 10, Sort.INDEXORDER);
	         
	        System.out.println("Search terms found in :: " + hits.totalHits + " files");
	        
	        for (ScoreDoc sd : hits.scoreDocs) {

				Document d = searcher.doc(sd.doc);

				System.out.println("Path : " + d.get("path"));
			}
	         
	        /**
	         * Wildcard "?" Example
	         * */
	         
	        //Create wildcard query
	        query = new WildcardQuery(new Term("contents", "ho??e"));
	         
	        //Search the lucene documents
	        hits = searcher.search(query, 10, Sort.INDEXORDER);
	         
	        System.out.println("\nSearch terms found in :: " + hits.totalHits + " files");	         	       
	         
	        for (ScoreDoc sd : hits.scoreDocs) {

				Document d = searcher.doc(sd.doc);

				System.out.println("Path : " + d.get("path"));
			}
	        
	        dir.close();
	        analyzer.close();
	    }	 
}
