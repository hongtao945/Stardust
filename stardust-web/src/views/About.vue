<template>
  <div>
    <iframe
      src="/static/sakura.html"
      style="width: 100%; height: 60vh; margin-top: -64px"
      scrolling="no"
      frameborder="0"
    ></iframe>
    <main class="content">
      <div id="aboutme" class="container about-container">
        <div class="card">
          <div class="card-content">
            <div class="row">
              <div
                class="post-statis col l4 hide-on-med-and-down"
                data-aos="zoom-in-right"
              >
                <div class="statis">
                  <span class="count"
                    ><a href="/">{{ about?.articleCount }}</a></span
                  >
                  <span class="name">文章</span>
                </div>
                <div class="statis">
                  <span class="count"
                    ><a href="/classifications">{{
                      about?.classificationCount
                    }}</a></span
                  >
                  <span class="name">分类</span>
                </div>
                <div class="statis">
                  <span class="count"
                    ><a href="/tags">{{ about?.tagCount }}</a></span
                  >
                  <span class="name">标签</span>
                </div>
              </div>
              <div class="col s12 m12 l4">
                <div class="profile center-align">
                  <div class="avatar">
                    <img
                      src="https://portrait.gitee.com/uploads/avatars/user/2713/8140513_hongtao945_1617601273.png!avatar200"
                      class="circle responsive-img avatar-img"
                    />
                  </div>
                  <div class="author">
                    <div
                      class="post-statis hide-on-large-only"
                      data-aos="zoom-in-right"
                    >
                      <div class="statis">
                        <span class="count"
                          ><a href="/">{{ about?.articleCount }}</a></span
                        >
                        <span class="name">文章</span>
                      </div>
                      <div class="statis">
                        <span class="count"
                          ><a href="/classifications">{{
                            about?.classificationCount
                          }}</a></span
                        >
                        <span class="name">分类</span>
                      </div>
                      <div class="statis">
                        <span class="count"
                          ><a href="/tags">{{ about?.tagCount }}</a></span
                        >
                        <span class="name">标签</span>
                      </div>
                    </div>
                    <div class="title">Hongtao</div>
                    <div class="career">Bug 设计师</div>
                    <div
                      class="social-link hide-on-large-only"
                      data-aos="zoom-in-left"
                    >
                      <a
                        href="https://github.com/hongtao945"
                        class="tooltipped"
                        target="_blank"
                        data-tooltip="访问我的GitHub"
                        data-position="top"
                        data-delay="50"
                      >
                        <font-awesome-icon :icon="['fab', 'github']" />
                      </a>
                      <a
                        href="mailto:1821997710@qq.com"
                        class="tooltipped"
                        target="_blank"
                        data-tooltip="邮件联系我"
                        data-position="top"
                        data-delay="50"
                      >
                        <font-awesome-icon :icon="['fas', 'envelope-open']" />
                      </a>
                      <a
                        href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=1821997710"
                        class="tooltipped"
                        target="_blank"
                        data-tooltip="QQ: 1821997710"
                        data-position="top"
                        data-delay="50"
                      >
                        <font-awesome-icon :icon="['fab', 'qq']" />
                      </a>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col l4 hide-on-med-and-down" data-aos="zoom-in-left">
                <div class="social-link">
                  <a
                    href="https://github.com/hongtao945"
                    class="tooltipped"
                    target="_blank"
                    data-tooltip="访问我的GitHub"
                    data-position="top"
                    data-delay="50"
                  >
                    <font-awesome-icon :icon="['fab', 'github']" />
                  </a>

                  <a
                    href="mailto:1821997710@qq.com"
                    class="tooltipped"
                    target="_blank"
                    data-tooltip="邮件联系我"
                    data-position="top"
                    data-delay="50"
                  >
                    <font-awesome-icon :icon="['fas', 'envelope-open']" />
                  </a>
                  <a
                    href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=1821997710"
                    class="tooltipped"
                    target="_blank"
                    data-tooltip="QQ: 1821997710"
                    data-position="top"
                    data-delay="50"
                  >
                    <font-awesome-icon :icon="['fab', 'qq']" />
                  </a>
                </div>
              </div>
            </div>

            <div class="introduction center-align" data-aos="fade-up">
              现实的抽象是语言，语言的抽象是程序，程序的抽象是数理逻辑，数理逻辑的抽象是超越认知的真理。
            </div>

            <div id="postCharts" class="post-charts">
              <div class="title center-align" data-aos="zoom-in-up">
                <font-awesome-icon :icon="['fa', 'chart-bar']"/>&nbsp;&nbsp;文章统计图
              </div>
              <div class="row">
                <div class="chart col s12 m6 l4" data-aos="zoom-in-up">
                  <div id="posts-chart"></div>
                </div>

                <div class="chart col s12 m6 l4" data-aos="zoom-in-up">
                  <div id="classifications-chart"></div>
                </div>

                <div class="chart col s12 m6 l4" data-aos="zoom-in-up">
                  <div id="tags-chart"></div>
                </div>
              </div>
            </div>

            <div class="my-skills">
              <div class="title center-align" data-aos="zoom-in-up">
                <font-awesome-icon :icon="['fa', 'wrench']"/>&nbsp;&nbsp;我的技能
              </div>
              <div class="row">
                <div class="col s12 m6 l6" data-aos="fade-up">
                  <div class="skillbar">
                    <div
                      class="skillbar-title"
                      style="
                        width: 90%;
                        background: linear-gradient(to right,#ff0066 0%,#ff00cc 100%);">                    
                      <span>Java</span>
                    </div>
                    <div class="skill-bar-percent">90%</div>
                  </div>
                </div>
                <div class="col s12 m6 l6" data-aos="fade-up">
                  <div class="skillbar">
                    <div
                      class="skillbar-title"
                      style="
                        background: linear-gradient(to right,#9900ff 0%,#cc66ff 100%);
                        width: 70%;
                      "
                    >
                      <span>Javascript</span>
                    </div>
                    <div class="skill-bar-percent">70%</div>
                  </div>
                </div>
                <div class="col s12 m6 l6" data-aos="fade-up">
                  <div class="skillbar">
                    <div
                      class="skillbar-title"
                      style="
                        background: linear-gradient(to right, #2196F3 0%, #42A5F5 100%);
                        width: 80%;
                      "
                    >
                      <span>程序设计</span>
                    </div>
                    <div class="skill-bar-percent">80%</div>
                  </div>
                </div>
                <div class="col s12 m6 l6" data-aos="fade-up">
                  <div class="skillbar">
                    <div
                      class="skillbar-title"
                      style="
                        background: linear-gradient(to right, #4CAF50 0%, #81C784 100%);
                        width: 80%;
                      "
                    >
                      <span>SQL</span>
                    </div>
                    <div class="skill-bar-percent">80%</div>
                  </div>
                </div>
              </div>

              <div class="other-skills chip-container" data-aos="zoom-in-up">
                <div class="sub-title center-align">
                  <font-awesome-icon :icon="['fa', 'book']"/>&nbsp;&nbsp;其他技能
                </div>
                <div class="tag-chips center-align">
                  <router-link
                  :to="{ name: 'tags', params: { id: tag.tagId}}"
                  v-for="tag in about?.tags"
                  :key="tag.tagId"
                >
                    <span
                      class="
                        chip
                        center-align
                        waves-effect waves-light
                        chip-default
                      "
                      >{{ tag.name }}</span
                    >
                  </router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script lang="ts">
import { reactive, ref, toRefs } from "@vue/reactivity";
import { METHOD, request } from "../utils/api";
import { nextTick, onBeforeMount } from "@vue/runtime-core";
import { About, ArticleDate, Tag } from "../types/model";
import * as echarts from "echarts";
export default {
  setup() {
    onBeforeMount(() => {
      initCount();
    });

    const baseAboutUrl = "/front/about";
    const state = reactive({
      articleCount: 0,
      classificationCount: 0,
      tagCount: 0,
    });
    const about = ref<About>();

    const initCount = (): void => {
      request(baseAboutUrl, METHOD.GET).then((resp) => {
        about.value = resp.data;
        nextTick(() => {
          initArticlePublishedChart();
          initClassificationsChart();
          iniTagsChart();
        });       
      });
    };

    // 初始化左边的表
    const initArticlePublishedChart = (): void => {
      // 获取下一个月
      const nextMonth = (date: string): string => {
        const [year, month]: string[] = date.split("-");
        let nextY = Number.parseInt(year);
        let nextM = Number.parseInt(month);
        if (nextM + 1 === 13) {
          return `${nextY + 1}-01`;
        }
        return `${nextY}-${++nextM < 10 ? "0" + nextM : nextM}`;
      };

      // 日期格式化为 yyyy-MM格式
      const timeFormat = (date: Date): string => {
        return `${date.getFullYear()}-${date.getMonth() + 1}`; // yyyy-MM
      };

      const data: ArticleDate[] = about.value
        ?.articleDateVoList as Array<ArticleDate>;
      const chart = echarts.init(
        document.getElementById("posts-chart") as HTMLElement
      );
      const dayTime = 60 * 60 * 24 * 1000;
      const endTime: number = +echarts.number.parseDate(new Date());
      const startTime: number = endTime - 365 * dayTime;
      const startDate = timeFormat(new Date(startTime));
      // 将时间截取成 yyyy-MM格式
      const date = data.map((item) =>
        item.createTime.substring(0, item.createTime.lastIndexOf("-"))
      );
      const totalDate: string[] = Array<string>();
      const totalCount: number[] = Array<number>();

      for (
        let i = 1, curDate = startDate;
        i <= 13;
        i++, curDate = nextMonth(curDate)
      ) {
        const index = date.indexOf(curDate);
        let count = 0;
        if (index !== -1) {
          count = data[index].count;
        }
        totalDate.push(curDate);
        totalCount.push(count);
      }

      const option = {
        title: {
          text: "文章发布统计图",
          top: -5,
          x: "center",
        },
        tooltip: {
          trigger: "axis",
        },
        xAxis: {
          type: "category",
          data: totalDate,
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            name: "文章篇数",
            type: "line",
            color: ["#6772e5"],
            data: totalCount,
            markPoint: {
              symbolSize: 45,
              color: ["#fa755a", "#3ecf8e", "#82d3f4"],
              data: [
                {
                  type: "max",
                  itemStyle: { color: ["#3ecf8e"] },
                  name: "最大值",
                },
                {
                  type: "min",
                  itemStyle: { color: ["#fa755a"] },
                  name: "最小值",
                },
              ],
            },
            markLine: {
              itemStyle: { color: ["#ab47bc"] },
              data: [{ type: "average", name: "平均值" }],
            },
          },
        ],
      };
      chart.setOption(option);
    };

    // 初始化中间的表
    const initClassificationsChart = (): void => {
      const classificationsChart = echarts.init(
        document.getElementById("classifications-chart") as HTMLElement
      );
      const data: any[] = about.value?.classifications.map((item) => {
        return {
          name: item.name,
          value: item.articleCount,
        };
      }) as any[];
      const option = {
        title: {
          text: "文章分类统计图",
          top: -4,
          x: "center",
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        series: [
          {
            name: "分类",
            type: "pie",
            radius: "50%",
            color: [
              "#6772e5",
              "#ff9e0f",
              "#fa755a",
              "#3ecf8e",
              "#82d3f4",
              "#ab47bc",
              "#525f7f",
              "#f51c47",
              "#26A69A",
            ],
            data: data,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      classificationsChart.setOption(option);
    };

    // 初始化右边的表
    const iniTagsChart = (): void => {
      const tagsChart = echarts.init(
        document.getElementById("tags-chart") as HTMLElement
      );
      const data = about.value?.tags.slice(0, 10) as Tag[];
      const names: string[] = data.map((item) => item.name);
      const values: number[] = data.map((item) => item.articleCount);

      const option = {
        title: {
          text: "TOP10 标签统计图",
          top: -5,
          x: "center",
        },
        tooltip: {},
        xAxis: [
          {
            type: "category",
            data: names,
          },
        ],
        yAxis: [
          {
            type: "value",
          },
        ],
        series: [
          {
            type: "bar",
            color: ["#82d3f4"],
            barWidth: 18,
            data: values,
            markPoint: {
              symbolSize: 45,
              data: [
                {
                  type: "max",
                  itemStyle: { color: ["#3ecf8e"] },
                  name: "最大值",
                },
                {
                  type: "min",
                  itemStyle: { color: ["#fa755a"] },
                  name: "最小值",
                },
              ],
            },
            markLine: {
              itemStyle: { color: ["#ab47bc"] },
              data: [{ type: "average", name: "平均值" }],
            },
          },
        ],
      };
      tagsChart.setOption(option);
    };

    return {
      ...toRefs(state),
      about,
    };
  },
  components: {},
};
</script>

<style>
#posts-chart,
#classifications-chart,
#tags-chart {
  width: 100%;
  height: 300px;
  margin: 0.5rem auto;
  padding: 0.5rem;
}
.about-cover {
  height: 75vh;
}
</style>
