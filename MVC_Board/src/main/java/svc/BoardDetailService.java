package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

public class BoardDetailService {

	public BoardBean getBoard(int board_num, boolean isUpdateReadcount) {
		System.out.println("BoardDetailService - getBoard()");
		BoardBean bean = null;
		
		Connection con = JdbcUtil.getConnetion();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		bean = dao.selectBoard(board_num);
		
		if(bean != null && isUpdateReadcount) {
			int updateCount = dao.updateReadcount(board_num);
			if(updateCount > 0) {
				JdbcUtil.commit(con);
				
				bean.setBoard_readcount(bean.getBoard_readcount() + 1);
			}
		}
		
		JdbcUtil.close(con);
		
		return bean;
	}

}
