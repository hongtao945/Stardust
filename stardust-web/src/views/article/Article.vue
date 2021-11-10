<template>
  <div>
    <iframe
      src="/static/sakura.html"
      style="width: 100%; height: 60vh; margin-top: -64px"
      scrolling="no"
      frameborder="0"
    ></iframe>
    <!-- content start -->
    <main class="post-container content">
      <div class="row">
        <!-- 文章内容详情 -->
        <div id="main-content" class="col s12 m12 l9">
          <div id="artDetail">
            <div class="card">
              <div class="card-content article-info">
                <div class="row tag-cate">
                  <!--文章标签-->
                  <div class="col s8">
                    <div class="article-tag">
                      <router-link
                        :to="{ name: 'tags', params: { id: tag.tagId } }"
                        v-for="tag in article?.tagList"
                        :key="tag.tagId"
                      >
                        <span class="chip bg-color">
                          {{ tag.name }}
                        </span>
                      </router-link>
                    </div>
                  </div>

                  <!--文章分类-->
                  <div class="col s4 right-align">
                    <div class="post-cate">
                      <font-awesome-icon :icon="['fa', 'bookmark']" />&nbsp;

                      <router-link
                        :to="{
                          name: 'classifications',
                          params: { id: article?.classification.classId },
                        }"
                      >
                        <span class="post-category">
                          {{ article?.classification.name }}
                        </span>
                      </router-link>
                    </div>
                  </div>
                </div>

                <div class="post-info">
                  <!--发布日期-->
                  <div class="post-date info-break-policy">
                    <!-- 由于详细时间在移动端显示得并不好看，所以截掉一段 -->
                    <font-awesome-icon
                      :icon="['fa', 'calendar-minus']"
                    />&nbsp;发布日期:&nbsp;&nbsp;<span>{{
                      article?.createTime.split(" ")[0]
                    }}</span>
                  </div>
                  <!--浏览次数-->
                  <div
                    id="busuanzi_container_page_pv"
                    class="info-break-policy"
                  >
                    <font-awesome-icon
                      :icon="['fa', 'eye']"
                    />&nbsp;浏览次数:&nbsp;&nbsp;
                    <span>
                      {{ article?.views }}
                    </span>
                  </div>
                  <!--文章字数-->
                  <div class="info-break-policy">
                    <font-awesome-icon
                      :icon="['fa', 'file-word']"
                    />&nbsp;文章字数:&nbsp;&nbsp;
                    <span>
                      {{ wordCount }}
                    </span>
                  </div>
                  <!--评论量-->
                  <div class="info-break-policy">
                    <font-awesome-icon
                      :icon="['fa', 'comment']"
                    />&nbsp;评论量:&nbsp;&nbsp;
                    <span>
                      {{ total }}
                    </span>
                  </div>
                </div>
              </div>
              <hr class="clearfix" />
              <div class="card-content article-card-content">
                <!--文章文本内容-->
                <!-- <lightgallery :settings="{ speed: 500, plugins: plugins }">
                  <a
                    class="img-item"
                    data-src="http://cdn.alanliang.site/note/使用插座图.jpg"
                    data-sub-html=".caption"
                    ><img
                      src="http://cdn.alanliang.site/note/使用插座图.jpg"
                      alt="使用插座"
                      class="img-shadow img-margin"
                    />
                    <div class="caption">
                      <b class="center-caption">使用插座</b>
                    </div></a
                  > -->
                  <div
                    id="articleContent"
                    data-aos="fade-up"
                    v-html="article?.htmlContent"
                  ></div>
                <!-- </lightgallery> -->
                <hr />
                <!--复制-->
                <div class="reprint" id="reprint-statement">
                  <!--文章作者-->
                  <div class="reprint__author">
                    <span class="reprint-meta" style="font-weight: bold">
                      <font-awesome-icon
                        :icon="['fa', 'user']"
                      />&nbsp;文章作者:&nbsp;
                    </span>
                    <span class="reprint-info">
                      <a href="#" rel="external nofollow noreferrer">{{
                        article?.author.nickname
                      }}</a>
                    </span>
                  </div>
                  <!--文章链接-->
                  <div class="reprint__type">
                    <span class="reprint-meta" style="font-weight: bold">
                      <font-awesome-icon
                        :icon="['fa', 'link']"
                      />&nbsp;文章链接:&nbsp;
                    </span>
                    <span class="reprint-info">
                      <a href="#" rel="external nofollow noreferrer"
                        >文章链接</a
                      >
                    </span>
                  </div>
                  <!--文章复制提醒-->
                  <div class="reprint__notice">
                    <span class="reprint-meta" style="font-weight: bold">
                      <font-awesome-icon :icon="['fa', 'copyright']" />
                      版权声明:
                    </span>
                    <span class="reprint-info">
                      本博客所有文章除特別声明外，均采用
                      <a
                        href="javascript:;"
                        rel="external nofollow noreferrer"
                        target="_blank"
                      >
                        CC BY 4.0
                      </a>
                      许可协议。转载请注明来源
                      <a href="javascript:;" target="_blank">
                        {{ article?.author.nickname }} </a
                      >&nbsp;!
                    </span>
                  </div>
                </div>
                <!--分享-->
                <div class="tag_share" style="display: block">
                  <!--文章的类型-->
                  <div
                    class="post-meta__tag-list"
                    style="display: inline-block"
                  >
                    <div class="article-tag">
                      <a href="#">
                        <span class="chip bg-color">{{ articleType }}</span>
                      </a>
                    </div>
                  </div>
                  <!--文章分享 -->
                  <div
                    class="post_share"
                    style="
                      float: right;
                      zoom: 80%;
                      width: fit-content;
                      display: inline-block;
                      margin: -0.15rem 0;
                    "
                  >
                    <div id="article-share">
                      <div
                        class="social-share"
                        data-sites="qq,qzone,wechat,weibo"
                        data-wechat-qrcode-helper="<p>微信扫一扫即可分享！</p>"
                      ></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!--评论区-->
            <div
              class="card valine-card aos-init aos-animate"
              data-aos="fade-up"
              id="comment-area"
            >
              <div
                class="comment_headling"
                style="
                  font-size: 20px;
                  font-weight: 700;
                  position: relative;
                  left: 20px;
                  top: 15px;
                  padding-bottom: 5px;
                "
              >
                <span aria-hidden="true">
                  <font-awesome-icon :icon="['fa', 'comments']" />&nbsp;
                </span>
                <span>评论</span>
              </div>
              <div id="comment-form">
                <!-- <input type="hidden" name="pid" v-model="pid" /> -->
                <div
                  id="vcomments"
                  class="card-content v"
                  style="display: grid"
                >
                  <div class="vwrap">
                    <div class="vheader item3">
                      <input
                        name="nick"
                        placeholder="昵称"
                        class="vnick vinput"
                        type="text"
                        v-model="comment.nickname"
                      />
                    </div>
                    <div class="vedit">
                      <textarea
                        id="veditor"
                        class="veditor vinput"
                        :placeholder="placeholder"
                        name="content"
                        required
                        v-model="comment.content"
                      ></textarea>
                    </div>
                    <div class="vcontrol" style="float: right">
                      <div class="col col-80 text-right">
                        <button
                          type="button"
                          class="vsubmit vbtn"
                          @click="addComment"
                        >
                          回复
                        </button>
                      </div>
                    </div>
                    <div style="display: none" class="vmark"></div>
                  </div>
                  <div class="vinfo" style="display: block">
                    <div class="vcount col">
                      <span class="vnum">{{ total }}</span
                      >&nbsp;评论
                    </div>
                  </div>
                  <div class="center-align">
                    <div class="preloader-wrapper big active" v-show="loading">
                      <div class="spinner-layer spinner-blue">
                        <div class="circle-clipper left">
                          <div class="circle"></div>
                        </div>
                        <div class="gap-patch">
                          <div class="circle"></div>
                        </div>
                        <div class="circle-clipper right">
                          <div class="circle"></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="vlist">
                    <div
                      class="vcard"
                      v-for="comment in comments"
                      :key="comment.commentId"
                      :id="'comment' + comment.commentId"
                    >
                      <img class="vimg" src="../../assets/浣熊火箭.png" />
                      <div class="vh">
                        <div class="vhead">
                          <span class="vnick">
                            {{
                              comment.nickname === null || comment.nickname.trim().length === 0
                                ? "匿名用户"
                                : comment.nickname
                            }}
                          </span>
                          <span class="vsys">{{ comment.browser }}</span>
                          <span class="vsys">{{ comment.os }}</span>
                          <span class="vsys">{{ comment.address }}</span>
                        </div>
                        <!--  | dateFormat -->
                        <div class="vmeta">
                          <span class="vtime">{{ comment.createTime }}</span>
                          <span
                            class="vat"
                            @click="reply(comment.commentId, comment.nickname)"
                          >
                            回复
                          </span>
                        </div>
                        <div
                          class="vcontent"
                          :style="comment.isDelete ? 'color:red' : ''"
                        >
                          <p>{{ comment.content }}</p>
                        </div>
                        <div
                          class="vquote"
                          v-if="
                            comment.children !== undefined &&
                            comment.children.length > 0
                          "
                        >
                          <div
                            class="vcard"
                            v-for="child in comment.children"
                            :key="child.commentId"
                          >
                            <img class="vimg" src="../../assets/黑豹.png" />
                            <div class="vh">
                              <div class="vhead">
                                <span class="vnick">
                                  {{
                                    child.nickname === null || child.nickname.trim().length === 0
                                      ? "匿名用户"
                                      : child.nickname
                                  }}
                                </span>
                                <span class="vsys">{{ child.browser }}</span>
                                <span class="vsys">{{ child.os }}</span>
                                <span class="vsys">{{ child.address }}</span>
                              </div>
                              <div class="vmeta">
                                <span class="vtime">{{
                                  child.createTime
                                }}</span>
                                <span
                                  class="vat"
                                  @click="
                                    reply(child.commentId, child.nickname)
                                  "
                                >
                                  回复
                                </span>
                              </div>
                              <div class="vcontent">
                                <p :style="child.isDelete ? 'color:red' : ''">
                                  <a class="at" :href="'#comment' + child.fid"
                                    >@{{ child.fcommentNickname }}</a
                                  >&nbsp;&nbsp;{{ child.content }}
                                </p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="vempty" style="display: none"></div>
                  <div class="vpage txt-center" v-if="cnt < total">
                    <button
                      type="button"
                      class="vsubmit vbtn"
                      @click="loadMore"
                    >
                      加载更多
                    </button>
                  </div>
                  <div class="info"></div>
                </div>
              </div>
            </div>
            <!--上、下一篇文章-->
            <article id="prenext-posts" class="prev-next articles">
              <div class="row article-row">
                <!--上一篇-->
                <div
                  class="article col s12 m6"
                  data-aos="fade-up"
                  v-if="prev !== null"
                >
                  <div class="article-badge left-badge text-color">
                    <font-awesome-icon
                      :icon="['fa', 'chevron-left']"
                    />&nbsp;上一篇
                  </div>
                  <div class="card">
                    <a :href="'/article/' + prev?.articleId">
                      <div class="card-image">
                        <img :src="prev?.articleFace" class="responsive-img" />
                        <span class="card-title">{{ prev?.title }}</span>
                      </div>
                    </a>
                    <div class="card-content article-content">
                      <div
                        class="summary block-with-text"
                        style="min-height: 67.5px"
                      >
                        {{ prev?.summary }}
                      </div>
                      <div class="publish-info">
                        <span class="publish-date">
                          <font-awesome-icon
                            :icon="['fa', 'clock']"
                          />&nbsp;<span>{{ prev?.createTime }}</span>
                        </span>
                        <span class="publish-author">
                          <font-awesome-icon :icon="['fa', 'bookmark']" />
                          <router-link
                            :to="{
                              name: 'classifications',
                              params: { id: prev?.classification.classId },
                            }"
                          >
                            <span class="post-category">
                              {{ prev?.classification.name }}
                            </span>
                          </router-link>
                        </span>
                      </div>
                    </div>
                    <div class="card-action article-tags">
                      <router-link
                        :to="{ name: 'tags', params: { id: tag.tagId } }"
                        v-for="tag in prev?.tagList"
                        :key="tag.tagId"
                      >
                        <span class="chip bg-color">
                          {{ tag.name }}
                        </span>
                      </router-link>
                    </div>
                  </div>
                </div>
                <!--下一篇-->
                <div
                  class="article col s12 m6"
                  data-aos="fade-up"
                  v-if="next !== null"
                >
                  <div class="article-badge right-badge text-color">
                    下一篇&nbsp;<font-awesome-icon
                      :icon="['fa', 'chevron-right']"
                    />
                  </div>
                  <div class="card">
                    <a :href="'/article/' + next?.articleId">
                      <div class="card-image">
                        <img :src="next?.articleFace" class="responsive-img" />
                        <span class="card-title">{{ next?.title }}</span>
                      </div>
                    </a>
                    <div class="card-content article-content">
                      <div
                        class="summary block-with-text"
                        style="min-height: 67.5px"
                      >
                        {{ next?.summary }}
                      </div>
                      <div class="publish-info">
                        <span class="publish-date">
                          <font-awesome-icon :icon="['fa', 'clock']" /><span>{{
                            next?.createTime
                          }}</span>
                        </span>
                        <span class="publish-author">
                          <font-awesome-icon :icon="['fa', 'bookmark']" />
                          <router-link
                            :to="{
                              name: 'classifications',
                              params: { id: next?.classification.classId },
                            }"
                          >
                            <span class="post-category">
                              {{ next?.classification.name }}
                            </span>
                          </router-link>
                        </span>
                      </div>
                    </div>
                    <div class="card-action article-tags">
                      <router-link
                        :to="{ name: 'tags', params: { id: tag.tagId } }"
                        v-for="tag in next?.tagList"
                        :key="tag.tagId"
                      >
                        <span class="chip bg-color">
                          {{ tag.name }}
                        </span>
                      </router-link>
                    </div>
                  </div>
                </div>
              </div>
            </article>
          </div>
        </div>
        <!--文章幕布-->
        <div id="toc-aside" class="expanded col l3 hide-on-med-and-down">
          <div class="toc-widget">
            <div class="toc-title">
              <font-awesome-icon :icon="['fas', 'list-alt']" />&nbsp;&nbsp;目录
            </div>
            <div id="toc-content"></div>
          </div>
        </div>
      </div>
      <!-- 目录悬浮按钮. -->
      <div id="floating-toc-btn" class="hide-on-med-and-down">
        <a class="btn-floating btn-large bg-color my-icon">
          <span>
            <font-awesome-icon :icon="['fas', 'list-ul']" />
          </span>
        </a>
      </div>
    </main>
    <!-- content end -->
  </div>
