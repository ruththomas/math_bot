<template>
  <div class="admin-nav pl-0 pr-1" style="width: 200px;"
       :class="adminTheme"
  >

    <ul class="list-group">
      <li class="list-group-item" @click="updateActive('users')"
          :class="activeDisplay === 'users' ? 'active' : null">
        <i class="fa fa-user-o" aria-hidden="true"></i>
        Users
      </li>
      <li class="list-group-item" @click="updateActive('levels')"
          :class="activeDisplay === 'levels' ? 'active' : null">
        <i class="fa fa-star-o" aria-hidden="true"></i>
        Levels
      </li>
      <li class="list-group-item" @click="updateActive('events')"
          :class="activeDisplay === 'events' ? 'active' : null">
        <i class="fa fa-calendar" aria-hidden="true"></i>
        Events
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'AdminNav',
  props: ['updateActive', 'activeDisplay'],
  computed: {
    adminTheme () {
      return this.$store.getters.getAdminTheme
    }
  }
}
</script>

<style scoped lang="scss">

  li {

    cursor: pointer;

  }

  li:not(.active):hover {

    background: var(--light);

  }

  .admin-nav.dark {

    li {

      border: 1px solid #444;
      background: var(--dark);
    }

    li.active {

      background-color: #375a7f;
      border-color: #375a7f;
      border-top-color: rgb(55, 90, 127);
      border-right-color: rgb(55, 90, 127);
      border-bottom-color: rgb(55, 90, 127);
      border-left-color: rgb(55, 90, 127);
    }

    li:not(.active):hover {

      background-color: #444;
    }

  }
</style>
