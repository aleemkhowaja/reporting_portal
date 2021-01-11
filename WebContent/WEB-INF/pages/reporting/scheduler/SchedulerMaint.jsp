<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>


<ps:form id="frmSchedulerControls" useHiddenProps="true">
	<div id="allSchedControlsDiv">
		<div id="inAllSchedControlsDiv">
			<ps:hidden name="schedule.SCHED_VO.SCHED_ID" id="SCHED_ID">
			</ps:hidden>
			<ps:hidden name="schedule.SCHED_VO.SCHED_TYPE" id="previousValue_${_pageRef}" />
			<ps:hidden name="schedule.SCHED_VO.SCHED_ID" id="schedule_Id_${_pageRef}" />
			<ps:hidden id="modeSched" value="add" name="mode"></ps:hidden>

			<table width="90%" style="padding-top: 10px">
				<tr>
					<td width="10%">
						<ps:label key="sch.name" for="SCHED_NAME"/>
					</td>
					<td width="30%">
						<ps:textfield name="schedule.SCHED_VO.SCHED_NAME" maxlength="20"
							id="SCHED_NAME"></ps:textfield>
					</td>
					<td width="10%"></td>
					<td>
						<ps:label key="sch.schedType" for="SCHED_TYPE_${_pageRef}" />
					</td>
					<td>
						<ps:select list="schedTypeList" listKey="VALUE_CODE"
							listValue="VALUE_DESC" name="schedule.SCHED_VO.SCHED_TYPE"
							id="SCHED_TYPE_${_pageRef}"
							onchange="schedTypeChanged(this.value,0)" />
					</td>
				
				</tr>
				<tr>
					<td colspan="3"></td>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td width="25%" style="text-align: left;"><ps:label
										key="Batch_Code_Key" id="batchCodeLbl_${_pageRef}" for="lookuptxt_BATCH_CODE_${_pageRef}" required="true" /></td>
								<td width="28%"><psj:livesearch id="BATCH_CODE_${_pageRef}"
										name="schedule.SCHED_VO.BATCH_CODE" mode="number"
										readOnlyMode="false"
										actionName="${pageContext.request.contextPath}/path/scheduler/scheduler_batchesLookup.action"
										searchElement="" paramList="_pageRef:_pageRef"
										resultList="eodBatchMasterVO.BATCH_CODE:lookuptxt_BATCH_CODE_${_pageRef},eodBatchMasterVO.BATCH_BRIEF_NAME:BATCH_BRIEF_NAME_${_pageRef}"
										parameter="updates:lookuptxt_BATCH_CODE_${_pageRef}">
									</psj:livesearch></td>
								<td width="47%%" align="right"><ps:textfield
										id="BATCH_BRIEF_NAME_${_pageRef}"
										name="schedule.BATCH_BRIEF_NAME" readonly="true" tabindex="-1"></ps:textfield></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3"></td>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td width="25%" style="text-align: left;">
									<ps:label key="event_id_key" id="eventIdLbl_${_pageRef}" for="lookuptxt_EVT_ID_${_pageRef}" required="true" />
								</td>
								<td width="28%">
									<psj:livesearch id="EVT_ID_${_pageRef}"
										name="schedule.SCHED_VO.EVT_ID" mode="number"
										readOnlyMode="false"
										actionName="${pageContext.request.contextPath}/path/scheduler/scheduler_individualEventsLookup.action"
										searchElement="" paramList="_pageRef:_pageRef"
										resultList="EVT_ID:lookuptxt_EVT_ID_${_pageRef},DESC_ENG:EVT_BRIEF_NAME_${_pageRef}"
										parameter="updates:lookuptxt_EVT_ID_${_pageRef}">
									</psj:livesearch>
								</td>
								<td width="47%%" align="right">
									<ps:textfield id="EVT_BRIEF_NAME_${_pageRef}" name="schedule.EVT_BRIEF_NAME" readonly="true" tabindex="-1"></ps:textfield>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td width="10%">
						<span id="frdLlb_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:label
								key="reporting.firstRunDate" for="FIRST_RUN_DATE" /> </span>
					</td>
					<td width="30%">
						<span id="frdVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker
								name="schedule.SCHED_VO.FIRST_RUN_DATE" id="FIRST_RUN_DATE"
								timepicker="true" timepickerAmPm="true" buttonImageOnly="true"
								size="50">
							</psj:datepicker> </span>
					</td>
					<td width="10%"></td>
					<td width="10%">
						<span id="edLbl_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:label
								key="sch.schedule_expiry_date" for="SCHED_EXPIRY_DATE"/> </span>
					</td>
					<td width="30%">
						<span id="edVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker
								name="schedule.SCHED_VO.SCHED_EXPIRY_DATE" id="SCHED_EXPIRY_DATE"
								timepicker="true" timepickerAmPm="true" buttonImageOnly="true"
								size="50"></psj:datepicker> </span>
					</td>
					
				</tr>

				<tr>
					<td width="10%">
							<span id="repLbl_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label key="sch.repetition" for="REPETITION"/></span>
					</td>
					<td width="30%">
						<ps:select name="schedule.SCHED_VO.SCHED_FREQUENCY" id="REPETITION"
							list="frequencyList" listKey="VALUE_CODE" listValue="VALUE_DESC"
							onchange="frequencyChanged(1)">
						</ps:select>
					</td>
					<td width="10%"></td>
					<td width="10%">
						<span id="nrdLbl_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label value="%{getText('sch.next_run_date')}" /></span>
					</td>
					<td width="30%">
						<span id="nrdVal_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:textfield name="schedule.NEXT_RUN_DATE_STR" id="NEXT_RUN_DATE"
							readonly="true" disabled="true" /></span>
					</td>
				</tr>
				<tr>
				<td width="10%">
						</td>
					<td width="30%">
					</td>
					<td width="10%"></td>
					<td width="10%"></td>
					<td width="30%">
					<span id="execButton_<ps:property value="_pageRef" escapeHtml="true"/>">
					<psj:submit button="true" buttonIcon="ui-icon-gear" onclick="executeSchedule()"  >
						<ps:label key="schedule.execute"></ps:label>
					</psj:submit> </span>
					</td>
						</tr>
				<tr>
				<td width="10%">
				<span id="hoursLbl_<ps:property value="_pageRef" escapeHtml="true"/>"> 
				<ps:label key="Hours_Key" for="FREQUENCY_HOUR"/>
				</span> 
				</td>
					<td nowrap="nowrap" colspan="4">
					<table >
					<tr>
						<td id="hoursVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:textfield
								name="schedule.SCHED_VO.FREQUENCY_HOUR" id="FREQUENCY_HOUR" mode="number"
								nbFormat="##" maxlength="2" size="2" minValue="0" /> </td>
						<td width="10%"></td>	
						<td id="minLbl_<ps:property value="_pageRef" escapeHtml="true"/>"> 
						<ps:label key="Minutes_key" for="FREQUENCY_MINUTE"/> 
						</td>
						<td width="10%"></td>	
						<td id="minVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:textfield
								name="schedule.SCHED_VO.FREQUENCY_MINUTE" id="FREQUENCY_MINUTE"
								mode="number" nbFormat="##" size="2" maxlength="2" minValue="0"
								onblur="checkMinuteValue(this.value)" /> </td>
					</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<div id="days">
							<ps:checkbox label="%{getText('monday_key')}" id="chkMON"
								name="days.MON"></ps:checkbox>
							<ps:checkbox label="%{getText('tuesday_key')}" id="chkTUE"
								name="days.TUE"></ps:checkbox>
							<ps:checkbox label="%{getText('wednesday_key')}" id="chkWED"
								name="days.WED"></ps:checkbox>
							<ps:checkbox label="%{getText('thursday_key')}" id="chkTHU"
								name="days.THU"></ps:checkbox>
							<ps:checkbox label="%{getText('friday_key')}" id="chkFRI"
								name="days.FRI"></ps:checkbox>
							<ps:checkbox label="%{getText('saturday_key')}" id="chkSAT"
								name="days.SAT"></ps:checkbox>
							<ps:checkbox label="%{getText('sunday_key')}" id="chkSUN"
								name="days.SUN"></ps:checkbox>
						</div>
						<div id="months">
							<ps:checkbox label="%{getText('Jan_key')}" id="chkJAN"
								name="months.JAN"></ps:checkbox>
							<ps:checkbox label="%{getText('Feb_key')}" id="chkFEB"
								name="months.FEB"></ps:checkbox>
							<ps:checkbox label="%{getText('Mar_key')}" id="chkMAR"
								name="months.MAR"></ps:checkbox>
							<ps:checkbox label="%{getText('Apr_key')}" id="chkAPR"
								name="months.APR"></ps:checkbox>
							<ps:checkbox label="%{getText('May_key')}" id="chkMAY"
								name="months.MAY"></ps:checkbox>
							<ps:checkbox label="%{getText('Jun_key')}" id="chkJUN"
								name="months.JUN"></ps:checkbox>
							<ps:checkbox label="%{getText('Jul_key')}" id="chkJUL"
								name="months.JUL"></ps:checkbox>
							<ps:checkbox label="%{getText('Aug_key')}" id="chkAUG"
								name="months.AUG"></ps:checkbox>
							<ps:checkbox label="%{getText('Sep_key')}" id="chkSEP"
								name="months.SEP"></ps:checkbox>
							<ps:checkbox label="%{getText('Oct_key')}" id="chkOCT"
								name="months.OCT"></ps:checkbox>
							<ps:checkbox label="%{getText('Nov_key')}" id="chkNOV"
								name="months.NOV"></ps:checkbox>
							<ps:checkbox label="%{getText('Dec_key')}" id="chkDEC"
								name="months.DEC"></ps:checkbox>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<ps:hidden id="DATE_UPDATED_${_pageRef}" name="schedule.SCHED_VO.DATE_UPDATED" />

	</div>
</ps:form>