<template>
  <div class="exception-page">
    <div class="img">
      <img :src="config[type].img" />
    </div>
    <div class="content">
      <h1>{{ config[type].title }}</h1>
      <div class="desc">{{ config[type].desc }}</div>
      <div class="action">
        <a class="waves-effect waves-light btn" href="/">返回首页</a>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { reactive, toRefs } from "@vue/reactivity";
import Config from "./typeConfig";

export default {
  name: "ExceptionPage",
  props: {
    type: {
      type: Number,
      default: () => 404,
    },
  },
  setup() {
    const state = reactive({
      config: Config,
    });

    return {
      ...toRefs(state),
    };
  },
};
</script>

<style scoped>
.exception-page {
  margin-top: -64px;
  height: 100vh;
  border-radius: 4px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
}
.desc {
  color: #434e59;
  font-size: 20px;
  line-height: 28px;
  margin-bottom: 16px;
}
h1 {
  color: #434e59;
  font-size: 72px;
  font-weight: 600;
  line-height: 72px;
  margin-bottom: 24px;
}
img {
  max-width: 430px;
}
.img {
  padding-right: 52px;
  zoom: 1;
}
</style>
