<template>
<div class="advanced-mode">
  <codemirror v-model="levelControl.mbl" :options="editorOptions" @changes="levelControl.storeMbl()"></codemirror>
</div>
</template>

<script>
import 'codemirror/lib/codemirror.js'
import '../services/mathbot-lang.js'
import 'codemirror/theme/monokai.css'
export default {
  name: 'Advanced_mode',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    runCompiled () {
      return this.levelControl.runCompiled
    }
  },
  data () {
    return {
      editorOptions: {
        tabSize: 2,
        styleActiveLine: true,
        lineNumbers: true,
        lineWrapping: false,
        line: true,
        mode: 'text/x-mathbot-lang',
        theme: 'monokai'
      }
    }
  },
  components: {
  }
}
</script>

<style scoped lang="scss">
$grid-space-size: 9vmin;
$grid-background: rgba(0, 0, 0, 0.6);

* {
  text-align: left;
}

.advanced-mode {
  border-top: none;
  background: $grid-background;
  height: 100%;
  width: calc(#{$grid-space-size} * 10);
  margin: 0 auto;
  position: relative;
  font-size: calc(5px + 1.5vmin);

  .bar {
    position: absolute;
    top: 100%;
    display: flex;
    justify-content: center;
    width: 100%;
    z-index: 100;
  }
}
</style>
<style lang="scss">
  .vue-codemirror {
    height: 100%;
    .cm-s-monokai.CodeMirror {
      height: 100%;
      background-color: rgba(0, 0, 0, 0.8);
    }
    .cm-s-monokai .CodeMirror-gutters {
      background-color: rgba(255, 255, 255, 0.2);
    }
  }
</style>
