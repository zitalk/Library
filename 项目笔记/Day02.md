## 逻辑实现：

除了reader之外，我们还有admin登录，所以我们需要对这两者加以区分

### LoginServlet：

在LoginServlet中定义type变量，用以区分登录用户类型

### entity：

定义新的管理员类，并实现有参构造

### LoginSericeImpl：

- 向下走到LoginServiceImpl，我们需在接口中添加type参数，并且在实现类中修改login函数

- 在这里我们发现，有了两种类型用户，但是我们再向下走是ReaderRepository，这显然是不对的

### Repository：

- 再新定义一个AdminRepository，并且实例化它，由它去和底层数据库交互

### LoginSericeImpl：

- 再回到LoginServiceImpl，我们又发现我们之前返回的是一个Reader对象，这显然不对

- 于是我们在这里利用**多态**，可以很好的提高我们的可用性。比如，现在我们有Reader和Admin两种对象，假设我们有一百种对象，那我们要返回给LoginServlet一百种对象吗？显然这里要利用到多态，我们只需要将LoginServiceImpl的login方法定义成Object祖宗类型，再根据type去分别这个对象到底是什么类型的对象即可

- 在login方法中定义Object对象，利用switch判断，根据type赋给object不同的方法，再将object返回给LoginServlet即可

  ```java
  public class LoginServiceImpl implements LoginService {
      private ReaderRepository readerRepository = new ReaderRepositoryImpl();
      private AdminRepository adminRepository = new AdminRepositoryImpl();
      @Override
      public Object login(String username, String password,String type) {
         Object object = null;//多态
         switch (type){
             case "reader":
                 object = readerRepository.login(username, password);
                 break;
             case "admin":
                 object = adminRepository.login(username, password);
                 break;
         }
          return object;
  
      }
  }
  ```

### LoginServlet：

- 回到LoginServlet，利用switch判断type类型，进行转化再交由session去传递。

  ```java
  public class LoginServlet extends HttpServlet {
  
      private LoginService loginService = new LoginServiceImpl();//实例化接口，也就是new一个实现类
  
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
  ```

这时我们启动服务，根据不同类型的用户去登陆，是可以的。加断点，测试。

![pfNvSyQDB9Pga6x](https://img.99couple.top/20200504181854.png)

![image-20200502182944662](https://i.loli.net/2020/05/02/ncZtG8kfwsz7QLb.png)

