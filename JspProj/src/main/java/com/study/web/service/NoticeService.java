package com.study.web.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.study.web.entty.Notice;

public class NoticeService {
	public List<Notice> getNoticeList() {
		return getNoticeList("title", "", 1);
	}
	public List<Notice> getNoticeList(int page) {
		return getNoticeList("title", "", page);
	}
	
	public List<Notice> getNoticeList(String field, String query, int page) {
		String sql = "SELECT * FROM ("
				+ "    SELECT  ROWNUM  NUM, N.*  "
				+ "    FROM (SELECT NOTICE.* FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N "
				+ ")"
				+ "WHERE  NUM BETWEEN ? AND ?";
	    System.out.println(" notice list sql= "+ sql);	
	    
		List<Notice> list = new ArrayList<>();
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
		String username = "sa";
		String password = "";
		
		try {
			Class.forName ("org.h2.Driver");
		    Connection con = DriverManager.getConnection(jdbcURL, username, password);
//		    Statement st = con.createStatement();
		    PreparedStatement st = con.prepareStatement(sql);
		    st.setString(1, "%"+query +"%");
		    
		    st.setInt(2, 1+(page-1)*10);
		    st.setInt(3, page*10);	
		    
		    ResultSet rs = st.executeQuery();
		    
			while(rs.next()){	
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate= rs.getDate("REGDATE");
				String content= rs.getString("CONTENT");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				Notice notice = new Notice (id, title, writerId, content, regdate, hit, files);
				list.add(notice);
			}

			rs.close();
			st.close();                        		
			con.close();   
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return list;
	}	
	
	public int getNoticeCount()
	{
		return getNoticeCount("", "");
	}
	
	public int getNoticeCount(String field, String query)
	{
		int count = 0;
		int page = 1;
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
		String username = "sa";
		String password = "";
		
		String sql = "SELECT COUNT(ID) COUNT FROM ("
				+ "    SELECT  ROWNUM  NUM, N.*  "
				+ "    FROM (SELECT NOTICE.* FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N "
				+ ")";
		
	    System.out.println("sql= "+ sql);
	    
		try {
			Class.forName ("org.h2.Driver");
		    Connection con = DriverManager.getConnection(jdbcURL, username, password);
		    PreparedStatement st = con.prepareStatement(sql);
		    st.setString(1, "%"+query +"%");
 
		    ResultSet rs = st.executeQuery();
		    
		    if (rs.next())
		    {
				count = rs.getInt("count");
				System.out.println("count= "+ count);

		    }
			rs.close();
			st.close();                        		
			con.close();   
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return count;		
	}
	
	public Notice getNotice(int id) {
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
		String username = "sa";
		String password = "";
		
		try {
			Class.forName ("org.h2.Driver");
		    Connection con = DriverManager.getConnection(jdbcURL, username, password);
		    PreparedStatement st = con.prepareStatement(sql);
		    st.setInt(1, id);
		    
		    ResultSet rs = st.executeQuery();
		    
			if(rs.next()){	
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate= rs.getDate("REGDATE");
				String content= rs.getString("CONTENT");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				notice = new Notice (id, title, writerId, content, regdate, hit, files);
			}

			rs.close();
			st.close();                        		
			con.close();   
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
				
		return notice;
	}
	
	public Notice getNextNotice(int id) {
		String sql = "SELECT * FROM "
				+ "  (SELECT * FROM NOTICE "
				+ "   WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID = ?) "
				+ "   ORDER BY REGDATE)"
				+ "WHERE ROWNUM =1";
		
		Notice notice = null;
	
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
		String username = "sa";
		String password = "";
		
		try {
			Class.forName ("org.h2.Driver");
		    Connection con = DriverManager.getConnection(jdbcURL, username, password);
		    PreparedStatement st = con.prepareStatement(sql);
		    st.setInt(1, id);
		    
		    ResultSet rs = st.executeQuery();
		    
			if(rs.next()){	
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate= rs.getDate("REGDATE");
				String content= rs.getString("CONTENT");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				notice = new Notice (id, title, writerId, content, regdate, hit, files);
			}

			rs.close();
			st.close();                        		
			con.close();   
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		String sql = "SELECT * FROM "
				+ "   (SELECT * FROM NOTICE "
				+ "    WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID = ?) "
				+ "    ORDER BY REGDATE DESC)"
				+ "WHERE ROWNUM =1";

		Notice notice = null;
	
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
		String username = "sa";
		String password = "";
		
		try {
			Class.forName ("org.h2.Driver");
		    Connection con = DriverManager.getConnection(jdbcURL, username, password);
		    PreparedStatement st = con.prepareStatement(sql);
		    st.setInt(1, id);
		    
		    ResultSet rs = st.executeQuery();
		    
			if(rs.next()){	
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate= rs.getDate("REGDATE");
				String content= rs.getString("CONTENT");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				notice = new Notice (id, title, writerId, content, regdate, hit, files);
			}

			rs.close();
			st.close();                        		
			con.close();   
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return notice;
	}	
}
