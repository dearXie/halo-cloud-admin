<template>
    <div class="page_warp">
        <Page :total="total" :page-size="pageSize" :current="curretPage" @on-change="pageChange"/>
    </div>
</template>

<script>
    export default {
        name: "IPage",
        props:{
            total:{
                require : true,
                type: Number
            },
            pageSize:{
                require: true,
                type: Number
            },
            curretPage:{
                require: true,
                type: Number
            }
        },
        methods:{
            pageChange(val){
                this.$emit("on-change",val);
            }
        }
    }
</script>

<style type="less">

</style>