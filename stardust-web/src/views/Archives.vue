<template>
  <div>
    <iframe
      src="/static/sakura.html"
      style="width: 100%; height: 60vh; margin-top: -64px"
      scrolling="no"
      frameborder="0"
    ></iframe>

    <!-- content start -->
    <main class="content">
      <div class="container archive-calendar">
        <div class="card">
          <div id="post-calendar" class="card-content"></div>
        </div>
      </div>
      <archives-article-card/>
    </main>
  </div>
</template>

<script lang="ts">
import { onMounted, ref } from "@vue/runtime-core";
import * as echarts from "echarts";
import { ArticleDate } from "../types/model";
import { METHOD, request } from "../utils/api";
import ArchivesArticleCard from '../components/fragments/ArchivesArticleCard.vue';
export default {
  setup() {
    const baseArichivesUrl = "/front/archives";
    const articleDates = ref<ArticleDate[]>();

    onMounted(() => {
      getArticleDate();
    });

    // 获取文章创作频率表视图对象
    const getArticleDate = (): void => {
      request(baseArichivesUrl, METHOD.GET).then((resp) => {
        articleDates.value = resp.data;
        initCalender();
      });
    };

    // 初始化文章日历
    const initCalender = (): void => {
      const data: ArticleDate[] = articleDates.value as ArticleDate[];
      const myChart = echarts.init(
        document.getElementById("post-calendar") as HTMLElement
      );
      const dayTime = 60 * 60 * 24 * 1000;
      const endTime: number = +echarts.number.parseDate(new Date());
      const startTime: number = endTime - 365 * dayTime;
      const startDate = new Date(startTime);
      const endDate = new Date(endTime);
      const getData = (): Array<any> => {
        const date = data.map((item) => item.createTime);
        const res = new Array<any>();
        for (let time = startTime; time <= endTime; time += dayTime) {
          let formatTime = echarts.format.formatTime("yyyy-MM-dd", time);/*echarts.time.format(time, "yyyy-MM-dd", true);*/
          let count = 0;
          let index = date.indexOf(formatTime);
          if (index !== -1) {
            count = data[index].count;
          }
          res.push([formatTime, count]);
        }
        return res;
      };

      const option = {
        title: {
          top: 0,
          text: "文章日历",
          left: "center",
          textStyle: {
            color: "#3C4858",
          },
        },
        tooltip: {
          padding: 10,
          backgroundColor: "#fff",
          borderColor: "#777",
          borderWidth: 1,
          formatter: function (obj: any) {
            var value = obj.value;
            return (
              '<div style="font-size: 14px;">' +
              value[0] +
              "：" +
              value[1] +
              "</div>"
            );
          },
        },
        visualMap: {
          show: true,
          showLabel: true,
          categories: [0, 1, 2, 3, 4],
          calculable: true,
          inRange: {
            symbol: "rect",
            color: ["#ebedf0", "#c6e48b", "#7bc96f", "#239a3b", "#196127"],
          },
          itemWidth: 12,
          itemHeight: 12,
          orient: "horizontal",
          left: "center",
          bottom: 0,
        },
        calendar: [
          {
            left: "center",
            range: [startDate, endDate],
            cellSize: [14, 14],
            splitLine: {
              show: false,
            },
            itemStyle: {
              color: "#196127",
              borderColor: "#fff",
              borderWidth: 2,
            },
            yearLabel: {
              show: true,
            },
            monthLabel: {
              nameMap: "cn",
              fontSize: 11,
            },
            dayLabel: {
              formatter: "{start}  1st",
              nameMap: "cn",
              fontSize: 11,
            },
          },
        ],
        series: [
          {
            type: "heatmap",
            coordinateSystem: "calendar",
            calendarIndex: 0,
            data: getData(),
          },
        ],
      };
      myChart.setOption(option);
    };
  },
  components: {ArchivesArticleCard},
};
</script>

<style></style>
