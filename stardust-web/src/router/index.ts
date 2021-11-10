import { createRouter, createWebHistory } from "vue-router";
const router = createRouter({
  history: createWebHistory("/"),
  routes: [
    {
      path: "/",
      component: () => import("../views/Index.vue"),
      meta: {
        title: '首页'
      }
    },
    {
      path: "/tags",
      name: "tags",
      component: () => import("../views/Tags.vue"),
      meta: {
        title: '文章标签'
      }
    },
    {
      path: "/about",
      component: () => import("../views/About.vue"),
      meta: {
        title: '关于我'
      }
    },
    {
      path: "/archives",
      component: () => import("../views/Archives.vue"),
      meta: {
        title: '归档'
      }
    },
    {
      path: "/friends",
      component: () => import("../views/Friends.vue"),
      meta: {
        title: '友链详情'
      }
    },
    {
      path: "/classifications",
      name: "classifications",
      component: () => import("../views/Classification.vue"),
      meta: {
        title: '文章分类'
      }
    },
    {
      name: "article",
      path: "/article/:id",
      component: () => import("../views/article/Article.vue"),
    },
    {
      path: "/:catchAll(.*)",
      name: "404",
      component: () => import("../views/error/404.vue"),
      meta: {
        title: '出错啦!'
      }
    },
  ],
});

router.afterEach(to => {
  //遍历meta改变title
  if (to.meta.title) {
    document.title = to.meta.title as string;
  }
  window.scrollTo(0,0);
});

export default router;
