package cn.itcast.web;

import cn.itcast.pojo.Admin;
import cn.itcast.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    public RegisterService registerService;

    @RequestMapping("insertUser")
    public String insertUser(Admin admin){
        registerService.insertUser(admin);
        return "成功";
    }
}
