<template>
    <touchable-opacity class = "link" :on-press="() => click()">
        <text class= "link" :style="myStyle">{{displayedLink}}</text>
    </touchable-opacity>
</template>

<style>
  .link {
    text-decoration-line: underline;
  }
</style>

<script>
import {Linking} from "react-native"
export default {
  props: ['url', 'name', 'color'],
  data: function() {
    return {
      myStyle : {'color': this.color === undefined ? 'blue' : this.color},
      displayedLink: (this.name === undefined) ? this.url : this.name
    }
  },
  methods: {
    click () {
      Linking.openURL(this.url)
    },
  },
  computed: {
    computeDisplay() {
      displayedLink = (name === null) ? url : name
    }
  }
};
</script>
