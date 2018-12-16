<template>
  <div
    :id="id"
    class="piece"
    :class="[func.placeholder ? 'piece-placeholder' : '', pieceToShow === 'closed' ? 'piece-closed'
      : pieceToShow === 'start' ? 'piece-start'
      : 'piece-middle-or-end',
      func.created_id,
      func.color === 'empty' ? 'piece-drop-shadow' : ''
    ]"
    @click="method ? method($event, func, ind) : ''"
    @mousedown="runCompiled.resetIfFailure"
    data-toggle="tooltip" :title="func.name"
    :style="size ? {height: size, width: size} : {}"
  >
    <!--<div-->
      <!--class="denomination-name"-->
    <!--&gt;-->
      <!--{{func.color}}-->
    <!--</div>-->
    <svg v-if="pieceToShow === 'closed'" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1">
      <linearGradient id="rainbow-border" x1="0%" y1="0%" x2="100%" y2="0%">
        <stop offset="20%" stop-color="#4A90E2" />
        <stop offset="40%" stop-color="#CA7AFF" />
        <stop offset="60%" stop-color="#50E3C2" />
        <stop offset="80%" stop-color="#FF98B1" />
        <stop offset="100%" stop-color="#F25C5C" />
      </linearGradient>
      <rect rx="5" ry="5" height="100" width="100" fill="#000000" :stroke="func.color === 'any' ? 'url(#rainbow-border)' : convertColor(func.color)" stroke-width="2" />
    </svg>

    <svg v-else-if="pieceToShow === 'start'" version="1.1" viewBox="-10 0 85 75" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
      <!-- Generator: Sketch 48.2 (47327) - http://www.bohemiancoding.com/sketch -->
      <title>Combined Shape</title>
      <desc>Created with Sketch.</desc>

      <defs>
        <path d="M304,582.756816 C298.58185,582.954085 294.25,587.408664 294.25,592.875 C294.25,598.341336 298.58185,602.795915 304,602.993184 L304,626.990365 C304,628.652541 302.666404,630 300.990365,630 L232.009635,630 C230.347459,630 229,628.666404 229,626.990365 L229,558.009635 C229,556.347459 230.333596,555 232.009635,555 L300.990365,555 C302.652541,555 304,556.333596 304,558.009635 L304,582.756816 Z" id="path-1"></path>
      </defs>
      <g id="Icons" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" transform="translate(-229.000000, -555.000000)">
        <g
          id="Combined-Shape"
          fill="#000000"
          stroke-width="1"
        >
          <path
            :stroke="func.color === 'any' ? 'url(#rainbow-border)' : convertColor(func.color)"
            d="
            M303.5,582.285514
            L303.5,558.009635
            C303.5,556.614884
            302.381558,555.5
            300.990365,555.5
            L232.009635,555.5
            C230.614884,555.5
            229.5,556.618442
            229.5,558.009635
            L229.5,626.990365
            C229.5,628.385116
            230.618442,629.5
            232.009635,629.5
            L300.990365,629.5
            C302.385116,629.5
            303.5,628.381558
            303.5,626.990365
            L303.5,603.464486
            C298.02767,603.018692
            293.75,598.434342
            293.75,592.875
            C293.75,587.315658
            298.02767,582.731308
            303.5,582.285514 Z"
          ></path>
        </g>
      </g>
    </svg>

    <svg v-else-if="pieceToShow === 'middle'" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 85 75" version="1.1">
      <!-- Generator: Sketch 47.1 (45422) - http://www.bohemiancoding.com/sketch -->
      <title>Combined Shape</title>
      <desc>Created with Sketch.</desc>
      <defs>
        <path d="M9.75,27.7568158 L9.75,3.00963453 C9.75,1.34745928 11.0835964,0 12.7596345,0 L81.7403655,0 C83.4025407,0 84.75,1.33359642 84.75,3.00963453 L84.75,27.7568158 C79.3318504,27.9540853 75,32.4086637 75,37.875 C75,43.3413363 79.3318504,47.7959147 84.75,47.9931842 L84.75,71.9903655 C84.75,73.6525407 83.4164036,75 81.7403655,75 L12.7596345,75 C11.0974593,75 9.75,73.6664036 9.75,71.9903655 L9.75,47.9931842 C4.33185035,47.7959147 0,43.3413363 0,37.875 C0,32.4086637 4.33185035,27.9540853 9.75,27.7568158 Z" id="path-1"/>
      </defs>
      <g id="Symbols" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <g id="Function/ActionMiddle">
          <g id="Combined-Shape">
            <path :stroke="func.color === 'any' ? 'url(#rainbow-border)' : convertColor(func.color)" fill="#000000" stroke-width="1" d="M84.25,48.4644864 C78.7776704,48.0186922 74.5,43.4343422 74.5,37.875 C74.5,32.3156578 78.7776704,27.7313078 84.25,27.2855136 L84.25,3.00963453 C84.25,1.61488395 83.1315575,0.5 81.7403655,0.5 L12.7596345,0.5 C11.3648839,0.5 10.25,1.61844247 10.25,3.00963453 L10.25,28.2389426 L9.76819246,28.2564847 C4.60452726,28.4444887 0.5,32.6921053 0.5,37.875 C0.5,43.0578947 4.60452726,47.3055113 9.76819246,47.4935153 L10.25,47.5110574 L10.25,71.9903655 C10.25,73.3851161 11.3684425,74.5 12.7596345,74.5 L81.7403655,74.5 C83.1351161,74.5 84.25,73.3815575 84.25,71.9903655 L84.25,48.4644864 Z"/>
          </g>
        </g>
      </g>
    </svg>

    <svg  v-else-if="pieceToShow === 'end'" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 85 75" version="1.1">
      <!-- Generator: Sketch 48.2 (47327) - http://www.bohemiancoding.com/sketch -->
      <title>Combined Shape Copy 19</title>
      <desc>Created with Sketch.</desc>
      <defs>
        <path d="M598.778761,602.993184 C593.344629,602.795915 589,598.341336 589,592.875 C589,587.408664 593.344629,582.954085 598.778761,582.756816 L598.778761,558.009635 C598.778761,556.347459 600.116291,555 601.773942,555 L671.00482,555 C672.659012,555 674,556.333596 674,558.009635 L674,626.990365 C674,628.652541 672.66247,630 671.00482,630 L601.773942,630 C600.119749,630 598.778761,628.666404 598.778761,626.990365 L598.778761,602.993184 Z" id="path-1"/>
      </defs>
      <g id="Icons" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" transform="translate(-589.000000, -555.000000)">
        <g id="Combined-Shape-Copy-19">
          <path :stroke="func.color === 'any' ? 'url(#rainbow-border)' : convertColor(func.color)" fill="#000000" stroke-width="1" d="M598.7969,602.493513 L599.278761,602.511006 L599.278761,626.990365 C599.278761,628.385655 600.391294,629.5 601.773942,629.5 L671.00482,629.5 C672.384503,629.5 673.5,628.378225 673.5,626.990365 L673.5,558.009635 C673.5,556.614345 672.387467,555.5 671.00482,555.5 L601.773942,555.5 C600.394258,555.5 599.278761,556.621775 599.278761,558.009635 L599.278761,583.238994 L598.7969,583.256487 C593.61692,583.44453 589.5,587.692441 589.5,592.875 C589.5,598.057559 593.61692,602.30547 598.7969,602.493513 Z"/>
        </g>
      </g>
    </svg>
    <img v-if="func.name !== undefined && (!func.name.length || !func.displayName)" :src="funcAndCmdImages[func.image]" />
    <span class="text" v-else v-html="name"></span>
    <div
      v-if="pieceToShow === 'closed' && showName  "
      class="piece-name"
      :class="func.color"
    >
      <span :style="{opacity: func.name === '' ? 0 : 1}">
        {{func.name === '' ? 'name me' : func.name}}
      </span>
    </div>
  </div>