</template>

<script lang="ts">
import {
  computed,
  onBeforeMount,
  onMounted,
  reactive,
  ref,
  toRefs,
} from "@vue/runtime-core";
import VueScrollTo from "vue-scrollto";
import { useRoute } from "vue-router";
import { Article, Comment } from "../../types/model";
import { request, METHOD } from "../../utils/api";
import sweetAlert from "sweetalert";
export default {
  setup() {
    const articleId = useRoute().params.id;
    const baseCommentUrl = `/front/comments/${articleId}`;
    const baseArticleUrl = `/front/article/${articleId}`;
    onBeforeMount(() => {
      const libsUrl: string[] = [
        "/static/libs/Valine.min.js",
        "/static/libs/tocbot.min.js",
        "/static/libs/social-share.min.js",
        // "/static/libs/article.js",
        // "/static/libs/prism.js"
      ];
      viewsIncrement();
      getPrevAndNextArticle(); // 放在onMounted里面会导致在渲染route-link时undefined
      importLib(libsUrl);
    });

    onMounted(() => {
      initArticle();
      initComments();
      // 开启行号显示，但是与toc的适配会有问题， 在代码显示变宽后行号会多余，不好看
      // setTimeout(() => $('pre').addClass("line-numbers"), 150);
      setTimeout(() => {
        importLib(["/static/libs/article.js", "/static/libs/prism.js"]);
        $("article .article").on("mouseover mouseout", function (event) {
          const animateClass = "animated pulse";
          if (event.type === "mouseover") {
            $(this).addClass(animateClass);
          } else if (event.type === "mouseout") {
            $(this).removeClass(animateClass);
          }
        });
      }, 200);
    });

    // 导入所需依赖
    const importLib = (urls: string[]): void => {
      for (let i = 0; i < urls.length; i++) {
        const s = document.createElement("script");
        s.type = "text/javascript";
        s.src = urls[i];
        document.body.appendChild(s);
      }
    };

    const state = reactive({
      comment: {
        fid: 0,
        content: "",
        fcommentNickname: "",
        nickname: localStorage.getItem("visitorNickname"),
      },
      params: {
        page: 1,
        limit: 4,
      },
      total: 0,
      placeholder: "留下你的评论吧~",
      loading: false,
    });
    const prev = ref<Article>();
    const next = ref<Article>();
    const article = ref<Article>();
    const comments = ref<Comment[]>();

    // 浏览量增加
    const viewsIncrement = (): void => {
      request(baseArticleUrl + "/views", METHOD.GET);
    };

    // 添加评论
    const addComment = (): void => {
      if (!state.comment.content || state.comment.content.trim().length === 0) {
        sweetAlert({
          text: "请编辑好后再回复!",
          icon: "error",
        });
        return;
      }
      state.loading = true;
      request(baseCommentUrl, METHOD.POST, state.comment).then((resp) => {
        state.comment.fid = 0;
        state.comment.content = "";
        state.comment.fcommentNickname = "";
        if (state.comment.nickname && state.comment.nickname.length > 0) {
          localStorage.setItem("visitorNickname", state.comment.nickname);
        }
        if (resp.data.code === 200) {
          sweetAlert({
            text: "评论成功!",
            icon: "success",
          });
          state.params.page = 1;
          state.loading = false;
          initComments();
          return;
        }
        sweetAlert({
          text: "评论失败!",
          icon: "error",
        });
      });
    };

    // 初始化评论
    const initComments = (): void => {
      request(baseCommentUrl, METHOD.GET, state.params).then((resp) => {
        if (state.params.page === 1) {
          comments.value = resp.data.data;
        } else {
          comments.value = comments.value?.concat(resp.data.data);
        }
        state.total = resp.data.count;
      });
    };

    /**
     * @description: 评论回复
     * @param fid: 父评论id
     * @param fNickname: 父评论昵称
     */
    const reply = (fid: number, fNickname: string): void => {
      state.comment.fid = fid;
      state.comment.fcommentNickname = fNickname;
      state.placeholder = `@${fNickname}`;
      VueScrollTo.scrollTo("#comment-area", 1000);
      $("#veditor").focus();
    };

    // 获取文章
    const initArticle = (): void => {
      request(baseArticleUrl, METHOD.GET).then((resp) => {
        article.value = resp.data;
        document.title = article.value?.title as string;
      });
    };

    // 获取上一篇和下一篇文章
    const getPrevAndNextArticle = (): void => {
      request(baseArticleUrl + "/pn", METHOD.GET).then((resp) => {
        if (resp.data.length === 0) return;
        if (resp.data.length === 1) {
          next.value = resp.data[0];
          return;
        }
        prev.value = resp.data[0];
        next.value = resp.data[1];
      });
    };

    // 加载更多
    const loadMore = (): void => {
      state.params.page++;
      initComments();
    };

    // 当前已显示的数据条数
    const cnt = computed(() => state.params.page * state.params.limit);

    // 文章字数转换
    const wordCount = computed(
      () => Math.round((article.value?.words as number) / 100) / 10 + "k"
    );

    // 文章类型枚举
    enum ArticleTypeEnum {
      ORIGINAL = 1, // 原创
      REPRINT = 2, // 转载
      TRANSLATE = 3, // 翻译
    }

    // 转换文章类型
    const articleType = computed(() => {
      switch (article.value?.type) {
        case ArticleTypeEnum.ORIGINAL:
          return "原创";
        case ArticleTypeEnum.REPRINT:
          return "转载";
        case ArticleTypeEnum.TRANSLATE:
          return "翻译";
      }
    });

    return {
      ...toRefs(state),
      article,
      prev,
      next,
      comments,
      cnt,
      wordCount,
      articleType,
      reply,
      addComment,
      loadMore,
    };
  },
};
</script>

<style>
/* https://prismjs.com/ prismjs官网 */
@import url(./css/prism.css);
@import url(./css/tocbot.css);
@import url(./css/content.css);
@import url(./css/share.min.css);
@import url(./css/code-theme.css);
</style>
