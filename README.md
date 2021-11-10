# Stardust

#### 介绍
Stardust是一个博客系统，后台基于Springboot + LayUi构建;
前台基于Vue3 + Vite + Typescript构建;



### 项目部署

#### 1. 准备工作

先在服务器的任一文件夹下新建一个文件夹stardust，进入stardust，在里面再新建一个server文件夹存放后台相关的文件、新建一个web文件夹存放前台相关的文件。

> 目录结构



#### 2. 构建镜像

> 构建后台系统镜像

1. 在命令行运行打包命令

```bash
mvn clean package -Dmaven.test.skip=true
```

2. 在stardust-server的target目录下找到生成的jar包，将其与DockerFile上传到服务器的server文件夹下，再新建一个log文件夹存放日志
3. 开始构建docker镜像，运行下面命令

```bash
docker build -t stardust-server:v1.0 .
```

4. 查看生成的镜像

``` bash
docker images
```

> 构建前台项目镜像

1. 先将项目打包，在控制台中输入命令生成dist文件

``` bash
npm run build
```

2. 将生成的dist文件夹和develop文件夹下的Dockerfile和default.conf上传到服务器的web文件夹下，然后在这个文件夹下新建一个conf目录，移入default.conf文件，再新建一个log文件夹存储日志

3. 开始构建docker镜像，运行下面命令

```bash
docker build -t stardust-web:v1.0 .
```

4. 查看生成的镜像

``` bash
docker images
```

一切无误后就可以进行下一步了

#### 3. 开始运行

将docker-compose.yml文件上传到stardust目录下，在stardust目录下运行命令使用镜像构建容器

```bash
docker-compose up -d
```

没问题的话项目就跑起来了，在浏览器输入服务器的公网IP地址就可以进行访问了，如果想要停下服务的话，还是在stardust目录下，输入以下命令停止服务

``` bash
docker-compose stop
```

### 总结