</template>
<script>
import _ from 'underscore'

export default {
  name: 'puzzle_pieces',
  mounted () {
  },
  computed: {
    levelControl () {
      return this.$store.getters.getLevelControl
    },
    permanentImages () {
      return this.$store.getters.getPermanentImages
    },
    commandImages () {
      return this.permanentImages.cmdImages
    },
    funcImages () {
      return this.permanentImages.funcImages
    },
    funcAndCmdImages () {
      return _.extend(this.funcImages, this.commandImages)
    },
    runCompiled () {
      return this.levelControl.runCompiled
    },
    robotState () {
      return this.runCompiled.robot.state
    },
    name () {
      return this.makeNameHtml(this.func.name)
    }
  },
  methods: {
    makeNameHtml (name) {
      if (name) {
        const text = name.trim()
        return text.split(' ').map((w, _, words) => {
          if (words.length === 1 || w.length > 7) {
            const amtToIncrease = w.length === 1 ? '-20px' : '3px'
            return `<p style="margin: 0; font-size: calc(7.5vmin / ${w.length} + ${amtToIncrease});">${w}</p>`
          } else if (words.length > 3) {
            return `<p style="margin: 0; font-size: calc(7.5vmin / ${words.length});">${w}</p>`
          } else {
            return `<p style="margin: 0; font-size: 2vmin">${w}</p>`
          }
        }).join('')
      }
    },
    convertColor (color) {
      if (this.func.placeholder) {
        return this.levelControl.getColorHex('white')
      } else {
        return this.levelControl.getColorHex(color)
      }
    }
  },
  props: ['id', 'ind', 'func', 'pieceToShow', 'showName', 'method', 'size']
}
</script>

