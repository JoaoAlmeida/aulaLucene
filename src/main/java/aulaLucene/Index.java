package aulaLucene;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class Index {

	
	public static IndexWriter createWriter() throws IOException, URISyntaxException 
	{
	    FSDirectory dir = FSDirectory.open(Paths.get(new URI("D:\\Documents\\GitHub\\aulaLucene\\index")));
	    IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
	    IndexWriter writer = new IndexWriter(dir, config);
	    
	    return writer;
	}
	
	public static Document createDocument(Integer id, String firstName, String lastName, String website) 
	{
	    Document document = new Document();
	    document.add(new StringField("id", id.toString() , Store.YES));
	    document.add(new TextField("firstName", firstName , Store.YES));
	    document.add(new TextField("lastName", lastName , Store.YES));
	    document.add(new TextField("website", website , Store.YES));
	    return document;
	}
}
