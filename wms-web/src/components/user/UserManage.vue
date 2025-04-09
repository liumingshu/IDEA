<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "UserManage",
  data() {
    let checkAge = (rule, value,callback) => {
      if(value>150){
        callback(new Error('年龄输入过大'));
      }else{
        callback();
      }
    };
    let checkDuplicate = (rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){
          callback();
        }else {
          callback(new Error('账号已经存在'));
        }
      }).catch(error => {
        console.error('Request failed:', error);
        callback(new Error('请求失败'));
      });
    };
    return {
      tableData: [],
      pageSize: 10, // 默认每页显示条数
      pageNum: 1,
      total: 0,
      name:this.name,
      sex:'',
      sexs:[
        {
          value:'1',
          label:'男'
        },
        {
          value:'0',
          label:'女'
        }
      ],
      id:this.id,
      dialogVisible:false,
      form:{
        id:'',
        no:'',
        name:'',
        password:'',
        age:'',
        phone:'',
        sex:'0',
        roleId:'2'
      },
      rules: {
        no: [
          {required: true,message: '请输入账号', trigger:'blur'},
          {min:3, max:8, message: '长度在3~8个字符', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name: [
          {required: true,message: '请输入名字', trigger:'blur'},
        ],
        password: [
          {required: true,message: '请输入密码', trigger:'blur'},
          {min:3, max:8, message: '长度在3~8个字符', trigger: 'blur'}
        ],
        age: [
          {required: true,message: '请输入年龄', trigger:'blur'},
          {min:1, max:3, message: '长度在1~3位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/,message:'年龄必须为正整数字',trigger: 'blur'},
          {validator:checkAge,trigger: 'blur'}
        ],
//         //这条规则使用正则表达式来验证手机号码的格式。正则表达式 ^1[3|4|5|6|7|8|9][0-9]\d{8}$ 的含义如下：
//           ^：表示匹配字符串的开始。
// 1：手机号码以数字1开头。
// [3|4|5|6|7|8|9]：手机号码的第二位可以是3、4、5、6、7、8或9中的任意一个数字。
// [0-9]：手机号码的第三位可以是0到9中的任意一个数字。
//   \d{8}：手机号码的剩余8位必须是数字，\d 表示数字，{8} 表示恰好8次。
// $：表示匹配字符串的结束。
        phone: [
          {required: true,message: '手机号不能为空', trigger:'blur'},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,message:'请输入正确的手机号码',trigger: 'blur'},
        ],
      }
    }
  },
  created() {
    // 从 localStorage 中获取用户选择的每页显示条数
    const savedPageSize = localStorage.getItem('pageSize');
    if (savedPageSize) {
      this.pageSize = parseInt(savedPageSize, 10);
    }
  },
  mounted() {
    this.loadPost(); // 在组件挂载后调用loadPost方法
  },
  methods: {
    restForm(){
      this.$refs.form.resetFields();
      // 手动清空form对象的值
      this.form = {
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '0',
        roleId: '2'
      };
    },
    del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl+'/user/del?id='+id, this.form).then(res => res.data).then(res=>{
        console.log(res)
        if (res.code == 200) {

          this.$message({
            message:'操作成功',
            type:'success'
          })
          this.loadPost()
        } else {
          this.$message({
            message:'操作失败',
            type:'error'
          });
        }
      })
    },
    mod(row){
      this.form.id = row.id
      this.form.no = row.no
      this.form.name = row.name
      this.form.password = ''
      this.form.age = row.age+''
      this.form.sex = row.sex+''
      this.form.phone = row.phone
      this.form.roleId = row.roleId
      this.dialogVisible = true
    },
    add(){
      this.dialogVisible = true
      this.$nextTick(()=>{
        this.restForm()
      })
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save', this.form).then(res => res.data).then(res=>{
        console.log(res)
        if (res.code == 200) {

          this.$message({
            message:'操作成功',
            type:'success'
          })
          this.dialogVisible = false
          this.loadPost()
          this.restForm()
        } else {
          this.$message({
            message:'操作失败',
            type:'error'
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/user/update', this.form).then(res => res.data).then(res=>{
        console.log(res)
        if (res.code == 200) {

          this.$message({
            message:'操作成功',
            type:'success'
          })
          this.dialogVisible = false
          this.loadPost()
          this.restForm()
        } else {
          this.$message({
            message:'操作失败',
            type:'error'
          });
        }
      })
    },
    save(){
      this.$refs.form.validate((valid) =>{
        if(valid) {
          if(this.form.id){
            this.doMod();
          }else{
            this.doSave();
          }
        }else{
          console.log('error submit!');
          return false;
        }
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val; // 更新当前每页显示条数
      this.pageNum = 1; // 重置当前页码为第一页
      this.loadPost();
      // 将用户选择的每页显示条数保存到 localStorage
      localStorage.setItem('pageSize', val);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    loadGet(){
      this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
        console.log(res)
      })
    },
    resetParam(){
      this.name=''
    },
    loadPost() {
      this.$axios.post('http://localhost:8090/user/listPageC1', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          name:this.name,
          sex:this.sex,
          roleId:'2'
        }
      }).then(res => res.data).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.tableData = res.data;
          this.total = res.total;
        } else {
          alert('获取数据失败');
        }
      })
    },
    loadPost1() {
      this.$axios.post('http://localhost:8090/user/listPageC1', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          id:this.id
        }
      }).then(res => res.data).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.tableData = res.data;
          this.total = res.total;
        } else {
          alert('获取数据失败');
        }
      });
    }

  },
  beforeMount() {
    this.loadPost();
    this.loadPost1()
  }
}
</script>

<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input  v-model="name" placeholder="请输入名字或账号" suffix-icon="el-icon-search" style="width: 300px;" @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost()">查询姓名</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost1">查询账号</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-right: 5px;" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{ backgroundColor: 'dark' }" border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="no" label="账号" width="180">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transitions>{{ scope.row.sex === 1 ? '男' : '女' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{ scope.row.roleId === 0 ? '超级管理员' :
              (scope.row.roleId === 1 ? '管理员' : '用户')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="180">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗" @confirm="del(scope.row.id)" style="margin-left: 5px;">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<style scoped>
</style>