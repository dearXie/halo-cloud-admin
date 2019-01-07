<template>
    <div class="page_warp">
        <header class="page_header">
            <div class="">
                <h1>应用详情</h1>
            </div>
        </header>
        <div class="page_block">
            <div class="" v-if="application.instances && application.instances.length > 0" v-for="instance in application.instances">
                {{instance.id}}
                {{JSON.stringify(instance)}}
            </div>
        </div>
    </div>
</template>

<script>
    import {getApplicationInfo} from '@/api/application'
    export default {
        name: "Application_info",
        data(){
            return {
                application:{}
            }
        },
        methods:{
            getApplicationInfo(){
                getApplicationInfo(this.$route.params.name).then(res =>{
                    if(res.status === 200){
                        this.application = res.data;
                    }
                },error =>{

                })
            }
        },
        mounted(){
            this.getApplicationInfo();
        },
        watch:{
            "$route": "getApplicationInfo"
        }
    }
</script>

<style scoped>

</style>