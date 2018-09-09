<template>
 <div class="login">
   <vue-form :state="formstate" @submit.prevent="onSubmit">
     <validate auto-label class="form-group required-field" :class="fieldClassName(formstate.name)">
       <div class="input-group-prepend">
         <span class="input-group-text input-icon" id="login-email-icon-sm"><i class="fa fa-at"></i></span>
       </div>

       <input aria-describedby="login-email-icon-sm" type="text" name="name" class="form-control" placeholder="yours@example.com" required v-model.lazy="loginForm.email">

       <field-messages name="name" show="$touched || $submitted" class="form-control-feedback">
         <div>Success!</div>
         <div slot="required">Name is a required field</div>
       </field-messages>

     </validate>

     <validate auto-label class="form-group required-field" :class="fieldClassName(formstate.password)">
       <div class="input-group-prepend">
         <span class="input-group-text input-icon" id="login-password-1-icon-sm"><i class="fa fa-lock"></i></span>
       </div>

       <input aria-describedby="login-password-icon-sm" type="password" password-strength name="password" class="form-control" placeholder="your password" required v-model.lazy="loginForm.password">

       <field-messages auto-label name="password" show="$touched || $submitted" class="form-control-feedback">
         <div>Success!</div>
         <div slot="required">Password is a required field</div>
         <div slot="password-strength">Password requires UpperCase, LowerCase, Number/SpecialChar and min 8 Chars</div>
       </field-messages>

     </validate>

     <div class="py-2 text-center">
       <button class="btn btn-primary" type="submit">LOG IN</button>
     </div>
   </vue-form>
 </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      formstate: {},
      loginForm: {
        email: '',
        password: '',
        confirmPassword: ''
      }
    }
  },
  methods: {
    fieldClassName (field) {
      // for bootstrap classes
      if (!field) {
        return ''
      }
      if ((field.$touched || field.$submitted) && field.$valid) {
        return 'has-success'
      }

      if ((field.$touched || field.$submitted) && field.$invalid) {
        return 'has-danger'
      }
    },
    onSubmit () {
      console.log(this.formstate.$valid)
    }
  }
}
</script>

<style scoped lang="scss">
  $btn-height: 3rem;
  .signup {
    padding-top: 2vmin;
  }
  .form-group {
    display: flex;
    justify-content: center;
    width: 90%;
    height: $btn-height;
    margin: 1em auto;
    .form-control {
      font-size: 0.75rem;
      height: 100%;
      border-radius: 0 0.25rem 0.25rem 0;
    }
    .input-group-prepend {
      .input-icon {
        border-radius: 0.25rem 0 0 0.25rem;
      }
    }
  }
  .text-center {
    height: 4em;
    padding: 0!important;
    .btn {
      font-size: 1em;
      width: 100%;
      margin: 0;
      height: 100%;
      border-radius: 0 0 0.25rem 0.25rem;
      background-color: rgb(0, 170, 228);
    }
  }
</style>
