<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "StorageManage",
  data() {

    return {
      tableData: [],
      pageSize: 10, // 默认每页显示条数
      pageNum: 1,
      total: 0,
      name:this.name,
      dialogVisible:false,
      form:{
        id:'',
        name:'',
        remark:'',
      },
      rules: {
        name: [
          {required: true,message: '请输入名字', trigger:'blur'},
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
  methods: {
    restForm(){
      this.$refs.form.resetFields();
      // 手动清空form对象的值
      this.form = {
        no: '',
        name: '',
        remark: '',
      };
    },
    del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl+'/storage/del?id='+id, this.form).then(res => res.data).then(res=>{
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
      this.form.name = row.name
      this.form.remark = row.remark+''
    },
    add(){
      this.dialogVisible = true
      this.$nextTick(()=>{
        this.restForm()
      })
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/storage/save', this.form).then(res => res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/storage/update', this.form).then(res => res.data).then(res=>{
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
    resetParam(){
      this.name=''
    },
    loadPost() {
      this.$axios.post('http://localhost:8090/storage/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          name:this.name,
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
      this.$axios.post('http://localhost:8090/storage/listPage', {
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
      <el-input  v-model="name" placeholder="请输入仓库名" suffix-icon="el-icon-search" style="width: 300px;" @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost()">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-right: 5px;" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{ backgroundColor: 'dark' }" border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="仓库名" width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="80">
      </el-table-column>
      <el-table-column prop="total" label="触发器" width="80">
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
        <el-form-item label="仓库名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="remark">
          <el-col :span="20">
            <el-input v-model="form.remark"></el-input>
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