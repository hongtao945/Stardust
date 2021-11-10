<template>
  <!-- 所有文章卡片 -->
  <article class="container articles" id="articles" style="position: relative; height: 0px;">
    <div class="row article-row">
      <div v-for="article in articles" :key="article.articleId">
        <div
          data-aos="zoom-in"
          class="article col s12 m6 l4 aos-init aos-animate"
        >
          <div class="card">
            <a :href="`/article/${article.articleId}`" target="_blank">
              <div class="card-image">
                <img
                  :src="article.articleFace"
                  class="responsive-img loaded"
                  data-ll-status="loaded"
                />
                <span class="card-title">{{ article.title }}</span>
              </div>
            </a>
            <div class="card-content article-content">
              <div class="summary block-with-text" style="min-height: 67.5px">
                {{ article.summary }}
              </div>
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
                      params: {
                        id: article.classification.classId,
                      },
                    }"
                    class="post-category"
                  >
                    {{ article.classification.name }}
                  </router-link>
                </span>
              </div>
            </div>
            <div class="card-action article-tags">
              <router-link
                :to="{ name: 'tags', params: { id: tag.tagId } }"
                v-for="tag of article.tagList"
                :key="tag.tagId"
              >
                <span class="chip bg-color">{{ tag.name }}</span>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </article>
  <!--分页-->
  <div class="container paging">
    <div class="row">
      <!--左边按钮-->
      <div class="col s4 m4 l4">
        <span
          class="left btn-floating btn-large waves-effect waves-light"
          style="font-size: 2.75em;"
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
          class="right btn-floating btn-large waves-effect waves-light"    
          style="font-size: 2.75em;"    
          @click="pageChange(true)"
          :class="{ disabled: params.page === cnt || cnt === 0}"
        >
          <font-awesome-icon :icon="['fas', 'angle-right']" />
        </span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {
  computed,
  onBeforeMount,
  onUpdated,
  reactive,
  ref,
  SetupContext,
  toRefs,
  watch,
} from "vue";
import { Article } from "../../types/model";
import { request, METHOD } from "../../utils/api";
import VueScrollTo from "vue-scrollto";
export default {
  props: {
    tagId: {
      type: Number,
      default: () => 0,
    },
    classId: {
      type: Number,
      default: () => 0,
    },
  },
  setup(props: any, ctx: SetupContext) {

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

    const { tagId, classId } = toRefs(props);
    const articles = ref<Article[]>();
    const state = reactive({
      // 分页参数
      params: {
        page: 1,
        limit: 12,
      },
      // 文章总条数
      total: 0,
    });

    // 共有多少页
    const cnt = computed((): number => {
      return Math.ceil(state.total / state.params.limit);
    });

    // 初始化获取文章
    const initArticles = (): void => {
      const url = `/front/article?tagId=${tagId.value}&classId=${classId.value}`;
      request(url, METHOD.GET, state.params).then((resp: any) => {
        articles.value = resp.data.data;
        state.total = resp.data.count;
        // 用nginx代理后会莫名其妙自家加上这两个属性？？？导致样式有问题，手动移除一下
        $("#articles").css("position", "");
        $("#articles").css("height", "");
      });
    };

    // 监听tagId, classId的变化,一旦有变化就更新
    watch([tagId, classId], (): void => {
      initArticles();
    });

    // 点击上一页或下一页
    const pageChange = (type: boolean): void => {
      if (!type) {
        state.params.page -= 1;
      } else {
        state.params.page += 1;
      }
      initArticles();
      VueScrollTo.scrollTo("#articles", 1000);
      // ctx.emit("updateVisable", state.params.page);
    };

    return {
      ...toRefs(state),
      articles,
      cnt,
      pageChange,
    };
  },
};
</script>

<style></style>
