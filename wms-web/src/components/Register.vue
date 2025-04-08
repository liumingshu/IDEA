<template>
  <div class="registerBody">
    <div class="registerDiv">
      <div class="register-content">
        <h1 class="register-title">用户注册</h1>
        <form @submit.prevent="onSubmit">
          <el-form :model="registerForm" label-width="100px" :rules="rules" ref="registerForm">
            <el-form-item label="账号" prop="no">
              <el-input style="width: 200px" type="text" v-model="registerForm.no" autocomplete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input style="width: 200px" type="password" v-model="registerForm.password" show-password autocomplete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input style="width: 200px" type="password" v-model="registerForm.confirmPassword" show-password autocomplete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit" :disabled="submitDisabled">注册</el-button>
            </el-form-item>
          </el-form>
        </form>
        <p v-if="message">{{ message }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Register",
  data() {
    return {
      submitDisabled: false,
      registerForm: {
        no: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        no: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: this.checkConfirmPassword, trigger: 'blur' }
        ],
      },
      message: ""
    };
  },
  methods: {
    onSubmit() {
      this.submitDisabled = true;
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          if (this.registerForm.password !== this.registerForm.confirmPassword) {
            this.message = "密码和确认密码不匹配";
            this.submitDisabled = false;
            return;
          }
          // 发送注册请求到后端 API
          axios.post('http://localhost:8080/user/register', this.registerForm)
              .then(response => {
                console.log(response.data);
                if (response.data.success) {
                  this.message = "注册成功";
                } else {
                  this.message = response.data.message || "注册失败";
                }
              })
              .catch(error => {
                console.error(error);
                this.message = "注册失败";
              })
              .finally(() => {
                this.submitDisabled = false;
              });
        } else {
          this.submitDisabled = false;
          console.log('校验失败');
        }
      });
    },
    checkConfirmPassword(rule, value, callback) {
      if (value !== this.registerForm.password) {
        callback(new Error('确认密码与密码不一致'));
      } else {
        callback();
      }
    }
  }
}
</script>

<style scoped>
.registerBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}

.registerDiv {
  position: absolute;
  top: 50%;
  left: 50% ;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: #fff;
  border-radius: 5%;
}

.register-title {
  margin: 20px 0;
  text-align: center;
}

.register-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;
}
</style>