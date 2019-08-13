// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";

Vue.config.productionTip = false;

import "../node_modules/bootstrap/dist/css/bootstrap.min.css";

import VueAxios from "vue-axios";
import axios from "axios";

Vue.use(VueAxios, axios);

//const axios = require('axios');
//Vue.prototype.$http = axios;

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>"
});
