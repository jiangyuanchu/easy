(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7b5251e9"],{"76fe":function(t,e,a){"use strict";a.d(e,"a",function(){return r}),a.d(e,"b",function(){return s}),a.d(e,"c",function(){return o});var n=a("b775");function r(t){return Object(n["b"])({url:"/job",method:"post",data:t})}function s(t){return Object(n["b"])({url:"/job/search",method:"post",data:t})}function o(t){return Object(n["b"])({url:"/job-record/search",method:"post",data:t})}},ac62:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("a-card",{attrs:{bordered:!1}},[a("div",{staticClass:"table-page-search-wrapper"},[a("a-form",{attrs:{layout:"inline"}},[a("a-row",{attrs:{gutter:48}},[a("a-col",{attrs:{md:8,sm:24}},[a("a-form-item",{attrs:{label:"蜘蛛名"}},[a("a-input",{attrs:{placeholder:""},model:{value:t.searchModel.spiderName,callback:function(e){t.$set(t.searchModel,"spiderName",e)},expression:"searchModel.spiderName"}})],1)],1),t.advanced?[a("a-col",{attrs:{md:8,sm:24}},[a("a-form-item",{attrs:{label:"表名"}},[a("a-input",{attrs:{placeholder:""},model:{value:t.searchModel.spiderTableName,callback:function(e){t.$set(t.searchModel,"spiderTableName",e)},expression:"searchModel.spiderTableName"}})],1)],1)]:t._e(),a("a-col",{attrs:{md:t.advanced?24:8,sm:24}},[a("span",{staticClass:"table-page-search-submitButtons",style:t.advanced&&{float:"right",overflow:"hidden"}||{}},[a("a-button",{attrs:{type:"primary"},on:{click:t.search}},[t._v("查询")]),a("a-button",{staticStyle:{"margin-left":"8px"},on:{click:function(){return t.searchModel={}}}},[t._v("重置")]),a("a",{staticStyle:{"margin-left":"8px"},on:{click:t.toggleAdvanced}},[t._v("\n              "+t._s(t.advanced?"收起":"展开")+"\n              "),a("a-icon",{attrs:{type:t.advanced?"up":"down"}})],1)],1)])],2)],1)],1),a("div",{staticClass:"table-operator"}),a("a-table",{attrs:{columns:t.columns,rowKey:function(t){return t.id},dataSource:t.data,pagination:t.pagination,loading:t.loading},on:{change:t.handleTableChange},scopedSlots:t._u([{key:"status",fn:function(e){return[a("a-badge",{attrs:{status:t._f("statusTypeFilter")(e),text:t._f("statusFilter")(e)}})]}},{key:"action",fn:function(e,n){return[a("a-button",{attrs:{type:"primary",size:"small"},on:{click:function(e){return t.seeResult(n)}}},[t._v("查看爬取结果")])]}}])})],1)},r=[],s=(a("2338"),a("f763"),a("fb37"),a("a506")),o=(a("55a0"),a("6bf2"),a("391c")),c=a.n(o),i=a("2af9"),l=a("76fe");a("b27f");function d(t,e){var a=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter(function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable})),a.push.apply(a,n)}return a}function u(t){for(var e=1;e<arguments.length;e++){var a=null!=arguments[e]?arguments[e]:{};e%2?d(a,!0).forEach(function(e){Object(s["a"])(t,e,a[e])}):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(a)):d(a).forEach(function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(a,e))})}return t}var p={wait:{status:"default",text:"待运行"},running:{status:"processing",text:"运行中"},success:{status:"success",text:"完成"},error:{status:"error",text:"异常"}},f={pageNo:1,pageSize:10,count:0},b=[{title:"蜘蛛名",dataIndex:"spiderName"},{title:"表名",dataIndex:"spiderTableName"},{title:"状态",dataIndex:"status",scopedSlots:{customRender:"status"}},{title:"开始时间",dataIndex:"startTime"},{title:"完成时间",dataIndex:"endTime"},{title:"耗时(秒)",dataIndex:"timeCost"},{title:"爬取数量",dataIndex:"successNum"},{title:"操作",dataIndex:"action",scopedSlots:{customRender:"action"}}],h={name:"JobList",components:{Ellipsis:i["c"]},data:function(){return{advanced:!1,searchModel:c.a.cloneDeep(f),data:[],columns:b,pagination:{},loading:!1,opLoading:!1}},filters:{statusFilter:function(t){return p[t].text},statusTypeFilter:function(t){return p[t].status}},mounted:function(){this.search()},methods:{seeResult:function(t){this.$router.push("/spider/jobRecord/".concat(t.id))},toggleAdvanced:function(){this.advanced=!this.advanced},handleTableChange:function(t,e,a){console.log(t);var n=u({},this.pagination);n.current=t.current,this.pagination=n,this.getList(t.current)},search:function(){this.getList(1)},getList:function(t){var e=this;this.loading=!0,void 0!=t&&(this.searchModel.pageNo=t);var a=c.a.cloneDeep(this.searchModel);Object(l["b"])(a).then(function(t){if(t.success){e.data=t.data;var a=u({},e.pagination);a.total=t.count,e.pagination=a}e.loading=!1})}}},m=h,g=a("6691"),v=Object(g["a"])(m,n,r,!1,null,null,null);e["default"]=v.exports}}]);