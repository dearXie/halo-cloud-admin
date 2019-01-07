<template>
    <div class="application_list_container">
        <header class="application_header_warp">
            <div class="title_warp">
                <h1 class="title">
                    <span>{{$route.meta.title}}</span>
                    <Button type="dashed" shape="circle" size="small" icon="md-add">添加应用</Button>
                </h1>
                <p class="grey">应用列表的描述</p>
            </div>
            <div class="tool_bar">
                <Input class="application_search_input" placeholder="服务搜索" type="text" v-model="search"></Input>
                <ButtonGroup>
                    <Button :class="{'ivu-btn-info':type==='1'}">收藏</Button>
                    <Button :class="{'ivu-btn-info':type==='2'}">全部</Button>
                </ButtonGroup>
            </div>
        </header>
        <div class="application_list_warp">
            <div class="application_item" v-for="app in this.applications" @click.stop="goApplicationInfo(app)">
                <header class="application_header">
                    <div class="image">
                        <img src="http://spring.io/img/homepage/icon-spring-framework.svg">
                    </div>
                    <div class="header_info">
                        <p class="title">{{app.name}}</p>
                        <p class="other"><span>{{app.instances.length}}</span> instances</p>
                        <p class="other">
                            <sba-time-ago :date="app.statusTimestamp"/>
                        </p>
                    </div>
                </header>
                <div class="application_body">
                    <div class="body_info">
                        <div class="application_status">
                            <span>status</span>
                            <span :class="{'up': (app.status === 'UP')}">{{app.status}}</span>
                        </div>
                        <div class="application_group">
                            <span>owner</span>
                            <span>某某事业部</span>
                        </div>
                        <div class="application_version">
                            <span>project</span>
                            <span>渠道平台</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="application_footer">
            <iPage :total="total" :pageSize="req.pageSize" curretPage.sync="req.curretPage" @on-change="pageChange"/>
        </div>
    </div>
</template>

<script>
    import { getApplicationPage } from '@/api/application'
    import iPage from '@/components/page'
    import SbaTimeAgo from "../../components/sba/sba-time-ago";

    export default {
        name: "application_list",
        components: {SbaTimeAgo, iPage},
        data(){
            return {
                applications:[],
                total:0,
                search:"",
                type: "2",
                req:{
                    curretPage:1,
                    pageSize:8
                }
            }
        },
        methods:{
            goApplicationInfo(application) {
                this.$router.push({name: 'application_info', params: {name: application.name}});
            },
            getPage(){
                getApplicationPage(this.req.curretPage,this.req.pageSize).then((res)=>{
                    if(res.status === 200 && res.data){
                        if(res.data.code === 200){
                            const pageData = res.data.data;
                            this.applications = pageData.data;
                            this.total = pageData.total
                        }
                    }
                },error => {
                    /* eslint-disable */
                    console.log(error);
                })
            },
            pageChange(val){
                this.req.curretPage=val;
                this.getPage();
            }
        },
        mounted(){
            this.getPage();
        }
    }
</script>

<style lang="less">
    @import "../../style/application.less";
</style>