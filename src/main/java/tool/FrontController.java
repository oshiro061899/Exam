//package tool;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet(urlPatterns={"*.action"})
//public class FrontController extends HttpServlet {
//
//	public void doPost(
//		HttpServletRequest request, HttpServletResponse response
//	) throws ServletException, IOException {
//		PrintWriter out=response.getWriter();
//		try {
//			String path=request.getServletPath().substring(1);
//			String name=path.replace(".a", "A").replace('/', '.');
//			Action action=(Action)Class.forName(name).
//				getDeclaredConstructor().newInstance();
//			String url=action.execute(request, response);
//			request.getRequestDispatcher(url).
//				forward(request, response);
//		} catch (Exception e) {
//			e.printStackTrace(out);
//		}
//	}
//
//	public void doGet(
//		HttpServletRequest request, HttpServletResponse response
//	) throws ServletException, IOException {
//		doPost(request, response);
//	}
//}

package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// パスを取得
			String path = req.getServletPath().substring(1);
			// ファイル名を取得しクラス名に変換
			String name = path.replace(".a", "A").replace('/', '.');
			// アクションクラスのインスタンスを返却
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			// 遷移先URLを取得
			action.execute(req, res);


		} catch (Exception e) {
			e.printStackTrace();
			// エラーページへリダイレクト
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req,res);

	}
}
