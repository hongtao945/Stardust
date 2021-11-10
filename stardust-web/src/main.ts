import { createApp } from "vue";
import App from "./App.vue";
import "materialize-css/dist/css/materialize.min.css";
import "./theme/css/index.css";
import "materialize-css/dist/js/materialize.min.js";
import router from "./router";
// font-awesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { faGithub, faQq } from "@fortawesome/free-brands-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import {
  faHome,
  faTags,
  faBookmark,
  faCalendar,
  faPaperPlane,
  faComment,
  faSearch,
  faEnvelopeOpen,
  faEye,
  faLightbulb,
  faThumbsUp,
  faAngleDoubleDown,
  faArrowUp,
  faAngleLeft,
  faAngleRight,
  faCloud,
  faClock,
  faCalendarMinus,
  faHeart,
  faLink,
  faUser,
  faCopyright,
  faChevronLeft,
  faChevronRight,
  faListAlt,
  faListUl,
  faCopy,
  faBars,
  faComments,
  faAddressBook,
  faArchive,
  faWrench,
  faBook,
  faMusic,
  faChartBar,
  faFileWord,
} from "@fortawesome/free-solid-svg-icons";
library.add(
  faHome,
  faTags,
  faBookmark,
  faCalendar,
  faPaperPlane,
  faComment,
  faSearch,
  faEnvelopeOpen,
  faGithub,
  faQq,
  faEye,
  faLightbulb,
  faThumbsUp,
  faAngleDoubleDown,
  faArrowUp,
  faAngleLeft,
  faAngleRight,
  faCloud,
  faClock,
  faCalendarMinus,
  faHeart,
  faComment,
  faLink,
  faUser,
  faCopyright,
  faChevronLeft,
  faChevronRight,
  faListAlt,
  faListUl,
  faCopy,
  faBars,
  faComments,
  faAddressBook,
  faArchive,
  faWrench,
  faBook,
  faMusic,
  faAddressBook,
  faChartBar,
  faFileWord
);

// createApp
const app = createApp(App);
app.component("font-awesome-icon", FontAwesomeIcon);
app.use(router);
app.mount("#app");
