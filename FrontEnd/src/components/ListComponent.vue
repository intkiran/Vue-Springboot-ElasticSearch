<template v-if=tables>
  <div v-if="renderComponent">
    <h1>Tables</h1>
    <div class="row">
      <div class="col-md-2">
        <input
          type="text"
          v-model="search"
          class="searchbox_1"
          placeholder="Search table name, desc, domain.."
        />
      </div>
      <div class="col-md-2">
        <button v-on:click="searchBook()" class="btn btn-primary">Search</button>
      </div>
      <div class="col-md-6"></div>
      <div class="col-md-2">
        <router-link :to="{ name: 'create' }" class="btn btn-primary">Create Table</router-link>
      </div>
    </div>

    <table class="table table-hover">
      <thead>
        <tr>
          <th>Id</th>
          <th>Tabe Name</th>
          <th>Table Desc</th>
          <th>Storage Type</th>
          <th>Source System</th>
          <th>Schema</th>
          <th>Schedule</th>
          <th>Row Count</th>
          <th>Data Domain</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(table,index) in tables" :key="index">
          <td>{{ table.id }}</td>
          <td>{{ table.table_name }}</td>
          <td>{{ table.table_desc }}</td>
          <td>{{ table.storage_type }}</td>
          <td>{{ table.source_system }}</td>
          <td>{{ table.schema }}</td>
          <td>{{ table.schedule }}</td>
          <td>{{ table.row_count }}</td>
          <td>{{ table.data_domain }}</td>
          <td>
            <router-link :to="{name: 'edit', params: { id: table.id }}" class="btn btn-primary">Edit</router-link>
          </td>
          <td>
            <button class="btn btn-danger" @click.prevent="deleteTable(table.id,index)">
              <i class="fa fa-trash-alt"></i>
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { EventBus } from "../Events.js";
import EditComponent from "./EditComponent";
import CreateComponent from "./CreateComponent";

export default {
  name: "tables",

  data() {
    return {
      tables: [],
      search: [],
      renderComponent: true,
      interval: null,
      data: null
    };
  },
  components: {
    EditComponent,
    CreateComponent
  },
  created() {
    this.fetchTables();
  },
  mounted() {
    //clearInterval(this.interval);
    //this.fetchTables();
  },
  methods: {
    fetchTables() {
      var results = [];
      let self = this;
      let uri = "http://localhost:8889/catalog/table/all";
      this.axios.get(uri).then(response => {
        console.log("response fetch tables11111", response);
        this.data = response.data;

        this.tables = this.data;
      });
    },

    deleteTable(id, index) {
      console.log("ID ", this.tables);
      console.log("index ", index);
      console.log("before ", this.tables);
      let self = this;

      this.axios
        .delete("http://localhost:8889/catalog/table/" + id)
        .then(response => {
          console.log("ID ", this.tables);
          //.log("index ", this.tables.indexOf(id));
          this.tables.splice(this.tables.indexOf(index), 1);
          console.log("afterore ", this.tables);
        });
    },
    forceRerender() {
      // Remove my-component from the DOM
      this.renderComponent = false;

      this.$nextTick(() => {
        // Add the component back in
        this.renderComponent = true;
      });
    },
    searchBook() {
      this.axios
        .get("http://localhost:8889/catalog/table/search/" + this.search)
        .then(response => {
          this.data = response.data;
          this.tables = this.data;
        })
        .catch(e => {
          this.errors.push(e);
        });
    }
  }
};
</script>
<style>
.searchbox_1 {
  background-color: #fffbf8;
  padding: 7px;
  width: 175px;
  margin: 1px auto;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  border-radius: 6px;
  -webkit-box-shadow: 0 2px 4px 0 rgba(72, 72, 72, 0.83),
    0 10px 15px 0 rgba(126, 126, 126, 0.12),
    0 -2px 6px 1px rgba(199, 199, 199, 0.55) inset,
    0 2px 4px 2px rgba(255, 255, 255, 0.83) inset;
  -moz-box-shadow: 0 2px 4px 0 rgba(72, 72, 72, 0.83),
    0 10px 15px 0 rgba(126, 126, 126, 0.12),
    0 -2px 6px 1px rgba(199, 199, 199, 0.55) inset,
    0 2px 4px 2px rgba(255, 255, 255, 0.83) inset;
  box-shadow: 0 2px 4px 0 rgba(72, 72, 72, 0.83),
    0 10px 15px 0 rgba(126, 126, 126, 0.12),
    0 -2px 6px 1px rgba(199, 199, 199, 0.55) inset,
    0 2px 4px 2px rgba(255, 255, 255, 0.83) inset;
}
</style>