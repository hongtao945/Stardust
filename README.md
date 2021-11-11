  <h1 align="center">
    Stardust Blog
  </h1>
  <h4 align="center">
    基于SpringBoot、Layui和Vue的博客系统
  </h4> 

<p align="center">
    <a href="#">
        <img src="https://img.shields.io/badge/JDK-8-green.svg">
    </a> 
    <a href="#">
        <img src="https://img.shields.io/badge/Spring Boot-2.3.0+-green.svg" alt="Jquery Version">
    </a>
    <a href="#">
        <img src="https://img.shields.io/badge/Pear Admin Layui-3.1.0+-green.svg" alt="Pear Admin Layui Version">
    </a>    
    <a href="#">
        <img src="https://img.shields.io/badge/Layui-2.5.6+-green.svg" alt="Layui Version">
    </a>
	</br>
    <a href="#">
        <img src="https://img.shields.io/badge/Vue-3.X-green.svg">
    </a> 
    <a href="#">
        <img src="https://img.shields.io/badge/Vite-2.X-green.svg">
    </a> 
    <a href="#">
        <img src="https://img.shields.io/badge/Typescript-4.4.X-green.svg">
    </a> 
	<a href="#">
        <img src="https://img.shields.io/badge/Matery-green.svg">
    </a> 
</p>

### 项目概览