<style scoped lang="scss">
  $piece-margin: 0.5vmin;
  $piece-height: 7.5vmin;
  $click-color: #B8E986;
  $danger-color: #F25C5C;
  $white: #ffffff;
  $black: #353535;
  $blue: #4A90E2;
  $purple: #CA7AFF;
  $green: #50E3C2;
  $pink: #FF98B1;
  $red: #F25C5C;

  .piece {
    position: relative;
    height: min-content;
    width: min-content;
    width: -moz-min-content;
    cursor: grab;
    border-radius: 3px;
    z-index: 111;

    .text {
      position: absolute;
      top: 0;
      width: 100%;
      height: 100%;
      color: white;
      /*font-size: 1.9vmin;*/
      line-height: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    img {
      height: 50%;
      width: auto;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      z-index: 2;
    }

    .piece > svg {
      position: relative;
      z-index: 1;
    }

    .piece-name {
      color: #000000;
      font-size: 1.2vmin;
      font-weight: 500;
      letter-spacing: -0.5px;
      line-height: 1.5vmin;
      text-align: center;
      width: 90%;
      margin: -0.3rem auto;
      border-radius: 3px;
      background-color: #ffffff;
      z-index: 2;
      position: relative;
    }

    .any {
      background: #696969!important; /* For browsers that do not support gradients */
      background: -webkit-linear-gradient($blue, $purple, $green, $pink, $red)!important; /* For Safari 5.1 to 6.0 */
      background: -o-linear-gradient($blue, $purple, $green, $pink, $red)!important; /* For Opera 11.1 to 12.0 */
      background: -moz-linear-gradient($blue, $purple, $green, $pink, $red)!important; /* For Firefox 3.6 to 15 */
      background: linear-gradient(to right, $blue, $purple, $green, $pink, $red)!important; /* Standard syntax (must be last) */
    }
  }

  .piece.piece-closed {
    margin: $piece-margin;
    height: $piece-height;
    width: $piece-height;
  }

  .piece.piece-start, .piece.piece-middle-or-end {
    height: $piece-height;
    width: 8vmin;
  }

  .piece.piece-start {
  }

  .piece.piece-middle-or-end {
    margin: 0 0 0 -1vmin;
  }

  .piece.piece-placeholder {
    opacity: 0.3;
  }
</style>
