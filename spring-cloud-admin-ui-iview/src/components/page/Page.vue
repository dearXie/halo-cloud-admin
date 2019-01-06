<template>
    <div class="page_warp">
        <Page :total="total" :page-size="pageSize"/>
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
            }
        }
    }
</script>

<style type="less">

</style>