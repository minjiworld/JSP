package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardReplyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardReplyProAction");
		ActionForward forward = null;
		
		try {
			String uploadPath = "upload";
			String realPath = request.getServletContext().getRealPath(uploadPath);
			System.out.println("실제 업로드 경로 : " + realPath);
			int fileSize = 1024 * 1024 * 10;
			
			MultipartRequest multi = new MultipartRequest(
					request,
					realPath,
					fileSize,
					"UTF-8",
					new DefaultFileRenamePolicy()
			);
			
			BoardBean bean = new BoardBean();
			bean.setBoard_name(multi.getParameter("board_name"));
			bean.setBoard_pass(multi.getParameter("board_pass"));
			bean.setBoard_subject(multi.getParameter("board_subject"));
			bean.setBoard_content(multi.getParameter("board_content"));
			bean.setBoard_file(multi.getOriginalFileName("board_file"));
			bean.setBoard_real_file(multi.getFilesystemName("board_file"));
//			System.out.println(bean);
			
			if(bean.getBoard_file() == null) { // 파일명이 null일 경우 널 스트링으로 교체
				bean.setBoard_file("");
				bean.setBoard_real_file("");
			}
			
//			System.out.println(multi.getOriginalFileName("board_file"));
//			Enumeration e = multi.getFileNames();
//			while(e.hasMoreElements()) {
//				String fileElement = e.nextElement().toString();
//				System.out.println(fileElement);
//				System.out.println("원본 파일명 : " + multi.getOriginalFileName(fileElement));
//				System.out.println("실제 파일명 : " + multi.getFilesystemName(fileElement));
//			}
			BoardReplyProService service = new BoardReplyProService();
			boolean isWriteSuccess = service.registReplyBoard(bean);
			
			if(!isWriteSuccess) { // 답글쓰기 실패
				
				File f = new File(realPath, bean.getBoard_real_file());
				if(f.exists()) {
					f.delete();
				}
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('답글 쓰기 실패!')");
				out.println("history.back()");
				out.println("</script>");
				
			} else { // 답글쓰기 성공
				forward = new ActionForward();
				forward.setPath("BoardList.bo?pageNum=" + multi.getParameter("pageNum"));
				forward.setRedirect(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return forward;
	}

}
