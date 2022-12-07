package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardModifyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardModifyProAction");
		ActionForward forward = null;
		String realPath = "";
		String deleteFileName = "";
		
		try {
			String uploadPath = "upload";
			realPath = request.getServletContext().getRealPath(uploadPath);
//			System.out.println("실제 업로드 경로 : " + realPath);
			
			File f = new File("realPath");
			if(!f.exists()) {
				f.mkdir();
			}
			
			int fileSize = 1024 * 1024 * 10;
			
			MultipartRequest multi = new MultipartRequest(
					request,
					realPath,
					fileSize,
					"UTF-8",
					new DefaultFileRenamePolicy()
			);
			
			BoardBean bean = new BoardBean();
			bean.setBoard_num(Integer.parseInt(multi.getParameter("board_num")));
			bean.setBoard_name(multi.getParameter("board_name"));
			bean.setBoard_pass(multi.getParameter("board_pass"));
			bean.setBoard_subject(multi.getParameter("board_subject"));
			bean.setBoard_content(multi.getParameter("board_content"));
			bean.setBoard_file(multi.getOriginalFileName("board_file"));
			bean.setBoard_real_file(multi.getFilesystemName("board_file"));
//			System.out.println(bean);
			
			BoardModifyProService service = new BoardModifyProService();
			boolean isBoardWriter = service.isBoardWriter(bean);
			
			if(!isBoardWriter) { // 수정 권한 없음
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 권한 없음!')");
				out.println("history.back()");
				out.println("</script>");
				deleteFileName = bean.getBoard_real_file();
						
			} else { // 수정 권한 있음
				boolean isModifySuccess = service.modifyBoard(bean);
			
				if(!isModifySuccess) { // 수정 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('수정 실패!')");
					out.println("history.back()");
					out.println("</script>");
					deleteFileName = bean.getBoard_real_file();
					
				} else { // 수정 성공
					forward = new ActionForward();
					forward.setPath("BoardDetail.bo?board_num="+ bean.getBoard_num() + "&pageNum=" + multi.getParameter("pageNum"));
					forward.setRedirect(true);
					
					if(bean.getBoard_file() != null) {
					deleteFileName = multi.getParameter("board_real_file");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File f = new File(realPath, deleteFileName);
			if(f.exists()) {
				f.delete();
			}
		}
		return forward;
	}

}
