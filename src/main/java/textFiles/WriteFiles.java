package textFiles;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class WriteFiles {

	public static void main(String[] args) {
		// Input folder
		String docsPath = "./files";

		// Output folder
		String indexPath = "./indexedFiles";

		// Input Path Variable
		final Path docDir = Paths.get(docsPath);

		FilesIndex index = new FilesIndex();

		try {
			// org.apache.lucene.store.Directory instance
			Directory dir = FSDirectory.open(Paths.get(indexPath));

			// analyzer with the default stop words
			Analyzer analyzer = new StandardAnalyzer();

			// IndexWriter Configuration
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

			// IndexWriter writes new index files to the directory
			IndexWriter writer = new IndexWriter(dir, iwc);

			// Its recursive method to iterate all files and directories
			index.indexDocs(writer, docDir);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
