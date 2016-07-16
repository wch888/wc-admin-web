package com.wc.web.controller;

import com.jpush.JPushService;
import com.wc.base.bean.News;
import com.wc.base.service.NewsService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/list")
    public String list( Model model, HttpServletRequest request)  {
//        return "redirect:/";

        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = newsService.page(new News(), pi);
        model.addAttribute("pc",pc);
        return "/news/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request,HttpServletResponse res) {
        News news = getNews(request);
        news.setCreateTime(new Date());
        Long id = newsService.add(news);
        Map json = new HashMap<String, Object>();
        json.put("type", "news");
        json.put("title", news.getTitle());
        json.put("id", id + "");
        JPushService.getInstance().sendNotification(news.getTitle(), json);
        return StageHelper.successForward("保存成功", "/news/list",res);

    }

    private News getNews(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request,"title","");
        String desc = ServletRequestUtils.getStringParameter(request,"desc","");
        String thumb = ServletRequestUtils.getStringParameter(request, "thumb", "");
        String content = ServletRequestUtils.getStringParameter(request,"content","");
        int status = ServletRequestUtils.getIntParameter(request, "status", 1);
        long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);
        News news = new News();
        news.setThumb(thumb);
        news.setDescription(desc);
        news.setTitle(title);
        news.setContent(content);
        news.setPid(pid);
        news.setStatus((short) status);
        news.setUpdateTime(new Date());
        return news;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, Model model) {
        int pid = ServletRequestUtils.getIntParameter(request, "pid", 0);
        model.addAttribute("pid", pid);
        return "/news/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if(id<=0){
            return StageHelper.failForward("参数提交错误", res);
        }
        News news = newsService.getById(id);
        request.setAttribute("bean",news);
        return "/news/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        News news = getNews(request);
        news.setId(id);
        newsService.update(news);
        return StageHelper.successForward("保存成功","/news/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        newsService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
