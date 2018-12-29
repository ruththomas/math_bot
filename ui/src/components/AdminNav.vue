<template>
  <div class="pl-0 pr-1" style="width: 200px;">

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
  props: ['updateActive', 'activeDisplay']
}
</script>

<style scoped>

  li {

    cursor: pointer
  }

  li:not(.active):hover {

    background: var(--light);
  }
</style>
