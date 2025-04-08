<template>
  <div style="display: flex; line-height: 60px;">
    <div>
      <i class="el-icon-s-fold" style="font-size: 20px"></i>
    </div>
    <div style="flex: 1; text-align: center; font-size: 20px">
      <span>欢迎来到超市管理系统</span>
    </div>
    <el-dropdown>
      <span>{{user.name}}</span><i class="el-icon-arrow-down" style="margin-left: 20px;"></i>
      <el-dropdown-menu slot="dropdown" class="custom-dropdown-menu">
        <el-dropdown-item class="custom-dropdown-item" @click.native="toUser" style="font-size: 20px; margin-top: 5px;">个人中心</el-dropdown-item>
        <el-dropdown-item class="custom-dropdown-item" @click.native="logout" style="font-size: 20px; margin-top: 10px;">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
    };
  },
  methods: {
    toUser() {
      console.log("to_user");
    },
    logout() {
      console.log("logout");
      window.alert("确认退出?");
      this.$router.push("/");
      sessionStorage.clear();
    },
  },
};
</script>