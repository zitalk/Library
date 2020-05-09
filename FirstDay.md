#    MVC

是一种开发模式，将程序按流水线形式分层级。

- M层：Model，业务数据层(service，repository，entity)
- V层：View，视图层（JSP）
- C：Controller，控制层（Servlet）

##  流程：

用户在web端（这里是浏览器）操作产生业务，Servlet作为桥梁将用户的Request传给Service，Repository，进行业务数据处理后，再交给JSP，返还给用户。

Web-->Servlet-->Service-->Repository-->DB

![library流程图](https://img.99couple.top/20200504181813.jpg)

##  代码实现：

先将所需的login.jsp页面导入进来，同时将所需资源（img、js、css）也导入进来

###  定义各种类：

1. 定义Reader实体类（业务数据层）便于数据在各层之间传输以对象的方式

2. 定义LoginServlet类（控制层）重写doPost方法（jsp里用的post方法），并且添加WebServlet注解，相当于web.xml配置servlet和servlet-mapping

3. 定义LoginService接口和ReaderRepository接口（业务数据层），在接口中定义Reader类型的login方法，并且实例化他们的实现类，以接口的方式提高拓展性

4. 定义JDBCTools工具类，我们采用c3p0连接池，简化数据库操作，将c3p0-config.xml放在src根目录下，并且修改用户名和密码，定义DataSource静态对象。

   ```java
   public class JDBCTools {
       /**
        * c3p0是一个数据库连接池，是对DataSource（接口）的实现，在这里定义成静态对象
        */
       private static DataSource dataSource;
       static {
           dataSource = new ComboPooledDataSource("testc3p0");//将c3p0-config.xml的名字传进来
   
       }
   
       public static Connection getConnection(){
          Connection connection = null;
           try {
               connection = dataSource.getConnection();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
           return connection;
       }
   
       public static void release(Connection connection, Statement statement, ResultSet resultSet){
           try {
               if (connection != null){
                   connection.close();
               }
               if (statement != null){
                   statement.close();
               }
               if (resultSet != null){
                   resultSet.close();
               }
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }
   
   }
   ```
   
   
   
   

###  逻辑实现：

1. 首先在Reader类中定义私有变量，变量依据数据库reader表来定义。生成get/set方法，并且生成有参构造

2. 来到LoginServlet，获取参数，调用LoginService.login方法，并且把参数传下去

3. 进入service层，声明私有对象实现ReaderRepositoryImpl实现类，并且在service的实现类中返回并直接返回repository的login方法，以便向下一层给repository

4. repository层，已经是底层了，要和数据库进行通信，在实现类里进行数据库的经典操作，并且初始化一个Reader对象为null，便于最后如果验证成功，我们只需返回这个reader对象即可

   ```java
   public class ReaderRepositoryImpl implements ReaderRepository {
       @Override
       public Reader login(String username, String password) {
           Connection connection = JDBCTools.getConnection();
           String sql ="select * from reader where username = ? and password = ?";
           PreparedStatement statement = null;
           ResultSet resultSet = null;
           Reader reader = null;
   
           try {
               statement =connection.prepareStatement(sql);
               statement.setString(1,username);
               statement.setString(2,password);
               resultSet = statement.executeQuery();
               if (resultSet.next()){
                   reader = new           Reader(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7));
               }
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           } finally {
               JDBCTools.release(connection,statement,resultSet);
   
           }
   
           return reader;
       }
   }
   ```

5. 来到LoginServlet，判断返回来的reader对象是否为空，空就重定向到登录界面，否则就创建session进行下一步。



