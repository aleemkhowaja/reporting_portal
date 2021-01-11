package com.path.actions.reporting.ftr.columntemplate;

import java.math.BigDecimal;
import java.util.List;

import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateBO;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;

public class ColumnTemplateSearchGridAction extends ReportingGridBaseAction {
	// private BigDecimal TEMPL_CODE;
	private BigDecimal CODE;

	public BigDecimal getCODE() {
		return CODE;
	}

	public void setCODE(BigDecimal cODE) {
		CODE = cODE;
	}

	private ColumnTemplateBO columnTemplateBO;
	private List<COLMNTMPLTCO> ctList;
	COLMNTMPLTSC sc = new COLMNTMPLTSC();

	/*
	 * public void setTEMPL_CODE(BigDecimal tEMPL_CODE) { TEMPL_CODE =
	 * tEMPL_CODE; }
	 * 
	 * public BigDecimal getTEMPL_CODE() { return TEMPL_CODE; }
	 */

	public String loadGrid() throws Exception {
		SessionCO sessionCO = returnSessionObject();
		this.copyproperties(sc);
		sc.setCOMP_CODE(sessionCO.getCompanyCode());
		sc.setLINE_NBR(BigDecimal.ZERO);

		ctList = columnTemplateBO.findAllColumnTemplates(sc);
		int cnt = columnTemplateBO.findAllColumnTemplatesCount(sc);
		setRecords(cnt);
		setGridModel(ctList);
		return "grid";
	}

	public String openLine() throws Exception {
		SessionCO sessionCO = returnSessionObject();
		this.copyproperties(sc);
		sc.setCOMP_CODE(sessionCO.getCompanyCode());
		sc.setLINE_NBR(BigDecimal.ZERO);
		setRecords(columnTemplateBO.findAllColumnTemplatesCount(sc));

		// ctList = columnTemplateBO.findAllColumnTemplates(sc);
		setGridModel(ctList);
		return SUCCESS;
	}

	public void setColumnTemplateBO(ColumnTemplateBO columnTemplateBO) {
		this.columnTemplateBO = columnTemplateBO;
	}

	public Object getModel() {
		return sc;
	}
}
