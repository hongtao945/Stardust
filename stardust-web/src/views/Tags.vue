<template>
  <div>
    <iframe
      src="/static/sakura.html"
      style="width: 100%; height: 60vh; margin-top: -64px"
      scrolling="no"
      frameborder="0"
    ></iframe>
    <main class="content" id="articleTags">
      <!-- 标签列表 -->
      <div id="tags" class="container chip-container">
        <div class="card">
          <div class="card-content">
            <div class="tag-title center-align">
              <font-awesome-icon :icon="['fa', 'tags']" />&nbsp;&nbsp;文章标签
            </div>
            <!-- 标签列表 -->
            <div class="tag-chips">
              <a
                href="javascript:;"
                v-for="tag of tags"
                :key="tag.tagId"
                @click="tagId = tag.tagId"
              >
                <span
                  class="
                    chip
                    center-align
                    waves-effect waves-light
                    chip-default
                  "
                  :class="{'chip-active' : tag.tagId == tagId}"
                  :style="`background-color: ${tag.color}`"
                >
                  {{ tag.name }}
                  <span class="tag-length">{{ tag.articleCount }}</span>
                </span>
              </a>
            </div>
          </div>
        </div>
      </div>
      <!-- 动态标签云 -->
      <div
        id="tag-cloud"
        class="container"
        data-aos="fade-up"
        v-show="tagId === 0"
      >
        <div class="card">
          <div class="card-content">
            <div class="my-tag-title center-align" style="font-size: 1.75rem">
              <font-awesome-icon :icon="['fa', 'cloud']" />&nbsp;文章标签云
            </div>
          </div>
          <div id="tags-cloud" class="card-content"></div>
        </div>
      </div>
      <article-card :tagId="tagId" v-if="tagId !== 0"></article-card>
    </main>
  </div>
</template>

<script lang="ts">
import { ref } from "@vue/reactivity";
import { Tag, TagCloud } from "../types/model";
import { request, METHOD } from "../utils/api";
import { SetupContext, onBeforeMount, nextTick } from "@vue/runtime-core";
import ArticleCard from "../components/fragments/ArticleCard.vue";
import { useRoute } from "vue-router";
import * as echarts from "echarts";
import "echarts-wordcloud";
export default {
  setup(props: any, ctx: SetupContext) {
    onBeforeMount(() => {
      initTags();
    });

    // 保存点击的标签
    const tagId = ref<number>(0);
    const tags = ref<Tag[]>();

    // 如果跳转过来时携带有参数的话就给tagId赋值，这个tagId被监听到有变化时会去查询对应的文章
    if (useRoute().params.id) {
      tagId.value = Number.parseInt(useRoute().params.id as string);
    }

    // 初始化获取所有标签
    const initTags = (): void => {
      request("/front/tags", METHOD.GET).then((resp) => {
        tags.value = resp.data;
        nextTick(() => initTagCloud());
      });
    };

    // 标签云
    const initTagCloud = (): void => {
      const cloud: TagCloud[] = [];
      tags.value?.forEach((tag: Tag) => {
        const tagCloud: TagCloud = {
          name: tag.name,
          value: tag.articleCount,
        };
        cloud.push(tagCloud);
      });
      const chart = echarts.init(
        document.getElementById("tags-cloud") as HTMLElement
      );
      const option = {
        tooltip: {},
        series: [
          {
            type: "wordCloud",
            gridSize: 2,
            sizeRange: [12, 50],
            // rotationRange: [-90, 90],
            rotationRange: [-50, 50],
            shape: "triangle",
            // width: 100,
            // height: 400,
            textStyle: {
              normal: {
                color: function () {
                  return (
                    "rgb(" +
                    [
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160),
                    ].join(",") +
                    ")"
                  );
                },
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: "#333",
              },
            },
            data: cloud,
          },
        ],
      };
      chart.setOption(option);
    };

    return {
      tags,
      tagId,
    };
  },
  components: {
    ArticleCard,
  },
};
</script>

<style>
#tags-cloud {
  width: 100%;
  height: 250px;
}
</style>
