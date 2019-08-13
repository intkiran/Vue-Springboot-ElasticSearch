<template>
  <div>
    <h1>Create A Table</h1>
    <form @submit.prevent="addTable">
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
        <button class="btn btn-primary">Create</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      table: {},
      interval: null
    };
  },
  beforeDestory() {
    clearInterval(this.interval);
  },
  methods: {
    addTable() {
      console.log(this.table);
      let uri = "http://localhost:8889/catalog/table/add";
      this.axios.post(uri, this.table).then(response => {
        this.interval = setInterval(
          function() {
            console.log("response");
            clearInterval(this.interval);
            this.interval = null;
            this.$router.push({
              name: "tables",
              params: { id: response.data.id }
            });
          }.bind(this),
          1000
        );
      });
    }
  }
};
</script>
