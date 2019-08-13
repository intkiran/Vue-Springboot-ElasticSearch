import Vue from "vue";
import Router from "vue-router";
import HomeComponent from "@/components/HomeComponent.vue";
import CreateComponent from "@/components/CreateComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import EditComponent from "@/components/EditComponent.vue";
Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      name: "home",
      path: "/",
      component: HomeComponent
    },
    {
      name: "create",
      path: "/create",
      component: CreateComponent
    },
    {
      name: "tables",
      path: "/tables",
      component: ListComponent
    },
    {
      name: "edit",
      path: "/edit/:id",
      component: EditComponent
    }
  ]
});
