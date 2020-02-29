package aulaLucene;

import java.util.List;
import java.util.ArrayList;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;

public class Write {
	 
    public static void main(String[] args) throws Exception 
    {
    	
    	Index index = new Index();
    	
    	IndexWriter writer = index.createWriter();
        
        List<Document> documents = new ArrayList<Document>();
         
        //id, primeiro nome, ultimo nome, website
        Document document1 = index.createDocument(1, "João Paulo", "Almeida", "howtodoinjava.com");
        documents.add(document1);
         
        Document document2 = index.createDocument(2, "Brian", "Schultz", "example.com");
        documents.add(document2);
         
        //Let's clean everything first
        writer.deleteAll();
         
        writer.addDocuments(documents);
        writer.commit();
        writer.close();
        
        System.out.println("Index Criado");
    }

}
