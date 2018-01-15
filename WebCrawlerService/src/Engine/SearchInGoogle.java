package Engine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchInGoogle {
	
	private static int NUMBER_OF_SEARCH = 2; //what number of the results to count as a relevant topic
	
	public static String[][] searchTopics(List<String> list) throws UnsupportedEncodingException, IOException{
		String google = "http://www.google.com/search?q=";
		String charset = "UTF-8";
		String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
		String[][] res = new String[2][list.size()];
		
		for (int i = 0; i < list.size(); ++i){
			
			String search = list.get(NUMBER_OF_SEARCH);
			Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");

			Element link = links.get(2);
			if (link != null) {
				String title = link.text();
				String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
				url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

				if (!url.startsWith("http")) {
					continue; // Ads/news/etc.
				}

				System.out.println("Title: " + title);
				res[0][i] = title;
				System.out.println("URL: " + url);
				res[1][i] = url;
			}
		}
		return res;
	}
	
//	public static void main(String args[]){
//		ArrayList<String> search = new ArrayList<String>();
//		search.add("http://www.ynet.co.il");
//		try {
//			SearchInGoogle.searchTopics(search);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
