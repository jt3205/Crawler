package util.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;;

public class Crawler {
	private String url = "http://www.y-y.hs.kr/lunch.view?";

	public String getMenuData(String date) {
		try {
			// http://www.y-y.hs.kr/lunch.view?date=20170623
			Document doc = Jsoup.connect(url).data("date", date).get();
			// Document doc = Jsoup.connect(url + "date=" + date).get();
			
			Element menu = doc.select("#morning .menuName > span").first();
			return menu.text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}