<!-- 归档页面文章 -->
<template>
  <div>
    <div id="cd-timeline" class="container">
      <div
        class="cd-timeline-block"
        v-for="article in articles"
        :key="article.articleId"
      >
        <div
          class="cd-timeline-img year aos-init aos-animate"
          data-aos="zoom-in-up"
          v-if="showYear(article.createTime)"
        >
          <a href="javascript:;">
            {{ article.createTime.split("-")[0] }}
          </a>
        </div>
        <div
          class="cd-timeline-img month aos-init aos-animate"
          data-aos="zoom-in-up"
          v-if="showMonth(article.createTime)"
        >
          <a href="javascript:;">
            {{ article.createTime.split("-")[1] }}
          </a>
        </div>
        <div
          class="cd-timeline-img day aos-init aos-animate"
          data-aos="zoom-in-up"
        >
          <span>
            {{ article.createTime.split("-")[2].split(" ")[0] }}
          </span>
        </div>
        <article
          class="cd-timeline-content aos-init aos-animate"
          data-aos="fade-up"
        >
          <div class="article col s12 m6">
            <div class="card">
              <a :href="`/article/${article.articleId}`">
                <div class="card-image">
                  <img class="responsive-img" :src="article.articleFace" />
                  <span class="card-title">{{ article.title }}</span>
                </div>
              </a>
              <div class="card-content article-content">
                <div class="summary block-with-text">{{ article.summary }}</div>
                <div class="publish-info">
                  <span class="publish-date">
                    <font-awesome-icon :icon="['fa', 'clock']" />
                    {{ article.createTime }}
                  </span>
                  <span class="publish-author">
                    <font-awesome-icon :icon="['fa', 'bookmark']" />
                    <router-link
                      :to="{
                        name: 'classifications',
                        params: { id: article.classification.classId },
                      }"
                      class="post-category"
                    >
                      {{ article.classification.name }}
                    </router-link>
                  </span>
                </div>
              </div>
              <!-- :href="`/tags/${tag.tagId}`" -->
              <div class="card-action article-tags">
                <router-link
                  :to="{ name: 'tags', params: { id: tag.tagId } }"
                  v-for="tag in article.tagList"
                  :key="tag.tagId"
                >
                  <span class="chip bg-color">
                    {{ tag.name }}
                  </span>
                </router-link>
              </div>
            </div>
          </div>
        </article>
      </div>
    </div>
    <!--分页-->
    <div class="container paging">
      <div class="row">
        <!--左边按钮-->
        <div class="col s4 m4 l4">
          <span
            style="font-size: 2.75em;"
            class="left btn-floating btn-large waves-effect waves-light"
            @click="pageChange(false)"
            :class="{ disabled: params.page === 1 }"
          >
            <font-awesome-icon :icon="['fas', 'angle-left']" />
          </span>
        </div>
        <!--分页信息-->
        <div class="page-info col s4 m4 l4">
          <div class="center-align b-text-gray">
            {{ params.page }} / {{ cnt }}
          </div>
        </div>
        <!--右边按钮-->
        <div class="col s4 m4 l4">
          <span
            style="font-size: 2.75em;"
            class="
              right
              btn-floating btn-large
              waves-effect waves-light
            "
            @click="pageChange(true)"
            :class="{ disabled: params.page === cnt || cnt === 0 }"
          >
            <font-awesome-icon :icon="['fas', 'angle-right']" />
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { reactive, toRefs } from "@vue/reactivity";
import { Article } from "../../types/model";
import { computed, onBeforeMount, onUpdated, ref } from "vue";
import { METHOD, request } from "../../utils/api";
import VueScrollTo from "vue-scrollto";
export default {
  setup() {
    onBeforeMount(() => {
      initArticles();
    });

    // 更新时为新渲染的元素添加hover时的动画效果，否则在分页后点击时的动画效果会失效
    onUpdated(() => {
      $('article .article').on("mouseover mouseout",function(event){
        const animateClass = 'animated pulse';
        if(event.type === "mouseover"){
          $(this).addClass(animateClass);
        }else if(event.type === "mouseout"){
          $(this).removeClass(animateClass);
        }
      });
    });

    const articles = ref<Article[]>();
    const state = reactive({
      params: {
        page: 1,
        limit: 8,
      },
      total: 0,
    });

    // 共有多少页
    const cnt = computed((): number => {
      return Math.ceil(state.total / state.params.limit);
    });

    // 初始化获取文章
    const initArticles = (): void => {
      const url = `/front/article?tagId=0&classId=0`;
      request(url, METHOD.GET, state.params).then((resp: any) => {
        articles.value = resp.data.data;
        state.total = resp.data.count;
      });
    };

    // 点击上一页或下一页
    const pageChange = (type: boolean): void => {      
      articles.value = [];
      currentYear = 0;
      currentMonth = 0;
      if (!type) {
        state.params.page -= 1;
      } else {
        state.params.page += 1;
      }
      VueScrollTo.scrollTo("#app", 1000);
      initArticles();
    };

    let currentYear = 0;
    let currentMonth = 0;

    const showYear = (time: string): boolean => {
      const year: number = Number.parseInt(time.split("-")[0]);
      if (year !== currentYear) {
        currentYear = year;
        return true;
      }
      return false;
    };

    const showMonth = (time: string): boolean => {
      const month: number = Number.parseInt(time.split("-")[1]);
      if (month !== currentMonth) {
        currentMonth = month;
        return true;
      }
      return false;
    };

    return {
      ...toRefs(state),
      articles,
      cnt,
      pageChange,
      showYear,
      showMonth,
    };
  },
};
</script>

<style></style>
