package textFiles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class FilesIndex {

	// Input folder
	String docsPath = "./files";

	// Output folder
	String indexPath = "./indexedFiles";

	// Input Path Variable
	final Path docDir = Paths.get(docsPath);

	public void indexDocs(final IndexWriter writer) throws IOException {

		// Directory?
		if (Files.isDirectory(docDir)) {
			// Iterate directory
			Files.walkFileTree(docDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					try {
						// Index this file
						indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			// Index this file
			indexDoc(writer, docDir, Files.getLastModifiedTime(docDir).toMillis());
		}
	}

	public void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {

		try {

			// Create lucene Document
			Document doc = new Document();

			doc.add(new StringField("path", file.toString(), Field.Store.YES));
			doc.add(new LongPoint("modified", lastModified));
			doc.add(new TextField("contents", new String(Files.readAllBytes(file)), Store.YES));

			// Updates a document by first deleting the document(s)
			// containing <code>term</code> and then adding the new
			// document. The delete and then add are atomic as seen
			// by a reader on the same index
			writer.updateDocument(new Term("path", file.toString()), doc);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws Exception {
		// Create search query
		QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
		Query query = qp.parse(textToFind);

		// search the index
		TopDocs hits = searcher.search(query, 10);
		return hits;
	}

	public IndexSearcher createSearcher() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(indexPath));

		// It is an interface for accessing a point-in-time view of a lucene index
		IndexReader reader = DirectoryReader.open(dir);

		// Index searcher
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}
}
