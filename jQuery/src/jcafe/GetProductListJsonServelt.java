package jcafe;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

@WebServlet("/GetProductListJsonServelt")
public class GetProductListJsonServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetProductListJsonServelt() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		ProductDAO dao = new ProductDAO();
		List<ProductVO> list = dao.getProductList();
		// response.getWriter 웹 페이지에 append로 삽입
		response.getWriter().append(JSONArray.fromObject(list).toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
