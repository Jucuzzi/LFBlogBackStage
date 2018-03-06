<template>
    <div class="sidebar">
        <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index">
                        <template slot="title"><i :class="item.icon"></i>{{ item.title }}</template>
                        <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">{{ subItem.title }}
                        </el-menu-item>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index">
                        <i :class="item.icon"></i>{{ item.title }}
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                items: [
//                  {
//                    
//                      icon: 'el-icon-setting',
//                      index: 'readme',
//                      title: '自述'
//                  },
                    {
                        icon: 'el-icon-date',
                        index: '1',
                        title: '赫马资讯',
                        subs: [
                            {
                                index: 'informationrelease',
                                title: '发布资讯'
                            },
                            {
                                index: 'informationoperation',
                                title: '资讯管理'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-plus',
                        index: '2',
                        title: '奖品兑换',
                        subs: [
                            {
                                index: 'lotteryoperation',
                                title: '优惠券兑换'
                            },
                            {
                                index: 'Lotterymanage',
                                title: '奖品管理'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-menu',
                        index: '3',
                        title: '评论管理',
                        subs: [
                        {
                                index: 'BaseComment',
                                title: '评论管理'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-check',
                        index: '4',
                        title: '用户管理',
                        subs: [
							{
                                index: 'baseforusers',
                                title: '用户管理'
                            },
                            {
                                index: 'addnewOreditUser',
                                title: '编辑用户'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-setting',
                        index: '5',
                        title: '角色管理',
                        subs: [
                            {
                                index: 'RoleList',
                                title: '角色管理'
                            },
                            {
                                index: 'MenuPrvlgConfig',
                                title: '菜单权限管理'
                            },
                            {
                                index: 'BtnList',
                                title: '按钮权限管理'
                            }
                        ]
                    },
   					
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        width: 250px;
        left: 0;
        top: 70px;
        bottom:0;
        background: #2E363F;
    }
    .sidebar > ul {
        height:100%;
    }
</style>
