package com.ssm.Controller;

import com.ssm.Utils.CheckCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
* 验证码控制器
* */
@Controller
@RequestMapping("VCode")
public class VCodeController {




    /*
     * 生成验证码，发送给前端，同时存入session域中，以便校验。
     * */
    @RequestMapping(value = "/SetVCode")
    public void SetVCode(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 通知服务器不要缓存
        response.setHeader("prama", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        BufferedImage image = CheckCodeUtils.getImage(request);
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "PNG", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
