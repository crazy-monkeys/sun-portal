webpackJsonp([2],{QH6o:function(n,e,i){(n.exports=i("Iqte")(!0)).push([n.i,"\n.claim{height:100%\n}\n.claim .desc{text-align:center;font-size:30px\n}\n.claim .desc img{width:100%;display:inline-block\n}\n.claim .link h2{color:#FF7F00;font-size:24px;height:36px;line-height:36px\n}\n","",{version:3,sources:["/Users/weiying/Documents/project/sun-portal-view/src/components/pages/claim.vue","claim.vue"],names:[],mappings:";AA0DA,OACE;ACzDF;ADwDA,aAKI,iBAAA,CAEA;AC7DJ;ADsDA,iBASM,UAAA,CACA;AC9DN;ADoDA,gBAeM,aAAA,CACA,cAAA,CACA,WAAA,CACA;ACpEN",file:"claim.vue",sourcesContent:["\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n.claim {\n  height: 100%;\n  .desc {\n    // height: 500px;\n    // line-height: 500px;\n    text-align: center;\n    // background: orange;\n    font-size: 30px;\n    img{\n      width: 100%;\n      display: inline-block\n    }\n  }\n  .link{\n    h2{\n      color: #FF7F00;\n      font-size: 24px;\n      height: 36px;\n      line-height: 36px;\n    }  \n  }\n}\n\n","\n.claim{height:100%\n}\n.claim .desc{text-align:center;font-size:30px\n}\n.claim .desc img{width:100%;display:inline-block\n}\n.claim .link h2{color:#FF7F00;font-size:24px;height:36px;line-height:36px\n}\n"]}])},Qp2o:function(n,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var t={name:"Claim",components:{Breadcrumb:i("e8d8").a},data:function(){return{checked:!1,breadcrumbList:[{path:"/warranty/extension",name:"Warranty"},{path:"/warranty/claim",name:"Online Warranty Claim"}]}},methods:{reset:function(){this.checked=!1},go:function(){this.$router.push({name:"ClaimForm"})}}},a=function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("el-container",{staticClass:"claim",attrs:{direction:"vertical"}},[t("Breadcrumb",{attrs:{breadcrumbList:n.breadcrumbList}}),n._v(" "),t("div",{staticClass:"desc"},[t("img",{attrs:{src:i("Ba9w"),alt:"图片"}})]),n._v(" "),t("div",{staticClass:"link"},[t("h2",[t("el-checkbox",{model:{value:n.checked,callback:function(e){n.checked=e},expression:"checked"}},[n._v("Check here to indicate that")])],1),n._v(" "),t("el-button",{attrs:{type:"primary",size:"small",disabled:!n.checked},on:{click:n.go}},[n._v("\n      Go to Warranty Claim Page\n    ")]),n._v(" "),t("el-button",{attrs:{type:"primary",size:"small"},on:{click:n.reset}},[n._v("\n      Reset\n    ")])],1)],1)};a._withStripped=!0;var c={render:a,staticRenderFns:[]},s=c;var l=!1;var r=i("C7Lr")(t,s,!1,function(n){l||i("eSIc")},null,null);r.options.__file="src/components/pages/claim.vue";e.default=r.exports},eSIc:function(n,e,i){var t=i("QH6o");"string"==typeof t&&(t=[[n.i,t,""]]),t.locals&&(n.exports=t.locals);i("FIqI")("635bb4ee",t,!1,{})}});
//# sourceMappingURL=2.39fe5b7ae810de8d88ba.js.map