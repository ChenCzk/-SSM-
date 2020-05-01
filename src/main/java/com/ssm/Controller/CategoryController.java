package com.ssm.Controller;

import com.ssm.Config.SpringConfig;
import com.ssm.Pojo.Category;
import com.ssm.Service.CategoryService;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @RequestMapping(value = "/getCategory")
    @ResponseBody
    public ModelAndView getCategory(){
        ModelAndView mv = new ModelAndView();
        ApplicationContext context =new AnnotationConfigApplicationContext(SpringConfig.class);
        CategoryService categoryService = context.getBean(CategoryService.class);
        List<Category> categories = categoryService.category_list();
        mv.addObject(categories);
        mv.setView(new MappingJackson2JsonView());
        return mv;

    }
}
