<template>
  <div id="app">
    <!--<img src="./assets/logo.png">-->
    <router-view ></router-view>
	  <mu-dialog :open="muDialog" title="提示">
		    {{muDialogContent}}
		    <mu-flat-button label="确定" slot="actions" primary @click="muDialogClose"/>
		</mu-dialog>
  </div>
</template>

<script>
export default {
  	name: 'app',
  	methods: {
		    muDialogClose () {
		       this.muDialog = false
		    },
		  },
		data() {
			return {
				muDialogContent:"系统繁忙",
				muDialog: false,
				
			}
		},
		created() {
			var self = this;
			this.$root.eventHub.$on('muDialog', (data)=>{
			     self.muDialogContent = data;
			     self.muDialog = true;
			} )
	    appSelf = this;
	 },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