Stardust是一个博客系统，后台基于Springboot + [PearAdmin](https://gitee.com/pear-admin/Pear-Admin-Layui)后台模板构建; 前台基于Vue3 + Vite + Typescript + [Matery](https://github.com/blinkfox/hexo-theme-matery)博客样式构建;

前台展示：[http://www.htdog.top/](http://www.htdog.top/)

后台不开放，部分截图展示如下：

* **后台首页**

<div align="center">
  <img  width="92%" style="border-radius:10px;margin-top:20px;margin-bottom:20px;box-shadow: 2px 0 6px gray;" src="https://user-images.githubusercontent.com/68481442/141325526-be0de54a-2fc4-4695-b723-2e8b27acc6ff.png" />
</div>

* **菜单管理**

<div align="center">
  <img  width="92%" style="border-radius:10px;margin-top:20px;margin-bottom:20px;box-shadow: 2px 0 6px gray;" src="https://user-images.githubusercontent.com/68481442/141326817-d903d3ae-dfea-4dea-a455-afd4528e5286.png" />
</div>

* **文章编辑**

<div align="center">
  <img  width="92%" style="border-radius:10px;margin-top:20px;margin-bottom:20px;box-shadow: 2px 0 6px gray;" src="https://user-images.githubusercontent.com/68481442/141326272-221d295a-08e5-470a-9a92-1271759b67ca.png" />
</div>

* **操作日志**

<div align="center">
  <img  width="92%" style="border-radius:10px;margin-top:20px;margin-bottom:20px;box-shadow: 2px 0 6px gray;" src="https://user-images.githubusercontent.com/68481442/141326973-24f8fef6-39c2-47a8-8e89-0615a5433b3e.png" />
</div>

* **接口文档**

<div align="center">
  <img  width="92%" style="border-radius:10px;margin-top:20px;margin-bottom:20px;box-shadow: 2px 0 6px gray;" src="https://user-images.githubusercontent.com/68481442/141327180-0a2a86ee-e5d9-4350-bb13-791e8a9537f1.png" />
</div>

### 详细介绍

#### 1. 后台主要使用工具及框架

| 技术               | 版本          |
| ------------------ | ------------- |
| SpringBoot         | 2.3.0 Release |
| MySQL              | 8.0.20        |
| Redis              | 6.1.0         |
| ElasticSearch      | 7.6.1         |
| Mybatis-plus       | 3.4.0         |
| Spring-Security    | X             |
| Swagger2 + knife4j | 3.0.2         |
| Spring-AOP         | X             |
| Spring-Cache       | X             |
| 七牛云存储         | 7.4.0         |

#### 2. 后台功能介绍

##### 1).  系统管理

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
3. 角色管理：角色菜单权限分配、设置角色的权限。
4. 公告管理：系统通知公告信息发布维护。

##### 2).  博客管理

1. 控制台：可以看到系统的文章数、前台页面访问总数、访问总人数、评论总数以及近日的浏览量变化情况等信息。
2. 文章编辑：此页面用户可以对文章进行编辑，编辑完可以提交保存到数据库。
3. 分类管理：可以看到文章的分类总数及其关联的文章数、可进行增删改查。
4. 标签管理：可以看到文章的标签总数及其关联的文章数、可进行增删改查。
5. 文章管理：可以看到文章的概要信息并对其进行编辑。
6. 评论管理：对前台的评论进行查看与逻辑删除。
7. 留言管理：对前台的留言进行查看与逻辑删除。
8. 友链管理：对前台的友链显示进行增删改查。
9. 相册管理：原Matery关于我页面下的相册展示，个人选择不做，转而用于保存一些适合做文章封面的图片。

##### 3).  系统监控

1. 操作日志：系统正常操作日志记录和查询。
2. 异常日志：系统异常信息日志记录和查询。
3. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

##### 4). 系统工具

1. 七牛云：配置七牛云并上传文件与删除文件。
2. 接口文档：可以看到整个系统的接口详情。

#### 3. 前台主要使用工具及框架

| 技术             | 版本   |
| ---------------- | ------ |
| Vue              | 3.2.16 |
| vite             | 2.6.4  |
| typescript       | 4.4.3  |
| vue-router       | 4.0.12 |
| Matery及相关插件 | X      |

#### 4. 前台功能介绍

1. 博客首页：展示博主的文章信息、推荐文章以及音乐播放
2. 文章标签：展示博主的文章相关标签、标签云图
3. 文章分类：展示博主的文章相关分类、分类雷达图
4. 文章归档：展示博主的创作日历图、每一篇文章按日期展示
5. 关于我：展示博主的一些个人信息
6. 友情链接：展示博客的友链以及提供留言墙供游客留言
7. 文章搜索：按关键字到ES中搜索相关文章
8. 文章详情页：展示文章内容与评论区，评论区可供游客评论

### 使用说明

* 数据库的编码格式为：utfmb4
* 后台管理员的初始账号：admin , 密码：123456
* 使用ElasticSearch时Maven依赖请与你安装的ElasticSearch版本保持一致，且ES需要自行安装IK分词器

### 项目部署

#### 1. 准备工作

先在服务器的任一文件夹下新建一个文件夹stardust，进入stardust，在里面再新建一个server文件夹存放后台相关的文件、新建一个web文件夹存放前台相关的文件。

> 目录结构

[![image](https://user-images.githubusercontent.com/68481442/141136292-91dcecae-06d5-47f0-94d5-5acb36a7d1ab.png)](https://user-images.githubusercontent.com/68481442/141136292-91dcecae-06d5-47f0-94d5-5acb36a7d1ab.png) [![image](https://user-images.githubusercontent.com/68481442/141136355-c0f5a993-9f39-4a35-895e-615c8827c261.png)](https://user-images.githubusercontent.com/68481442/141136355-c0f5a993-9f39-4a35-895e-615c8827c261.png) [![image](https://user-images.githubusercontent.com/68481442/141136409-32550524-1930-4431-93df-ccaef9806a09.png)](https://user-images.githubusercontent.com/68481442/141136409-32550524-1930-4431-93df-ccaef9806a09.png)

#### 2. 构建镜像

> 构建后台系统镜像

1. 在命令行运行打包命令

```
mvn clean package -Dmaven.test.skip=true
```

1. 在stardust-server的target目录下找到生成的jar包，将其与DockerFile上传到服务器的server文件夹下，再新建一个log文件夹存放日志
2. 开始构建docker镜像，运行下面命令

```
docker build -t stardust-server:v1.0 .
```

1. 查看生成的镜像

```
docker images
```

> 构建前台项目镜像

1. 先将项目打包，在控制台中输入命令生成dist文件

```
npm run build
```

1. 将生成的dist文件夹和develop文件夹下的Dockerfile和default.conf上传到服务器的web文件夹下，然后在这个文件夹下新建一个conf目录，移入default.conf文件，再新建一个log文件夹存储日志
2. 开始构建docker镜像，运行下面命令

```
docker build -t stardust-web:v1.0 .
```

1. 查看生成的镜像

```
docker images
```

一切无误后就可以进行下一步了

#### 3. 开始运行

将docker-compose.yml文件上传到stardust目录下，在stardust目录下运行命令使用镜像构建容器

```
docker-compose up -d
```

没问题的话项目就跑起来了，在浏览器输入服务器的公网IP地址就可以进行访问了，如果想要停下服务的话，还是在stardust目录下，输入以下命令停止服务

```
docker-compose stop
```

### 总结

本项目是本人在学完SpringBoot且跟着视频做完一个项目后开始着手做的，抱着学习的目的，想尝试自己独立开发一个项目，但是开始时设计各种数据库表格确实是难倒我了，因为对自己想要做的功能（对博客这个东西）并无一个清晰的认知，所以便在github上寻找一些优秀的开源项目进行学习，在此非常感谢[geek_blog](https://github.com/AlanLiang1998/geek_blog)这个项目为我提供了方向，后面后台完成大半的时候由于自己学习上的其他事情导致这个项目就搁置下来了（差不多半年），还因为某些原因转而去先去做了别的项目，直到半个多月前（2021.10.15号左右）才重新拾起这个项目，这个时候这个项目如果再按部就班做下去的话，我感觉就没什么意思了，于是打算前台直接用Vue工程化开发，想借这个机会使用下Vue3 + Vite + Ts进行开发体验一下，中途也遇到了许多坑，许多用到的组件Vue可能没有，有的话也不兼容Vue3，所以就只能通过直接导入JS的方式导入依赖，但是用Vue这种MVVM的框架干这种事确实很难受，时有发生因为导入依赖的时机与组件渲染的时机不适配导致渲染失败，大部分都可以通过`SetTimeout()`延时导入解决，但是有的时候又会引发新的bug，可以说是很操蛋了，但是最后还是一点一点地把这些问题解决了。部署的时候又遇到了许多坑，如nginx配置错误导致后台获取不到客户的真实ip地址，为此又找了好几天bug，搞得我也是很恼火和无奈，但最终还是部署上了，这个项目也算结束了。项目才上线不久（2021.11.10），想必还存在着某些bug，后面发现了也会修的，如果你打算使用这个项目进行学习的话，遇到什么问题我可以帮的上忙的话可以联系我，QQ：1821997710。最后，如果你喜欢这个项目的话，就请给个Star吧~

