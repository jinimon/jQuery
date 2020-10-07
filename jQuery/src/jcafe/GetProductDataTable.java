package jcafe;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/GetProductDataTable")
public class GetProductDataTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetProductDataTable() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		ProductDAO dao = new ProductDAO();
		List<ProductVO> list = dao.getProductList();
		// {"data":[{}, {}, {}]}
		
		JSONArray ary = new JSONArray();
		for(ProductVO vo : list) {
			ary.add(vo);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("data", ary);
		
		response.getWriter().append(obj.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
