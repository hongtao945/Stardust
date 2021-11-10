<!-- 页面顶部导航栏 -->
<template>
  <header class="navbar-fixed">
    <!-- 导航栏 -->
    <nav class="nav-transparent bg-color" id="headNav">
      <div class="nav-wrapper container" id="navContainer">
        <!--LOGO-->
        <div class="brand-logo">
          <a class="waves-effect waves-light" href="/">
            <img src="/static/images/logo.png" class="logo-img" alt="LOGO"> 
            <span class="logo-span">&nbsp;Stardust</span>
          </a>
        </div>
        <ul class="right nav-menu">
          <li class="hide-on-med-and-down nav-item">
            <a class="waves-effect waves-light" href="/">
              <font-awesome-icon :icon="['fas', 'home']" />&nbsp;
              <span>首页</span>
            </a>
          </li>
          <li class="hide-on-med-and-down nav-item">
            <a class="waves-effect waves-light" href="/tags">
              <font-awesome-icon :icon="['fas', 'tags']" />&nbsp;
              <span>标签</span>
            </a>
          </li>
          <li class="hide-on-med-and-down nav-item">
            <a class="waves-effect waves-light" href="/classifications">
              <font-awesome-icon :icon="['fas', 'bookmark']" />&nbsp;
              <span>分类</span>
            </a>
          </li>
          <li class="hide-on-med-and-down nav-item">
            <a class="waves-effect waves-light" href="/archives">
              <font-awesome-icon :icon="['fas', 'archive']" />&nbsp;
              <span>归档</span>
            </a>
          </li>
          <li class="hide-on-med-and-down nav-item">
            <a class="waves-effect waves-light" href="/about">
              <font-awesome-icon :icon="['fas', 'paper-plane']" />&nbsp;
              <span>关于我</span>
            </a>
          </li>
          <li class="hide-on-med-and-down nav-item">
            <a class="waves-effect waves-light" href="/friends">
              <font-awesome-icon :icon="['fas', 'address-book']" />&nbsp;
              <span>友链</span>
            </a>
          </li>
          <li class="nav-show">
            <a
              class="modal-trigger waves-effect waves-light"
              id="searchBtn"
              style="zoom: 0.85;color: lightsalmon;"
              href="#searchModal"
            >
              <font-awesome-icon :icon="['fas', 'search']" />
            </a>
          </li>
        </ul>
      </div>
      <!--移动设备导航栏-->
      <a
        class="sidenav-trigger button-collapse"
        data-target="mobile-nav"
        href="#"
        style="color: lightsalmon"
      >
        <font-awesome-icon :icon="['fa', 'bars']" />
      </a>
      <!--侧边导航栏-->
      <div class="side-nav sidenav" id="mobile-nav">
        <!--头部-->
        <div class="mobile-head bg-color">
          <div class="logo-name" style="margin-top: 10px">Stardust</div>
          <div class="logo-desc">晚风拂柳笛声残，夕阳山外山。</div>
        </div>
        <!--侧边导航菜单-->
        <ul class="menu-list mobile-menu-list">
          <li class="m-nav-item">
            <a class="waves-effect waves-light" href="/">
              <font-awesome-icon :icon="['fas', 'home']" />&nbsp;首页
            </a>
          </li>
          <li class="m-nav-item">
            <a class="waves-effect waves-light" href="/tags">
              <font-awesome-icon :icon="['fas', 'tags']" />&nbsp;标签
            </a>
          </li>
          <li class="m-nav-item">
            <a class="waves-effect waves-light" href="/classifications">
              <font-awesome-icon :icon="['fas', 'bookmark']" />&nbsp;分类
            </a>
          </li>
          <li class="m-nav-item">
            <a class="waves-effect waves-light" href="/archives">
              <font-awesome-icon :icon="['fas', 'archive']" />&nbsp;归档
            </a>
          </li>
          <li class="m-nav-item">
            <a class="waves-effect waves-light" href="/about">
              <font-awesome-icon :icon="['fas', 'paper-plane']" />&nbsp;关于
            </a>
          </li>
          <li class="m-nav-item">
            <a class="waves-effect waves-light" href="/friends">
              <font-awesome-icon
                :icon="['fas', 'address-book']"
              />&nbsp;友情链接
            </a>
          </li>
          <li>
            <div class="divider"></div>
          </li>
        </ul>
      </div>
      <!--github-corner-->
      <a
        class="github-corner tooltipped hide-on-med-and-down"
        data-delay="50"
        data-position="left"
        data-tooltip="Fork Me"
        href="https://github.com/hongtao945"
        target="_blank"
      >
        <svg viewBox="0 0 250 250" aria-hidden="true">
          <path d="M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"></path>
          <path
            d="M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2"
            fill="currentColor"
            style="transform-origin: 130px 106px"
            class="octo-arm"
          ></path>
          <path
            d="M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z"
            fill="currentColor"
            class="octo-body"
          ></path>
        </svg>
      </a>
      <!--end github-corner-->
    </nav>
    <!-- 进度条 -->
    <div class="progress-bar"></div>
    <!-- 搜索框组件 -->
    <search-modal />

    <!-- 回到顶部按钮 -->
    <div class="top-scroll" id="backTop">
      <a
        class="btn-floating btn-large waves-effect waves-light my-icon"
        href="#"
      >
        <font-awesome-icon :icon="['fa', 'arrow-up']" />
      </a>
    </div>
  </header>
