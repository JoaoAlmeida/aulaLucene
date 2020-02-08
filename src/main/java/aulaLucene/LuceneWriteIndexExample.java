package aulaLucene;

import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class LuceneWriteIndexExample {
	
	private static final String INDEX_DIR = "D:\\Documents\\GitHub\\aulaLucene\\index";
	 
    public static void main(String[] args) throws Exception 
    {
    	IndexWriter writer = createWriter();
        
        List<Document> documents = new ArrayList<Document>();
         
        Document document1 = createDocument(1, "Lokesh", "Gupta", "howtodoinjava.com");
        documents.add(document1);
         
        Document document2 = createDocument(2, "Brian", "Schultz", "example.com");
        documents.add(document2);
         
        //Let's clean everything first
        writer.deleteAll();
         
        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }
 
    private static Document createDocument(Integer id, String firstName, String lastName, String website) 
    {
        Document document = new Document();
        document.add(new StringField("id", id.toString() , Store.YES));
        document.add(new TextField("firstName", firstName , Store.YES));
        document.add(new TextField("lastName", lastName , Store.YES));
        document.add(new TextField("website", website , Store.YES));
        return document;
    }
 
    private static IndexWriter createWriter() throws IOException 
    {
        FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }
}