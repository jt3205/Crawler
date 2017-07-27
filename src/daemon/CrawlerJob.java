package daemon;

import java.util.TimerTask;

import util.crawler.NewsCrawler;

public class CrawlerJob extends TimerTask{
	private NewsCrawler crawler;
	public CrawlerJob() {
		crawler = new NewsCrawler();
	}
	
	@Override
	public void run() {
		crawler.updateNewsTable();
	}
	
}
