<template>
  <div>
    <!-- 封面 -->
    <div style="height: calc(var(--vh, 1vh) * 100 - 46px)">
      <iframe
        src="/static/sakura.html"
        style="width: 100%; height: 100vh; margin-top: -64px"
        scrolling="no"
        frameborder="0"
      ></iframe>
      <div class="stardust-desc">
        <div class="carousel-item white-text bg-cover">
          <div class="container">
            <div class="row">
              <div class="col s10 offset-s1 m8 offset-m2 l8 offset-l2">
                <div class="brand">
                  <!--标题-->
                  <div class="title center-align">Stardust Blog</div>
                  <!--打字效果-->
                  <div class="description center-align">
                    <span id="subtitle"></span>
                  </div>
                </div>
              </div>
            </div>
            <div class="cover-btns">
              <a
                class="waves-effect waves-light btn"
                onclick="$(window).scrollTo('#indexCard', 900)"
              >
                <font-awesome-icon
                  :icon="['fas', 'angle-double-down']"
                />&nbsp;&nbsp;开始阅读
              </a>
            </div>
            <div class="cover-social-link">
              <a
                class="tooltipped"
                data-delay="50"
                data-position="top"
                data-tooltip="访问我的GitHub"
                href="https://github.com/hongtao945"
                target="_blank"
              >
                <font-awesome-icon :icon="['fab', 'github']" />
              </a>
              <a
                class="tooltipped"
                data-delay="50"
                data-position="top"
                data-tooltip="邮件联系我"
                href="mailto:1821997710@qq.com"
                target="_blank"
              >
                <font-awesome-icon :icon="['fas', 'envelope-open']" />
              </a>
              <a
                class="tooltipped"
                data-delay="50"
                data-position="top"
                data-tooltip="QQ: 1821997710"
                href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=1821997710"
                target="_blank"
              >
                <font-awesome-icon :icon="['fab', 'qq']" />
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- content start -->
    <main class="content">
      <div class="index-card" id="indexCard">
        <div class="container">
          <div class="card">
            <div class="card-content" data-aos="fade-up">
              <!-- 我的梦想 -->
              <div class="dream">
                <div class="title center-align">
                  <font-awesome-icon
                    :icon="['fa', 'lightbulb']"
                  />&nbsp;&nbsp;我的梦想
                </div>
                <div class="row">
                  <div
                    class="
                      col
                      l8
                      offset-l2
                      m10
                      offset-m1
                      s10
                      offset-s1
                      center-align
                      text
                    "
                  >
                    不是每个人都应该像我这样去建造一座水晶大教堂，但是每个人都应该拥有自己的梦想，设计自己的梦想，追求自己的梦想，实现自己的梦想。梦想是生命的灵魂，是心灵的灯塔，是引导人走向成功的信仰。有了崇高的梦想，只要矢志不渝地追求，梦想就会成为现实，奋斗就会变成壮举，生命就会创造奇迹。——罗伯·舒乐
                  </div>
                </div>
              </div>
              <!-- 音乐 -->
              <!-- <div class="dream">
                <div class="title center-align">
                  <font-awesome-icon :icon="['fa', 'music']" />&nbsp;&nbsp;音乐
                </div>
                <div class="row" style="margin-top: 20px">
                  <meting-js
                    server="kugou"
                    type="playlist"
                    id="4393464"
                    theme="#2196f3"
                  >
                  </meting-js>
                </div>
              </div> -->
              <!-- 推荐文章 -->
              <div class="recommend" id="recommend-sections" data-aos="fade-up">
                <div class="title">
                  <font-awesome-icon
                    :icon="['fas', 'thumbs-up']"
                  />&nbsp;&nbsp;推荐文章
                </div>
                <div class="row">
                  <!-- 遍历文章 -->
                  <div
                    v-for="recommend of recommendArticles"
                    :key="recommend.articleId"
                  >
                    <div
                      data-aos="zoom-in-up"
                      class="col s12 m6 aos-init aos-animate"
                    >
                      <div
                        class="post-card loaded"
                        :style="`background-image: url(${recommend.articleFace});`"
                        data-ll-status="loaded"
                      >
                        <div class="post-body">
                          <div class="post-categories">
                            <router-link
                              :to="{
                                name: 'classifications',
                                params: {
                                  id: recommend.classification.classId,
                                },
                              }"
                              class="category"
                            >
                              {{ recommend.classification.name }}
                            </router-link>
                          </div>
                          <a
                            :href="`/article/${recommend.articleId}`"
                            target="_blank"
                          >
                            <h3 class="post-title">{{ recommend.title }}</h3>
                          </a>
                          <p class="post-description">
                            {{ recommend.summary }}
                          </p>
                          <a
                            :href="`/article/${recommend.articleId}`"
                            target="_blank"
                          >
                            <span
                              class="read-more btn waves-effect waves-light"
                              style="
                                background: linear-gradient(
                                  to right,
                                  rgb(255, 94, 58) 0%,
                                  rgb(255, 42, 104) 100%
                                );
                              "
                              ><font-awesome-icon
                                :icon="['fa', 'eye']"
                              />&nbsp;阅读更多
                            </span>
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <article-card @updateVisable="updateVisable" />
    </main>
    <!-- content end -->
  </div>
</template>

<script lang="ts">
import ArticleCard from "../components/fragments/ArticleCard.vue";
import { request, METHOD } from "../utils/api";
import {
  onBeforeMount,
  onMounted,
  reactive,
  ref,
  toRefs,
} from "@vue/runtime-core";
import { Article } from "../types/model";
export default {
  setup() {
    // onBeforeMount(() => {
    //   importLibs(["/static/libs/APlayer.min.js"]);
    // });

    onMounted(() => {
      // 节点挂载好后引入script
      const libs: string[] = [
        "/static/libs/my-typed.js",
        "/static/libs/meting.min.js",
      ];
      initRecommendArticles();
      setTimeout(() => importLibs(libs), 150);
    });

    const importLibs = (libs: string[]): void => {
      libs.forEach((item) => {
        const s = document.createElement("script");
        s.type = "text/javascript";
        s.src = item;
        document.body.appendChild(s);
      });
    };

    const recommendArticles = ref<Article[]>();
    const state = reactive({
      visable: true,
    });

    // 初始化获取推荐文章
    const initRecommendArticles = (): void => {
      request("/front/article/recommend", METHOD.GET).then((resp: any) => {
        recommendArticles.value = resp.data;
      });
    };

    const updateVisable = (vis: number): void => {
      state.visable = vis === 1;
    };

    return {
      ...toRefs(state),
      recommendArticles,
      updateVisable,
    };
  },
  components: { ArticleCard },
};
</script>

<style>
/* @import url(../theme/css/APlayer.min.css); */
.stardust-desc {
  background-color: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  width: 100%;
}
</style>
