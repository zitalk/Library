package com.controller;

import com.entity.Book;
import com.entity.Reader;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();

    /**
     * 加载图书数据
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");//对图书操作的方法区分
        if (method == null) {
            method = "findAll";//不传值默认查书
        }
        //流程控制
        switch (method) {
            case "findAll":
                String pageStr = req.getParameter("page");//记录页数
                Integer page = Integer.parseInt(pageStr);
                List<Book> list = bookService.findAll(page);//所有书的列表
                req.setAttribute("list", list);
                req.setAttribute("dataPrePage", 6);//每页多少条数据
                req.setAttribute("currentPage", page);//当前页数
                req.setAttribute("pages", bookService.getPages());//总页数
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            case "addBorrow":

                String bookidStr = req.getParameter("bookid");//从前端拿bookid
                Integer bookid = Integer.parseInt(bookidStr);//转型
                HttpSession session = req.getSession();
                Reader reader = (Reader) session.getAttribute("reader");
                bookService.addBorrow(bookid,reader.getId());
                break;
        }


    }
}
