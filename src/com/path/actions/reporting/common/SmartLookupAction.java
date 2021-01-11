package com.path.actions.reporting.common;

import java.util.ArrayList;
import java.util.List;

import com.path.bo.reporting.common.CommonLookupBO;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.struts2.lib.common.base.LookupBaseAction;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;

public class SmartLookupAction extends LookupBaseAction
{
    private CommonLookupBO commonLookupBO;
    private S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC = new S_ADDITIONS_OPTIONSSC();
    
    public String contructSmartLookup()
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "OPTION_CODE", "BRIEF_NAME_ENG", "LONG_NAME_ENG" };
	    String[] colType = { "number", "text", "text" };
	    String[] titles = { getText("reporting.optionCode"), getText("reporting.lkpBriefEng"),
		    getText("reporting.lkpLongEng") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("SMART");
	    grid.setRowNum("10");
	    grid.setUrl("/path/commonLkp/smartLkpAction_fillSmartLookupGrid");

	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(true);

		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, null);
	}
	catch(Exception e)
	{
	    log.error(e, "------------error in smart lookup-----------");
	}
	return "success";
    }
    
    public String fillSmartLookupGrid()
    {
	try
	{
	    setSearchFilter(sAdditionsOptionsSC);
	    copyproperties(sAdditionsOptionsSC);
	    if(checkNbRec(sAdditionsOptionsSC)) {
		setRecords(commonLookupBO.getAdditionsOptionsListCount(sAdditionsOptionsSC));
	    }
	    setGridModel(commonLookupBO.getAdditionsOptionsList(sAdditionsOptionsSC));
	}
	catch(Exception e)
	{
	    //log.error(e, "***************** error in loading the grid *************");
	    handleException(e,null,null);
	}
	return "success";

    }
    
    @Override
    public Object getModel()
    {
	return sAdditionsOptionsSC;
    }	    
    
    public S_ADDITIONS_OPTIONSSC getsAdditionsOptionsSC()
    {
        return sAdditionsOptionsSC;
    }
    public void setsAdditionsOptionsSC(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC)
    {
        this.sAdditionsOptionsSC = sAdditionsOptionsSC;
    }
    
    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
        this.commonLookupBO = commonLookupBO;
    }
    
}
