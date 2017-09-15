/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
if(!myfaces._impl.core._Runtime.fetchNamespace(_PFX_XHR+"_AjaxResponseJSF22")){myfaces._impl.core._Runtime.reserveNamespace(_PFX_XHR+"_AjaxResponseJSF22",new function(){var W="partial-response";var e="changes",H="update",Q="delete",Y="insert",D="eval",G="error",A="attributes",I="extension",a="redirect";var L="javax.faces.ViewState",B="javax.faces.ClientWindow",F="javax.faces.ViewRoot",T="javax.faces.ViewHead",c="javax.faces.ViewBody",R=myfaces._impl.core._Runtime,Z=myfaces._impl._util._Lang,K=myfaces._impl._util._Dom;this.processResponse=function(m,j){j._mfInternal=j._mfInternal||{};var i=j._mfInternal;i._updateElems=[];i._viewStateForms=[];i._clientWindowForms=[];try{if(!m||!Z.exists(m,"responseXML")){throw Z.makeException(new Error(),J().EMPTY_RESPONSE,J().EMPTY_RESPONSE,_nameSpace,"processResponse","");}var l=m.responseXML;var o=Z.fetchXMLErrorMessage(m.responseText||m.response,l);if(o){throw g(new Error(),o.errorMessage+"\n"+o.sourceText+"\n"+o.visualError+"\n","processResponse");}var p=l.childNodes[0];if("undefined"==typeof p||p==null){throw g(new Error(),"No child nodes for response","processResponse");}else{if(p.tagName!=W){p=p.nextSibling;if(!p||p.tagName!=W){throw g(new Error(),"Partial response not set","processResponse");}}}var q=p.childNodes.length;for(var n=0;n<q;n++){var h=p.childNodes[n];var k=h.tagName;if(k==G){P(m,j,h);}else{if(k==a){X(m,j,h);}else{if(k==e){V(m,j,h);}}}}b(j);J().sendEvent(m,j,J()["SUCCESS"]);}finally{delete i._updateElems;delete i._viewStateForms;delete i._clientWindowForms;}};var b=function(h){var i=h._mfInternal;if(i._viewStateForms&&i._viewStateForms.length){Z.arrForEach(i._viewStateForms,function(j){f(L,h,j);},0,this);}if(i._clientWindowForms&&i._clientWindowForms.length){Z.arrForEach(i._clientWindowForms,function(j){f(B,h,j);},0,this);}};var f=function(m,j,h){var l=Z.byId(h.form);if(!h){return ;}var k=(l.elements)?l.elements[m]:null;if(k){K.setAttribute(k,"value",h.newValue);}else{if(!k){var i=K.getDummyPlaceHolder();i.innerHTML=["<input type='hidden'","id='",h.id,"' name='",m,"' value='",h.newValue,"' />"].join("");try{l.appendChild(i.childNodes[0]);}finally{i.innerHTML="";}}}};var P=function(l,i,k){var j=k.firstChild.textContent||k.firstChild.text||"",h=k.childNodes[1].firstChild.data||"";J().sendError(l,i,J().SERVER_ERROR,j,h,"myfaces._impl.xhrCore._AjaxResponse","_processError");};var J=function(){return R.getGlobalConfig("jsfAjaxImpl",myfaces._impl.core.Impl);};var X=function(j,h,i){var k=i.getAttribute("url");if(!k){throw g(new Error(),Z.getMessage("ERR_RED_URL",null,"_AjaxResponse.processRedirect"),"_processRedirect");}k=Z.trim(k);if(k==""){return false;}window.location=k;return true;};var V=function(m,j,l){var k=l.childNodes;for(var h=0;h<k.length;h++){switch(k[h].tagName){case H:M(m,j,k[h]);break;case D:Z.globalEval(k[h].firstChild.data);break;case Y:d(m,j,k[h]);break;case Q:U(m,j,k[h]);break;case A:N(m,j,k[h]);break;case I:break;default:throw g(new Error(),"_AjaxResponse.processChanges: Illegal Command Issued","_processChanges");}}return true;};var M=function(o,k,n){var j=n.getAttribute("id");var m=j.indexOf(L);var l=j.indexOf(B);var h=k._mfInternal;var i=function(r,x){var v=Math.max(0,r-1);var w=(v)?j.substr(0,v):null;var t=(w)?document.getElementById(w):document.body;var s=K.findByTagName(t,"form");if(s){for(var u=s.length-1;u>=0;u--){x.push({form:s[u].id,newValue:n.firstChild.nodeValue,id:j+jsf.separatorchar+u});}}};if(m!=-1){i(m,h._viewStateForms);}else{if(l!=-1){i(l,h._clientWindowForms);}else{var p=K.concatCDATABlocks(n);switch(n.getAttribute("id")){case F:p=p.substring(p.indexOf("<html"));var q=O(o,k,p);("undefined"!=typeof q&&null!=q)?S(o,k,p,q):S(o,k,p);break;case T:O(o,k,p);break;case c:S(o,k,p);break;default:E(o,k,n.getAttribute("id"),p);break;}}}return true;};var O=function(l,j,q){var m=R.browser.isWebKit,o=(!m)?Z.parseXML(q):null,k=null;if(!m&&Z.isXMLParseError(o)){o=Z.parseXML(q.replace(/<!\-\-[\s\n]*<!\-\-/g,"<!--").replace(/\/\/-->[\s\n]*\/\/-->/g,"//-->"));}if(m||Z.isXMLParseError(o)){var i=new (R.getGlobalConfig("updateParser",myfaces._impl._util._HtmlStripper))();var p=i.parse(q,"head");k=Z.parseXML("<head>"+p+"</head>");if(Z.isXMLParseError(k)){try{k=K.createElement("head");k.innerHTML=p;}catch(n){throw g(new Error(),"Error head replacement failed reason:"+n.toString(),"_replaceHead");}}}else{k=o.getElementsByTagName("head")[0];}var h=K.findByTagNames(document.getElementsByTagName("head")[0],{"link":true,"style":true});K.runCss(k,true);K.deleteItems(h);K.runScripts(k,true);return o;};var S=function(k,i,t){var p=document.getElementsByTagName("body")[0],s=document.createElement("div"),l=R.browser.isWebKit;s.id="myfaces_bodyplaceholder";K._removeChildNodes(p);p.innerHTML="";p.appendChild(s);var n,r=null,h;if(!l){r=(arguments.length>3)?arguments[3]:Z.parseXML(t);}if(!l&&Z.isXMLParseError(r)){r=Z.parseXML(t.replace(/<!\-\-[\s\n]*<!\-\-/g,"<!--").replace(/\/\/-->[\s\n]*\/\/-->/g,"//-->"));}if(l||Z.isXMLParseError(r)){h=new (R.getGlobalConfig("updateParser",myfaces._impl._util._HtmlStripper))();n=h.parse(t,"body");}else{var o=r.getElementsByTagName("body")[0];var m=R.browser;if(!m.isIEMobile||m.isIEMobile>=7){for(var j=0;j<o.attributes.length;j++){var q=o.attributes[j].value;if(q){K.setAttribute(p,o.attributes[j].name,q);}}}}h=new (R.getGlobalConfig("updateParser",myfaces._impl._util._HtmlStripper))();n=h.parse(t,"body");E(k,i,s,n);};var E=function(k,i,l,h){var j=(!Z.isString(l))?l:K.byIdOrName(l);if(!j){throw g(Z.getMessage("ERR_ITEM_ID_NOTFOUND",null,"_AjaxResponse.replaceHtmlItem",(l)?l.toString():"undefined"));}return K.outerHTML(j,h);};var d=function(l,h,k){var j=C(l,h,k);if(j){var i=K.byIdOrName(j.opId);if(!i){throw g(new Error(),Z.getMessage("ERR_PPR_INSERTBEFID_1",null,"_AjaxResponse.processInsert",j.opId),"_processInsert");}K[j.insertType](i,j.cDataBlock);}};var C=function(p,j,l){var n=K.concatCDATABlocks,s="insertBefore",k="insertAfter",i=l.getAttribute("id"),m=l.getAttribute("before"),q=l.getAttribute("after"),r={};if(i&&m&&!q){r.insertType=s;r.opId=m;r.cDataBlock=n(l);}else{if(i&&!m&&q){r.insertType=k;r.opId=q;r.cDataBlock=n(l);}else{if(!i){var o=l.childNodes[0].tagName;if(o!="before"&&o!="after"){throw g(new Error(),Z.getMessage("ERR_PPR_INSERTBEFID"),"_parseInsertData");}o=o.toLowerCase();var h=l.childNodes[0].getAttribute("id");r.insertType=(o=="before")?s:k;r.opId=h;r.cDataBlock=n(l.childNodes[0]);}else{throw g(new Error(),[Z.getMessage("ERR_PPR_IDREQ"),"\n ",Z.getMessage("ERR_PPR_INSERTBEFID")].join(""),"_parseInsertData");}}}r.opId=Z.trim(r.opId);return r;};var U=function(l,i,k){var m=k.getAttribute("id");if(!m){throw g(new Error(),Z.getMessage("ERR_PPR_UNKNOWNCID",null,"_AjaxResponse.processDelete",""),"_processDelete");}var j=K.byIdOrName(m);if(!j){throw g(new Error(),Z.getMessage("ERR_PPR_UNKNOWNCID",null,"_AjaxResponse.processDelete",m),"_processDelete");}var h=K.getParent(j,"form");if(null!=h){i._mfInternal._viewStateForms.push(h);}K.deleteItem(j);};var N=function(l,h,j){var i=j.getAttribute("id");if(!i){throw g(new Error(),"Error in attributes, id not in xml markup","_processAttributes");}var q=j.childNodes;if(!q){return ;}for(var o=0;o<q.length;o++){var k=q[o],p=k.getAttribute("name"),n=k.getAttribute("value");if(!p){continue;}p=Z.trim(p);if("undefined"==typeof n||null==n){n="";}switch(i){case F:throw g(new Error(),Z.getMessage("ERR_NO_VIEWROOTATTR",null,"_AjaxResponse.processAttributes"),"_processAttributes");case T:throw g(new Error(),Z.getMessage("ERR_NO_HEADATTR",null,"_AjaxResponse.processAttributes"),"_processAttributes");case c:var m=document.getElementsByTagName("body")[0];K.setAttribute(m,p,n);break;default:K.setAttribute(document.getElementById(i),p,n);break;}}};var g=function(k,o,i,m,h){var l=J(),p=m||l.MALFORMEDXML,n=h||l.MALFORMEDXML,j=o||"";return Z.makeException(k,p,n,"myfaces._impl.xhrCore._AjaxResponse",i||((arguments.callee.caller)?arguments.callee.caller.toString():"_raiseError"),j);};});}