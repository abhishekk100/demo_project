<script>
import api from "./service/axios";
export default {
  data() {
    return {
      count: 0,
      name: 'Vue JS App',
      usersList: [],
      user: {
        id: null,
        username: '',
        password: ''
      }
    }
  },
  async mounted() {
    this.fetchUser();

  },
  methods: {
    async fetchUser() {
      console.log('fetchUser');
      const response = await api.get("/users/list");
      if (response?.status === 200) {
        this.usersList = response?.data;
      }
    },
    async saveUser() {
      if (!this.user?.username || !this.user?.password) {
        alert("Fill required fields.");
        return;
      }
      const response = await api.post("/users/save", this.user);
      if (response?.status === 200) {
        this.fetchUser();
        console.log("user", response?.data);
        alert("User sucessfully saved.");
      }
    },

    async deleteUser(id) {
      if (!id) return;
      const response = await api.delete(`users/delete/${id}`);
      if (response?.status === 200) {
        this.fetchUser();
        alert(response?.data || "User deleted sucessfully.");
      }
    }
  },

}
</script>

<template>
  <div id="app">
    <h3> Hello {{ name }}</h3>
    <br>
    <div style="display: flex; border: 1px solid;">
      <div style="flex: 1;border: 1px solid; padding: 0.5rem;">
        <h4>Add User</h4>
        <form @submit.prevent style="display: flex; 
        flex-direction: column; justify-content: center; align-items: center; padding: 0.5rem;">
          <div style="display: flex; flex-direction: column; margin-bottom: 10px;">
            <label for="username" style="margin-bottom: 6px;">Username</label>
            <input name="username" v-model="user.username" placeholder="Username" />
          </div>
          <div style="display: flex; flex-direction: column;">
            <label for="password" style="margin-bottom: 6px;">password</label>
            <input name="password" v-model="user.password" placeholder="Password" />
          </div>
          <br>
          <button @click="saveUser" style="width: 25%;">Submit</button>
        </form>
      </div>
      <div style="flex: 1; border: 1px solid; padding: 0.5rem;">
        <h4>User List</h4>
        <table style="width: 100%; border-collapse: collapse;">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Username</th>
              <th>Password</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in usersList">
              <td style="text-align: center;">{{ user.id }}</td>
              <td style="text-align: center;">{{ user.username }}</td>
              <td style="text-align: center;">{{ user.password }}</td>
              <td style="text-align: center; padding: 6px;"><button @click="deleteUser(user.id)">Delete</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
table,
th,
tr,
td {
  border: 1px solid;
}
</style>
