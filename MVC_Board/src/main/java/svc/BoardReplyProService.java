package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardReplyProService {

	public boolean registReplyBoard(BoardBean bean) {
		System.out.println("BoardReplyProService - registReplyBoard()");
		
		boolean isWriteSuccess = false;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		int insertCount = dao.insertReplyBoard(bean);
		if(insertCount > 0) { // 성공
			JdbcUtil.commit(con);
			isWriteSuccess = true;
		} else { // 실패
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isWriteSuccess; // BoardReplyProAction
	}

}
