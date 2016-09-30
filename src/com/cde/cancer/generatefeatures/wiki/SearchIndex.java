package com.cde.cancer.generatefeatures.wiki;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.cde.Parameter;

public class SearchIndex {
	static String path=Parameter.WikiIndexPath;
	static String userquery;
	static int required;
	public SearchIndex(String indexpath, String userquery)
	{
		this.path = indexpath;
		this.userquery = userquery;
		this.required = 50;
	}
	public SearchIndex(String userquery)
	{
		this.userquery = userquery;
		this.required = 50;
	}
	@SuppressWarnings("deprecation")
	public static void main(String [] args) throws IOException, ParseException 
	{
		SearchIndex sc=new SearchIndex("text:hki-272 AND cancer");
		sc.searching();
	}
	public String searching() throws IOException, ParseException
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		QueryParser parser = new QueryParser(Version.LUCENE_40, "text", analyzer);
		Query query = parser.parse(userquery);

		TopDocs results = searcher.search(query, 5);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		if(numTotalHits > 0)
			hits = searcher.search(query, numTotalHits).scoreDocs;
		int count=0;
		String title = "";
		if(hits.length>1)
		{
			Document doc = searcher.doc(hits[0].doc);
			title = doc.get("title");
			//System.out.println(title);
		}
		return title;

			
	}

}
