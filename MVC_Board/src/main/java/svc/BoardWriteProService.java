package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardWriteProService {

	public boolean registBoard(BoardBean bean) {
		System.out.println("BoardWriteProService - registBoard()");

		boolean isWriteSuccess = false;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		int insertCount = dao.insertBoard(bean);
		if(insertCount > 0) { // 성공
			JdbcUtil.commit(con);
			isWriteSuccess = true;
		} else { // 실패
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isWriteSuccess; // BoardWriteProAction
	}
	
}
