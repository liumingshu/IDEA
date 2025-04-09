<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246);height: 100%;border-bottom: darkgray 1px solid">
      <Aside></Aside>
    </el-aside>

    <el-container>
      <el-header>
        <Header></Header>
      </el-header>

      <el-main style="height: 100%;">
        <Main></Main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  //eslint-disable-next-line vue/multi-word-component-names
  name:"Index",
  components:{Aside,Header},
}
</script>

<style scoped>
.el-header {
  color:#42b983;
  line-height: 60px;
}
.el-main {
  padding: 5px;
}
.el-aside {
  color: #333;
}
</style>