package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

// db 작업 준비 및 해제(자원반환) 작업을 공통으로 수행할 클래스 정의
public class JdbcUtil {
	// 파라미터 : X, 리턴타입 : Connection(con)
	public static Connection getConnetion() {
		Connection con = null;
		
		try {
			Context initCtx = new InitialContext();
	//		Context envCtx = (Context)initCtx.lookup("java:comp/env");
	//		DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			con = ds.getConnection();
			con.setAutoCommit(false);
			
			BasicDataSource bds = (BasicDataSource)ds;
//			System.out.println("MaxTotal : " + bds.getMaxTotal()); // 최대 커넥션 수
//			System.out.println("Active : " + bds.getNumActive()); // 현재 사용 중인 커넥션 수
//			System.out.println("Idle : " + bds.getNumIdle()); // 유휴 상태 커넥션 수

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void commit(Connection con) { // 커밋작업
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection con) { // 롤백작업
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
