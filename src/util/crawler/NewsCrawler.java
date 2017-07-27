package util.crawler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import domain.NewsVO;
import domain.RecordVO;
import jdbc_connection.JdbcUtil;;

public class NewsCrawler {
	private Elements newsItem;

	public Elements getDataSet(String url, String selector) {
		try {
			Document doc = Jsoup.connect(url).get();
			this.newsItem = doc.select(selector);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String[] makeATags(String title, String href, String date) {
		String[] aTags = new String[newsItem.size()];
		for (int i = 0; i < newsItem.size(); i++) {
			Element item = newsItem.get(i);
			String content = item.select("title").text();
			String link = item.select("href").text();
			String pubDate = item.select(date).text();
			aTags[i] = "<a href='" + link + "'>" + content + "[" + pubDate + "]</a>";
		}
		return aTags;
	}

	public int updateNewsTable() {
		Connection conn = JdbcUtil.getConnection();
		int cnt = 0;
		if (conn == null) {
			return -1;
		} // 연결 실패로 실행이 불가능
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet insertKeyRs = null;
		try {
			pstmt = conn.prepareStatement("select * from record_log order by time DESC limit 0, 1");// 가장
																									// 최근
																									// 입력된
																									// 기록
																									// 가져옴
			rs = pstmt.executeQuery();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			// 서버 시스템의 현재시간
			if (rs.next()) {
				Timestamp lastInsert = rs.getTimestamp("time");
				// 마지막으로 기록된 시간 알아냄
				long delta = timestamp.getTime() - lastInsert.getTime();

				if (delta < 1000 * 60 * 10) {
					return cnt;// 최근 10분안에 새 기사를 가져왔다면 보류
				}
			}
			// 그렇지 않다면 새로 가져온다
			// 기록 테이블에 현재시간 기록
			pstmt.close();
			pstmt = conn.prepareStatement("insert into record_log(time) values(?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setTimestamp(1, timestamp);
			pstmt.executeUpdate();
			insertKeyRs = pstmt.getGeneratedKeys();
			// 기록된 AI 키값을 가져와서 해당 값을 지금 넣는 기사에 모두 입력
			int timeId;
			if (insertKeyRs.next()) {
				timeId = insertKeyRs.getInt(1);
			} else {
				return -1;
			}
			getDataSet("http://fs.jtbc.joins.com//RSS/newsflash.xml", "item");// 뉴스
																				// RSS에서
																				// Item
																				// 갱신

			for (int i = 0; i < newsItem.size(); i++) {
				Element item = newsItem.get(i);
				String title = item.select("title").text();
				String link = item.select("link").text();
				String pubDate = item.select("pubDate").text();
				cnt += insertNews(timeId, title, link, pubDate, conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (insertKeyRs != null) {
				try {
					insertKeyRs.close();
				} catch (Exception ex) {
				}
			}
		}
		return cnt;// 입력된 결과 갯수 반환
	}

	private int insertNews(int id, String title, String href, String date, Connection conn) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into news(id,link,title,pubDate) values(?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, href);
			pstmt.setString(3, title);
			pstmt.setString(4, date);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			// 이미 주소가 있는 것은 키 무결성 검사에서 걸려서 예외처리
			return 0;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			}
		}
		return 1;
	}

	// 레코드들을 가져오는 함수
	public List<RecordVO> getRecordList() {
		List<RecordVO> records = null;
		Connection conn = JdbcUtil.getConnection();
		if (conn == null) {
			return null; // 연결 실패로 실행불가.
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			records = new ArrayList<RecordVO>();
			pstmt = conn.prepareStatement("SELECT l.id, l.time, COUNT(*) FROM record_log l, news n WHERE l.id = n.id GROUP BY n.id ORDER BY id DESC;");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RecordVO temp = makeRecordFromResultSet(rs);
				records.add(temp);
			}
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
		}
	}

	// 결과 DB셋을 Java빈 객체로 변화시킴.
	private RecordVO makeRecordFromResultSet(ResultSet rs) throws SQLException {
		RecordVO temp = new RecordVO();
		temp.setId(rs.getInt("id"));
		temp.setTime(rs.getTimestamp("time"));
		temp.setCnt(rs.getInt(3));
		return temp;
	}

	// 해당 시간대에 특보를 가져오는 함수.
	public List<NewsVO> getNewsList(int id) {
		List<NewsVO> news = null;

		Connection conn = JdbcUtil.getConnection();
		if (conn == null) {
			return null; // 연결 실패로 실행불가.
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			news = new ArrayList<NewsVO>();
			pstmt = conn.prepareStatement("SELECT * FROM news WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NewsVO temp = makeNewsFromResultSet(rs);
				news.add(temp);
			}
			return news;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
		}

	}

	// 결과 DB셋을 NewsVO 객체로 만들어줌.
	private NewsVO makeNewsFromResultSet(ResultSet rs) throws SQLException {
		NewsVO temp = new NewsVO();
		temp.setId(rs.getInt("id"));
		temp.setLink(rs.getString("link"));
		temp.setTitle(rs.getString("title"));
		temp.setPubDate(rs.getString("pubDate"));
		return temp;
	}

}
