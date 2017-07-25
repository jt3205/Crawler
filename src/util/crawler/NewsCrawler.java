package util.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsCrawler {
	private Elements newsItem;
	public Elements getDataSet(String url, String selector){
		try {
			Document doc = Jsoup.connect(url).get();
			this.newsItem = doc.select(selector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] makeATage(String title, String href, String date){
		String[] aTags = new String[newsItem.size()];
		for (int i = 0; i < newsItem.size(); i++) {
			Element item = newsItem.get(i);
			String content = item.select(title).text();
			String link = item.select(href).text();
			String pubDate = item.select(date).text();
			aTags[i] = "<a href='" + link + "'>" + content + "[" + pubDate+"]</a>";
		}
		return aTags;
	}
}
