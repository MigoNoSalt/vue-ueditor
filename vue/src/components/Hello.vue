  <template>
    <div class="hello">
      <ueditor v-model="form.content" id="editor" ref="editor" style="width:100%"></ueditor>
      <button @click="check">check</button>
    </div>
  </template>

  <script>
    export default {
      name: 'hello',
      data () {
        return {
          msg: 'Welcome to Your Vue.js App',
          form: {
            content: 'test'
          }
        }
      },
      methods: {
        check() {
          alert(this.form.content)
        }
      }
    }


  </script>

  <!-- Add "scoped" attribute to limit CSS to this component only -->
  <style scoped>
    h1, h2 {
      font-weight: normal;
    }

    ul {
      list-style-type: none;
      padding: 0;
    }

    li {
      display: inline-block;
      margin: 0 10px;
    }

    a {
      color: #42b983;
    }




  </style>