</template>

<script lang="ts">
import { onBeforeMount, onMounted } from "@vue/runtime-core";
import { METHOD, request } from "../../utils/api";
import SearchModal from "./SearchModal.vue";
export default {
  setup() {
    onBeforeMount(() => {
      request("/front/increment", METHOD.GET);
    });
  },
  components: { SearchModal },
};
</script>

<style>
.github-emoji {
  height: 2em;
  width: 2em;
  display: inline-block !important;
  position: relative;
  margin: 0 3px !important;
  padding: 0;
}

.github-emoji:hover {
  animation: emoji-face 5s infinite ease-in-out;
}

@keyframes emoji-face {
  2% {
    transform: translate(0, 1.5px) rotate(1.5deg);
  }
  4% {
    transform: translate(0, -1.5px) rotate(-0.5deg);
  }
  6% {
    transform: translate(0, 1.5px) rotate(-1.5deg);
  }
  8% {
    transform: translate(0, -1.5px) rotate(-1.5deg);
  }
  10% {
    transform: translate(0, 2.5px) rotate(1.5deg);
  }
  12% {
    transform: translate(0, -0.5px) rotate(1.5deg);
  }
  14% {
    transform: translate(0, -1.5px) rotate(1.5deg);
  }
  16% {
    transform: translate(0, -0.5px) rotate(-1.5deg);
  }
  18% {
    transform: translate(0, 0.5px) rotate(-1.5deg);
  }
  20% {
    transform: translate(0, -1.5px) rotate(2.5deg);
  }
  22% {
    transform: translate(0, 0.5px) rotate(-1.5deg);
  }
  24% {
    transform: translate(0, 1.5px) rotate(1.5deg);
  }
  26% {
    transform: translate(0, 0.5px) rotate(0.5deg);
  }
  28% {
    transform: translate(0, 0.5px) rotate(1.5deg);
  }
}

.nav-transparent .github-corner {
  display: none !important;
}

.github-corner {
  position: absolute;
  z-index: 10;
  top: 0;
  right: 0;
  border: 0;
  transform: scale(1.1);
}

.github-corner svg {
  color: #0f9d58;
  fill: #fff;
  height: 64px;
  width: 64px;
}

.github-corner:hover .octo-arm {
  animation: a 0.56s ease-in-out;
}

.github-corner .octo-arm {
  animation: none;
}
</style>
