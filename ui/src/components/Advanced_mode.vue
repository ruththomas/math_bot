<template>
<div class="advanced-mode">
  <codemirror v-model="runCompiled.mbl" :options="editorOptions"></codemirror>
  <control-bar
    :wipe-function="runCompiled.clearMbl"
    :start="runCompiled.startMbl"
    :toggle-put="(n) => {}"
  ></control-bar>
</div>
</template>

<script>
import ControlBar from './Control_bar'
import 'codemirror/mode/commonlisp/commonlisp.js'
import 'codemirror/theme/monokai.css'
export default {
  name: 'Advanced_mode',
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    gridMap () {
      return this.levelControl.continent.gridMap
    },
    robot () {
      return this.levelControl.robot
    },
    robotCarrying () {
      return this.robot.robotCarrying
    },
    problem () {
      return this.levelControl.continent.problem.problem
    },
    mainFunction () {
      return this.levelControl.functions.main
    },
    mainFunctionFunc () {
      return this.mainFunction.func
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    }
  },
  data () {
    return {
      editorOptions: {
        tabSize: 4,
        styleActiveLine: true,
        lineNumbers: true,
        lineWrapping: false,
        line: true,
        mode: 'text/x-common-lisp',
        theme: 'monokai'
      }
    }
  },
  components: {
    ControlBar
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
  height: 90%;
  width: calc(#{$grid-space-size} * 10);
  margin: 0 auto;
  position: relative;

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
