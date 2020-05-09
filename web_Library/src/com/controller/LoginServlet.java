package com.controller;

import com.entity.Admin;
import com.entity.Book;
import com.entity.Reader;
import com.service.BookService;
import com.service.LoginService;
import com.service.impl.BookServiceImpl;
import com.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")//注解，与注释不同，简化了web.xml配置。
/**
 * MVC中的C层，主要起控制作用，接受请求，进行业务逻辑处理，再将结果返回去
 * Web应用-->Controller-->Service-->Repository-->DB
 */

public class LoginServlet extends HttpServlet {

    private LoginService loginService = new LoginServiceImpl();//实例化接口，也就是new一个实现类
    private BookService bookService = new BookServiceImpl();

    /**
     * 重写post方法，来处理login.jsp的post的业务逻辑
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");//通过键值对的形式去获取用户的参数，key是name属性，value是id属性
        String password = req.getParameter("password");
        String type = req.getParameter("type");//根据type去区分是读者还是管理员
        Object object = loginService.login(username, password, type);
        if (object != null) {
            HttpSession session = req.getSession();

            switch (type) {
                case "reader":
                    Reader reader = (Reader) object;
                    session.setAttribute("reader", object);
                    //跳转到读者界面
                    List<Book> list = bookService.findAll();
                    req.setAttribute("list",list);
                    req.getRequestDispatcher("index.jsp").forward(req,resp);
                    int i =0;
                    break;
                case "admin":
                    Admin admin = (Admin) object;
                    session.setAttribute("admin", object);
                    //跳转到管理员界面
                    break;
            }

        } else {
            resp.sendRedirect("login.jsp");
        }
    }


}
