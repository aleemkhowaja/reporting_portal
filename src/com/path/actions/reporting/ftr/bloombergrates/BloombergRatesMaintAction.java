package com.path.actions.reporting.ftr.bloombergrates;

import com.path.struts2.lib.common.base.BaseAction;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesMaintAction.java used to
 */
public class BloombergRatesMaintAction extends BaseAction
{

    public String loadBloombergRatesPage()
    {
	try
	{
	    set_showSmartInfoBtn("false");
	    set_searchGridId("bloombergRatesListGridTbl_Id");
	    set_showNewInfoBtn("true");
	}
	catch(Exception ex)
	{
	    handleException(ex, null, null);
	}
	return "bloombergRatesList";
    }

    public String emptyForm()
    {
	return SUCCESS;
    }
}
