package com.controller;

import com.entity.Admin;
import com.entity.Borrow;
import com.service.BookService;
import com.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 从前端传过来有关管理员的请求交由AdminServlet处理
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    //实现对书的service的实例化
    private BookService bookService = new BookServiceImpl();

    /**
     * 管理员对借阅请求的处理
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");//获取method，对管理员操作进行区分
        if (method == null) {
            method = "findAllBorrow";//默认查询所有借阅记录
        }
        //为了从session中取到管理员
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        switch (method) {
            case "findAllBorrow"://查询所有借阅记录
                String pageStr = req.getParameter("page");
                Integer page = Integer.parseInt(pageStr);
                List<Borrow> borrowList = bookService.findAllBorrowByState(0, page);
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getBorrowPagesByState(0));
                req.getRequestDispatcher("admin.jsp").forward(req, resp);
                break;
            case "handle"://对借阅请求进行处理
                String idStr = req.getParameter("id");//借阅记录的id
                String stateStr = req.getParameter("state");
                Integer id = Integer.parseInt(idStr);
                Integer state = Integer.parseInt(stateStr);
                bookService.handleBorrow(id, state, admin.getId());
                //区分借书审核和归还管理
                if (state == 1 || state == 2) {
                    resp.sendRedirect("/admin?page=1");
                }
                if (state == 3) {
                    resp.sendRedirect("/admin?method=getBorrowed&page=1");
                }
                break;
            case "getBorrowed"://图书归还处理
                pageStr = req.getParameter("page");
                page = Integer.parseInt(pageStr);
                borrowList = bookService.findAllBorrowByState(1, page);
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage", 6);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getBorrowPagesByState(1));
                req.getRequestDispatcher("return.jsp").forward(req, resp);
                break;
        }
    }
}
