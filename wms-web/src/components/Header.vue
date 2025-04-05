<template>
  <div style="display: flex;line-height: 60px;">
    <div>
      <i class="el-icon-s-fold" style="font-size: 20px"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 20px">
      <span>欢迎来到超市管理系统</span>
    </div>
  <el-dropdown>
    <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 20px;"></i>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
      <el-dropdown-item @click.native="logout">退出登录w</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>

  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name:"Header",
  methods:{
    toUser(){
      console.log('to_user')
    },
    logout(){
      console.log('logout')
    }
  }
}
</script>