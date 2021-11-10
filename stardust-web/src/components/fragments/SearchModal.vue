// 搜索对话框
<template>
  <!-- 搜索遮罩框 -->
  <div class="modal" id="searchModal">
    <div class="modal-content">
      <div class="search-header">
        <span class="title"><font-awesome-icon :icon="['fa', 'search']"/>&nbsp;&nbsp;搜索</span>
        <input
          class="search-input"
          id="searchInput"
          name="s"
          placeholder="请输入搜索的关键字进行检索"
          type="search"
          v-model="keyword"
        />
      </div>
      <div id="searchResult">
        <ul class="search-result-list" v-if="searchResult && searchResult.length > 0">
          <li v-for="item in searchResult" :key="item.articleId">
            <a
              :href="`/article/${item.articleId}`"
              class="search-result-title"
              v-html="item.title"
            ></a>
            <p class="search-result search-result-summary" v-html="item.summary"></p>
            <p class="search-result" v-html="item.htmlContent"></p>
          </li>
        </ul>
        <ul class="search-result-list" v-else>
          <li>
            <span class="search-result-title"
              >没有搜索到结果哦，可能是您输入的关键字太少了</span
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { reactive, ref, toRefs } from "@vue/reactivity";
import { onMounted, watch } from "@vue/runtime-core";
import { METHOD, request } from "../../utils/api";
import { ArticleSearchResult } from "../../types/model";
export default {
  setup() {

    const searchResult = ref<ArticleSearchResult[]>();
    const state = reactive({
      keyword: "",
    });

    // 点击搜索
    const toSearch = (): void => {
      if (state.keyword.trim().length === 0) {
        return;
      }
      request(
        `/front/article/search?keyword=${state.keyword}`,
        METHOD.GET
      ).then((resp) => {
        searchResult.value = resp.data;
      });
    };

    watch(state, () => {
      toSearch();
    })

    return {
      ...toRefs(state),
      searchResult,
      toSearch,
    };
  },
};
</script>

<style></style>
