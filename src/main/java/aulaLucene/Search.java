package aulaLucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Search {

	public static void main(String[] args) throws Exception {

		Index index = new Index();
		IndexSearcher searcher = index.createSearcher();

		// Search by ID
		int searchID = 1;
		TopDocs foundDocs = index.searchById(searchID, searcher);

		System.out.println("Buscado documentos que possuem id " + searchID + " ... ");

		System.out.println("Total Results :: " + foundDocs.totalHits);

		for (ScoreDoc sd : foundDocs.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println("\nAcessando o primeiro nome contido no documento " + searchID + ": "
					+ String.format(d.get("firstName")));
		}

		System.out.println(
				"\n----------------------------------------------------------------------------------------- ");

		// Search by firstName
		String firstName = "Brian";
		System.out.println("\nBuscado documentos que possuem o primeiro nome " + firstName + " ... ");
		TopDocs foundDocs2 = index.searchByFirstName(firstName, searcher);

		System.out.println("Total Results :: " + foundDocs2.totalHits);

		for (ScoreDoc sd : foundDocs2.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(
					"\nAcessando o ID do documento que possue o nome " + firstName + ": " + String.format(d.get("id")));
		}
	}
}
