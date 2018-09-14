<template>
 <div class="auth-error">
   <span>{{errorMessage}}</span>
 </div>
</template>

<script>
export default {
  name: 'Auth_error',
  props: ['errorMessage']
}
</script>

<style scoped lang="scss">
$error-background: #FF4500;
.auth-error {
  background-color: $error-background;
}
</style>
