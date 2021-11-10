<template>
  <div>
    <iframe
      src="/static/sakura.html"
      style="width: 100%; height: 60vh; margin-top: -64px"
      scrolling="no"
      frameborder="0"
    ></iframe>
    <main class="content">
      <div id="category-cloud" class="container chip-container">
        <div class="card">
          <div class="card-content">
            <div class="tag-title center-align">
              <font-awesome-icon
                :icon="['fa', 'bookmark']"
              />&nbsp;&nbsp;文章分类
            </div>
            <!--分类列表-->
            <div class="tag-chips">
              <a
                href="javascript:;"
                v-for="classification in classifications"
                :key="classification.classId"
                v-cloak
              >
                <span
                  class="
                    chip
                    center-align
                    waves-effect waves-light
                    chip-default
                  "
                  :style="{ backgroundColor: classification.color }"
                  @click="classId = classification.classId"
                  :class="{ 'chip-active': classification.classId === classId }"
                >
                  {{ classification.name }}
                  <span class="tag-length">{{
                    classification.articleCount
                  }}</span>
                </span>
              </a>
            </div>
          </div>
        </div>
      </div>
      <!--echarts雷达图-->
      <div class="container" data-aos="fade-up" v-show="classId === 0">
        <div class="card">
          <div id="classification-radar" class="card-content"></div>
        </div>
      </div>
      <article-card :classId="classId" v-if="classId !== 0"/>
    </main>
  </div>
</template>

<script lang="ts">
import { nextTick, onBeforeMount } from "@vue/runtime-core";
import { Classification } from "../types/model";
import { ref } from "vue";
import { METHOD, request } from "../utils/api";
import * as echarts from "echarts";
import ArticleCard from '../components/fragments/ArticleCard.vue';
import { useRoute } from 'vue-router';
export default {
  setup() {
    onBeforeMount(() => {
      initClassifications();      
    });

    const classId = ref(0);
    const classifications = ref<Classification[]>();
    // 如果跳转过来时携带有参数的话就给classId赋值，这个classId被监听到有变化时会去查询对应的文章
    if(useRoute().params.id) {   
      classId.value = Number.parseInt(useRoute().params.id as string);
    }

    const initClassifications = async (): Promise<void> => {
      const { data } = await request("/front/classification", METHOD.GET);
      classifications.value = data;   
      nextTick(() => initRadarMap()); 
    };

    const initRadarMap = (): void => {
      const radarChart = echarts.init(
        document.getElementById("classification-radar") as HTMLElement
      );
      const data: Classification[] = classifications.value as Classification[];
      const indicator: any[] = [];
      const value: number[] = [];
      let maxValue = 0;
      data.forEach(item => maxValue = item.articleCount > maxValue ? item.articleCount : maxValue);
      data.forEach(item => {
        const classification = {
          name: item.name,
          max: maxValue
        };
        indicator.push(classification);
        value.push(item.articleCount);
      });

      const option = {
        title: {
          left: "center",
          text: "文章分类雷达图",
          textStyle: {
            fontWeight: 500,
            fontSize: 22,
          },
        },
        tooltip: {},
        radar: {
          name: {
            textStyle: {
              color: "#3C4858",
            },
          },
          max: 8,
          indicator: indicator,
          nameGap: 5,
          center: ["50%", "55%"],
          radius: "66%",
        },
        series: [
          {
            type: "radar",
            color: ["#3ecf8e"],
            itemStyle: { normal: { areaStyle: { type: "default" } } },
            data: [
              {
                value: value,
                name: "文章分类数量",
              },
            ],
          },
        ],
      };

      radarChart.setOption(option);
    };

    return {
      classId,
      classifications,
    };
  },
  components: {ArticleCard},
};
</script>
px
<style scoped>
#classification-radar {
  width: 100%;
  height: 300px; 
}

[v-cloak] {
  display: none;
}
</style>
