package com.lht.admin.controller;

import com.lht.admin.dto.MenuIndexDto;
import com.lht.admin.pojo.Article;
import com.lht.admin.pojo.Menu;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.*;
import com.lht.admin.vo.ConsoleVo;
import com.lht.annotation.SysLog;
import com.lht.security.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * @Author lht
 * @date 2021/3/20 - 19:13
 * @description:
 */
@Controller
@RequestMapping("/api")
@Api(tags = "后台管理系统-菜单路由")
public class AdminController {

    @Resource
    private IMenuService menuService;
    @Resource
    private IMessageService messageService;
    @Resource
    private IArticleService articleService;
    @Resource
    private IRedisService redisService;
    @Resource
    private INoticeService noticeService;
    @Resource
    private ILogService logService;
    @Resource
    private ICommentService commentService;
    @Resource
    private Executor executor;

    @GetMapping("/console")
    @SysLog("访问首页")
    @ApiOperation(value = "来到首页控制台")
    public String index(Model model) throws ExecutionException, InterruptedException {
        CompletableFuture<Void>[] tasks = new CompletableFuture[8];
        ConsoleVo consoleVo = new ConsoleVo();
        tasks[0] = CompletableFuture.runAsync(() -> consoleVo.setArticleCount(articleService.getCount()), executor);
        tasks[1] = CompletableFuture.runAsync(() -> consoleVo.setPu(redisService.getPvAndUv()), executor);
        tasks[2] = CompletableFuture.runAsync(() -> consoleVo.setMessageCount(messageService.getCount()), executor);
        tasks[3] = CompletableFuture.runAsync(() -> consoleVo.setViewsDateVo(redisService.halfMonthViewInfo()), executor);
        tasks[4] = CompletableFuture.runAsync(() -> consoleVo.setNotices(noticeService.recentNotice()), executor);
        tasks[5] = CompletableFuture.runAsync(() -> consoleVo.setLogs(logService.getNewestErrorLogs()), executor);
        tasks[6] = CompletableFuture.runAsync(() -> consoleVo.setComments(commentService.getNewestComments()), executor);
        tasks[7] = CompletableFuture.runAsync(() -> consoleVo.setMessages(messageService.getNewestMessages()), executor);
        // 阻塞等待所有异步任务完成
        CompletableFuture.allOf(tasks).get();
        model.addAttribute("consoleVo", consoleVo);
        return "console/console1";
    }

    @GetMapping("/index")
    @ResponseBody
    @ApiOperation(value = "通过用户id获取对应的菜单")
    @SysLog("通过用户id获取对应的菜单")
    public List<MenuIndexDto> getMenuByUserId() {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuService.getMenuByUserId(user.getUser().getUserId());
    }

}
