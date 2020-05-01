package com.ssm.Controller;

import com.ssm.Config.SpringConfig;
import com.ssm.Pojo.ResultInfo;
import com.ssm.Pojo.User;
import com.ssm.Service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * 登陆控制器
 * */
@Controller
@RequestMapping("/login")
@SessionAttributes(types = User.class)
public class LoginController {

    /**
     * 账号登陆验证
     */
    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView login(
            @SessionAttribute String CheckCode, //获取Session中的CheckCode
            @RequestParam("check") String check,     // 获取request中的check
            @RequestParam("password") String password,  // 获取前端的password
            @RequestParam("username") String username   // 获取前端的username
    ) {
        /* 1.验证码的校验*/
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        ModelAndView mv = new ModelAndView();
        if (check == null || !(check.equalsIgnoreCase(CheckCode))) {
            // 验证码错误，设置错误信息Pojo
            info.setFlag(false);
            info.setErrorMessage("验证码输入错误，请重新输入");
            mv.addObject(info);
            mv.setView(new MappingJackson2JsonView());
            return mv;
        }

        /*2.密码的校验*/
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService service = context.getBean(UserService.class);
        User user = service.findByuserName(username);
        if (user == null) {
            // 无效用户名
            info.setFlag(false);
            info.setErrorMessage("不存在该用户！");
            System.out.println("不存在该用户！");
            mv.addObject(info);
            mv.setView(new MappingJackson2JsonView());
            return mv;
        }
        if (user.getPassword().equals(password) || password == user.getPassword()) {
            //密码校验成功
            /* 3.邮箱激活的校验*/
            if (user.getStatus() == "Y" || user.getStatus().equals("Y")) {
                // 已激活
                // 登陆成功
                info.setFlag(true);
                info.setErrorMessage("登陆成功！");
                System.out.println("登陆成功");

                // 将该用户信息放入session域
                mv.addObject("user", user);
//                request.getSession().setAttribute("user", user);

                mv.addObject(info);
                mv.setView(new MappingJackson2JsonView());
                return mv;
            } else {
                // 未激活
                info.setFlag(false);
                info.setErrorMessage("该用户还没经过邮箱激活，请去注册时的邮箱激活！");

                System.out.println("该用户还没经过邮箱激活");
                mv.addObject(info);
                mv.setView(new MappingJackson2JsonView());
                return mv;
            }
        } else {
            //密码错误
            info.setFlag(false);
            info.setErrorMessage("密码错误");
            System.out.println("密码错误");
            mv.addObject(info);
            mv.setView(new MappingJackson2JsonView());
            return mv;
        }
    }

    /**
     * 获取session中的用户信息
     */
    @RequestMapping(value = "/findUser", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ModelAndView findUser(@SessionAttribute("user") User user) {
        ModelAndView mv = new ModelAndView();
        mv.addObject(user);
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    @RequestMapping(value = "exitUser")
    public String exitUser(HttpServletRequest request, SessionStatus sessionStatus) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        sessionStatus.setComplete();  //针对@SessionAttribute问题
        return "redirect:../login.html";
    }
}
