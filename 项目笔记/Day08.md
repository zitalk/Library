# 项目跟进

- 接下来我们实现管理员功能，首先我们在最开始写LoginServlet的时候已经预留了case管理员，所以说我们从这里入手，做一个转发到admin.jsp页面。
- 管理员需要做的事情就是查询读者的借阅信息，然后进行审核，改变state，写入数据库。跟前面的大体思路一致，我们只不过需要获取到的就是state，所以我们可以对之前写的findAllByReaderId这个方法进行复用，只需将SQL语句进行改变，这里我们查的是state，不是readerid。
- 这里我们顺便加上分页，也就是index，limit那一套，从Servlet传state和page给Service，Service通过逻辑（即对page进行计算）拿到state，index，limit（还是写死的6），传给BorrowRepository（因为还是在borrow表中查询所以还用它），Repository通过三个参数对SQL语句中的变量进行替换，再去和数据库进行交互，最终返回给Sevlet我们需要的信息。同理总页数，作重定向将参数交给前端JSP。
- 我们先在LoginServlet中调用findAllBorrowByState方法，参数传入state=0和page=1。代表着管理员登录就查询state为0的第一页。加断点测试。如图：
- ![image-20200607094822169](https://img.99couple.top/20200607094829.png)
- 拿到了6本state为0的borrow记录，即第一页。
- 但是我们当前的LoginServlet的耦合度过高了，在case管理员下，我们既要实现管理员登录，还要实现跳转到管理员首页。所以我们对他解耦合，新建一个AdminServlet。在LoginServlet实现管理员登录后重定向到AdminServlet下，在进行逻辑处理。
- 添加管理员同意，拒绝功能。原理就是用户在前端点击同意，拒绝。我们做了一个a标签链接，链接到admin的Servlet，新建方法handle。对于此方法，我们需要做到是，依据借阅记录的id，来对借阅记录进行处理，处理后数据库中的state和adminid应该进行更改，并更新。所以我们需要将参数borrowid，state，adminid传给BookService，在BookService中调用BorrowRepository的方法，并且将参数继续往下传，由BorrowRepository来和数据库进行交互，实现更新adminid和state。测试，如图：
- ![image-20200607105048971](https://img.99couple.top/20200607105049.png)
- 我们操作的几条借阅记录在数据库中的信息已经改变。
- 然后我们添加对管理员登录的过滤器，同读者的过滤器。
- 接下来我们实现管理员的还书管理功能，其实就是对对查询所有借阅记录的代码复用。同样我们一步写好分页。其实都是对已有代码，或者说已有接口的复用。因为区别仅仅就是此时state查询的是所有为1的，要来还书。Service里面不需要重新写，因为我们的区别是state，而state是作为参数向下传的，BorrowRepository也一样。从前端拿到state，统一跳转到case handle，加一个if判断来区分页面刷新到哪里（也即是重定向到哪个方法）。
- 至此完成了所有的工作，测试，一切正常使用。
- bug修复，我们在index页面的首页处跳转的链接是/book.do如果我们就是在首页会报一个404错误，只需跳转到/book?page=1即可。

