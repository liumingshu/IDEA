<script>
  export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name:"Aside"
  }
</script>

<template>
  <el-menu
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    style="height: 100vh;"
    default-active="/Home">
    <el-menu-item index="Home">
      <i class="el-icon-s-home"></i>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item index="/one">
      <i class="el-icon-s-promotion"></i>
      <span>导航一</span>
    </el-menu-item>
    <el-menu-item index="=/two">
      <i class="el-icon-s-promotion"></i>
      <span>导航二</span>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>

</style>