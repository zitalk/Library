# 逻辑实现：

## 导入页面及所需资源：

接下来就该实现到主页面显示书了，还是遵循流水线。在写代码之前我们需要将所需的主页（index.jsp）和所需的头部（top.jsp）底部（footer.jsp）还有jstl.jar与standard.jar导入进来

## entity：

首先我们先写好实体类，方便传输过程中以对象的方式传输

- Book类：将book表中的参数（id，name，author，publish，pages，price）定义出来，至于bookcaseid是书的分类，是以外键的形式存在。但是外键是MySQL中面向关系的体现方式，到Java的面向对象中不能适用，所以这时我们再定义一个**BookCase类**以对象的方式来体现。这时我们在参数后面追加BookCase类型的bookcase参数。然后get/set方法，有参构造。
- BookCase类：定义（id，name），这里的id，name是书类的id和书类名name。get/set方法，有参构造。

## service：

有了实体类我们就可以走流水线了，这时不会产生web端的操作，不需要controller层的代码，所以我们向下走到service层。

- BookService接口：在接口定义findAll函数，并且在实现类中实现它
- BookServiceImpl类：声明一个BookRepository对象，实现接口方法，并且返回bookrepository的findall方法，体现出流水线式的一级级传递

## repository：

repository和底层数据库交互，并且返回给上一级service

- BookRepository接口：定义findAll函数，并且在实现类中实现它

- BookRepositoryImpl类：

  - 实例化接口的findAll方法
  - 进行数据库一系列操作。但是这里我们用到了数据库的**内连接**，因为我们要将book表和bookcase表关联起来。
  - ![image-20200506234419974](https://img.99couple.top/20200506235156.png)
  - 因为在传递过程中是以Book传递的，但是Book中有bookcase这个字段，bookcase我们也是以对象的形式来体现，用来体现MySQL中的外键。所以我们需要将最后的两个字段组成bookcase这个对象和前六个字段拼接起来形成book对象，为了传递的时候更高效，我们再将所有的book对象装进集合List中，这样我们只需要传一个List即可。在将book加进list中时，我们不多余new book，new bookcase，而是统一在add语句中完成。这样可以**减少栈消耗**，而new对象是在堆中完成，这个没办法减少。
  - 将list返回给service

  

## 测试：

加断点测试，

![image-20200506235919912](https://img.99couple.top/20200506235921.png)

可发现repository在传给service时确实是按照我们的设计进行的。

同时，主页面也确实将书展示了出来。

![image-20200507000243382](https://img.99couple.top/20200507000245.png)

