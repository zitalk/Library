# 逻辑实现：

1. 实现借阅功能：
   - 实际上是在数据库的borrow表中进行操作理解不同字段含义，需特别指出state分别为0、发起借阅请求 1、管理员审批通过 2、管理员审核失败 3还书成功
   - 都是从前端传到后端对书得操作，我们都封装在BookServlet里面，定义method参数来标记对书的不同操作。
   - 首先判空操作，空的话默认是查询操作。
   - 流程控制，switch语句。
   - 书的id我们需要从前端获取到，读者id直接从session里面取，我们在登陆操作里已经把Reader对象放进了session里面，直接从session里面拿就行。至此BookServlet就写完了，向下走到Service。
   - 定义addBorrow方法。Service层拿不到数据，需要从servlet穿过来，后面的参数在读者发送借阅请求时时间可以获取，涉及不到管理员，state默认0。
   - 在实现类中完善方法，借书时间，还书时间（借书+14天）等等
   - 时间用Date获取时间，将时间利用simpleDateFormat方法格式化成字符串。
   - 还书时间利用Calendar方法的set和get转化。拿到是还书的日期。至此Service写完了，向下到Repository。
   - 原则上一个Repository对应着一个数据库表，所以我们新建BorrowRepository接口和实现类，只是进行数据库操作，定义插入函数。参数除了adminid都需要传入，替换参数时state默认0，其余都是实际传的参数。写完后向上走在service层调用，注入进BookServiceImpl。在addBorrow中调用insert方法，把参数都传进去。
   - 这里admin为null就体现出了我们用Integer的好处，可以接受为null的值。
   - 再向上到BookServlet的addBorrow情况下调用addBorrow方法，传相应的参数。
   - 完成借书的读者发送请求代码。
   - 测试：
     - 我们点击借阅第二本书之后，跳转空白，说明正常，因为后续逻辑还没写，在参数栏可以详细的看到我们的参数。到数据看有没有增加借阅记录。
     - ![image-20200602230307271](https://img.99couple.top/20200602230307.png)
     - 确实有了！
     - ![image-20200602230838043](https://img.99couple.top/20200602230838.png)
     - 