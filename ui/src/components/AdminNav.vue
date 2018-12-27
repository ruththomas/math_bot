<template>
  <div class="pl-0 pr-1" style="width: 200px;">

    <ul class="list-group">
      <li class="list-group-item" @click="updateActive('users')"
          :class="activeDisplay === 'users' ? 'active' : null">Users
      </li>
      <li class="list-group-item" @click="updateActive('levels')"
          :class="activeDisplay === 'levels' ? 'active' : null">Levels
      </li>
      <li class="list-group-item" @click="updateActive('events')"
          :class="activeDisplay === 'events' ? 'active' : null">
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

</style>
