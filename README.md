基于SSM的旅游网设计
=
简要介绍这个项目
-
    这个项目的是要使用的SSM框架开发一个旅游网，在开发中主要负责用户注册登陆，旅游路线展示（分类分页显示），搜索路线功能等开发。 
    这个项目主要技术有SSM，MySQL，Redis，JavaScript，JQury，Ajax，HTML。
--------------------------------------------------------------------------------
用户注册登陆模块
-
    用户注册：使用JavaScript检测每个输入框的要求格式，使用Ajax发送表单数据给对应的Servlet，
            后端校验验证码，然后去数据库获取是与否有相同的信息（比如账号重复等）。
            如果注册不通过，则发送对应的错误信息JSON给前端。
            如果成功，将用户注册信息录入数据库，然后前端跳转到注册成功页面。
    
   
    用户登陆：也是使用JavaScript检测每个输入框的要求格式，使用Ajax发送表单数据给对应的Servlet，
            后端校验验证码，去数据库通过账号获取该用户信息，校验密码等。
            如果校验不通过，则发送对应的错误信息JSON给前端。
            如果登陆成功，跳转到主页面，同时记录用户登陆状态。
--------------------------------------------------------------------------------
分类
-
        分类栏：主要使用MySQL，Redis获取分类。服务器刚开启，后端访问数据库，获取各种分类，然后存放在Redis中，第二次访问服务器，直接从redis返回对应分类信息。
        分类内容：服务器根据用户点击的分类，分页显示对应的内容，后端使用分类id，limit内容等去数据库拿相关分类内容，返回该内容给前端。


--------------------------------------------------------------------------------
三层架构
-
    1.Web层：
        Servlet:前端控制器
        html:视图
        Filter:过滤器
        BeanUtils:数据封装
        Jackson: json序列化
    2.Service层：
        javamail：java发送工具。
        Redis：nosql内存数据库。
        Jedis：java的redis客户端。
    3.Dao层：
        MySQL：数据库
        Druis：数据库连接池
        Spring-Mybatis：jdbc
--------------------------------------------------------------------------------
1.JavaScript校验
-
    校验：获取输入框内容，编写正则表达式，进行校验。
    触发：在输入框失去光标时校验
2.Ajax提交
-
    异步请求技术，可使用JavaScript向服务器发送请求并处理响应，不需要重新加载整个页面，而不堵塞用户。
        $.post("Servlet",数据,function (返回值) {返回值逻辑处理})
    数据：
    form.serialize 表单序列化->id=xx&name=xx...
        JSON.stringify(str)->JSON格式的数据

3.JSON格式数据传输
-
    1.JSONObject类：将字符串与JSON类互转。
    2.Jackson(ObjectMapper类)：可以将类写进JSON文件，反之，还可以使用注解
    3.SpringMVC：@ResponseBody：响应内容为JSON格式，@RequestBody：接收参数为JSON格式的数据。
    4.response.setContentType("application/json;charset=utf-8");
      response.getWriter().write(json);

4.页面跳转？重定向？请求转发？
-
    1.前端AJax返回值进行页面跳转 
      window.location.href = "xx.html";
    2.后端执行跳转：
      response.sendRedirect("url")  //重定向
      request.getRequestDispatcher("/servlet").forword(req,resp); //请求转发
    3.SpringMVC：
      String："redirect:./StringInfo.do"   //重定向  
    "forward:/WEB_INF/pages/success.jsp" //请求转发
      ModelAndView：mv.setViewName("redirect:./StringInfo.do"); //重定向 
    5.记录用户状态session
    由于HTTP是无状态的，要记录是否是同一个用户，要使用Session来记录。
    当浏览器第一次访问服务器，服务器通过getSession()生成一个session对象。
    服务器中的session列表(map集合)的key用算法得到JsessionId，value是session的地址
    然后服务器发送一个cookie(jsessionId,JsessionId)回客户端，在浏览器发送同类请求时，
    服务器通过这个JsessionId(key)去session列表中找到对应的session对象，
    使这两次请求使用的是同一个session对象，这使session的对象里的数据可以给不同请求共享。
6.SSM框架
-
    本项目使用的是大部分注解形式来完成配置，Web，Spring，SpringMVC，MyBatis配置文件都是使用类注解配置的	