import { loadEnv, UserConfig } from "vite";
import vue from "@vitejs/plugin-vue";

const viteConfig:UserConfig = {
  build: {
    chunkSizeWarningLimit:1500,
  },
  server: {
    port: 3000,
    host: "localhost",
    proxy: {
      // 无前置请求头
      "/front": {
        ws: false,
        target: "http://localhost:8881",
        changeOrigin: true,
      },
      // 有前置请求头且需重写
      "/api": {
        ws: false,
        target: "http://localhost:8881",
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, "")
      }
    },
    
  },  
  plugins: [vue()],
};

// https://vitejs.dev/config/
// export default defineConfig(viteConfig);
export default viteConfig;

