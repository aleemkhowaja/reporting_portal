<#--
/*
 * $Id: scripting-events.ftl 720258 2008-11-24 19:05:16Z musachy $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#if parameters.onclick?default("") != "">
 onclick="${parameters.onclick?html}"<#rt/>
</#if>
<#if parameters.ondblclick??>
 ondblclick="${parameters.ondblclick?html}"<#rt/>
</#if>
<#if parameters.onmousedown??>
 onmousedown="${parameters.onmousedown?html}"<#rt/>
</#if>
<#if parameters.onmouseup??>
 onmouseup="${parameters.onmouseup?html}"<#rt/>
</#if>
<#if parameters.onmouseover??>
 onmouseover="${parameters.onmouseover?html}"<#rt/>
</#if>
<#if parameters.onmousemove??>
 onmousemove="${parameters.onmousemove?html}"<#rt/>
</#if>
<#if parameters.onmouseout??>
 onmouseout="${parameters.onmouseout?html}"<#rt/>
</#if>
<#if parameters.onfocus??>
 onfocus="${parameters.onfocus?html}"<#rt/>
</#if>
<#if parameters.onblur??>
	 onblur="${parameters.onblur?html}"<#rt/>
</#if>
<#if parameters.onkeypress??>
	onkeypress="${parameters.onkeypress?html}"<#rt/>
</#if>
<#if parameters.onkeydown??>
 onkeydown="${parameters.onkeydown?html}"<#rt/>
</#if>
<#if parameters.onkeyup??>
 onkeyup="${parameters.onkeyup?html}"<#rt/>
</#if>
<#if parameters.onselect??>
 onselect="${parameters.onselect?html}"<#rt/>
</#if>
<#if parameters.onchange?default("") != "">
	onchange="${parameters.onchange?html}"<#rt/>
</#if>	
<#--
/*
*Added the Attribute fieldAudit in order to handle prvvalue
*for elements that have no dependency
*/
-->
<#if parameters.fieldAudit?default(false)>
	fieldAudit="1"<#rt/>
</#if>
<#if parameters.dependency??>
	dependency="${parameters.dependency?html}"<#rt/>
</#if>
<#if parameters.dependencySrc??>
	dependencySrc = "${parameters.dependencySrc?html}"<#rt/>
</#if>
<#if parameters.parameter??>
	parameters = "${parameters.parameter?html}"<#rt/>
</#if>
<#if parameters.overrideLabelText??>
	overrideLabelText = "${parameters.overrideLabelText?html}"<#rt/>
</#if>
<#if parameters.overrideLabelKey??>
	overrideLabelKey = "${parameters.overrideLabelKey?html}"<#rt/>
</#if>