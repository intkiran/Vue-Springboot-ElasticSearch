<template>
  <div>
    <h1>Edit A Table</h1>
    <form @submit.prevent="updateTable">
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Id:</label>
            <input type="text" class="form-control" v-model="table.id" />
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label>Schema:</label>
            <input type="text" class="form-control" v-model="table.schema" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Table Name:</label>
            <input type="text" class="form-control" v-model="table.table_name" />
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label>Table Desc:</label>
            <input type="text" class="form-control" v-model="table.table_desc" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Storage Type:</label>
            <input type="text" class="form-control" v-model="table.storage_type" />
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label>Schedule:</label>
            <input type="text" class="form-control" v-model="table.schedule" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Storage System:</label>
            <input type="text" class="form-control" v-model="table.source_system" />
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label>Data as of Date:</label>
            <input type="text" class="form-control" v-model="table.data_as_of_date" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Domain:</label>
            <input type="text" class="form-control" v-model="table.data_domain" />
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-group">
            <label>Load Date:</label>
            <input type="text" class="form-control" v-model="table.load_date" />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label>Row Count:</label>
            <input type="text" class="form-control" v-model="table.row_count" />
          </div>
        </div>
      </div>
      <div class="form-group">
        <button class="btn btn-primary">Update Table</button>
      </div>
    </form>
  </div>
</template>

<script>
import { EventBus } from "../Events.js";

export default {
  data() {
    return {
      table: {},
      interval: null
    };
  },
  created() {
    let uri = `http://localhost:8889/catalog/table/${this.$route.params.id}`;
    this.axios.get(uri).then(response => {
      this.table = response.data;
    });
  },
  beforeDestory() {
    clearInterval(this.interval);
  },
  methods: {
    async updateTable() {
      let uri = `http://localhost:8889/catalog/table/${this.$route.params.id}`;
      console.log("response edit");
      this.axios.post(uri, this.table).then(response => {
        this.interval = setInterval(
          function() {
            console.log("response edit", response.status);
            if (response.status == 200) {
              clearInterval(this.interval);
              this.interval = null;
              this.$router.push({
                name: "tables",
                params: { id: this.table.id }
              });
            }
          }.bind(this),
          1000
        );
      });
    }
  }
};
</script>
