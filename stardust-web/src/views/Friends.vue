<template>
  <div>
    <iframe
      src="/static/sakura.html"
      style="width: 100%; height: 60vh; margin-top: -64px"
      scrolling="no"
      frameborder="0"
    ></iframe>
    <main class="content">
      <div class="container friends-container">
        <div class="card">
          <div class="card-content">
            <div class="tag-title center-align">
              <font-awesome-icon
                :icon="['fa', 'address-book']"
              />&nbsp;&nbsp;友情链接
            </div>
            <article id="friends-link" style="position: relative">
              <div class="row tags-posts friend-all">
                <div
                  class="col s12 m6 l4 friend-div aos-init"
                  data-aos="zoom-in-up"
                  v-for="(friend, index) in friends"
                  :key="friend.linkId"
                >
                  <div class="card" :class="'frind-card' + (index + 1)">
                    <div class="frind-ship">
                      <div class="title">
                        <img :src="friend.avatar" alt="img" />
                        <div>
                          <h1 class="friend-name">{{ friend.nickName }}</h1>
                          <p style="position: relative; top: -35px">
                            {{ friend.introduction }}
                          </p>
                        </div>
                      </div>
                      <div class="friend-button">
                        <a
                          :href="friend.link"
                          target="_blank"
                          class="
                            button button-glow button-rounded button-caution
                          "
                        >
                          前去学习
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </article>
          </div>
        </div>
        <!--留言墙-->
        <div
          class="card valine-card aos-init aos-animate"
          data-aos="fade-up"
          id="comment-area"
        >
          <div
            class="comment_headling"
            style="
              font-size: 20px;
              font-weight: 700;
              position: relative;
              left: 20px;
              top: 15px;
              padding-bottom: 5px;
            "
          >
            <span aria-hidden="true">
              <font-awesome-icon :icon="['fa', 'comments']" />&nbsp;
            </span>
            <span>留言</span>
          </div>
          <div id="comment-form">
            <!-- <input type="hidden" name="pid" v-model="pid" /> -->
            <div id="vcomments" class="card-content v" style="display: grid">
              <div class="vwrap">
                <div class="vheader item3">
                  <input
                    name="nick"
                    placeholder="昵称"
                    class="vnick vinput"
                    type="text"
                    v-model="msg.nickname"
                  />
                  <input
                    name="nick"
                    placeholder="邮箱"
                    class="vnick vinput"
                    type="text"
                    v-model="msg.email"
                  />
                  <input
                    name="nick"
                    placeholder="个人网站地址"
                    class="vnick vinput"
                    type="text"
                    v-model="msg.siteUrl"
                  />
                </div>
                <div class="vedit">
                  <textarea
                    id="veditor"
                    class="veditor vinput"
                    :placeholder="placeholder"
                    name="content"
                    required
                    v-model="msg.content"
                  ></textarea>
                  <!-- <div class="vctrl"><span class="vemoji-btn">表情</span> | <span class="vpreview-btn">预览</span></div> -->
                </div>
                <div class="vcontrol" style="float: right">
                  <div class="col col-80 text-right">
                    <button
                      type="button"
                      class="vsubmit vbtn"
                      @click="addMessage"
                    >
                      回复
                    </button>
                  </div>
                </div>
                <div style="display: none" class="vmark"></div>
              </div>
              <div class="vinfo" style="display: block">
                <div class="vcount col">
                  <span class="vnum">{{ total }}</span
                  >&nbsp;评论
                </div>
              </div>
              <div class="center-align">
                <div class="preloader-wrapper big active" v-show="loading">
                  <div class="spinner-layer spinner-blue">
                    <div class="circle-clipper left">
                      <div class="circle"></div>
                    </div>
                    <div class="gap-patch">
                      <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                      <div class="circle"></div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="vlist">
                <div
                  class="vcard"
                  v-for="message in messages"
                  :key="message.messageId"
                  :id="'message' + message.messageId"
                >
                  <img class="vimg" src="../assets/浣熊火箭.png" />
                  <div class="vh">
                    <div class="vhead">
                      <span class="vnick">
                        {{ (message.nickname === null || message.nickname.trim().length === 0) ? '匿名用户' : message.nickname}}
                        </span>
                      <span class="vsys">{{ message.browser }}</span>
                      <span class="vsys">{{ message.os }}</span>
                      <span class="vsys">{{ message.address }}</span>
                    </div>
                    <!--  | dateFormat -->
                    <div class="vmeta">
                      <span class="vtime">{{ message.createTime }}</span>
                      <span
                        class="vat"
                        
                        @click="reply(message.messageId, message.nickname)"
                      >
                        回复
                      </span>
                    </div>
                    <div
                      class="vcontent"                      
                    >
                      <p :style="message.isDelete ? 'color:red' : ''">{{ message.content }}</p>
                    </div>
                    <div
                      class="vquote"
                      v-if="
                        message.children !== undefined &&
                        message.children.length > 0
                      "
                    >
                      <div
                        class="vcard"
                        v-for="child in message.children"
                        :key="child.messageId"
                      >
                        <img class="vimg" src="../assets/黑豹.png" />
                        <div class="vh">
                          <div class="vhead">
                            <span class="vnick">
                              {{ (child.nickname === null || child.nickname.trim().length === 0) ? '匿名用户' : child.nickname}}
                            </span>
                            <span class="vsys">{{ child.browser }}</span>
                            <span class="vsys">{{ child.os }}</span>
                            <span class="vsys">{{ child.address }}</span>
                          </div>
                          <div class="vmeta">
                            <span class="vtime">{{ child.createTime }}</span>
                            <span
                              class="vat"
                              @click="reply(child.messageId, child.nickname)"
                            >
                              
                              回复
                            </span>
                          </div>
                          <div class="vcontent">
                            <p :style="child.isDelete ? 'color:red' : ''">
                              <a class="at" :href="'#message' + child.fid"
                                >@{{ child.fcommentNickname }}</a
                              >&nbsp;&nbsp;{{ child.content }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="vempty" style="display: none"></div>
              <div class="vpage txt-center" v-if="cnt < total">
                <button type="button" class="vsubmit vbtn" @click="loadMore">
                  加载更多
                </button>
              </div>
              <div class="info">
                <div class="power txt-right">
                  Powered By
                  <a href="https://valine.js.org" target="_blank">Valine</a
                  ><br />v1.3.10
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
import { ref, toRefs } from "@vue/reactivity";
import { Link, Message } from "../types/model";
import { METHOD, request } from "../utils/api";
import sweetAlert from "sweetalert";
import { nextTick, onBeforeMount, onMounted } from "@vue/runtime-core";
import { computed, reactive } from "vue";
import VueScrollTo from 'vue-scrollto';
export default {
  setup() {
    const baseFriendsUrl = "/front/friends";

    onBeforeMount(() => {
      initFriendsLink();
      initMessages();
      nextTick(() => {
        const s = document.createElement("script");
        s.type = "text/javascript";
        s.src = "/static/libs/Valine.min.js";
        document.body.appendChild(s);
      });
    });

    const friends = ref<Link[]>();
    const messages = ref<Message[]>();
    const state = reactive({
      msg: {
        fid: 0,
        content: "",
        fcommentNickname: "",
        nickname: localStorage.getItem("visitorNickname"),
        email: localStorage.getItem("visitorEmail"),
        siteUrl: localStorage.getItem("visitorSiteUrl"),
      },
      params: {
        page: 1,
        limit: 4,
      },
      total: 0,
      placeholder: "你想对博主说什么呢。。。",
      loading: false,
    });

    // 添加一条留言
    const addMessage = (): void => {
      if (!state.msg.content || state.msg.content.trim().length === 0) {
        sweetAlert({
          text: "请编辑好后再回复!",
          icon: "error",
        });
        return;
      }
      state.loading = true;
      request(baseFriendsUrl, METHOD.POST, state.msg).then((resp) => {
        state.msg.fid = 0;
        state.msg.content = "";
        state.msg.fcommentNickname = "";
        if (state.msg.nickname && state.msg.nickname.trim().length > 0) {
          localStorage.setItem("visitorNickname", state.msg.nickname);
        }
        if (state.msg.email && state.msg.email.trim().length > 0) {
          localStorage.setItem("visitorEmail", state.msg.email);
        }
        if (state.msg.siteUrl && state.msg.siteUrl.trim().length > 0) {
          localStorage.setItem("visitorSiteUrl", state.msg.siteUrl);
        }
        if (resp.data.code === 200) {
          sweetAlert({
            text: "留言成功!",
            icon: "success",
          });
          state.params.page = 1;
          state.loading = false;
          initMessages();
          return;
        }
        sweetAlert({
          text: "留言失败!",
          icon: "error",
        });
      });
    };

    // 初始化留言墙
    const initMessages = (): void => {
      request(baseFriendsUrl + "/msg", METHOD.GET, state.params).then(
        (resp) => {
          state.total = resp.data.count;
          if (state.params.page === 1) {
            messages.value = resp.data.data;
          } else {
            messages.value = messages.value?.concat(resp.data.data);
          }
        }
      );
    };

    // 当前已显示的数据条数
    const cnt = computed(() => state.params.page * state.params.limit);

    // 初始化友链墙
    const initFriendsLink = (): void => {
      request("/front/friends", METHOD.GET).then((resp) => {
        friends.value = resp.data;
      });
    };

    // 加载更多
    const loadMore = (): void => {
      state.params.page++;
      initMessages();
    };

    /**
     * @description: 评论回复
     * @param fid: 父评论id
     * @param fNickname: 父评论昵称
     */
    const reply = (fid: number, fNickname: string): void => {
      state.msg.fid = fid;
      state.msg.fcommentNickname = fNickname;
      state.placeholder = `@${fNickname}`;
      VueScrollTo.scrollTo("#comment-area", 1000);
    };

    return {
      ...toRefs(state),
      friends,
      messages,
      cnt,
      reply,
      addMessage,
      loadMore,
    };
  },
  data() {
    return {};
  },
  components: {},
};
</script>

<style scoped>
.friends-container {
  margin-top: -100px;
  margin-bottom: 30px;
}

.friends-container .tag-title {
  margin-bottom: 10px;
  color: #3c4858;
  font-size: 1.75rem;
  font-weight: 400;
}

.frind-ship img {
  border-radius: 50%;
}

/* 一下是按钮样式 */

.frind-ship {
  padding: 10px 20px;
}

.frind-ship .title {
  display: flex;
  align-items: center;
}

.frind-ship .title div {
  color: #fff;
  padding-left: 10px;
}

.frind-ship .title img {
  width: 100px;
  height: 100px;
  flex-shrink: 0;
}

.frind-ship .title h1 {
  padding-bottom: 5px;
  border-bottom: 2px solid #fff;
  position: relative;
  top: -15px;
  left: 3px;
}

.friend-button {
  display: flex;
  justify-content: center;
  margin-bottom: -27px;
}

.friend-button a {
  border-radius: 40px;
}

.friend-all .tag-post {
  margin-bottom: 30px;
}

.button-caution {
  background-color: #ff4351;
  border-color: #ff4351;
  color: #fff;
}

.button {
  font-weight: 300;
  font-size: 16px;
  font-family: "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial,
    "Lucida Grande", sans-serif;
  text-decoration: none;
  text-align: center;
  line-height: 40px;
  height: 40px;
  padding: 0 40px;
  margin: 0;
  display: inline-block;
  appearance: none;
  cursor: pointer;
  border: none;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition-property: all;
  transition-property: all;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
}

.title {
  margin-bottom: 0rem !important;
}

.card {
  margin: 3rem 0 1rem 0 !important;
}

.button-glow.button-caution {
  -webkit-animation-name: glowing-caution;
  animation-name: glowing-caution;
}

@-webkit-keyframes glowing-caution {
  from {
    -webkit-box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
    box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
  }

  50% {
    -webkit-box-shadow: 0 0 20px rgba(255, 67, 81, 0.8);
    box-shadow: 0 0 20px rgba(255, 67, 81, 0.8);
  }

  to {
    -webkit-box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
    box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
  }
}

@keyframes glowing-caution {
  from {
    -webkit-box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
    box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
  }

  50% {
    -webkit-box-shadow: 0 0 20px rgba(255, 67, 81, 0.8);
    box-shadow: 0 0 20px rgba(255, 67, 81, 0.8);
  }

  to {
    -webkit-box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
    box-shadow: 0 0 0 rgba(255, 67, 81, 0.3);
  }
}

.button-caution:hover {
  background-color: #ff7680;
  border-color: #ff7680;
  color: #fff;
  text-decoration: none;
}

.frind-card1 {
  background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
}

.frind-card2 {
  background-image: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.frind-card3 {
  background-image: linear-gradient(
    to right,
    #b8cbb8 0%,
    #b8cbb8 0%,
    #b465da 0%,
    #cf6cc9 33%,
    #ee609c 66%,
    #ee609c 100%
  );
}

.frind-card4 {
  background-image: linear-gradient(to right, #6a11cb 0%, #2575fc 100%);
}

.frind-card5 {
  background-image: linear-gradient(to top, #c471f5 0%, #fa71cd 100%);
}

.frind-card6 {
  background-image: linear-gradient(to top, #48c6ef 0%, #6f86d6 100%);
}

.frind-card7 {
  background-image: linear-gradient(to top, #0ba360 0%, #3cba92 100%);
}

.frind-card8 {
  background-image: linear-gradient(
    to top,
    #0c3483 0%,
    #a2b6df 100%,
    #6b8cce 100%,
    #a2b6df 100%
  );
}

.frind-card9 {
  background-image: linear-gradient(to right, #ff758c 0%, #ff7eb3 100%);
}

.frind-card10 {
  background-image: linear-gradient(to top, #f77062 0%, #fe5196 100%);
}

article .card {
  overflow: visible !important;
}
</style>
