package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.BoardBean;

public class BoardDAO {
	
	// 싱글톤 패턴
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	
	// DB연결
	private Connection con;
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	// 글 쓰기 작업
	public int insertBoard(BoardBean bean) {
		System.out.println("BoardDAO - insertBoard()");
		int insertCount = 0;
		
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		
		try {
			int board_num = 1; // 새 글 번호
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board_num = rs.getInt(1) + 1;
			}
			System.out.println("새 글 번호 : " + board_num);
			//-------------------------------------------------------
			sql = "INSERT INTO board VALUES(?,?,?,?,?,?,?,?,?,?,?,now())";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, board_num);
			pstmt2.setString(2, bean.getBoard_name());
			pstmt2.setString(3, bean.getBoard_pass());
			pstmt2.setString(4, bean.getBoard_subject());
			pstmt2.setString(5, bean.getBoard_content());
			pstmt2.setString(6, bean.getBoard_file());
			pstmt2.setString(7, bean.getBoard_real_file());
			pstmt2.setInt(8, board_num);
			pstmt2.setInt(9, 0);
			pstmt2.setInt(10, 0);
			pstmt2.setInt(11, 0);
			insertCount = pstmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - insertBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return insertCount; // Service
	}
	
	// 글 목록 조회
	public List<BoardBean> selectBoardList(String keyword, int startRow, int listLimit) {
		System.out.println("BoardDAO - selectBoardList()");
		List<BoardBean> boardList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board "
					+ "WHERE board_subject "
					+ "LIKE ? "
					+ "ORDER BY board_re_ref DESC, board_re_seq ASC "
					+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardBean>();
			while(rs.next()) {
				BoardBean bean = new BoardBean();
				
				bean.setBoard_num(rs.getInt("board_num"));
				bean.setBoard_name(rs.getString("board_name"));
				bean.setBoard_pass(rs.getString("board_pass"));
				bean.setBoard_subject(rs.getString("board_subject"));
				bean.setBoard_content(rs.getString("board_content"));
				bean.setBoard_file(rs.getString("board_file"));
				bean.setBoard_real_file(rs.getString("board_file"));
				bean.setBoard_re_ref(rs.getInt("board_re_ref"));
				bean.setBoard_re_lev(rs.getInt("board_re_lev"));
				bean.setBoard_re_seq(rs.getInt("board_re_seq"));
				bean.setBoard_readcount(rs.getInt("board_readcount"));
				bean.setBoard_date(rs.getTimestamp("board_date"));
				boardList.add(bean);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - selectBoardList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return boardList; // BoardListService
	}
	
	// 전체 게시물 수 조회
	public int getBoardListCount(String keyword) {
		System.out.println("BoardDAO - getBoardListCount()");
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM board WHERE board_subject LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - selectBoardList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return listCount; // BoardListService
	}

	// 게시물 1개 상세 조회
	public BoardBean selectBoard(int board_num) {
		System.out.println("BoardDAO - selectBoard()");
		BoardBean bean = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean = new BoardBean();
				bean.setBoard_num(rs.getInt("board_num"));
				bean.setBoard_name(rs.getString("board_name"));
				bean.setBoard_pass(rs.getString("board_pass"));
				bean.setBoard_subject(rs.getString("board_subject"));
				bean.setBoard_content(rs.getString("board_content"));
				bean.setBoard_file(rs.getString("board_file"));
				bean.setBoard_real_file(rs.getString("board_file"));
				bean.setBoard_re_ref(rs.getInt("board_re_ref"));
				bean.setBoard_re_lev(rs.getInt("board_re_lev"));
				bean.setBoard_re_seq(rs.getInt("board_re_seq"));
				bean.setBoard_readcount(rs.getInt("board_readcount"));
				bean.setBoard_date(rs.getTimestamp("board_date"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - selectBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return bean; // BoardDetailService
	}

	// 조회수 증가 작업
	public int updateReadcount(int board_num) {
		System.out.println("BoardDAO - updateReadcount()");
		int updateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE board SET board_readcount = board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - updateReadcount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return updateCount;
	}

	// 글 삭제 작업을 위한 가능 여부 판별
	public boolean isBoardWriter(int board_num, String board_pass) {
		System.out.println("BoardDAO - isBoardWriter()");
		boolean isBoardWriter = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=? AND board_pass=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, board_pass);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isBoardWriter = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - isBoardWriter()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return isBoardWriter;
	}

	// 글 삭제 작업
	public int deleteBoard(int board_num) {
		System.out.println("BoardDAO - deleteBoard()");
		int deleteCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
		}  catch (SQLException e) {
			System.out.println("SQL 구문 오류 - deleteBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return deleteCount;
	}

	// 글 수정 작업
	public int updateBoard(BoardBean bean) {
		System.out.println("BoardDAO - updateBoard()");
		int UpdateCount = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE board SET board_subject=?, board_content=?";
			if(bean.getBoard_file() != null) {
				sql += ", board_file=?, board_real_file=?";
			}
			sql += " WHERE board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getBoard_subject());
			pstmt.setString(2, bean.getBoard_content());
			
			if(bean.getBoard_file() != null) {
				pstmt.setString(3, bean.getBoard_file());
				pstmt.setString(4, bean.getBoard_real_file());
				pstmt.setInt(5, bean.getBoard_num());
			} else {
				pstmt.setInt(3, bean.getBoard_num());
			}
			UpdateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - updateBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return UpdateCount;
	}

	public int insertReplyBoard(BoardBean bean) {
		System.out.println("BoardDAO - insertReplyBoard()");
		int insertCount = 0;
		
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		
		try {
			int board_num = 1; // 새 글 번호
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board_num = rs.getInt(1) + 1;
			}
			System.out.println("새 답글 번호 : " + board_num);
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - insertReplyBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			// Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		return insertCount; // BoardReplyProService
	}
} // BoardDAO 클래스 끝
