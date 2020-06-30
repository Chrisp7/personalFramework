package com.imooc.controller;

import com.imooc.controller.frontend.MainPageController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("request path is : " + req.getServletPath());
        System.out.println("request method is : " + req.getMethod());
        if (req.getServletPath().equals("/frontend/getmainpageinfo") && req.getMethod().equals("GET")) {
            new MainPageController().getMainPageInfo(req, resp);
        }else if(req.getServletPath().equals("/superadmin/addheadline") && req.getMethod().equals("POST")){

        }
    }
}
