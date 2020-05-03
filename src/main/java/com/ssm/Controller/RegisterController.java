package com.ssm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.Config.SpringConfig;
import com.ssm.Pojo.ResultInfo;
import com.ssm.Pojo.User;
import com.ssm.Service.UserService;
import com.ssm.Utils.CheckCodeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 注册控制器
 * */
@Controller
@RequestMapping("/reg")
public class RegisterController {

    /*
     * 注册按钮
     * */
    @RequestMapping(value = "/register",produces = {"application/json;charset=utf-8"})//注明返回的是JSON信息
    @ResponseBody
    public String register(
            HttpServletRequest request,
            User user
    ) {

        String json = null;
        try {
            /* 1.验证码的校验 */
            // 检验成功返回null
            String checkcode = (String)request.getSession().getAttribute("CheckCode");
            request.getSession().removeAttribute("CheckCode");
            String check = request.getParameter("check");
            json = CheckCodeUtils.checkCode(checkcode,check);
            if (json != null) {
                return json; // 程序结束
            }

            //2.2 Spring获取bean对象，调用 数据库校验
            ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
            UserService service = context.getBean(UserService.class);
            User is = service.findByuserName(user.getUserName());//通过username查询数据库有无相同字段。

            //2.3 检测是否存在相同用户名
            if (is == null || is.getUserName() == null) {
                // 不存在相同名，存入数据库
                int i = service.setUser(user);//保存该用户信息
                // 返回注册成功信息
                ResultInfo info = new ResultInfo(true, "注册成功！", null);
                ObjectMapper mapper = new ObjectMapper();
                json = mapper.writeValueAsString(info);
                System.out.println(user.getUserName() + "注册成功");
            } else {
                // 存在相同用户名，返回给前端错误信息
                ResultInfo info = new ResultInfo(false, "用户名已经被人注册啦，请重新换一个！", null);
                ObjectMapper mapper = new ObjectMapper();
                json = mapper.writeValueAsString(info);
                System.out.println("存在相同用户名");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }


    @RequestMapping("/email")
    public void email(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService bean = context.getBean(UserService.class);
        // 根据code找到激活用户
            int i = bean.updateToStatus(code);
        if (i == 1) {
            System.out.println("激活成功");
        } else {
            System.out.println("激活失败");
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("激活成功,请<a href=\"http://localhost:8080/myTravel_war_exploded/login.html\">登陆</a>");
    }
}
