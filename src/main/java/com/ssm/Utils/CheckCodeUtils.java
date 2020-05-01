package com.ssm.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.Pojo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
*   1.生成图片
*   2.生成验证码
*   3.校验验证码
* */
public class CheckCodeUtils {

    /*
     * 生成图片
     * */
    public static BufferedImage getImage(HttpServletRequest request) {

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0, 0, width, height);
        //产生4个随机验证码
        String checkCode = getCheckCode();

        request.getSession().setAttribute("CheckCode",checkCode);


        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        //向图片上写入验证码
        g.drawString(checkCode, 15, 25);
        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        return image;

    }


    /**
     * 产生4位随机字符串
     */
    private static String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    /*
     * 验证码校验
     *      成功：返回null
     *      失败：返回错误信息，将封装的错误Pojo->json发送回前端
     * */
    public static String checkCode(String checkCode,String check) {
        String json;
        //1.1 获取session中的验证码
//        HttpSession session = request.getSession(false);
//        String checkCode = (String) session.getAttribute("CHECKCODE");
//        session.removeAttribute("CHECKCODE");   // 保证唯一

        //1.2 获取用户填写的验证码
//        String check = request.getParameter("check");

        if (check == null || !(check.equalsIgnoreCase(checkCode))) {
            // 验证码错误，设置错误信息Pojo
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMessage("验证码输入错误，请重新输入");

            // 将Pojo转化成JSON
            ObjectMapper mapper = new ObjectMapper();
            try {
                json = mapper.writeValueAsString(info);
                return json;  // 返回
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //校验成功，返回null
        return null;
    }


}
