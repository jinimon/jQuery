package jcafe;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PostProductServlet")
public class PostProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		ProductVO vo = new ProductVO();
		
		String itemNo = request.getParameter("itemNo");
		String item = request.getParameter("item");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));
		String content = request.getParameter("content");
		double likeIt = Double.parseDouble(request.getParameter("likeIt"));
		String image = request.getParameter("image");
		
		vo.setAlt(item);
		vo.setCategory(category);
		vo.setContent(content);
		vo.setImage(image);
		vo.setItem(item);
		vo.setItemNo(itemNo);
		vo.setLikeIt(likeIt);
		vo.setLink("item.jsp");
		vo.setPrice(price);
		
		dao.insertProduct(vo);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
