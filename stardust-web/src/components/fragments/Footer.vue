<template>
  <footer class="page-footer bg-color">
    <!--网站信息-->
    <div
      class="container row center-align"
      style="margin-bottom: 15px !important"
    >
      <!--copyright-->
      <div class="col s12 m8 l8 copy-right">
        Copyright&nbsp;&copy;
        <span id="year">2021</span>
        <a href="javascript:;" target="_blank"
          >&nbsp;&nbsp;Hongtao&nbsp;&nbsp;</a
        >
        |&nbsp;&nbsp;Powered by&nbsp;&nbsp;<a
          href="https://github.com/blinkfox/hexo-theme-matery"
          target="_blank"
          >Matery</a
        >
        <br />
        <span style="display: inline"
          >&nbsp;<font-awesome-icon
            :icon="['fas', 'eye']"
          />&nbsp;总访问量:&nbsp;<span
            class="white-color"
            >{{ pageAndUserView?.pv }}</span
          >&nbsp;次
        </span>
        <span style="display: inline">
          |&nbsp;<font-awesome-icon
            :icon="['fas', 'user']"
          />&nbsp;总访问人数:&nbsp;<span
            class="white-color"
            >{{ pageAndUserView?.uv }}</span
          >&nbsp;人
        </span>
        <br />
        <!--网站运行时间-->
        <span id="sitetime">载入运行时间...</span>
        <br />
      </div>
      <!--联系方式-->
      <div class="col s12 m4 l4 social-link social-statis">
        <!--联系图标-->
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
    
  </footer>
</template>

<script lang="ts">
import { onMounted } from "@vue/runtime-core";
import { METHOD, request } from "../../utils/api";
import { ref } from "vue";
import { PU } from "../../types/model";

export default {
  setup() {
    onMounted(() => {
      request("/front/pu", METHOD.GET).then((resp) => {
        pageAndUserView.value = resp.data;
      });
      const libs: string[] = [
        "/static/libs/time.js",
        "/static/libs/matery.js",
      ]
      importLib(libs);
    });

    // 导入所需依赖
    const importLib = (urls: string[]): void => {
      for (let i = 0; i < urls.length; i++) {
        const s = document.createElement("script");
        s.type = "text/javascript";
        s.src = urls[i];
        document.body.appendChild(s);
      }
      // 不是移动端才导入例子特效,不然移动端会比较卡
      if(!navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){
        const s = document.createElement("script");
        s.type = "text/javascript";
        s.setAttribute("color", "0, 0, 0");
        s.setAttribute("count", "170");
        s.setAttribute("opacity", "0.9");
        // s.setAttribute("pointcolor", "123,231,255");
        s.setAttribute("zindex", "-100");
        s.src = "/static/libs/canvas-nest.min.js";
        document.body.appendChild(s);
      }
    };

    const pageAndUserView = ref<PU>();

    return {
      pageAndUserView,
    };
  },
};
</script>

<style></style>
