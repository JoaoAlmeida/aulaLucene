package aulaLucene;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Index {

	// Index directory file path
	private static final String INDEX_DIR = "D:\\Documents\\GitHub\\aulaLucene\\index";

	// Configure and create the object that writes information in the index
	public IndexWriter createWriter() throws IOException, URISyntaxException {
		FSDirectory dir = FSDirectory.open((Paths.get(INDEX_DIR)));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(dir, config);

		return writer;
	}

	// Create a document containing ID, a Name (first and last name), and a Website
	public Document createDocument(Integer id, String firstName, String lastName, String website) {
		Document document = new Document();

		document.add(new StringField("id", id.toString(), Store.YES));
		document.add(new TextField("firstName", firstName, Store.YES));
		document.add(new TextField("lastName", lastName, Store.YES));
		document.add(new TextField("website", website, Store.YES));

		return document;
	}

	public TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
		Query firstNameQuery = qp.parse(firstName);
		TopDocs hits = searcher.search(firstNameQuery, 10);

		return hits;
	}

	public TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("id", new StandardAnalyzer());
		Query idQuery = qp.parse(id.toString());
		TopDocs hits = searcher.search(idQuery, 10);

		return hits;
	}

	public IndexSearcher createSearcher() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);

		return searcher;
	}
}
