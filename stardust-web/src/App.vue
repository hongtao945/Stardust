<script setup lang="ts">
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import Navbar from "./components/fragments/Navbar.vue";
import Footer from "./components/fragments/Footer.vue";
</script>
<template>
  <!-- 导航栏 -->
  <Navbar />
  <router-view />
  <!-- footer -->
  <Footer />
</template>

<style scoped></style>
