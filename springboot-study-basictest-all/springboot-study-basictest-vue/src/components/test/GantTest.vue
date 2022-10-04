<template>
  <div class="container">
    <div class = "sub-left-container">
      <div class="cabinet">
        <div class="box">
          <div class="box-title">
            <div>title-title</div>
          </div>
          <div class="box-content">
            <div>title-content-1</div>
            <div>title-content-2</div>
            <div>title-content-3</div>
            <div>title-content-4</div>
            <div>title-content-5</div>
          </div>
        </div>
      </div>
    </div>
    <div class = "sub-right-container">
      <div class="cabinet">
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
        <div class="box">
          <div class="box-title">
            <div>content-title</div>
          </div>
          <div class="box-content">
            <div>content-content-1</div>
            <div>content-content-2</div>
            <div>content-content-3</div>
            <div>content-content-4</div>
            <div>content-content-5</div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

export default {
    name: 'EmployeeList',
    data(){
        return {
        }
    },
    methods: {
        getEmployees(){

        }
    },
    created() {
    }
}
</script>
<style>

.container{
  content: "";
  display: block;
  clear: both;
  width: 1800px;
  border: 1px solid red;
}

.sub-left-container {
  float: left;
  width: 20%;
  border: 1px solid skyblue;
}

.sub-right-container {
  float: right;
  width: 80%;
  border: 1px solid greenyellow;
}

.sub-right-container .cabinet {
  display: flex;
  flex-direction: row;
  overflow-x: scroll;
}

.sub-left-container .cabinet .box{
  width: 100%;
  border: 1px solid black;
}

.sub-right-container .cabinet .box{
  width: 300px;
  border: 1px solid black;
  flex: 0 0 auto;
}


</style>