webpackJsonp([3],{"0jB2":function(t,e){},fMHG:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=a("e8d8"),n=a("UZd0"),l=a("dqLd"),i={name:"Extension",components:{Breadcrumb:r.a},data:function(){return{country:l.a.dropValue,tableData:[],breadcrumbList:[{path:"/warranty/extension",name:"Warranty"},{path:"/warranty/extension",name:"Extension"}]}},created:function(){this.getPriceTab()},methods:{formatter:function(t,e,a,r){return console.log(t,e,a,r),"$ "+a},getPriceTab:function(){var t=this;Object(n.e)().then(function(e){1==e.data.code&&(t.tableData=e.data.data)})},sigleBtn:function(){this.$router.push({name:"SigleItem"})},multipleBtn:function(){this.$router.push({name:"MultipleItems"})}},mounted:function(){var t=this;l.a.$on("dropValue",function(e){t.country=e})}},o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",{staticClass:"extension",attrs:{direction:"vertical"}},[a("Breadcrumb",{attrs:{breadcrumbList:t.breadcrumbList}}),t._v(" "),a("div",{staticClass:"desc"},["AU"==t.country?a("el-table",{staticStyle:{width:"100%",margin:"20px 0"},attrs:{data:t.tableData}},[a("el-table-column",{attrs:{prop:"model",label:"Model","header-align":"center",align:"enter",width:""}}),t._v(" "),a("el-table-column",{attrs:{prop:"","header-align":"center",align:"enter",label:"Add 5 Years Parts Warranty",width:""}},[a("el-table-column",{attrs:{prop:"partEarlyBirdDiscount","header-align":"center",align:"enter",formatter:t.formatter,label:"Early-bird Discount",width:""}}),t._v(" "),a("el-table-column",{attrs:{prop:"partStandard",formatter:t.formatter,"header-align":"center",align:"enter",label:"Standard",width:""}})],1),t._v(" "),a("el-table-column",{attrs:{prop:"address","header-align":"center",align:"enter",label:"Add 5 Years Standard Warranty"}},[a("el-table-column",{attrs:{prop:"standardEarlyBirdDiscount","header-align":"center",align:"enter",label:"Early-bird Discount",formatter:t.formatter,width:""}}),t._v(" "),a("el-table-column",{attrs:{prop:"standardStandard",align:"enter",formatter:t.formatter,"header-align":"center",label:"Standard",width:""}})],1)],1):t._e(),t._v(" "),a("p",{staticStyle:{"text-align":"right","font-size":"16px"}},[t._v("Prices are effective from 31 July 2019")])],1),t._v(" "),a("div",{staticClass:"link"},[a("h2",[t._v("\n      Warranty extension online order\n    ")]),t._v(" "),a("ul",[a("li",[a("el-button",{attrs:{type:"text"},on:{click:t.sigleBtn}},[t._v("Sungrow warranty extension for single item")])],1),t._v(" "),a("li",[a("el-button",{attrs:{type:"text"},on:{click:t.multipleBtn}},[t._v("Sungrow warranty extension for multiple items")])],1)])])],1)},staticRenderFns:[]};var s=a("VU/8")(i,o,!1,function(t){a("0jB2")},null,null);e.default=s.exports}});
//# sourceMappingURL=3.542ff0eae81b0d2761ff.js.map