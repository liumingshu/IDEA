<script>
  export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name:"Aside",
    data() {
      return {
        menu:[
          {
            menuClick:'Admin',
            menuName:'管理员管理',
            menuIcon:'el-icon-s-custom'
          },{
            menuClick:'User',
            menuName:'用户管理',
            menuIcon:'el-icon-user-solid'
          }
        ]
      }
    }
  }
</script>

<template>
  <el-menu
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    style="height: 100vh;"
    default-active="/Home"
    router
  >
    <el-menu-item index="Home">
      <i class="el-icon-s-home"></i>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item index="/Admin">
      <i class="el-icon-s-promotion"></i>
      <span>管理员管理</span>
    </el-menu-item>
    <el-menu-item index="/User">
      <i class="el-icon-s-promotion"></i>
      <span>用户管理</span>
    </el-menu-item>
    <el-menu-item index="/storage">
      <i class="el-icon-s-promotion"></i>
      <span>仓库管理</span>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>

</style>