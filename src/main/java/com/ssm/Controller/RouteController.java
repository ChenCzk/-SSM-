package com.ssm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.Config.SpringConfig;
import com.ssm.Dao.FavoriteMapper;
import com.ssm.Dao.RouteImgMapper;
import com.ssm.Pojo.PageRoute;
import com.ssm.Pojo.Route;
import com.ssm.Pojo.RouteImg;
import com.ssm.Pojo.User;
import com.ssm.Service.routeService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/route")
public class RouteController {

    /*
     * 分页栏
     * */
    @RequestMapping("/Cat")
    public void Cat(HttpServletRequest request, HttpServletResponse response) {

        //接收前端给定参数 cid currentPage PageSize
        String str_cid = request.getParameter("cid");
        String str_currentPage = request.getParameter("currentPage");
        String str_pageSize = request.getParameter("PageSize");

        int cid;
        int currentPage;
        int pageSize;

        /*处理数据*/
        if (str_cid != null) {
            cid = Integer.parseInt(str_cid);
        } else {
            cid = 5;//默认第五个分类
        }
        if (str_currentPage != null) {
            currentPage = Integer.parseInt(str_currentPage);
        } else {
            currentPage = 1; //默认第一页
        }
        if (str_pageSize != null) {
            pageSize = Integer.parseInt(str_pageSize);
        } else {
            pageSize = 5; // 默认一页显示5条数据
        }

        /*计算数据*/
        PageRoute pageRoute = new PageRoute();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        routeService routeService = context.getBean(routeService.class);

        // 获取总记录数
        int count = routeService.count(cid);
        pageRoute.setTotalCount(count);  //对应cid的总条数

        // 计算总页数  = 总记录数/每页显示条数 +1?
        pageRoute.setTotalPage(count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1);

        // 当前页码和显示数据
        pageRoute.setCurrentPage(currentPage);
        pageRoute.setPageSize(pageSize);

        // 当前页码数据
        List<Route> data = routeService.findRoute(cid, (currentPage - 1) * pageSize, pageSize);
        pageRoute.setList(data);

        // 将数据返回
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            String s = mapper.writeValueAsString(pageRoute);
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
     * 搜索框
     * */
    @RequestMapping("/findByName")
    public void findByName(HttpServletRequest request, HttpServletResponse response) {

        String str_currentPage = request.getParameter("currentPage");
        String str_rname = request.getParameter("rname");


        int currentPage;
        int pageSize = 5;


        if (str_currentPage != null) {
            currentPage = Integer.parseInt(str_currentPage);
        } else {
            currentPage = 1; //默认第一页
        }

        /*计算数据*/
        PageRoute pageRoute = new PageRoute();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        routeService routeService = context.getBean(routeService.class);

        // 获取总记录数
        int count = routeService.countSearch(str_rname);
        pageRoute.setTotalCount(count);  //对应rname的总条数

        // 计算总页数  = 总记录数/每页显示条数 +1?
        pageRoute.setTotalPage(count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1);

        // 当前页码和显示数据
        pageRoute.setCurrentPage(currentPage);
        pageRoute.setPageSize(pageSize);

        String a = "%" + str_rname + "%";
        // 当前页码数据
        List<Route> data = routeService.searchRoute(a, (currentPage - 1) * pageSize, pageSize);
        pageRoute.setList(data);

        // 将数据返回
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            String s = mapper.writeValueAsString(pageRoute);
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * 查看详情
     * */
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(@RequestParam("rid") int rid, @SessionAttribute("user") User user) {
        //1.根据rid去访问route表
        //2.根据rid去访问route_img表
        //3.根据sid去访问seller表
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        routeService bean = context.getBean(routeService.class);
        FavoriteMapper favoriteMapper = context.getBean(FavoriteMapper.class);
        Route routeByRid = bean.findRouteByRid(rid);

        // 判断是否收藏
        int byURId = favoriteMapper.findByURId(user.getUid(), rid);
        System.out.println("boolean:"+byURId);
        boolean flag =false;
        if (byURId==1){
            flag = true;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject(flag);
        mv.addObject(routeByRid);
        mv.setView(new MappingJackson2JsonView());

        return mv;
    }

    @RequestMapping("/click")
    public void click(@RequestParam("rid") int rid, @SessionAttribute("user") User user){
        System.out.println(rid);
        System.out.println(user);

    }

}
