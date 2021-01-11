package com.path.actions.reporting.ftr.columntemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateBO;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.columnTemplate.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public class ColumnTemplateAction extends ReportingLookupBaseAction implements ServletRequestAware,
	ServletResponseAware
{
    private final CommonDetailsSC commDet = new CommonDetailsSC();
    private String detailColGridMode;
    private String allCodes;
    private String detGridMode;
    private String linesGridMode;
    private String detExprGridMode;
    private String TEMPLATE_CODE;
    private String updates;
    private String tempCodeLineNbr;
    private String expressionNbr;
    private String columnTemplateCount;
    private String columnTemplateDetailsCount;
    private ColumnTemplateBO columnTemplateBO;
    private CommonDetailsVO lkpDependencyVO;
    private List<SYS_PARAM_LOV_TRANSVO> paperOrientationList;
    private List<SYS_PARAM_LOV_TRANSVO> paperSizeList;

    private BigDecimal code;
    private Boolean allCriteria;
    private String lineNbr = "0";
    private TemplateBO templateBO;
    private String lineNumber;
    private BigDecimal valueToRemember;
    private BigDecimal startingLineValue;
    private int previousValue;// for the reorganize line functions
    // (addHashmap and dbHashMap share same
    // values)
    private int firstLoop;// for the reorganize line functions (addHashmap
    // and dbHashMap share same values
    private BigDecimal interval;
    private String tempCodeWithLineNb;
    private ArrayList operatorArrList = new ArrayList(); // to fill the operator
    // combo
    private CommonDetailsVO commonDetVO; // used to apply dependency between the
    // GL lookups code and value

    COLMNTMPLTSC sc = new COLMNTMPLTSC();
    COLMNTMPLTCO singleColumnDetail = new COLMNTMPLTCO();

    private COLMNTMPLT_PARAM_LINKCO crtCO; // used to fill the CRT form
    private FTR_TMPLT_EXPRCO voExpr = new FTR_TMPLT_EXPRCO();
    private COLMNTMPLTVO singleColumn;

    private HttpServletRequest request;
    private String column1;// parameter 1 of the lookup
    private String column2;// parameter 2 of the lookup
    private String column3;// parameter 3 of the lookup
    private String str;
    private String str1;
    private String str2;
    private String VALUE;
    private final TmplGridSC tmplGridSC = new TmplGridSC();
    private Boolean det_DISP_COL_TOT_ZERO;
    private Boolean det_IS_BOLD;
    // to fill the column type combo
    private ArrayList<CommonDetailsSC> colTypeArrList = new ArrayList<CommonDetailsSC>();
    private BigDecimal newCode;

    private String FROM_BRANCH;
    private String TO_BRANCH;
    private String FROM_REGION;
    private String TO_REGION;
    private String FROM_DIV;
    private String TO_DIV;
    private String FROM_DEP;
    private String TO_DEP;
    private String FROM_UNIT;
    private String TO_UNIT;

    private String FROM_BRANCH_DESC;
    private String TO_BRANCH_DESC;
    private String FROM_REGION_DESC;
    private String TO_REGION_DESC;
    private String FROM_DIV_DESC;
    private String TO_DIV_DESC;
    private String FROM_DEP_DESC;
    private String TO_DEP_DESC;
    private String FROM_UNIT_DESC;
    private String TO_UNIT_DESC;

    private String colType;
    private String fromWhere;
    private String updates1;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> colDaysOFWeek;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> colListMonths;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> daysMonthYear;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> maleFemaleList;
    private int newLineIndicator;
    
    

    public int getNewLineIndicator()
    {
        return newLineIndicator;
    }

    public void setNewLineIndicator(int newLineIndicator)
    {
        this.newLineIndicator = newLineIndicator;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getColDaysOFWeek()
    {
	return colDaysOFWeek;
    }

    public void setColDaysOFWeek(ArrayList<SYS_PARAM_LOV_TRANSVO> colDaysOFWeek)
    {
	this.colDaysOFWeek = colDaysOFWeek;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getColListMonths()
    {
	return colListMonths;
    }

    public void setColListMonths(ArrayList<SYS_PARAM_LOV_TRANSVO> colListMonths)
    {
	this.colListMonths = colListMonths;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDaysMonthYear()
    {
	return daysMonthYear;
    }

    public void setDaysMonthYear(ArrayList<SYS_PARAM_LOV_TRANSVO> daysMonthYear)
    {
	this.daysMonthYear = daysMonthYear;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getMaleFemaleList()
    {
	return maleFemaleList;
    }

    public void setMaleFemaleList(ArrayList<SYS_PARAM_LOV_TRANSVO> maleFemaleList)
    {
	this.maleFemaleList = maleFemaleList;
    }

    public String getStr2()
    {
	return str2;
    }

    public void setStr2(String str2)
    {
	this.str2 = str2;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public String getFromWhere()
    {
	return fromWhere;
    }

    public void setFromWhere(String fromWhere)
    {
	this.fromWhere = fromWhere;
    }

    public String getColType()
    {
	return colType;
    }

    public void setColType(String colType)
    {
	this.colType = colType;
    }

    public BigDecimal getNewCode()
    {
	return newCode;
    }

    public void setNewCode(BigDecimal newCode)
    {
	this.newCode = newCode;
    }

    public String getLinesGridMode()
    {
	return linesGridMode;
    }

    public void setLinesGridMode(String linesGridMode)
    {
	this.linesGridMode = linesGridMode;
    }

    public String getVALUE()
    {
	return VALUE;
    }

    public void setVALUE(String vALUE)
    {
	VALUE = vALUE;
    }

    public String getStr()
    {
	return str;
    }

    public void setStr(String str)
    {
	this.str = str;
    }

    public String getStr1()
    {
	return str1;
    }

    public void setStr1(String str1)
    {
	this.str1 = str1;
    }

    public CommonDetailsVO getCommonDetVO()
    {
	return commonDetVO;
    }

    public void setCommonDetVO(CommonDetailsVO commonDetVO)
    {
	this.commonDetVO = commonDetVO;
    }

    public ArrayList getOperatorArrList()
    {
	return operatorArrList;
    }

    public void setOperatorArrList(ArrayList operatorArrList)
    {
	this.operatorArrList = operatorArrList;
    }

    public String getTempCodeWithLineNb()
    {
	return tempCodeWithLineNb;
    }

    public void setTempCodeWithLineNb(String tempCodeWithLineNb)
    {
	this.tempCodeWithLineNb = tempCodeWithLineNb;
    }

    public BigDecimal getInterval()
    {
	return interval;
    }

    public void setInterval(BigDecimal interval)
    {
	this.interval = interval;
    }

    public BigDecimal getStartingLineValue()
    {
	return startingLineValue;
    }

    public void setStartingLineValue(BigDecimal startingLineValue)
    {
	this.startingLineValue = startingLineValue;
    }

    public BigDecimal getValueToRemember()
    {
	return valueToRemember;
    }

    public void setValueToRemember(BigDecimal valueToRemember)
    {
	this.valueToRemember = valueToRemember;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    public FTR_TMPLT_EXPRCO getVoExpr()
    {
	return voExpr;
    }

    public void setVoExpr(FTR_TMPLT_EXPRCO voExpr)
    {
	this.voExpr = voExpr;
    }

    private COLMNTMPLTCO colTemplCO;

    public COLMNTMPLTCO getColTemplCO()
    {
	return colTemplCO;
    }

    public void setColTemplCO(COLMNTMPLTCO colTemplCO)
    {
	this.colTemplCO = colTemplCO;
    }

    public COLMNTMPLT_PARAM_LINKCO getCrtCO()
    {
	return crtCO;
    }

    public void setCrtCO(COLMNTMPLT_PARAM_LINKCO crtCO)
    {
	this.crtCO = crtCO;
    }

    public List<SYS_PARAM_LOV_TRANSVO> getPaperSizeList()
    {
	paperSizeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("9");
	sysVO.setVALUE_DESC(getText("reporting.A4"));
	paperSizeList.add(sysVO);

	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("8");
	sysVO.setVALUE_DESC(getText("reporting.A3"));
	paperSizeList.add(sysVO);

	return paperSizeList;
    }

    public void setPaperSizeList(List<SYS_PARAM_LOV_TRANSVO> paperSizeList)
    {
	this.paperSizeList = paperSizeList;
    }

    public List<SYS_PARAM_LOV_TRANSVO> getPaperOrientationList()
    {
	paperOrientationList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("1");
	sysVO.setVALUE_DESC(getText("reporting.landscape"));
	paperOrientationList.add(sysVO);
	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("2");
	sysVO.setVALUE_DESC(getText("reporting.portrait"));
	paperOrientationList.add(sysVO);
	return paperOrientationList;
    }

    public void setPaperOrientationList(List<SYS_PARAM_LOV_TRANSVO> paperOrientationList)
    {
	this.paperOrientationList = paperOrientationList;
    }

    public String getFROM_BRANCH()
    {
	return FROM_BRANCH;
    }

    public void setFROM_BRANCH(String fROMBRANCH)
    {
	FROM_BRANCH = fROMBRANCH;
    }

    public String getTO_BRANCH()
    {
	return TO_BRANCH;
    }

    public void setTO_BRANCH(String tOBRANCH)
    {
	TO_BRANCH = tOBRANCH;
    }

    public String getFROM_REGION()
    {
	return FROM_REGION;
    }

    public void setFROM_REGION(String fROMREGION)
    {
	FROM_REGION = fROMREGION;
    }

    public String getTO_REGION()
    {
	return TO_REGION;
    }

    public void setTO_REGION(String tOREGION)
    {
	TO_REGION = tOREGION;
    }

    public String getFROM_DIV()
    {
	return FROM_DIV;
    }

    public void setFROM_DIV(String fROMDIV)
    {
	FROM_DIV = fROMDIV;
    }

    public String getTO_DIV()
    {
	return TO_DIV;
    }

    public void setTO_DIV(String tODIV)
    {
	TO_DIV = tODIV;
    }

    public String getFROM_DEP()
    {
	return FROM_DEP;
    }

    public void setFROM_DEP(String fROMDEP)
    {
	FROM_DEP = fROMDEP;
    }

    public String getTO_DEP()
    {
	return TO_DEP;
    }

    public void setTO_DEP(String tODEP)
    {
	TO_DEP = tODEP;
    }

    public String getFROM_UNIT()
    {
	return FROM_UNIT;
    }

    public void setFROM_UNIT(String fROMUNIT)
    {
	FROM_UNIT = fROMUNIT;
    }

    public String getTO_UNIT()
    {
	return TO_UNIT;
    }

    public void setTO_UNIT(String tOUNIT)
    {
	TO_UNIT = tOUNIT;
    }

    public String getFROM_BRANCH_DESC()
    {
	return FROM_BRANCH_DESC;
    }

    public void setFROM_BRANCH_DESC(String fROMBRANCHDESC)
    {
	FROM_BRANCH_DESC = fROMBRANCHDESC;
    }

    public String getTO_BRANCH_DESC()
    {
	return TO_BRANCH_DESC;
    }

    public void setTO_BRANCH_DESC(String tOBRANCHDESC)
    {
	TO_BRANCH_DESC = tOBRANCHDESC;
    }

    public String getFROM_REGION_DESC()
    {
	return FROM_REGION_DESC;
    }

    public void setFROM_REGION_DESC(String fROMREGIONDESC)
    {
	FROM_REGION_DESC = fROMREGIONDESC;
    }

    public String getTO_REGION_DESC()
    {
	return TO_REGION_DESC;
    }

    public void setTO_REGION_DESC(String tOREGIONDESC)
    {
	TO_REGION_DESC = tOREGIONDESC;
    }

    public String getFROM_DIV_DESC()
    {
	return FROM_DIV_DESC;
    }

    public void setFROM_DIV_DESC(String fROMDIVDESC)
    {
	FROM_DIV_DESC = fROMDIVDESC;
    }

    public String getTO_DIV_DESC()
    {
	return TO_DIV_DESC;
    }

    public void setTO_DIV_DESC(String tODIVDESC)
    {
	TO_DIV_DESC = tODIVDESC;
    }

    public String getFROM_DEP_DESC()
    {
	return FROM_DEP_DESC;
    }

    public void setFROM_DEP_DESC(String fROMDEPDESC)
    {
	FROM_DEP_DESC = fROMDEPDESC;
    }

    public String getTO_DEP_DESC()
    {
	return TO_DEP_DESC;
    }

    public void setTO_DEP_DESC(String tODEPDESC)
    {
	TO_DEP_DESC = tODEPDESC;
    }

    public String getFROM_UNIT_DESC()
    {
	return FROM_UNIT_DESC;
    }

    public void setFROM_UNIT_DESC(String fROMUNITDESC)
    {
	FROM_UNIT_DESC = fROMUNITDESC;
    }

    public String getTO_UNIT_DESC()
    {
	return TO_UNIT_DESC;
    }

    public void setTO_UNIT_DESC(String tOUNITDESC)
    {
	TO_UNIT_DESC = tOUNITDESC;
    }

    public Boolean getAllCriteria()
    {
	return allCriteria;
    }

    public void setAllCriteria(Boolean allCriteria)
    {
	this.allCriteria = allCriteria;
    }

    public CommonDetailsVO getLkpDependencyVO()
    {
	return lkpDependencyVO;
    }

    public void setLkpDependencyVO(CommonDetailsVO lkpDependencyVO)
    {
	this.lkpDependencyVO = lkpDependencyVO;
    }

    public String getAllCodes()
    {
	return allCodes;
    }

    public void setAllCodes(String allCodes)
    {
	this.allCodes = allCodes;
    }

    public String getExpressionNbr()
    {
	return expressionNbr;
    }

    public void setExpressionNbr(String expressionNbr)
    {
	this.expressionNbr = expressionNbr;
    }

    public String getDetailColGridMode()
    {
	return detailColGridMode;
    }

    public void setDetailColGridMode(String detailColGridMode)
    {
	this.detailColGridMode = detailColGridMode;
    }

    public String getTempCodeLineNbr()
    {
	return tempCodeLineNbr;
    }

    public void setTempCodeLineNbr(String tempCodeLineNbr)
    {
	this.tempCodeLineNbr = tempCodeLineNbr;
    }

    private CommonLookupBO commonLookupBO;

    public String getColumnTemplateCount()
    {
	return columnTemplateCount;
    }

    public void setColumnTemplateCount(String columnTemplateCount)
    {
	this.columnTemplateCount = columnTemplateCount;
    }

    public String getColumnTemplateDetailsCount()
    {
	return columnTemplateDetailsCount;
    }

    public void setColumnTemplateDetailsCount(String columnTemplateDetailsCount)
    {
	this.columnTemplateDetailsCount = columnTemplateDetailsCount;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String getDetExprGridMode()
    {
	return detExprGridMode;
    }

    public void setDetExprGridMode(String detExprGridMode)
    {
	this.detExprGridMode = detExprGridMode;
    }

    public void setTemplateBO(TemplateBO templateBO)
    {
	this.templateBO = templateBO;
    }

    public String getTEMPLATE_CODE()
    {
	return TEMPLATE_CODE;
    }

    public void setTEMPLATE_CODE(String tEMPLATECODE)
    {
	TEMPLATE_CODE = tEMPLATECODE;
    }

    public String getDetGridMode()
    {
	return detGridMode;
    }

    public void setDetGridMode(String detGridMode)
    {
	this.detGridMode = detGridMode;
    }

    public String getLineNbr()
    {
	return lineNbr;
    }

    public void setLineNbr(String lineNbr)
    {
	this.lineNbr = lineNbr;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public ArrayList getColTypeArrList()
    {
	return colTypeArrList;
    }

    public void setColTypeArrList(ArrayList colTypeArrList)
    {
	this.colTypeArrList = colTypeArrList;
    }

    public Boolean getDet_DISP_COL_TOT_ZERO()
    {
	return det_DISP_COL_TOT_ZERO;
    }

    public void setDet_DISP_COL_TOT_ZERO(Boolean detDISPCOLTOTZERO)
    {
	det_DISP_COL_TOT_ZERO = detDISPCOLTOTZERO;
    }

    public Boolean getDet_IS_BOLD()
    {
	return det_IS_BOLD;
    }

    public void setDet_IS_BOLD(Boolean detISBOLD)
    {
	det_IS_BOLD = detISBOLD;
    }

    public COLMNTMPLTCO getSingleColumnDetail()
    {
	return singleColumnDetail;
    }

    public void setSingleColumnDetail(COLMNTMPLTCO singleColumnDetail)
    {
	this.singleColumnDetail = singleColumnDetail;
    }

    public String getColumn1()
    {
	return column1;
    }

    public void setColumn1(String column1)
    {
	this.column1 = column1;
    }

    public String getColumn2()
    {
	return column2;
    }

    public void setColumn2(String column2)
    {
	this.column2 = column2;
    }

    public String getColumn3()
    {
	return column3;
    }

    public void setColumn3(String column3)
    {
	this.column3 = column3;
    }

    public COLMNTMPLTVO getSingleColumn()
    {
	return singleColumn;
    }

    public void setColumnTemplateBO(ColumnTemplateBO columnTemplateBO)
    {
	this.columnTemplateBO = columnTemplateBO;
    }

    public void setSingleColumn(COLMNTMPLTVO singleColumn)
    {
	this.singleColumn = singleColumn;
    }

    public String getLineNumber()
    {
	return lineNumber;
    }

    public void setLineNumber(String lineNumber)
    {
	this.lineNumber = lineNumber;
    }

    public String execute() throws Exception
    {
	set_showSmartInfoBtn("true");

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(6)); // 6 =column template
	// type (check table
	// sys_param_lov_type)
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	colTypeArrList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.COL_CALC_DAYS);
	colDaysOFWeek = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.COL_CALC_MONTHS);
	colListMonths = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	SYS_PARAM_LOV_TRANSVO vo = new SYS_PARAM_LOV_TRANSVO();
	vo.setVALUE_CODE(ConstantsCommon.ZERO);
	vo.setVALUE_DESC(getText("allkey"));
	colDaysOFWeek.add(0, vo);
	colListMonths.add(0, vo);
	if(repSessionCO.getCurrentTemplate() != null)
	{
	    repSessionCO.setCurrentTemplate(null);
	}

	set_searchGridId("colGrid");
	set_showNewInfoBtn("true");
	return SUCCESS;
    }

    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    // Calling when select a record in the Grid "colGrid_${_pageRef}"
    public String selectTemplate() throws Exception
    {
	// Get the sent parameter "updates" that contain the selected object
	// "colTemplCO"
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SessionCO sessionCO = returnSessionObject();

	if(repSessionCO.getCurrentTemplate() != null)
	{
	    repSessionCO.setCurrentTemplate(null);
	}

	COLMNTMPLTVO colVO = new COLMNTMPLTVO();
	colVO.setCOMP_CODE(sessionCO.getCompanyCode());
	colVO.setCODE(getCode());
	colVO.setLINE_NBR(BigDecimal.ZERO);

	// Get the Template Header to fill the Template Header form
	singleColumn = columnTemplateBO.retrieveColumnTemplate(colVO);
	// Fill the Grid of Template lines
	fillGridFromDB();

	applyRetrieveAudit(AuditConstant.COL_TEMPL_KEY_REF, singleColumn, singleColumn);
	return "successMnt";
    }

    private void fillGridFromDB() throws Exception
    {
	this.copyproperties(sc);
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SessionCO sessionCO = returnSessionObject();
	sc.setCOMP_CODE(sessionCO.getCompanyCode());
	sc.setCODE(singleColumn.getCODE());
	sc.setLANG_CODE(sessionCO.getLanguage());

	COLMNTMPLTCO CurrentTemplate = new COLMNTMPLTCO();

	CurrentTemplate.getColmnTmpltVO().setBRIEF_NAME_ENG(singleColumn.getBRIEF_NAME_ENG());
	CurrentTemplate.getColmnTmpltVO().setBRIEF_NAME_ARAB(singleColumn.getBRIEF_NAME_ARAB());
	CurrentTemplate.getColmnTmpltVO().setCOMP_CODE(singleColumn.getCOMP_CODE());
	CurrentTemplate.getColmnTmpltVO().setPRINT_PAPER_ORIENTATION(singleColumn.getPRINT_PAPER_ORIENTATION());
	CurrentTemplate.getColmnTmpltVO().setPRINT_PAPER_SIZE(singleColumn.getPRINT_PAPER_SIZE());
	CurrentTemplate.getColmnTmpltVO().setCODE(singleColumn.getCODE());
	CurrentTemplate.getColmnTmpltVO().setLINE_NBR(singleColumn.getLINE_NBR());

	CurrentTemplate.setColumnDetails(columnTemplateBO.findColumnTemplateDetails(sc));

	for(COLMNTMPLTCO colDet : CurrentTemplate.getColumnDetails())
	{
	    // Fill the list of Expressions related to colDet, set it in the
	    // "expressions" in "COLMNTMPLTCO"
	    COLMNTMPLTVO scLine = new COLMNTMPLTVO();
	    scLine.setCOMP_CODE(sessionCO.getCompanyCode());
	    scLine.setCODE(CurrentTemplate.getColmnTmpltVO().getCODE());
	    scLine.setLINE_NBR(colDet.getColmnTmpltVO().getLINE_NBR());
	    colDet.setExpressions(columnTemplateBO.findExprList(scLine));

	    // Fill the list of Criteria related to colDet, set it in the
	    // "colmnTmpltParamLink" in "COLMNTMPLTCO"
	    COLMNTMPLTSC crtSC = new COLMNTMPLTSC();
	    crtSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    crtSC.setCODE(CurrentTemplate.getColmnTmpltVO().getCODE());
	    crtSC.setLINE_NBR(CurrentTemplate.getColmnTmpltVO().getLINE_NBR());
	    crtSC.setLANG_CODE(sessionCO.getLanguage());
	    colDet.setColmnTmpltParamLink(columnTemplateBO.getCrtList(crtSC));
	}

	repSessionCO.setCurrentTemplate(CurrentTemplate);
    }

    /**
     * Method that finds a columntmplt vo in session
     * @param line_number
     * @param flag
     * @return
     */
    private COLMNTMPLTCO findTemplateCODetInSession(BigDecimal line_number,boolean flag)
    {
	// added flag param because checking will be made on newLineNumber if
	// reorganize has been
	// clicked
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if(repSessionCO != null && repSessionCO.getCurrentTemplate() != null)
	{
	    for(COLMNTMPLTCO co : (repSessionCO.getCurrentTemplate()).getColumnDetails())
	    {
		if(!flag && co.getColmnTmpltVO().getLINE_NBR().equals(line_number))
		{
		    return co;
		}
		else if(co.getNewLineNumber().equals(line_number))
		{
		    return co;
		}
	    }
	}
	return null;
    }

    public String deleteColumnTemplate() throws Exception
    {
	TEMPLATE_CODE = request.getParameter("TEMPLATE_CODE");
	if(RepConstantsCommon.EMPTY_STRING.equals(TEMPLATE_CODE))
	{
	    return SUCCESS;
	}
	SessionCO sessionCO = returnSessionObject();

	COLMNTMPLTVO key = new COLMNTMPLTVO();
	key.setCOMP_CODE(sessionCO.getCompanyCode());
	key.setCODE(new BigDecimal(TEMPLATE_CODE));

	// apply audit
	AuditRefCO refCO = initAuditRefCO();
	refCO.setOperationType(AuditConstant.DELETE);
	refCO.setKeyRef(AuditConstant.COL_TEMPL_KEY_REF);
	key.setAuditRefCO(refCO);

	// Delete column Template with related Lines and Expressions
	columnTemplateBO.deleteColumnTemplate(key);
	return SUCCESS;
    }

    public String fillDetailsGrid() throws Exception
    {
	if(detGridMode == null)
	{
	    return SUCCESS;
	}

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if(RepConstantsCommon.MODE_NONE.equals(detGridMode))
	{
	    repSessionCO.setCurrentTemplate(null);
	    setGridModel(null);
	    return SUCCESS;
	}

	SessionCO sessionCO = returnSessionObject();
	COLMNTMPLTCO curTmplCO=repSessionCO.getCurrentTemplate();
	if(curTmplCO == null)
	{
	    setGridModel(null);
	}
	else
	{
	    List<COLMNTMPLTCO> colmnTmpltList = curTmplCO.getColumnDetails();
	    String concatKey = "";
	    COLMNTMPLTVO vo;
	    COLMNTMPLTCO co;
	    for(int i = 0; i < colmnTmpltList.size(); i++)
	    {
		co=colmnTmpltList.get(i);
		vo=co.getColmnTmpltVO();
		if(co.getNewLineNumber()==null || co.getConcatKey()==null)
		{
		// Create the concatKey and set it below to use it later during
		// reorganizing
		concatKey = sessionCO.getCompanyCode().toString()+ vo.getCODE().toString()+ vo.getLINE_NBR().toString();
		co.setConcatKey(new BigDecimal(concatKey));
		co.setNewLineNumber(vo.getLINE_NBR());
		}
	    }
	    copyproperties(tmplGridSC);
	    int nbRec = tmplGridSC.getNbRec();
	    int recToSkip = tmplGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(colmnTmpltList.size() < maxRec)
	    {
		maxRec = colmnTmpltList.size();
	    }
	    ArrayList<COLMNTMPLTCO> lst = new ArrayList<COLMNTMPLTCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add(colmnTmpltList.get(i));
	    }	    
	    setRecords(colmnTmpltList.size());
	    setGridModel(lst);
	}

	return SUCCESS;
    }

    public String createSession() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if(repSessionCO.getCurrentTemplate() == null)
	{
	    COLMNTMPLTVO CurrentTemplateVO = singleColumn;
	    COLMNTMPLTCO CurrentTemplate = new COLMNTMPLTCO();

	    CurrentTemplate.getColmnTmpltVO().setBRIEF_NAME_ENG(CurrentTemplateVO.getBRIEF_NAME_ENG());
	    CurrentTemplate.getColmnTmpltVO().setBRIEF_NAME_ARAB(CurrentTemplateVO.getBRIEF_NAME_ARAB());
	    CurrentTemplate.getColmnTmpltVO().setCOMP_CODE(CurrentTemplateVO.getCOMP_CODE());
	    CurrentTemplate.getColmnTmpltVO()
		    .setPRINT_PAPER_ORIENTATION(CurrentTemplateVO.getPRINT_PAPER_ORIENTATION());
	    CurrentTemplate.getColmnTmpltVO().setPRINT_PAPER_SIZE(CurrentTemplateVO.getPRINT_PAPER_SIZE());
	    CurrentTemplate.getColmnTmpltVO().setCODE(CurrentTemplateVO.getCODE());
	    repSessionCO.setCurrentTemplate(CurrentTemplate);
	}
	return SUCCESS;
    }

    // This function used to add a line detail related to the Column Template
    public String addColumnTemplateLine() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if(repSessionCO.getCurrentTemplate() == null)
	{
	    return SUCCESS;
	}
	String concatKey = "";
	COLMNTMPLTVO colmnTmpltConcatKey;
	SessionCO sessionCO = returnSessionObject();

	// If the user add a new line
	if(RepConstantsCommon.MODE_ADD.equals(detailColGridMode))
	{
	    if((repSessionCO.getCurrentTemplate()).getColumnDetails() == null)
	    {
		(repSessionCO.getCurrentTemplate()).setColumnDetails(new ArrayList<COLMNTMPLTCO>());
	    }

	    // Added to use it below when creating the concatKey
	    colmnTmpltConcatKey = singleColumnDetail.getColmnTmpltVO();

	    // Set the fields of the CO object "singleColumnDetail"
	    singleColumnDetail.getColmnTmpltVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    /*
	     * singleColumnDetail.getColmnTmpltVO().setCODE(
	     * repSessionCO.getCurrentTemplate().getColmnTmpltVO() .getCODE());
	     */
	    singleColumnDetail.getColmnTmpltVO().setCODE(code);
	    singleColumnDetail.getColmnTmpltVO().setLINE_NBR(new BigDecimal(lineNbr));

	    if(det_DISP_COL_TOT_ZERO)
	    {
		singleColumnDetail.getColmnTmpltVO().setDISP_COL_TOT_ZERO(RepConstantsCommon.Y);
	    }
	    else
	    {
		singleColumnDetail.getColmnTmpltVO().setDISP_COL_TOT_ZERO(RepConstantsCommon.N);
	    }
	    if(det_IS_BOLD)
	    {
		singleColumnDetail.getColmnTmpltVO().setBOLD(RepConstantsCommon.Y);
	    }
	    else
	    {
		singleColumnDetail.getColmnTmpltVO().setBOLD(RepConstantsCommon.N);
	    }

	    if(allCriteria)
	    {
		singleColumnDetail.getColmnTmpltVO().setALL_CRITERIA(RepConstantsCommon.Y);
	    }
	    else
	    {
		singleColumnDetail.getColmnTmpltVO().setALL_CRITERIA(RepConstantsCommon.N);
	    }

	    // Create the concatKey and set it below to use it later during
	    // reorganizing
	    concatKey = colmnTmpltConcatKey.getCOMP_CODE().toString() + colmnTmpltConcatKey.getCODE().toString()
		    + colmnTmpltConcatKey.getLINE_NBR().toString();
	    singleColumnDetail.setConcatKey(new BigDecimal(concatKey));
	    singleColumnDetail.setNewLineNumber(singleColumnDetail.getColmnTmpltVO().getLINE_NBR());

	    checkIsEmptyBigDecimal(singleColumnDetail);
	    (repSessionCO.getCurrentTemplate()).getColumnDetails().add(singleColumnDetail);
	}
	else if(RepConstantsCommon.MODE_EDIT.equals(detailColGridMode))// If the
	// user
	// edit
	// an
	// existing line
	{
	    COLMNTMPLTCO oldCO = findTemplateCODetInSession(new BigDecimal(lineNbr),false);

	    if(oldCO == null)
	    {
		throw new Exception("Template line with ID" + lineNbr.toString() + " not found");
	    }

	    // Get Old Column Line details
	    HashMap<BigDecimal, COLMNTMPLTVO> oldColDetMap = repSessionCO.getCurrentTemplate().getOldColDetMap();

	    if(oldColDetMap.get(singleColumnDetail.getColmnTmpltVO().getCOMP_CODE()) == null)
	    {
		COLMNTMPLTVO oldColDetVO = new COLMNTMPLTVO();
		oldColDetVO.setCOMP_CODE(oldCO.getColmnTmpltVO().getCOMP_CODE());
		oldColDetVO.setCODE(oldCO.getColmnTmpltVO().getCODE());
		oldColDetVO.setLINE_NBR(oldCO.getColmnTmpltVO().getLINE_NBR());
		oldColDetVO.setBRIEF_NAME_ENG(oldCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
		oldColDetVO.setBRIEF_NAME_ARAB(oldCO.getColmnTmpltVO().getBRIEF_NAME_ARAB());
		oldColDetVO.setCOL_TYPE(oldCO.getColmnTmpltVO().getCOL_TYPE());
		oldColDetVO.setFROM_DATE(oldCO.getColmnTmpltVO().getFROM_DATE());
		oldColDetVO.setTO_DATE(oldCO.getColmnTmpltVO().getTO_DATE());
		oldColDetVO.setCREATED_BY(oldCO.getColmnTmpltVO().getCREATED_BY());
		oldColDetVO.setDATE_CREATED(oldCO.getColmnTmpltVO().getDATE_CREATED());
		oldColDetVO.setMODIFIED_BY(oldCO.getColmnTmpltVO().getMODIFIED_BY());
		oldColDetVO.setDATE_MODIFIED(oldCO.getColmnTmpltVO().getDATE_MODIFIED());
		oldColDetVO.setCOMP(oldCO.getColmnTmpltVO().getCOMP());
		oldColDetVO.setFROM_BRANCH(oldCO.getColmnTmpltVO().getFROM_BRANCH());
		oldColDetVO.setTO_BRANCH(oldCO.getColmnTmpltVO().getTO_BRANCH());
		oldColDetVO.setFROM_DIV(oldCO.getColmnTmpltVO().getFROM_DIV());
		oldColDetVO.setTO_DIV(oldCO.getColmnTmpltVO().getTO_DIV());
		oldColDetVO.setFROM_DEPT(oldCO.getColmnTmpltVO().getFROM_DEPT());
		oldColDetVO.setTO_DEPT(oldCO.getColmnTmpltVO().getTO_DEPT());
		oldColDetVO.setFROM_UNIT(oldCO.getColmnTmpltVO().getFROM_UNIT());
		oldColDetVO.setTO_UNIT(oldCO.getColmnTmpltVO().getTO_UNIT());
		oldColDetVO.setBOLD(oldCO.getColmnTmpltVO().getBOLD());
		oldColDetVO.setDISP_COL_TOT_ZERO(oldCO.getColmnTmpltVO().getDISP_COL_TOT_ZERO());
		oldColDetVO.setALL_CRITERIA(oldCO.getColmnTmpltVO().getALL_CRITERIA());
		oldColDetVO.setFROM_REGION(oldCO.getColmnTmpltVO().getFROM_REGION());
		oldColDetVO.setTO_REGION(oldCO.getColmnTmpltVO().getTO_REGION());

		oldColDetMap.put(oldCO.getColmnTmpltVO().getLINE_NBR(), oldColDetVO);
	    }

	    singleColumnDetail.getColmnTmpltVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    oldCO.setCOMP_STR(singleColumnDetail.getCOMP_STR());

	    oldCO.getColmnTmpltVO().setCOL_TYPE(singleColumnDetail.getColmnTmpltVO().getCOL_TYPE());

	    if(det_DISP_COL_TOT_ZERO)
	    {
		oldCO.getColmnTmpltVO().setDISP_COL_TOT_ZERO(RepConstantsCommon.Y);
	    }
	    else
	    {
		oldCO.getColmnTmpltVO().setDISP_COL_TOT_ZERO(RepConstantsCommon.N);
	    }

	    oldCO.getColmnTmpltVO().setEXPRESSION(singleColumnDetail.getColmnTmpltVO().getEXPRESSION());
	    oldCO.getColmnTmpltVO().setEXPRESSION_LINES(singleColumnDetail.getColmnTmpltVO().getEXPRESSION_LINES());
	    oldCO.setFROM_BRANCH_STR(singleColumnDetail.getFROM_BRANCH_STR());

	    oldCO.getColmnTmpltVO().setFROM_DATE(singleColumnDetail.getColmnTmpltVO().getFROM_DATE());
	    oldCO.setFROM_DEPT_STR(singleColumnDetail.getFROM_DEPT_STR());
	    oldCO.setFROM_REGION_STR(singleColumnDetail.getFROM_REGION_STR());
	    oldCO.setFROM_DIV_STR(singleColumnDetail.getFROM_DIV_STR());
	    oldCO.setFROM_UNIT_STR(singleColumnDetail.getFROM_UNIT_STR());
	    oldCO.setCOL_TYPE_STR(singleColumnDetail.getCOL_TYPE_STR());
	    oldCO.getColmnTmpltVO().setBRIEF_NAME_ENG(singleColumnDetail.getColmnTmpltVO().getBRIEF_NAME_ENG());
	    oldCO.getColmnTmpltVO().setBRIEF_NAME_ARAB(singleColumnDetail.getColmnTmpltVO().getBRIEF_NAME_ARAB());

	    if(det_IS_BOLD)
	    {
		oldCO.getColmnTmpltVO().setBOLD(RepConstantsCommon.Y);
	    }
	    else
	    {
		oldCO.getColmnTmpltVO().setBOLD(RepConstantsCommon.N);
	    }

	    if(allCriteria)
	    {
		oldCO.getColmnTmpltVO().setALL_CRITERIA(RepConstantsCommon.Y);
	    }
	    else
	    {
		oldCO.getColmnTmpltVO().setALL_CRITERIA(RepConstantsCommon.N);
	    }

	    oldCO.setTO_BRANCH_STR(singleColumnDetail.getTO_BRANCH_STR());

	    oldCO.getColmnTmpltVO().setTO_DATE(singleColumnDetail.getColmnTmpltVO().getTO_DATE());
	    oldCO.setTO_DEPT_STR(singleColumnDetail.getTO_DEPT_STR());
	    oldCO.setTO_REGION_STR(singleColumnDetail.getTO_REGION_STR());
	    oldCO.setTO_DIV_STR(singleColumnDetail.getTO_DIV_STR());
	    oldCO.setTO_UNIT_STR(singleColumnDetail.getTO_UNIT_STR());
	    oldCO.getColmnTmpltVO().setSPECIFY_DAY(singleColumnDetail.getColmnTmpltVO().getSPECIFY_DAY());
	    oldCO.getColmnTmpltVO().setSPECIFY_MONTH(singleColumnDetail.getColmnTmpltVO().getSPECIFY_MONTH());
	    oldCO.setCURRENCY_STR(singleColumnDetail.getCURRENCY_STR());
	    checkIsEmptyBigDecimal(oldCO);
	}
	return SUCCESS;
    }

    public void checkIsEmptyBigDecimal(COLMNTMPLTCO detCO)
    {

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getCOMP_CODE()))
	{
	    detCO.getColmnTmpltVO().setCOMP_CODE(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setCOMP_CODE(singleColumnDetail.getColmnTmpltVO().getCOMP_CODE());
	}
	if(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(singleColumnDetail.getColmnTmpltVO().getCOMP()))
	{
	    detCO.getColmnTmpltVO().setCOMP(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setCOMP(singleColumnDetail.getColmnTmpltVO().getCOMP());
	}
	if(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(singleColumnDetail.getColmnTmpltVO().getTO_REGION()))
	{
	    detCO.getColmnTmpltVO().setTO_REGION(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setTO_REGION(singleColumnDetail.getColmnTmpltVO().getTO_REGION());
	}
	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getFROM_BRANCH()))
	{
	    detCO.getColmnTmpltVO().setFROM_BRANCH(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setFROM_BRANCH(singleColumnDetail.getColmnTmpltVO().getFROM_BRANCH());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getFROM_REGION()))
	{
	    detCO.getColmnTmpltVO().setFROM_REGION(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setFROM_REGION(singleColumnDetail.getColmnTmpltVO().getFROM_REGION());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getFROM_DEPT()))
	{
	    detCO.getColmnTmpltVO().setFROM_DEPT(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setFROM_DEPT(singleColumnDetail.getColmnTmpltVO().getFROM_DEPT());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getFROM_DIV()))
	{
	    detCO.getColmnTmpltVO().setFROM_DIV(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setFROM_DIV(singleColumnDetail.getColmnTmpltVO().getFROM_DIV());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getFROM_UNIT()))
	{
	    detCO.getColmnTmpltVO().setFROM_UNIT(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setFROM_UNIT(singleColumnDetail.getColmnTmpltVO().getFROM_UNIT());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getTO_BRANCH()))
	{
	    detCO.getColmnTmpltVO().setTO_BRANCH(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setTO_BRANCH(singleColumnDetail.getColmnTmpltVO().getTO_BRANCH());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getTO_CY()))
	{
	    detCO.getColmnTmpltVO().setTO_CY(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setTO_CY(singleColumnDetail.getColmnTmpltVO().getTO_CY());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getTO_DEPT()))
	{
	    detCO.getColmnTmpltVO().setTO_DEPT(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setTO_DEPT(singleColumnDetail.getColmnTmpltVO().getTO_DEPT());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getTO_DIV()))
	{
	    detCO.getColmnTmpltVO().setTO_DIV(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setTO_DIV(singleColumnDetail.getColmnTmpltVO().getTO_DIV());
	}

	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getTO_UNIT()))
	{
	    detCO.getColmnTmpltVO().setTO_UNIT(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setTO_UNIT(singleColumnDetail.getColmnTmpltVO().getTO_UNIT());
	}
	if(NumberUtil.isEmptyDecimal(singleColumnDetail.getColmnTmpltVO().getFROM_CY()))
	{
	    detCO.getColmnTmpltVO().setFROM_CY(null);
	}
	else
	{
	    detCO.getColmnTmpltVO().setFROM_CY(singleColumnDetail.getColmnTmpltVO().getFROM_CY());
	}
    }

    public String findSingleColTempl() throws Exception
    {
	COLMNTMPLTVO vo = new COLMNTMPLTVO();
	SessionCO sessionCO = returnSessionObject();
	vo.setCOMP_CODE(sessionCO.getCompanyCode());
	vo.setCODE(new BigDecimal(TEMPLATE_CODE));

	singleColumn = columnTemplateBO.findSingleColumnTemplate(vo);

	return SUCCESS;
    }

    public Object getModel()
    {
	return tmplGridSC;
    }

    // Function to delete the lines details of column template
    public void deleteColumnTemplateDetail() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());

	// Loop through the lines of current column template, and check the one
	// that has LINE_NBR = lineNbr of the selected line
	List<FTR_TMPLT_EXPRCO> exprLst;
	for(COLMNTMPLTCO co : (repSessionCO.getCurrentTemplate()).getColumnDetails())
	{
	    if(lineNbr.equals(co.getColmnTmpltVO().getLINE_NBR().toString()))
	    {
		exprLst = new ArrayList<FTR_TMPLT_EXPRCO>();
		exprLst.addAll(co.getExpressions());
		(repSessionCO.getCurrentTemplate()).getColumnDetails().remove(co);

		// Delete the lines expressions of the deleted line
		for(FTR_TMPLT_EXPRCO expr : exprLst)
		{
		    co.getExpressions().remove(expr);
		}

		// Delete the lines criteria of the deleted line
		for(COLMNTMPLT_PARAM_LINKCO crt : co.getColmnTmpltParamLink())
		{
		    co.getColmnTmpltParamLink().remove(crt);
		}
		
		//add a record to the delCrtMap in order to delete the criteria related to the deleted line
		COLMNTMPLT_PARAM_LINKCO crtCO=new COLMNTMPLT_PARAM_LINKCO();
		COLMNTMPLTVO colTmplVO=co.getColmnTmpltVO();
		BigDecimal compCode =colTmplVO.getCOMP_CODE();
		BigDecimal tmplCode=colTmplVO.getCODE();
		StringBuffer concatKeyCrt=new StringBuffer();
		concatKeyCrt.append(compCode).append(tmplCode).append(lineNbr);
		crtCO.getColumntmpltParamLinkVO().setCOMP_CODE(compCode);
		crtCO.getColumntmpltParamLinkVO().setTEMP_CODE(tmplCode);
		crtCO.getColumntmpltParamLinkVO().setLINE_NO(new BigDecimal(lineNbr));
		repSessionCO.getCurrentTemplate().getDelCrtMap().put(concatKeyCrt.toString(), crtCO);

		break;
	    }
	}
    }

    public void deleteExpression() throws Exception
    {
	COLMNTMPLTCO co = findTemplateCODetInSession(new BigDecimal(lineNbr),false);
	if(co == null)
	{
	    return;
	}
	if(co.getExpressions() == null)
	{
	    return;
	}
	for(FTR_TMPLT_EXPRCO expr : co.getExpressions())
	{
	    if(singleColumnDetail.getColmnTmpltVO().getCODE().equals(expr.getFtrTmpltExprVO().getCODE())
		    && lineNbr.equals(expr.getFtrTmpltExprVO().getTMPLT_LINE_NO().toString())
		    && expressionNbr.equals(expr.getFtrTmpltExprVO().getLINE_NO().toString()))
	    {
		co.getExpressions().remove(expr);
		return;
	    }
	}
    }

    public void deleteExpressions() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if(lineNbr.equals("") || lineNbr.equals("0") || repSessionCO.getCurrentTemplate() == null)
	{
	    return;
	}
	COLMNTMPLTCO co = findTemplateCODetInSession(new BigDecimal(lineNbr),false);
	co.setExpressions(null);
    }

    public String editColumnTemplate() throws Exception
    {
	COLMNTMPLTSC sc = new COLMNTMPLTSC();
	SessionCO sessionCO = returnSessionObject();
	sc.setCOMP_CODE(sessionCO.getCompanyCode());
	sc.setCODE(new BigDecimal(TEMPLATE_CODE));
	sc.setLINE_NBR(new BigDecimal(lineNbr));
	sc.setLANG_CODE(sessionCO.getLanguage());
	singleColumnDetail = columnTemplateBO.findSingleColumnTemplateDet(sc);
	if(singleColumnDetail.getColmnTmpltVO().getDISP_COL_TOT_ZERO().equals("1"))
	{
	    setDet_DISP_COL_TOT_ZERO(true);
	}
	else
	{
	    setDet_DISP_COL_TOT_ZERO(false);
	}

	if(singleColumnDetail.getColmnTmpltVO().getBOLD().equals("1"))
	{
	    setDet_IS_BOLD(true);
	}
	else
	{
	    setDet_IS_BOLD(false);
	}

	// fill the combo of display values
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(6)); // 6 =column template
	// type (check table
	// sys_param_lov_type)
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	colTypeArrList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	return SUCCESS;
    }

    public String openColumnDetExpr() throws Exception
    {
	if(detExprGridMode == null)
	{
	    return SUCCESS;
	}
	if(RepConstantsCommon.MODE_NONE.equals(detExprGridMode))
	{
	    if(singleColumnDetail == null)
	    {
		setGridModel(null);
		return SUCCESS;
	    }
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());

	    if(repSessionCO.getCurrentTemplate() == null)
	    {
		setGridModel(null);
		return SUCCESS;
	    }

	    COLMNTMPLTCO currentCO = findTemplateCODetInSession(singleColumnDetail.getColmnTmpltVO().getLINE_NBR(),false);
	    setGridModel(currentCO.getExpressions());
	}
	// setGridModel((List<IRP_TEMPLATE_COLUMNS_DETCO>)request.getSession().getAttribute("allColTemplExpr"));
	return SUCCESS;
    }

    public String countOfColumnTemplate() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    COLMNTMPLTSC sc = new COLMNTMPLTSC();
	    sc.setCOMP_CODE(sessionCO.getCompanyCode());
	    sc.setCODE(new BigDecimal(request.getParameter("TEMPLATE_CODE")));

	    columnTemplateCount = columnTemplateBO.findSingleColumnTemplatesCount(sc).toString();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    public String findColumnTemplateDetailsCount() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    //if null => reorganize button has not been pressed
	    COLMNTMPLTCO currentTemplate = repSessionCO.getCurrentTemplate(); 
	    boolean flag = repSessionCO.getCurrentTemplate() != null
		    && repSessionCO.getCurrentTemplate().getNewLineNumber() == null ? false : true;
	    String tmplCode = getUpdates();
	    String lineNb = getLineNbr();
	    if(tmplCode == null || tmplCode.equals(""))
	    {
		setUpdates(getText("template.col.posNbAlert"));
	    }
	    else if(lineNb == null || lineNb.equals(""))
	    {
		setUpdates(getText("col.missingTmplCodeAlert"));
	    }
	    else if(findTemplateCODetInSession(new BigDecimal(lineNbr), flag) == null&& currentTemplate != null
		    && currentTemplate.getDelLinesMap().get(new BigDecimal(lineNbr)) == null)
	    {
		    COLMNTMPLTSC sc = new COLMNTMPLTSC();
		    sc.setCOMP_CODE(sessionCO.getCompanyCode());
		    sc.setCODE(new BigDecimal(tmplCode));
		    sc.setLINE_NBR(new BigDecimal(lineNb));
		    sc.setLANG_CODE(sessionCO.getLanguage());
		    if(!flag)
		    {
			columnTemplateDetailsCount = columnTemplateBO.findColumnTemplateDetailsCount(sc).toString();
		    }
		    setUpdates("");
	    }
	    else if(currentTemplate != null && currentTemplate.getDelLinesMap().get(new BigDecimal(lineNbr)) == null)
	    {
		setUpdates("");
		setColumnTemplateDetailsCount(RepConstantsCommon.ONE);
	    }
	    else
	    {
		setUpdates("");
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }


    /**
     * Method that checks if the line is of type expression
     * @return
     * @throws Exception
     */
    public String findColumnTemplateDetailType() throws Exception
    {
	COLMNTMPLTCO co = findTemplateCODetInSession(new BigDecimal(lineNbr),false);
	if(co == null)
	{
	    setColumnTemplateDetailsCount("false");
	}
	else
	{
	    if(co.getCOL_TYPE_STR().equals("Expression"))
	    {
		setColumnTemplateDetailsCount("true");
	    }
	    else
	    {
		setColumnTemplateDetailsCount("false");
	    }
	}

	return SUCCESS;
    }

    public String loadLookUpFromToDeptGrid()
    {
	try
	{
	    setSearchFilter(commDet);
	    copyproperties(commDet);
	    if("".equals(StringUtil.nullToEmpty(getColumn1())) || "".equals(StringUtil.nullToEmpty(getColumn2())) ||
		    "".equals(StringUtil.nullToEmpty(getColumn3())))
	    {
		setRecords(0);
		setGridModel(new ArrayList());

	    }
	    else
	    {
		commDet.setCOMP_CODE(new BigDecimal(getColumn1()));
		commDet.setDIV_CODE(new BigDecimal(getColumn2()));
		commDet.setTO_DIV_CODE(new BigDecimal(getColumn3()));
		List deptList = columnTemplateBO.findDepartmentsBetweenDiv(commDet);
		int cnt = columnTemplateBO.countOfDepartmentsFunc(commDet).intValue();
		setRecords(cnt);
		setGridModel(deptList);

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    private BigDecimal getMaxID() throws Exception
    {
	COLMNTMPLTCO currentLine = findTemplateCODetInSession(new BigDecimal(tempCodeLineNbr),false);
	if(currentLine == null)
	{
	    throw new Exception("Template line not selected");
	}
	if(currentLine.getExpressions() == null)
	{
	    return BigDecimal.ZERO;
	}
	BigDecimal maxID = BigDecimal.ZERO;
	for(FTR_TMPLT_EXPRCO co : currentLine.getExpressions())
	{
	    if(co.getFtrTmpltExprVO().getLINE_NO().compareTo(maxID) >= 0)
	    {
		maxID = co.getFtrTmpltExprVO().getLINE_NO();
	    }
	}
	return maxID;
    }

    public String lineOrValueChange() throws Exception
    {
	try
	{
	    if(request.getParameter("EXP_TYPE").equals("L"))
	    {
		String exp = request.getParameter("EXP_LINE");
		if(RepConstantsCommon.EMPTY_STRING.equals(exp) || "0".equals(exp))
		{
		    setUpdates("false");
		    String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.INVALID_LINE_NB,
			    returnSessionObject().getLanguage());
		    addDependencyMsg(msg, null);
		}

		COLMNTMPLTCO tco = findTemplateCODetInSession(new BigDecimal(exp),false);

		if(tco == null)
		{
		    voExpr.getFtrTmpltExprVO().setEXP_LINE(null);
		    setUpdates("false");
		    String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.INVALID_LINE_NB,
			    returnSessionObject().getLanguage());
		    addDependencyMsg(msg, null);
		}
		else
		{
		    voExpr.getFtrTmpltExprVO().setEXP_LINE(new BigDecimal(exp));
		    voExpr.getFtrTmpltExprVO().setEXP_VALUE(null);
		    setUpdates("true");
		}
	    }
	    else
	    {
		voExpr.getFtrTmpltExprVO().setEXP_LINE(null);
		voExpr.getFtrTmpltExprVO().setEXP_VALUE(new BigDecimal(request.getParameter("VALUE")));
		setUpdates("false");
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String expressionDependency() throws Exception
    {
	try
	{
	    String expType = request.getParameter("EXP_TYPE");
	    String val = request.getParameter("VALUE");
	    String exp = request.getParameter("EXP_LINE_NO");

	    if(RepConstantsCommon.EMPTY_STRING.equals(val))
	    {
		val = "0";
	    }
	    if(RepConstantsCommon.EMPTY_STRING.equals(exp))
	    {
		exp = "0";
	    }
	    if("L".equals(expType))// line
	    {
		voExpr.setEXP_LINE_NO_READONLY("false");
		voExpr.setVALUE_READONLY("true");

		voExpr.getFtrTmpltExprVO().setEXP_VALUE(BigDecimal.ZERO);
		voExpr.getFtrTmpltExprVO().setEXP_LINE(new BigDecimal(exp));
	    }
	    else
	    // value
	    {
		voExpr.setEXP_LINE_NO_READONLY("true");
		voExpr.setVALUE_READONLY("false");

		voExpr.getFtrTmpltExprVO().setEXP_LINE(BigDecimal.ZERO);
		voExpr.getFtrTmpltExprVO().setEXP_VALUE(new BigDecimal(val));
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method called on the save button in column template screen
     * @return
     * @throws Exception
     */
    public String saveTemplate() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    COLMNTMPLTCO currentTemplate = (repSessionCO.getCurrentTemplate());
	    checkIfWithoutLines(currentTemplate);
	    
	    if(singleColumn.getCODE() != null && currentTemplate == null
		    && !singleColumn.getCODE().toString().equals("0"))
	    {
		currentTemplate = new COLMNTMPLTCO();
		currentTemplate.getColmnTmpltVO().setCODE(singleColumn.getCODE());
	    }
	    // checking if there's a criteria currency and included in case the
	    // column template has any line with calculation types:
	    // VBF,ABF,AGF,AMF,GMF,MBF
	    boolean currencyCrt;
	    int cntCrtCurrency;
	    BigDecimal crtConcatKey;
	    COLMNTMPLT_PARAM_LINKCO crtCO;
	    for(COLMNTMPLTCO colTmpltCO : currentTemplate.getColumnDetails())
	    {
		cntCrtCurrency = 0;
		if(RepConstantsCommon.COL_TEMP_FC_CALCS.contains(colTmpltCO.getColmnTmpltVO().getCOL_TYPE()))
		{
		    currencyCrt = false;
		    if(colTmpltCO.getDbCrtMap().size() == 0)
		    {
			COLMNTMPLTSC crtSC = new COLMNTMPLTSC();
			crtSC.setCOMP_CODE(compCode);
			crtSC.setCODE(currentTemplate.getColmnTmpltVO().getCODE());
			crtSC.setLINE_NBR(colTmpltCO.getColmnTmpltVO().getLINE_NBR());
			crtSC.setLANG_CODE(sessionCO.getLanguage());
			List<COLMNTMPLT_PARAM_LINKCO> crtList = columnTemplateBO.getCrtList(crtSC);
			for(int j = 0; j < crtList.size(); j++)
			{
			    crtCO = crtList.get(j);
			    if(colTmpltCO.getDelCrtMap().get(crtCO.getConcatKey())==null)
			    {
			    	colTmpltCO.getDbCrtMap().put(crtCO.getConcatKey(), crtCO);
			    }
			}
		    }
		    Iterator<Map.Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO>> itCrtMapEn = colTmpltCO.getDbCrtMap()
			    .entrySet().iterator();
		    while(itCrtMapEn.hasNext())
		    {
			Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itCrtMapEn.next();
			crtCO = entry.getValue();
			crtConcatKey = new BigDecimal(compCode.toString()
				+ currentTemplate.getColmnTmpltVO().getCODE().toString()
				+ colTmpltCO.getColmnTmpltVO().getLINE_NBR().toString()
				+ crtCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().toString());
			if(colTmpltCO.getModifCrtMap().get(crtConcatKey) == null
				&& colTmpltCO.getDelCrtMap().get(crtConcatKey) == null
				&& crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CURRENCY))
			{
			    if(RepConstantsCommon.YES_CAP.equals(crtCO.getColumntmpltParamLinkVO().getINCLUDE()))
			    {
				currencyCrt = true;
			    }
			    cntCrtCurrency++;
			}
		    }

		    itCrtMapEn = colTmpltCO.getAddCrtMap().entrySet().iterator();
		    while(itCrtMapEn.hasNext())
		    {
			Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itCrtMapEn.next();
			crtCO = entry.getValue();
			if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CURRENCY))
			{
			    if(RepConstantsCommon.YES_CAP.equals(crtCO.getColumntmpltParamLinkVO().getINCLUDE()))
			    {
				currencyCrt = true;
			    }
			    cntCrtCurrency++;
			}
		    }

		    itCrtMapEn = colTmpltCO.getModifCrtMap().entrySet().iterator();
		    while(itCrtMapEn.hasNext())
		    {
			Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itCrtMapEn.next();
			crtCO = entry.getValue();
			crtConcatKey = new BigDecimal(compCode.toString()
				+ currentTemplate.getColmnTmpltVO().getCODE().toString()
				+ colTmpltCO.getColmnTmpltVO().getLINE_NBR().toString()
				+ crtCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().toString());
			if(colTmpltCO.getDelCrtMap().get(crtConcatKey) == null
				&& crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CURRENCY))
			{
			    if(RepConstantsCommon.YES_CAP.equals(crtCO.getColumntmpltParamLinkVO().getINCLUDE()))
			    {
				currencyCrt = true;
			    }
			    cntCrtCurrency++;
			}
		    }
		    if(!currencyCrt || cntCrtCurrency > 1)
		    {
			BOException e = new BOException();
			e.setMsgIdent(RepConstantsCommon.COLTMP_MISSING_CRITERIA);
			throw e;
		    }

		}
	    }
	 

	    currentTemplate.getColmnTmpltVO().setBRIEF_NAME_ENG(singleColumn.getBRIEF_NAME_ENG());
	    currentTemplate.getColmnTmpltVO().setBRIEF_NAME_ARAB(singleColumn.getBRIEF_NAME_ARAB());
	    currentTemplate.getColmnTmpltVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    currentTemplate.getColmnTmpltVO().setPRINT_PAPER_ORIENTATION(singleColumn.getPRINT_PAPER_ORIENTATION());
	    currentTemplate.getColmnTmpltVO().setPRINT_PAPER_SIZE(singleColumn.getPRINT_PAPER_SIZE());

	    // get oldColTempl and apply audit
	    COLMNTMPLTVO oldColTmpl = null;
	    if(RepConstantsCommon.MODE_EDIT.equals(detGridMode))
	    {
		oldColTmpl = (COLMNTMPLTVO) returnAuditObject(COLMNTMPLTVO.class);
	    }

	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.COL_TEMPL_KEY_REF);
	    currentTemplate.setAuditRefCO(refCO);

	    templateBO.checkRequiredFields(currentTemplate, get_pageRef(), compCode, sessionCO.getCurrentAppName(),
		    sessionCO.getLanguage());

	    columnTemplateBO.saveColumnTemplate(detGridMode, linesGridMode, currentTemplate, returnUserName(),
		    oldColTmpl,sessionCO.getLanguage());

	    repSessionCO.setCurrentTemplate(null);
	}
	catch(BOException e)
	{
	    if("no lines in template being created".equals(e.getMsgIdent()))
	    {
		handleException(null, null, "col.detailsInfUnexisting");
		return ERROR;
	    }
	    else if(RepConstantsCommon.COLTMP_MISSING_CRITERIA.equals(e.getMsgIdent()))
	    {
		handleException(null, null,RepConstantsCommon.COLTMP_MISSING_CRITERIA);
	    }
	    else
	    {
		handleException(e, null, null);
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    // Add a new Expression
    public String addExpression() throws Exception
    {
	COLMNTMPLTCO currentTemplateLine = findTemplateCODetInSession(new BigDecimal(tempCodeLineNbr),false);

	if(currentTemplateLine == null)
	{
	    throw new Exception("Template detail with ID " + tempCodeLineNbr.toString() + " is not found");
	}

	SessionCO sessionCO = returnSessionObject();

	if(updates != null && !updates.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates, FTR_TMPLT_EXPRCO.class);
	    ArrayList lstAdd = gu.getLstAdd();
	    ArrayList lstEdit = gu.getLstModify();
	    FTR_TMPLT_EXPRVO oldExprVO;

	    HashMap<BigDecimal, FTR_TMPLT_EXPRVO> oldExprMap = currentTemplateLine.getOldExprMap();

	    for(int i = 0; i < lstAdd.size(); i++)
	    {
		((FTR_TMPLT_EXPRCO) lstAdd.get(i)).getFtrTmpltExprVO().setLINE_NO(
			new BigDecimal(getMaxID().intValue() + 1));
		((FTR_TMPLT_EXPRCO) lstAdd.get(i)).getFtrTmpltExprVO()
			.setTMPLT_LINE_NO(new BigDecimal(tempCodeLineNbr));
		((FTR_TMPLT_EXPRCO) lstAdd.get(i)).getFtrTmpltExprVO().setCOMP_CODE(sessionCO.getCompanyCode());
		((FTR_TMPLT_EXPRCO) lstAdd.get(i)).getFtrTmpltExprVO().setCODE(singleColumn.getCODE());

		if(currentTemplateLine.getExpressions() == null)
		{
		    currentTemplateLine.setExpressions(new ArrayList<FTR_TMPLT_EXPRCO>());
		}

		currentTemplateLine.getExpressions().add((FTR_TMPLT_EXPRCO) lstAdd.get(i));
	    }

	    for(int i = 0; i < lstEdit.size(); i++)
	    {
		for(FTR_TMPLT_EXPRCO expression : currentTemplateLine.getExpressions())
		{
		    if(expression.getFtrTmpltExprVO().getLINE_NO().equals(
			    ((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getLINE_NO()))
		    {
			// store oldValue
			if(oldExprMap.get(expression.getFtrTmpltExprVO().getLINE_NO()) == null)
			{
			    oldExprVO = new FTR_TMPLT_EXPRVO();
			    oldExprVO.setCOMP_CODE(expression.getFtrTmpltExprVO().getCOMP_CODE());
			    oldExprVO.setTMPLT_TYPE("C");
			    oldExprVO.setCODE(expression.getFtrTmpltExprVO().getCODE());
			    oldExprVO.setTMPLT_LINE_NO(expression.getFtrTmpltExprVO().getTMPLT_LINE_NO());
			    oldExprVO.setLINE_NO(expression.getFtrTmpltExprVO().getLINE_NO());
			    oldExprVO.setEXP_TYPE(expression.getFtrTmpltExprVO().getEXP_TYPE());
			    oldExprVO.setLEFT_OPERATOR(expression.getFtrTmpltExprVO().getLEFT_OPERATOR());
			    oldExprVO.setEXP_LINE(expression.getFtrTmpltExprVO().getEXP_LINE());
			    oldExprVO.setEXP_VALUE(expression.getFtrTmpltExprVO().getEXP_VALUE());
			    oldExprVO.setRIGHT_OPERATOR(expression.getFtrTmpltExprVO().getRIGHT_OPERATOR());
			    oldExprVO.setOPERATOR(expression.getFtrTmpltExprVO().getOPERATOR());
			    oldExprMap.put(expression.getFtrTmpltExprVO().getLINE_NO(), oldExprVO);
			}

			expression.getFtrTmpltExprVO().setCOMP_CODE(sessionCO.getCompanyCode());
			expression.getFtrTmpltExprVO().setCODE(singleColumn.getCODE());
			expression.getFtrTmpltExprVO().setTMPLT_LINE_NO(new BigDecimal(tempCodeLineNbr));
			expression.getFtrTmpltExprVO().setEXP_TYPE(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getEXP_TYPE());
			expression.getFtrTmpltExprVO().setLEFT_OPERATOR(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getLEFT_OPERATOR());
			expression.getFtrTmpltExprVO().setEXP_LINE(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getEXP_LINE());
			expression.getFtrTmpltExprVO().setEXP_VALUE(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getEXP_VALUE());
			expression.getFtrTmpltExprVO().setRIGHT_OPERATOR(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getRIGHT_OPERATOR());
			expression.getFtrTmpltExprVO().setOPERATOR(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getOPERATOR());
			expression.setExprOrGLByLine(((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getExprOrGLByLine());
		    }
		}
	    }
	}
	return SUCCESS;
    }

    public String loadLookUpFromToUnitGrid() throws Exception
    {
	try
	{
	    copyproperties(commDet);
	    setSearchFilter(commDet);
	    if((getColumn1() == null || getColumn1().isEmpty()) || (getColumn2() == null || getColumn2().isEmpty())
		    || (getColumn3() == null || getColumn3().isEmpty()))
	    {

		setRecords(0);
		setGridModel(new ArrayList());

	    }
	    else
	    {
		commDet.setCOMP_CODE(new BigDecimal(getColumn1()));
		commDet.setDIV_CODE(new BigDecimal(getColumn2()));
		commDet.setDEPT_CODE(new BigDecimal(getColumn3()));
		List<CommonDetailsVO> deptList = new ArrayList<CommonDetailsVO>();
		CommonDetailsVO commonDetailsVO = new CommonDetailsVO();
		commonDetailsVO.setCODE(BigDecimal.ZERO);
		commonDetailsVO.setBRIEF_DESC_ENG(RepConstantsCommon.ALL_UNITS);
		commonDetailsVO.setLONG_DESC_ENG(RepConstantsCommon.ALL_UNITS);
		commonDetailsVO.setBRIEF_DESC_ARAB(RepConstantsCommon.ALL_UNITS);
		commonDetailsVO.setLONG_DESC_ARAB(RepConstantsCommon.ALL_UNITS);
		deptList.add(commonDetailsVO);
		deptList.addAll(columnTemplateBO.findUnits(commDet));
		int cnt = columnTemplateBO.findUnitsListCount(commDet);

		setRecords(cnt);
		setGridModel(deptList);
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadLookUpDivisonGrid() throws Exception
    {
	try
	{
	    setSearchFilter(tmplGridSC);
	    copyproperties(tmplGridSC);
	    if(getColumn1() == null || getColumn1().isEmpty())
	    {
		setRecords(0);
		setGridModel(new ArrayList());

	    }
	    else
	    {

		tmplGridSC.setCOMP_CODE(new BigDecimal(getColumn1()));
		tmplGridSC.setBRIEF_DESC_ARAB(getText("gl.allDivisions"));
		tmplGridSC.setFROM_COL(ConstantsCommon.YES);
		List divList = templateBO.getDivisonsList(tmplGridSC);
		int cnt = templateBO.getDivisonsListCount(tmplGridSC);
		setRecords(cnt);
		setGridModel(divList);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookupDivision() throws JSONException
    {
	try
	{
	    fillLookup("divisionGridId", "/path/colTemplateMaintReport/divisionDetailsLookUpGrid", "Divisions List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookupFromToUnit() throws JSONException
    {
	try
	{
	    fillLookup("unitGridId", "/path/colTemplateMaintReport/unitDetailsLookUpGrid", "Units List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookupFromToDept() throws JSONException
    {
	try
	{
	    fillLookup("deptGridId", "/path/colTemplateMaintReport/deptDetailsLookUpGrid", "Departments List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookupFromToBranch() throws JSONException
    {
	try
	{
	    fillLookup("branchGridId", "/path/colTemplateMaintReport/branchLookUpGrid", "Branches List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadLookUpFromToBranchGrid() throws Exception
    {
	try
	{
	    setSearchFilter(tmplGridSC);
	    copyproperties(tmplGridSC);
	    if(getColumn1() == null || getColumn1().isEmpty())
	    {
		setRecords(0);
		setGridModel(new ArrayList());

	    }
	    else
	    {

		tmplGridSC.setCOMP_CODE(new BigDecimal(getColumn1()));
		List branchList = templateBO.getBranchesList(tmplGridSC);
		int cnt = templateBO.getBranchesListCount(tmplGridSC);
		setRecords(cnt);
		setGridModel(branchList);

	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadLookUpFromToCurrencyGrid() throws Exception
    {
	return SUCCESS;
    }

    // start fill currency lookup
    public String fillLookupFromToCurrency() throws JSONException
    {
	try
	{
	    fillLookup("currencyGridId", "/path/colTemplateMaintReport/currencyLookUpGrid", "Currency List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "CODE", "BRIEF_DESC_ENG", "BRIEF_DESC_ARAB", "LONG_DESC_ENG", "LONG_DESC_ARAB" };
	    String[] colType = { "number", "text", "text", "text", "text" };
	    String[] titles = { getText("reporting.lkpCode"), getText("reporting.lkpBriefEng"),
		    getText("reporting.lkpBriefAr"), getText("reporting.lkpLongEng"), getText("reporting.lkpLongAr") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    // grid.setId(gridId);
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    // grid.setFilter(false);
	    grid.setUrl(gridUrl);

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

		if(name[i].equals("value_date"))
		{
		    gridColumn.setFormatter("date");
		    gridColumn.setEditrules("{date:true}");
		}
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, commDet);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applyCurrDependency()
    {
	return SUCCESS;
    }

    public String applyDivisionDependency() throws JSONException
    {
	try
	{
	    String compCode = getColumn1();
	    String divCode = getColumn2();
	    String isFrom = getColumn3();
	    if(compCode == null || compCode.equals(""))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    if(divCode == null || divCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
		updateInputDisplay(BigDecimal.ONE, 11, isFrom, null);
	    }
	    else
	    {
		TmplGridSC divSC = new TmplGridSC();
		divSC.setDIV_CODE(new BigDecimal(divCode));
		divSC.setCOMP_CODE(new BigDecimal(compCode));
		divSC.setGrid(false);
		divSC.setBRIEF_DESC_ARAB(getText("gl.allDivisions"));
		divSC.setFROM_COL(ConstantsCommon.YES);
		List divList = templateBO.getDivisonsList(divSC);
		lkpDependencyVO = new CommonDetailsVO();
		if(divList == null || divList.isEmpty())
		{
		    lkpDependencyVO.setBRIEF_DESC_ENG(null);
		    updateInputDisplay(BigDecimal.ZERO, 1, isFrom, null);
		}
		else
		{
		    CommonDetailsVO retVal = (CommonDetailsVO) divList.get(0);
		    if(divList.size()>1) //case of other divisions
		    {
			retVal =(CommonDetailsVO) divList.get(1);
		    }
		    
		    lkpDependencyVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    lkpDependencyVO.setCODE(new BigDecimal(divCode));
		    updateInputDisplay(BigDecimal.ZERO, 1, isFrom, null);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> updateInputDisplay(BigDecimal readOnly, int isCalledFrom,
	    String isFrom, HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> initMap)
    {
	SYS_PARAM_SCREEN_DISPLAYVO buisnessElement = new SYS_PARAM_SCREEN_DISPLAYVO();
	buisnessElement.setIS_READONLY(readOnly);
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm;
	if(initMap == null)
	{
	    hm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	}
	else
	{
	    hm = initMap;
	}
	if(isCalledFrom == 0)
	{
	    hm.put("lookuptxt_brnchCode", buisnessElement);
	    hm.put("lookuptxt_brnchCodeTo", buisnessElement);
	    hm.put("lookuptxt_divCodeFrom", buisnessElement);
	    hm.put("lookuptxt_divCodeTo", buisnessElement);
	    hm.put("lookuptxt_depCodeFrom", buisnessElement);
	    hm.put("lookuptxt_FROM_DEPT", buisnessElement);
	    hm.put("lookuptxt_TO_DEPT", buisnessElement);
	    hm.put("lookuptxt_FROM_UNIT", buisnessElement);
	    hm.put("lookuptxt_TO_UNIT", buisnessElement);
	    hm.put("lookuptxt_FROM_REGION", buisnessElement);
	    hm.put("lookuptxt_TO_REGION", buisnessElement);
	}
	if(isCalledFrom == 1 || isCalledFrom == 11)
	{
	    if(isFrom != null && isFrom.equals("1"))
	    {
		hm.put("lookuptxt_depCodeFrom", buisnessElement);
		if(isCalledFrom == 11)
		{
		    hm.put("lookuptxt_FROM_UNIT", buisnessElement);
		}
	    }
	    else
	    {
		hm.put("lookuptxt_TO_DEPT", buisnessElement);
		if(isCalledFrom == 11)
		{
		    hm.put("lookuptxt_TO_UNIT", buisnessElement);
		}
	    }
	}
	if(isCalledFrom == 2)
	{
	    if(isFrom != null && isFrom.equals("1"))
	    {
		hm.put("lookuptxt_FROM_UNIT", buisnessElement);
	    }
	    else
	    {
		hm.put("lookuptxt_TO_UNIT", buisnessElement);
	    }
	}
	if(initMap == null)
	{
	    setAdditionalScreenParams(hm);
	}
	return hm;
    }

    public String applyDeptDependency()
    {
	try
	{
	    String compCode = getColumn1();
	    String divCode = getColumn2();
	    String deptCode = getColumn3();
	    String isFrom = getUpdates();
	    String toDiv=getTO_DIV();
	    
	    if(compCode == null || compCode.equals(""))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if("".equals(StringUtil.nullToEmpty(divCode)) || "".equals(StringUtil.nullToEmpty(toDiv)))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_DIVISION_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(deptCode == null || deptCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
		updateInputDisplay(BigDecimal.ONE, 2, isFrom, null);
	    }
	    else
	    {
		CommonDetailsSC sc=new CommonDetailsSC();
		sc.setCOMP_CODE(new BigDecimal(compCode));
		sc.setDIV_CODE(new BigDecimal(divCode));
		sc.setTO_DIV_CODE(new BigDecimal(toDiv));
		sc.setCODE(new BigDecimal(getColumn3()));
		sc.setGrid(false);
		
		List deptList = columnTemplateBO.findDepartmentsBetweenDiv(sc);
		lkpDependencyVO = new CommonDetailsVO();
		if(deptList == null || deptList.isEmpty())
		{
		    lkpDependencyVO.setBRIEF_DESC_ENG(null);
		    updateInputDisplay(BigDecimal.ZERO, 2, isFrom, null);
		}
		else
		{
		    CommonDetailsVO retVal = (CommonDetailsVO) deptList.get(0);
		    lkpDependencyVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    lkpDependencyVO.setCODE(new BigDecimal(deptCode));
		    updateInputDisplay(BigDecimal.ZERO, 2, isFrom, null);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applyBranchDependency() throws BaseException
    {
	try
	{
	    String compCode = getColumn1();
	    String branchCode = getColumn2();
	    if(branchCode == null || branchCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
	    }
	    else if(compCode == null || compCode.equals(""))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else
	    {
		TmplGridSC brSC = new TmplGridSC();
		brSC.setBRANCH_CODE(new BigDecimal(branchCode));
		brSC.setCOMP_CODE(new BigDecimal(compCode));
		brSC.setGrid(false);
		List branchList = templateBO.getBranchesList(brSC);
		lkpDependencyVO = new CommonDetailsVO();
		if(branchList == null || branchList.isEmpty())
		{
		    lkpDependencyVO.setBRIEF_DESC_ENG(null);
		    lkpDependencyVO.setCODE(null);
		}
		else
		{
		    CommonDetailsVO retVal = (CommonDetailsVO) branchList.get(0);
		    lkpDependencyVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    lkpDependencyVO.setCODE(new BigDecimal(branchCode));

		}
	    }
	}
	catch(Exception ex)
	{
	    handleException(ex, null, null);

	}
	return SUCCESS;
    }

    public String findAllCriteria() throws BaseException
    {
	if(request.getParameter("comp_code") == null || request.getParameter("comp_code").equals(""))
	{
	    return SUCCESS;
	}
	CommonDetailsVO vo = new CommonDetailsVO();
	BigDecimal compCode = new BigDecimal(request.getParameter("comp_code"));
	tmplGridSC.setCOMP_CODE(compCode);

	vo = commonLookupBO.findMinBranch(tmplGridSC);
	if(vo != null)
	{
	    FROM_BRANCH = vo.getCODE().toString();
	    FROM_BRANCH_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMaxBranch(tmplGridSC);
	if(vo != null)
	{
	    TO_BRANCH = vo.getCODE().toString();
	    TO_BRANCH_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMinRegion(tmplGridSC);
	if(vo != null)
	{
	    FROM_REGION = vo.getCODE().toString();
	    FROM_REGION_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMaxRegion(tmplGridSC);
	if(vo != null)
	{
	    TO_REGION = vo.getCODE().toString();
	    TO_REGION_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMinDivision(tmplGridSC);
	if(vo != null)
	{
	    FROM_DIV = vo.getCODE().toString();
	    FROM_DIV_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMaxDivision(tmplGridSC);
	if(vo != null)
	{
	    TO_DIV = vo.getCODE().toString();
	    TO_DIV_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMinDepartment(tmplGridSC);
	if(vo != null)
	{
	    FROM_DEP = vo.getCODE().toString();
	    FROM_DEP_DESC = vo.getLONG_DESC_ENG();
	}
	vo = commonLookupBO.findMaxDepartment(tmplGridSC);
	if(vo != null)
	{
	    TO_DEP = vo.getCODE().toString();
	    TO_DEP_DESC = vo.getLONG_DESC_ENG();
	}
	FROM_UNIT = "0";
	FROM_UNIT_DESC = getText("allkey");

	TO_UNIT = "0";
	TO_UNIT_DESC = getText("allkey");

	return SUCCESS;
    }

    public String applyUnitDependency() throws JSONException, BaseException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String compCode = getColumn1();
	    String divCode = getColumn2();
	    String deptCode = getColumn3();
	    String unitCode = getFROM_UNIT();

	    if(compCode == null || compCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }

	    else if(divCode == null || divCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_DIVISION_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(deptCode == null || deptCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_DEPT_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(unitCode == null || unitCode.equals(""))
	    {
		lkpDependencyVO = new CommonDetailsVO();
	    }
	    else if(unitCode.equals(String.valueOf(0)))
	    {
		lkpDependencyVO = new CommonDetailsVO();
		lkpDependencyVO.setBRIEF_DESC_ENG(RepConstantsCommon.ALL_UNITS);
		lkpDependencyVO.setCODE(BigDecimal.ZERO);
	    }
	    else
	    {
		CommonDetailsSC commDet = new CommonDetailsSC();
		commDet.setCODE(new BigDecimal(deptCode));
		commDet.setCOMP_CODE(sessionCO.getCompanyCode());
		commDet.setDIV_CODE(new BigDecimal(divCode));
		commDet.setUNIT_CODE(new BigDecimal(unitCode));
		CommonDetailsVO unitRow = columnTemplateBO.findSingleUnit(commDet);
		lkpDependencyVO = new CommonDetailsVO();
		if(unitRow == null)
		{
		    lkpDependencyVO.setBRIEF_DESC_ENG(null);
		}
		else
		{
		    CommonDetailsVO retVal = unitRow;
		    lkpDependencyVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    lkpDependencyVO.setCODE(new BigDecimal(unitCode));
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String checkIfEmpty() throws BaseException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    if(RepConstantsCommon.COL_TEMP_EXP_TYPE.equals(singleColumnDetail.getColmnTmpltVO().getCOL_TYPE())
		    || RepConstantsCommon.EN_CUR_BRIEF_NAME.equals(singleColumnDetail.getColmnTmpltVO().getCOL_TYPE())
		    || RepConstantsCommon.AR_CUR_BRIEF_NAME.equals(singleColumnDetail.getColmnTmpltVO().getCOL_TYPE())
		     || RepConstantsCommon.COL_CALC_PERCENTAGE.equals(getColType()))
	    {
		return SUCCESS;
	    }
	    else
	    {
		columnTemplateBO.checkRequiredFields(singleColumnDetail, get_pageRef(), sessionCO.getCompanyCode(),
			sessionCO.getCurrentAppName(), sessionCO.getLanguage());
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    // This function used when inserting a new line in the Column Grid Details.
    // So we can save the line after in case inserting line between two lines.
    public void rememberLine() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    COLMNTMPLTCO colTempl = repSessionCO.getCurrentTemplate();
	    colTempl.setNumberAfter(new BigDecimal(getLineNumber()));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    e.printStackTrace();
	}
    }

    public String fillLookupRegion() throws Exception
    {
	try
	{
	    fillLookup("regionGridId", "/path/colTemplateMaintReport/regionLookUpGrid", "Region List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadLookUpRegionGrid() throws Exception
    {
	try
	{
	    setSearchFilter(commDet);
	    copyproperties(commDet);
	    List regionList = commonLookupBO.getRegionList(commDet);
	    int cnt = commonLookupBO.getRegionListCount(commDet);
	    setRecords(cnt);
	    setGridModel(regionList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applyRegionDependency() throws JSONException
    {
	try
	{
	    CommonDetailsSC sc = new CommonDetailsSC();
	    String code = getColumn1();
	    if(code != null && !code.equals(""))
	    {
		sc.setCODE(new BigDecimal(code));
	    }
	    sc.setGrid(false);
	    List regionList = commonLookupBO.getRegionList(sc);
	    lkpDependencyVO = new CommonDetailsVO();
	    lkpDependencyVO.setBRIEF_DESC_ENG(null);
	    if(code == null || RepConstantsCommon.EMPTY_STRING.equals(code))
	    {
		lkpDependencyVO = new CommonDetailsVO();
	    }
	    else
	    {
		if(regionList == null || regionList.isEmpty())
		{
		    lkpDependencyVO.setBRIEF_DESC_ENG(null);
		    lkpDependencyVO.setCODE(null);
		}
		else
		{
		    CommonDetailsVO retVal = (CommonDetailsVO) regionList.get(0);
		    lkpDependencyVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    lkpDependencyVO.setCODE(sc.getCODE());
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String reorganizeLines() throws Exception
    {
	SYS_PARAM_SCREEN_DISPLAYVO vo = new SYS_PARAM_SCREEN_DISPLAYVO();
	vo.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("startingLineValue", vo);
	getAdditionalScreenParams().put("interval", vo);
	return "orgLines";
    }

    /**
     * Method that reorganizes the line numbers of the current column template
     * 
     * @return
     * @throws Exception
     */
    public String reorganizeLinesOrder() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    // Get the current template from the session
	    COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();
	    if(allTempl==null)
	    {
		return SUCCESS;
	    }
	    //setting the newlinenumber to handle cases where the user reorganizes and insert a new 
	    //line number same as an old existing one (findColumnTemplateDetailsCount)
	    allTempl.setNewLineNumber(BigDecimal.ZERO);
	    LinkedHashMap<BigDecimal, COLMNTMPLTCO> finalDbLinesMap;
	    // This map used to fill the list of column details that saved in
	    // session
	    LinkedHashMap<BigDecimal, COLMNTMPLTCO> dbLinesMap = new LinkedHashMap<BigDecimal, COLMNTMPLTCO>();
	    //map used for the mappping (lineNbr)/newLineNumber before reorganize
	    //in order to replace in the line values for expressions
	    HashMap<BigDecimal,BigDecimal> oldNewExprLinesMap = new HashMap<BigDecimal, BigDecimal>();
	    //map used for the mappping old new line number/new newLineNumber in order to replace in the line values for expressions
	    HashMap<BigDecimal,BigDecimal> oldNewNewExprLinesMap = new HashMap<BigDecimal, BigDecimal>();
	    // Get Column lines of the current template
	    List<COLMNTMPLTCO> colmnTempLinesList = allTempl.getColumnDetails();
	    for(int i = 0; i < colmnTempLinesList.size(); i++)
	    {
		dbLinesMap.put(colmnTempLinesList.get(i).getConcatKey(), colmnTempLinesList.get(i));
		oldNewExprLinesMap.put(colmnTempLinesList.get(i).getColmnTmpltVO().getLINE_NBR(), colmnTempLinesList
			.get(i).getNewLineNumber());
	    }
	    // used to store newly added organized lines to avoid conflict with
	    // add lines map
	    LinkedHashMap<BigDecimal, COLMNTMPLTCO> tempHash = new LinkedHashMap<BigDecimal, COLMNTMPLTCO>();
	    // put new line numbers for added lines and db lines
	    // holds the newest newLineNumber To be deleted later (Just for
	    // testing)
	    previousValue = getStartingLineValue().intValue();
	    dbLinesMap = sortHashMapByValuesD(dbLinesMap);
	    finalDbLinesMap = putNewLinesNumberMap(dbLinesMap, allTempl, tempHash);
	    allTempl.getNewLinesFromReorganize().put(singleColumnDetail.getNewLineNumber(),
		    singleColumnDetail.getNewLineNumber());
	    COLMNTMPLTCO co;
	    for(Entry<BigDecimal, COLMNTMPLTCO> ent : finalDbLinesMap.entrySet())
	    {
		co = ent.getValue();
		oldNewNewExprLinesMap.put(oldNewExprLinesMap.get(co.getColmnTmpltVO().getLINE_NBR()), co
			.getNewLineNumber());
	    }
	    SessionCO sessionCO = returnSessionObject();
	    // now adding the tempHash lines to the addLines
	    // manipulating the dblinesmap to display the new lines in the grid
	    // handling Criterias	
	    for(Entry<BigDecimal, COLMNTMPLTCO> ent : finalDbLinesMap.entrySet())
	    {
		COLMNTMPLTCO line = ent.getValue();
		// if the dbcrtMap is not loaded yet should handle this case
		if((line.getDbCrtMap().size() == 0 && line.getColmnTmpltVO().getLINE_NBR() != null && ((newLineIndicator != 0 && line
			.getNewLineNumber().intValue() != newLineIndicator) || valueToRemember.intValue() == 0))
			&& allTempl.getNewLinesFromReorganize().get(line.getNewLineNumber()) == null)
		{
		    COLMNTMPLTSC crtSC = new COLMNTMPLTSC();
		    crtSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    crtSC.setCODE(allTempl.getColmnTmpltVO().getCODE());
		    crtSC.setLINE_NBR(line.getColmnTmpltVO().getLINE_NBR());
		    crtSC.setLANG_CODE(sessionCO.getLanguage());
		    // get the db criterias as list then put it in the
		    // linkedHashMap
		    List<COLMNTMPLT_PARAM_LINKCO> crtList = columnTemplateBO.getCrtList(crtSC);
		    for(int j = 0; j < crtList.size(); j++)
		    {
			COLMNTMPLT_PARAM_LINKCO crtCO = crtList.get(j);
			line.getDbCrtMap().put(crtCO.getConcatKey(), crtCO);
		    }
		}
		// end handling not loaded criterias
		LinkedHashMap<BigDecimal, COLMNTMPLT_PARAM_LINKCO> crtMap = line.getDbCrtMap();
		// modif
		Iterator<Map.Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO>> itCrtMapEn = crtMap.entrySet().iterator();
		while(itCrtMapEn.hasNext())
		{
		    Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itCrtMapEn.next();
		    COLMNTMPLT_PARAM_LINKCO crtCO = entry.getValue();
		    crtCO.setNewLineNumber(line.getNewLineNumber());
		}
		// handling the criterias in addCritMap
		LinkedHashMap<BigDecimal, COLMNTMPLT_PARAM_LINKCO> addCrtMap = line.getAddCrtMap();
		// modif
		Iterator<Map.Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO>> itAddCrtMapEn = addCrtMap.entrySet()
			.iterator();
		while(itAddCrtMapEn.hasNext())
		{
		    Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itAddCrtMapEn.next();
		    COLMNTMPLT_PARAM_LINKCO crtCO = entry.getValue();
		    crtCO.setNewLineNumber(line.getNewLineNumber());
		}
		// filling the delCrtMap of the currentTemplate with the old
		// line numbers in order for them to be deleted
		// on the save
		if(!line.getNewLineNumber().equals(line.getColmnTmpltVO().getLINE_NBR()))
		{
		    COLMNTMPLT_PARAM_LINKCO crtCO = new COLMNTMPLT_PARAM_LINKCO();
		    COLMNTMPLTVO colTmplVO = line.getColmnTmpltVO();
		    BigDecimal compCode = colTmplVO.getCOMP_CODE();
		    BigDecimal tmplCode = colTmplVO.getCODE();
		    StringBuffer concatKeyCrt = new StringBuffer();
		    concatKeyCrt.append(compCode).append(tmplCode).append(line.getColmnTmpltVO().getLINE_NBR());
		    crtCO.getColumntmpltParamLinkVO().setCOMP_CODE(compCode);
		    crtCO.getColumntmpltParamLinkVO().setTEMP_CODE(tmplCode);
		    crtCO.getColumntmpltParamLinkVO().setLINE_NO(line.getColmnTmpltVO().getLINE_NBR());
		    repSessionCO.getCurrentTemplate().getDelCrtMap().put(concatKeyCrt.toString(), crtCO);
		}
		// EXPRESSIONS
		// if the expressions are not loaded yet should handle this case
		if((line.getExpressions().size() == 0 && line.getColmnTmpltVO().getLINE_NBR() != null && ((newLineIndicator != 0 && line
			.getNewLineNumber().intValue() != newLineIndicator) || valueToRemember.intValue() == 0))
			&& allTempl.getNewLinesFromReorganize().get(line.getNewLineNumber()) == null)
		{
		    COLMNTMPLTVO scLine = new COLMNTMPLTVO();
		    scLine.setCOMP_CODE(sessionCO.getCompanyCode());
		    scLine.setCODE(allTempl.getColmnTmpltVO().getCODE());
		    scLine.setLINE_NBR(line.getColmnTmpltVO().getLINE_NBR());
		    line.setExpressions(columnTemplateBO.findExprList(scLine));
		}
		FTR_TMPLT_EXPRCO exprCO;
		for(int j = 0; j < line.getExpressions().size(); j++)
		{
		    exprCO = line.getExpressions().get(j);
		    exprCO.setNewLineNumber(line.getNewLineNumber());
		    //checking if type is Line,the line number should be changed
		    if(RepConstantsCommon.COL_EXP_LINE_TYPE.equals(exprCO.getFtrTmpltExprVO().getEXP_TYPE()))
		    {
			exprCO.getFtrTmpltExprVO().setEXP_VALUE(
				oldNewNewExprLinesMap.get(exprCO.getFtrTmpltExprVO().getEXP_VALUE()));
		    }
		}
	    }
	    // end handling criteria and expression
	    allTempl.setColumnDetails(new ArrayList<COLMNTMPLTCO>(finalDbLinesMap.values()));
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }

    /**
     * Method that sorts the passedMap by values
     * @param passedMap
     * @return
     */
    public LinkedHashMap<BigDecimal, COLMNTMPLTCO> sortHashMapByValuesD(
	    LinkedHashMap<BigDecimal, COLMNTMPLTCO> passedMap)
    {
	List<BigDecimal> mapKeys = new ArrayList<BigDecimal>(passedMap.keySet());
	Collections.sort(mapKeys);

	LinkedHashMap<BigDecimal, COLMNTMPLTCO> sortedMap = new LinkedHashMap<BigDecimal, COLMNTMPLTCO>();

	Iterator<BigDecimal> keyIt = mapKeys.iterator();

	while(keyIt.hasNext())
	{
	    BigDecimal key = keyIt.next();
	    COLMNTMPLTCO comp1 = passedMap.get(key);
	    sortedMap.put(key, comp1);
	}

	return sortedMap;
    }

    /**
     * Method that inserts the new line numbers on reorganize
     * @param param
     * @param allTempl
     * @param tempHash
     * @return
     * @throws Exception
     */
    public LinkedHashMap<BigDecimal, COLMNTMPLTCO> putNewLinesNumberMap(LinkedHashMap<BigDecimal, COLMNTMPLTCO> param,
	    COLMNTMPLTCO allTempl, LinkedHashMap<BigDecimal, COLMNTMPLTCO> tempHash) throws Exception
    {
	int dbLinesMapSize = param.size();
	BigDecimal flagValueRememberPassed = valueToRemember;
	// HashMap to be returned finally at the end of this function
	LinkedHashMap<BigDecimal, COLMNTMPLTCO> finalHashMap = new LinkedHashMap<BigDecimal, COLMNTMPLTCO>();
	// Used to set the template code of the smallest one for each time
	// looping through the "param" HashMap
	BigDecimal templateCode;
	BigDecimal compCode = returnSessionObject().getCompanyCode();
	// This is the line added newly on reorganizing
	COLMNTMPLTCO newLine = new COLMNTMPLTCO();
	// Have to get the smallest line number to set it's value
	//flag to know if the new line number must be added to the hashmap of newLinesNumbers in colmntmpltco
	boolean toBeAdded = false;
	HashMap<BigDecimal,BigDecimal> tempNewLinesReorg = new HashMap<BigDecimal, BigDecimal>();
	//contains the old key of a new line
	BigDecimal oldNewLineKey=null;
	for(int i = 0; i < dbLinesMapSize; i++)
	{
	    COLMNTMPLTCO line = getObjectHavingSmallestLineNbr(param);
	    if(allTempl.getNewLinesFromReorganize().containsKey(line.getNewLineNumber()))
	    {
		//the new line number has not been changed yet.
		oldNewLineKey=line.getNewLineNumber();
		toBeAdded = true;
	    }
	    templateCode = line.getColmnTmpltVO().getCODE();
	    param.remove(line.getConcatKey());

	    if(firstLoop == 0)
	    {
		// case where value to remember first line in the grid
		if(flagValueRememberPassed.intValue() != 0
			&& line.getColmnTmpltVO().getLINE_NBR().equals(flagValueRememberPassed)
			&& (line.getColmnTmpltVO().getLINE_NBR().equals(line.getNewLineNumber())))
		{
		    newLine.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue()));
		    newLineIndicator = newLine.getNewLineNumber().intValue();
		    String concatKey = compCode.toString() + templateCode.toString()
			    + String.valueOf((previousValue + getInterval().intValue()));
		    newLine.setConcatKey(new BigDecimal(concatKey));
		    newLine.getColmnTmpltVO().setCODE(line.getColmnTmpltVO().getCODE());

		    if(tempHash != null)
		    {
			tempHash.put(new BigDecimal(concatKey), newLine);
		    }
		    line.setNewLineNumber(getStartingLineValue()/*
								 * previousValue+
								 * getInterval
								 * ().intValue()
								 */);
		    singleColumnDetail.setNewLineNumber(newLine.getNewLineNumber());
		    previousValue += getInterval().intValue();
		    flagValueRememberPassed = BigDecimal.ZERO;
		    concatKey = compCode.toString() + templateCode.toString() + getStartingLineValue().toString();
		    line.setConcatKey(new BigDecimal(concatKey));
		    finalHashMap.put(line.getConcatKey(), line);
		}
		else
		// no value to remember for the first line in the grid
		{
		    line.setNewLineNumber(getStartingLineValue());
		    String concatKey = compCode.toString() + templateCode.toString()
			    + getStartingLineValue().toString();
		    line.setConcatKey(new BigDecimal(concatKey));
		    finalHashMap.put(line.getConcatKey(), line);
		}
		if(toBeAdded)
		{
		    tempNewLinesReorg.remove(oldNewLineKey);
		    tempNewLinesReorg.put(line.getNewLineNumber(), line.getNewLineNumber());
		    toBeAdded = false;
		}
		firstLoop = 1;
	    }
	    else
	    {
		if(flagValueRememberPassed.intValue() != 0
			&& ((line.getColmnTmpltVO().getLINE_NBR().equals(flagValueRememberPassed) && line
				.getNewLineNumber() == null) || (line.getNewLineNumber()
				.equals(flagValueRememberPassed))))
		{
		    newLine.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue() * 2));
		    newLineIndicator = newLine.getNewLineNumber().intValue();
		    String concatKey = compCode.toString() + templateCode.toString()
			    + String.valueOf(Integer.valueOf((previousValue + getInterval().intValue() * 2)));// newLine.getGlstmpltVO().getLINE_NBR();
		    /*
		     * have to check if same concatKey previously inserted for
		     * the case where the user is inserting again a new line
		     * number end check
		     */
		    newLine.setConcatKey(new BigDecimal(concatKey));
		    newLine.getColmnTmpltVO().setCODE(line.getColmnTmpltVO().getCODE());
		    if(tempHash == null)
		    {
			finalHashMap.put(newLine.getConcatKey(), newLine);
		    }
		    else
		    {
			tempHash.put(new BigDecimal(concatKey), newLine);
		    }
		    line.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue()));
		    concatKey = compCode.toString() + templateCode.toString()
			    + String.valueOf(previousValue + getInterval().intValue());
		    line.setConcatKey(new BigDecimal(concatKey));
		    finalHashMap.put(new BigDecimal(concatKey), line);
		    singleColumnDetail.setNewLineNumber(newLine.getNewLineNumber());
		    previousValue += getInterval().intValue() * 2;
		    flagValueRememberPassed = BigDecimal.ZERO;
		}
		else
		// normal case without valueToRemember
		{
		    line.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue()));
		    previousValue += getInterval().intValue();
		    String newKey = compCode.toString() + line.getColmnTmpltVO().getCODE().toString()
			    + line.getNewLineNumber().toString();
		    finalHashMap.put(new BigDecimal(newKey), line);
		    line.setConcatKey(new BigDecimal(newKey));
		}
		if(toBeAdded)
		{
		    tempNewLinesReorg.remove(oldNewLineKey);
		    tempNewLinesReorg.put(line.getNewLineNumber(), line.getNewLineNumber());
		    toBeAdded = false;
		}
	    }
	}
	setNullLlineNumberToNewLineNumber(finalHashMap);
	//setting the session's newLinesFromReorganize
	allTempl.getNewLinesFromReorganize().clear();
	allTempl.setNewLinesFromReorganize((HashMap<BigDecimal, BigDecimal>) tempNewLinesReorg.clone());
	return finalHashMap;
    }

    public void setNullLlineNumberToNewLineNumber(HashMap<BigDecimal, COLMNTMPLTCO> finalHashMap)
    {
	Iterator<COLMNTMPLTCO> it = finalHashMap.values().iterator();

	while(it.hasNext())
	{
	    COLMNTMPLTCO gls = it.next();
	    if(gls.getColmnTmpltVO().getLINE_NBR() == null)
	    {
		BigDecimal lnbr = gls.getNewLineNumber();
		gls.getColmnTmpltVO().setLINE_NBR(lnbr);
	    }
	}
    }

    public COLMNTMPLTCO getObjectHavingSmallestLineNbr(LinkedHashMap<BigDecimal, COLMNTMPLTCO> param) throws Exception
    {
	COLMNTMPLTCO result = new COLMNTMPLTCO();
	int firstRound = 0;
	int smallestNbr = 0;
	Iterator<Map.Entry<BigDecimal, COLMNTMPLTCO>> it = param.entrySet().iterator();
	try
	{
	    while(it.hasNext())
	    {
		Entry<BigDecimal, COLMNTMPLTCO> entry = it.next();
		BigDecimal theKey = entry.getKey();
		COLMNTMPLTCO line = entry.getValue();
		line.setConcatKey(theKey);
		int currentLineNbr = line.getNewLineNumber().intValue();
		if(firstRound == 0)
		{
		    result = line;
		    smallestNbr = currentLineNbr;
		    firstRound = 1;
		}
		else
		{
		    if(currentLineNbr < smallestNbr)
		    {
			smallestNbr = currentLineNbr;
			result = line;
		    }
		}
	    }
	}
	catch(Exception e)
	{
	   handleException(e, null, null);
	}
	return result;
    }

    // To be deleted later (Just for testing)
    // public static void printHashContents(LinkedHashMap map, String hashName)
    // {
    // // System.out.println(hashName + ":\n\n\n");
    // // Set s = map.keySet();
    // //
    // // Iterator it = s.iterator();
    // // while(it.hasNext())
    // // {
    // // BigDecimal theKey = (BigDecimal) it.next();
    // // COLMNTMPLTCO gg = (COLMNTMPLTCO) map.get(theKey);
    // // System.out.println("key hash:" + theKey + "     key: " +
    // gg.getConcatKey() + "       new Line: "
    // // + gg.getNewLineNumber() + "             true line: " +
    // gg.getColmnTmpltVO().getLINE_NBR()
    // // + "             description: " +
    // gg.getColmnTmpltVO().getBRIEF_NAME_ENG() + "\n");
    // // }
    // }

    // Criteria Section
    public String openCriteria() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    // fill the combo of the operation type
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(5)); // 5= operator
	    // (check table
	    // sys_param_lov_type)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    operatorArrList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    // fill a map with key and value of the calculation type in order to
	    // get the value of the selected key when needed
	    COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();
	    if(allTempl != null && allTempl.getOperatorsMap().size() == 0)
	    {
		HashMap operMap = allTempl.getOperatorsMap();
		for(int i = 0; i < operatorArrList.size(); i++)
		{
		    SYS_PARAM_LOV_TRANSVO operVO = (SYS_PARAM_LOV_TRANSVO) operatorArrList.get(i);
		    operMap.put(operVO.getVALUE_CODE(), operVO.getVALUE_DESC());
		}
	    }
	    // filling the list for the dmy drp dwn
	    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_CRITERIA_DMY);
	    sysVO.setLANG_CODE(sessionCO.getLanguage());
	    daysMonthYear = (ArrayList) commonLookupBO.getLookupList(sysVO);
	    // filling the list for the malefemale drp dwn
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_MALE_FEMALE);
	    maleFemaleList = (ArrayList) commonLookupBO.getLookupList(sysVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    public String loadCriteriaList() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = null;
	    String zLineNb = null;

	    if(tempCodeWithLineNb != null && !tempCodeWithLineNb.equals(""))
	    {
		zTemplCode = tempCodeWithLineNb.split("~")[0];
		zLineNb = tempCodeWithLineNb.split("~")[1];
	    }

	    if(zTemplCode == null || zLineNb == null)
	    {
		List GLList = new ArrayList();
		setRecords(GLList.size());
		setGridModel(GLList);
	    }
	    else
	    {
		BigDecimal compCode = sessionCO.getCompanyCode();

		String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();

		// get the glstmpltCO in order to fill it with dbCrt ;
		// first check in the dbLinesList;
		COLMNTMPLTCO colmnTmpltCO = retColmnTmpltCOById(lineConcatKey);

		if(colmnTmpltCO == null)
		{
		    List GLList = new ArrayList();
		    setRecords(GLList.size());
		    setGridModel(GLList);
		}
		else
		{

		    LinkedHashMap dbCrtMap = colmnTmpltCO.getDbCrtMap();
		    LinkedHashMap addCrtMap = colmnTmpltCO.getAddCrtMap();
		    HashMap delCrtMap = colmnTmpltCO.getDelCrtMap();
		    LinkedHashMap finalMap = new LinkedHashMap();
		    //if false=> line is a new line from reorganize and should not be checked in db
		    //because it will retrieve criteria related to another line
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(RepConstantsCommon.COL00MT);
		    COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();
		    boolean flag=false;
		    if(allTempl.getNewLinesFromReorganize().get(colmnTmpltCO.getNewLineNumber()) == null)
		    {
			flag = true;
		    }

		    // get the data from the DB
		    /*
		     * means when the dbCrtMap is empty and no empty because all
		     * the records are deleted
		     */
		    if((dbCrtMap == null || (dbCrtMap.isEmpty() && delCrtMap.isEmpty())) && flag)

		    {
			COLMNTMPLTSC crtSC = new COLMNTMPLTSC();
			crtSC.setCOMP_CODE(compCode);
			crtSC.setCODE(new BigDecimal(zTemplCode));
			crtSC.setLINE_NBR(new BigDecimal(zLineNb));
			crtSC.setLANG_CODE(sessionCO.getLanguage());
			/*
			 * we get the list cs it is ordered fill it in a
			 * linkedHashMap (dbCrtMap)
			 */
			List dbCrtLst = columnTemplateBO.getCrtList(crtSC);
			String table1;
			String table2;
			COLMNTMPLTSC detSC;
			List detailList;
			CommonDetailsVO retVal;
			for(int i = 0; i < dbCrtLst.size(); i++)
			{
			    COLMNTMPLT_PARAM_LINKCO crtCO = (COLMNTMPLT_PARAM_LINKCO) dbCrtLst.get(i);
			    /*
			     * get detail1 and detail2 from tbl1 and tbl2 and
			     * set value inside the CO in order to display
			     * inside the grid get Detail 1
			     */
			    table1 = crtCO.getTABLE_NAME1();
			    if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRI_TYPE()))
			    {
				retGenderDesc(sessionCO.getLanguage(), crtCO);
			    }
			    if(table1 != null && !table1.trim().equals(""))
			    {
				if(table1.toLowerCase(Locale.ENGLISH).equals(
					RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
				{
				    detSC = new COLMNTMPLTSC();
				    detSC.setGrid(false);
				    detSC.setTABLE_NAME(table1.toUpperCase(Locale.ENGLISH));
				    detSC.setCRITERIA_TYPE_CODE(crtCO.getCRITERIA_TYPE_CODE());
				    if(RepConstantsCommon.CRITERIA_MBK.equals(crtCO.getCRITERIA_TYPE_CODE()))
				    {
					detSC
						.setCODE_STR(((crtCO.getColumntmpltParamLinkVO().getOPERATOR1() == null) || crtCO
							.getColumntmpltParamLinkVO().getOPERATOR1().equals("0")) ? null
							: crtCO.getColumntmpltParamLinkVO().getOPERATOR1());
				    }
				    else
				    {
					detSC.setCODE_STR(crtCO.getCODE1());
				    }
				    detSC.setLANG_CODE(sessionCO.getLanguage());
				    detailList = columnTemplateBO.getFilterCrtDetList(detSC);
				}
				else
				{
				    detSC = new COLMNTMPLTSC();
				    detSC.setGrid(false);
				    detSC.setTABLE_NAME(table1);
				    detSC.setCODE(crtCO.getCODE1() == null ? null : new BigDecimal(crtCO.getCODE1()
					    .toString()));
				    if(RepConstantsCommon.GL_TERM_VIEW.equals(table1))
				    {
					detSC.setCOMP_CODE(null);
					detSC.setCODE_STR(crtCO.getCODE1());
				    }
				    else
				    {
					detSC.setCOMP_CODE(sessionCO.getCompanyCode());
				    }
				    if(RepConstantsCommon.SMT_VIEW.equals(table1))
				    {
					detSC.setCOMP_CODE(null);
					detSC.setOPTION_CODE(crtCO.getColumntmpltParamLinkVO().getCRITERIA_CODE());
				    }
				    detailList = columnTemplateBO.getFilterCrtDetLList(detSC);
				}
				if(detailList != null && !detailList.isEmpty())
				{
				    retVal = (CommonDetailsVO) detailList.get(0);
				    if(retVal != null)
				    {
					crtCO.setCODE1_NAME(retVal.getBRIEF_DESC_ENG());
					if(crtCO.getCODE1() == null || crtCO.getCODE1().equals("0"))
					{
					    crtCO.setCODE1(retVal.getCODE_STR());
					}
				    }
				}
			    }
			    table2 = crtCO.getTABLE_NAME2();
			    if(table2 != null && !table2.trim().equals(""))
			    {
				if(table2.toLowerCase(Locale.ENGLISH).equals(
					RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
				{
				    detSC = new COLMNTMPLTSC();
				    detSC.setGrid(false);
				    detSC.setTABLE_NAME(table2.toUpperCase(Locale.ENGLISH));
				    detSC.setCRITERIA_TYPE_CODE(crtCO.getCRITERIA_TYPE_CODE());
				    detSC.setCODE_STR(crtCO.getColumntmpltParamLinkVO().getCODE2().toString());
				    detSC.setLANG_CODE(sessionCO.getLanguage());
				    detailList = columnTemplateBO.getFilterCrtDetList(detSC);
				}
				else
				{
				    detSC = new COLMNTMPLTSC();
				    detSC.setGrid(false);
				    if(table2.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS"))
				    {
					detSC.setSECTOR_CODE((crtCO.getCODE1() == null) ? null : (new BigDecimal(crtCO
						.getCODE1().toString())));
				    }
				    else
				    {
					detSC.setSECTOR_CODE(null);
				    }
				    detSC.setTABLE_NAME(table2);
				    detSC.setCODE(crtCO.getColumntmpltParamLinkVO().getCODE2());
				    if(!RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(table2))
				    {
					detSC.setCOMP_CODE(sessionCO.getCompanyCode());
				    }
				    detSC.setCODE1(new BigDecimal(crtCO.getCODE1()));
				    detailList = columnTemplateBO.getFilterCrtDetLList(detSC);
				}
				if(detailList != null && !detailList.isEmpty())
				{
				    retVal = (CommonDetailsVO) detailList.get(0);
				    if(retVal != null)
				    {
					crtCO.setCODE2_NAME(retVal.getBRIEF_DESC_ENG());
				    }
				    if(RepConstantsCommon.CRITERIA_VAL_SEC.equals(crtCO.getCRITERIA_TYPE_CODE()))
				    {
					crtCO.setCODE1_NAME(crtCO.getCODE2_NAME());
				    }
				}
			    }
			    dbCrtMap.put(crtCO.getConcatKey(), crtCO);
			}

			colmnTmpltCO.setDbCrtMap(dbCrtMap);

		    }
		    finalMap.putAll(dbCrtMap);
		    finalMap.putAll(addCrtMap);
		    List GLList = new ArrayList(finalMap.values());
		    setRecords(GLList.size());
		    COLMNTMPLT_PARAM_LINKCO co;
		    for(int i = 0; i < GLList.size(); i++)
		    {
			co = (COLMNTMPLT_PARAM_LINKCO) GLList.get(i);
			if(co.getColumntmpltParamLinkVO().getINCLUDE().equalsIgnoreCase("yes"))
			{
			    co.setINCLUDE_TRANS(getText("yes_key"));
			}
			else if(co.getColumntmpltParamLinkVO().getINCLUDE().equalsIgnoreCase("no"))
			{
			    co.setINCLUDE_TRANS(getText("no_key"));
			}

		    }

		    setGridModel(GLList);
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public COLMNTMPLTCO retColmnTmpltCOById(String lineConcatKey)
    {
	COLMNTMPLTCO glstmpltCO = null;
	ReportingSessionCO repSessionCO = returnReportingSessionObject("COL00MT");

	COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();

	if(allTempl != null)
	{
	    // Get Column lines of the current template
	    List<COLMNTMPLTCO> colmnTempLinesList = allTempl.getColumnDetails();
	    LinkedHashMap<BigDecimal, COLMNTMPLTCO> dbLinesMap = new LinkedHashMap<BigDecimal, COLMNTMPLTCO>();

	    for(int i = 0; i < colmnTempLinesList.size(); i++)
	    {
		dbLinesMap.put(colmnTempLinesList.get(i).getConcatKey(), colmnTempLinesList.get(i));
	    }

	    glstmpltCO = dbLinesMap.get(new BigDecimal(lineConcatKey));
	}
	return glstmpltCO;
    }

    public String checkIfExprLine() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String lineNb = getLineNumber();
	    // get the colmnTmpltCO
	    // check if the line has a Expr (db or added)
	    COLMNTMPLTSC lineSC = new COLMNTMPLTSC();
	    lineSC.setCODE(code);
	    lineSC.setCOMP_CODE(compCode);
	    lineSC.setLINE_NBR(new BigDecimal(lineNb));

	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    COLMNTMPLTCO columnTemplate = repSessionCO.getCurrentTemplate();
	    if(columnTemplate != null)
	    {
		List<FTR_TMPLT_EXPRCO> dbExprList = columnTemplate.getExpressions();

		if(dbExprList != null)
		{

		    for(int i = 0; i < dbExprList.size(); i++)
		    {
			BigDecimal bdLineNb = BigDecimal.valueOf(Double.parseDouble(lineNb));
			if(bdLineNb.equals(dbExprList.get(i).getFtrTmpltExprVO().getLINE_NO()))
			{

			    break;
			}
		    }

		    // check for Crts
		    List<COLMNTMPLT_PARAM_LINKCO> dbCrtList = columnTemplate.getColmnTmpltParamLink();

		    for(int i = 0; i < dbCrtList.size(); i++)
		    {
			BigDecimal bdLineNb = BigDecimal.valueOf(Double.parseDouble(lineNb));
			if(bdLineNb.equals(dbCrtList.get(i).getColumntmpltParamLinkVO().getLINE_NO()))
			{

			    break;
			}
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * function that sets the gender's desc
     * 
     * @param language
     * @param crtCO
     */
    public void retGenderDesc(String language, COLMNTMPLT_PARAM_LINKCO crtCO)
    {
	try
	{
	    SYS_PARAM_LOV_TRANSVO drpMaleFemale = new SYS_PARAM_LOV_TRANSVO();
	    drpMaleFemale.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_MALE_FEMALE);
	    drpMaleFemale.setLANG_CODE(language);
	    maleFemaleList = (ArrayList) commonLookupBO.getLookupList(drpMaleFemale);
	    for(int k = 0; k < maleFemaleList.size(); k++)
	    {
		drpMaleFemale = maleFemaleList.get(k);
		if(drpMaleFemale.getVALUE_CODE().equals(crtCO.getColumntmpltParamLinkVO().getGENDER()))
		{
		    crtCO.setCODE1_NAME(drpMaleFemale.getVALUE_DESC());
		    break;
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }

    public String validateCrt() throws JSONException // validate Criteria
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject("COL00MT");
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();
	    String templateCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = templateCodeWithLineNb.split("~")[0];
	    String zLineNb = templateCodeWithLineNb.split("~")[1];
	    String operName = (String) allTempl.getOperatorsMap().get(crtCO.getColumntmpltParamLinkVO().getOPERATOR());
	    boolean includeChk = crtCO.getINCLUDE_CHK();
	    String include = includeChk ? "YES" : "NO";
	    crtCO.getColumntmpltParamLinkVO().setINCLUDE(include);
	    crtCO.setCRI_LINE_GL("L");
	    crtCO.setOPERATOR_NAME(operName);
	    crtCO.getColumntmpltParamLinkVO().setLINE_NO(new BigDecimal(zLineNb));
	    crtCO.getColumntmpltParamLinkVO().setTEMP_CODE(new BigDecimal(zTemplCode));
	    crtCO.getColumntmpltParamLinkVO().setCOMP_CODE(compCode);
	    // added condition to display in the grid's column details 1 the
	    // gender
	    if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRITERIA_TYPE_CODE()))
	    {
		retGenderDesc(sessionCO.getLanguage(), crtCO);
	    }
	    String crtConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString()
		    + crtCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().toString();
	    crtCO.setConcatKey(new BigDecimal(crtConcatKey));

	    if(crtCO.getVALUE1() != null
		    && (crtCO.getVALUE1().equals("") || NumberUtil.isEmptyDecimal(new BigDecimal(crtCO.getVALUE1()))))
	    {
		crtCO.setVALUE1(null);
	    }
	    if(crtCO.getVALUE2() != null
		    && (crtCO.getVALUE2().equals("") || NumberUtil.isEmptyDecimal(new BigDecimal(crtCO.getVALUE2()))))
	    {
		crtCO.setVALUE2(null);
	    }

	    // get the related glstmpltCO
	    String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();
	    // get the glstmpltCO in order to fill the crtMap of the glstmpltCO
	    COLMNTMPLTCO glstmpltCOO = retColmnTmpltCOById(lineConcatKey);
	    // check if it is a new crt
	    LinkedHashMap addCrtMap = glstmpltCOO.getAddCrtMap();
	    LinkedHashMap dbCrtMap = glstmpltCOO.getDbCrtMap();
	    // check if the crt has the same info as a crt.already saved
	    fillBusinessHmCrtCO(crtCO);
	    columnTemplateBO.checkRequiredFields(crtCO, get_pageRef(), compCode, sessionCO.getCurrentAppName(),
		    sessionCO.getLanguage());
	    BigDecimal crtConcatKeyNb = new BigDecimal(crtConcatKey);
	    COLMNTMPLT_PARAM_LINKCO critCO = (COLMNTMPLT_PARAM_LINKCO) addCrtMap.get(crtConcatKeyNb);
	    if(critCO == null)
	    {
		if(dbCrtMap.get(crtConcatKeyNb) == null)
		{
		    if(checkIfCrtExist(crtCO, addCrtMap, null) || checkIfCrtExist(crtCO, dbCrtMap, null))
		    {
			setUpdates("1");
		    }
		    else
		    {
			setUpdates("0");
			glstmpltCOO.getAddCrtMap().put(new BigDecimal(crtConcatKey), crtCO);
		    }
		}
		else
		{
		    if(checkIfCrtExist(crtCO, addCrtMap, crtConcatKeyNb)
			    || checkIfCrtExist(crtCO, dbCrtMap, crtConcatKeyNb))
		    {
			setUpdates("1");
		    }
		    else
		    {
			setUpdates("0");
			critCO = (COLMNTMPLT_PARAM_LINKCO) dbCrtMap.get(crtConcatKeyNb);
			if(glstmpltCOO.getOldCrtMap().get(crtConcatKeyNb) == null)
			{
			    glstmpltCOO.getOldCrtMap().put(crtConcatKeyNb, critCO);
			}
			dbCrtMap.put(crtConcatKeyNb, crtCO);
			glstmpltCOO.getModifCrtMap().put(crtConcatKeyNb, crtCO);
		    }

		}
	    }
	    else
	    // we are modifying a new criteria
	    {
		if(checkIfCrtExist(crtCO, addCrtMap, crtConcatKeyNb)
			|| checkIfCrtExist(crtCO, dbCrtMap, crtConcatKeyNb))
		{
		    setUpdates("1");
		}
		else
		{
		    setUpdates("0");
		    addCrtMap.put(crtConcatKeyNb, crtCO);
		}
	    }
	    glstmpltCOO.setMaxCrtSubLineNb(crtCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().intValue());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public boolean checkIfCrtExist(COLMNTMPLT_PARAM_LINKCO crtCO, LinkedHashMap addCrtMap, BigDecimal concatKey)
    {
	Iterator it;
	it = addCrtMap.values().iterator();
	COLMNTMPLT_PARAM_LINKCO savedCrtCO;
	while(it.hasNext())
	{
	    savedCrtCO = (COLMNTMPLT_PARAM_LINKCO) it.next();
	    if((concatKey == null || !savedCrtCO.getConcatKey().equals(concatKey))
		    && (((savedCrtCO.getColumntmpltParamLinkVO().getINCLUDE() != null && savedCrtCO
			    .getColumntmpltParamLinkVO().getINCLUDE().equals(
				    crtCO.getColumntmpltParamLinkVO().getINCLUDE())) || (savedCrtCO
			    .getColumntmpltParamLinkVO().getINCLUDE() == null && crtCO.getColumntmpltParamLinkVO()
			    .getINCLUDE() == null))
			    && savedCrtCO.getColumntmpltParamLinkVO().getCRITERIA_CODE().equals(
				    crtCO.getColumntmpltParamLinkVO().getCRITERIA_CODE())
			    && ((savedCrtCO.getColumntmpltParamLinkVO().getCODE1() != null && savedCrtCO
				    .getColumntmpltParamLinkVO().getCODE1().equals(
					    crtCO.getColumntmpltParamLinkVO().getCODE1())) || (savedCrtCO
				    .getColumntmpltParamLinkVO().getCODE1() == null && crtCO
				    .getColumntmpltParamLinkVO().getCODE1() == null) 
				    && (savedCrtCO.getCODE1() != null && savedCrtCO.getCODE1().equals(
					    crtCO.getCODE1())))
			    && ((savedCrtCO.getColumntmpltParamLinkVO().getCODE2() != null && savedCrtCO
				    .getColumntmpltParamLinkVO().getCODE2().equals(
					    crtCO.getColumntmpltParamLinkVO().getCODE2()))
				    || (savedCrtCO.getColumntmpltParamLinkVO().getCODE2() == null && crtCO
					    .getColumntmpltParamLinkVO().getCODE2() == null) || (savedCrtCO
				    .getColumntmpltParamLinkVO().getCODE2() == null && crtCO
				    .getColumntmpltParamLinkVO().getCODE2() == null))
			    && ((savedCrtCO.getVALUE1() != null && savedCrtCO.getVALUE1().equals(crtCO.getVALUE1())) || (savedCrtCO
				    .getVALUE1() == null && crtCO.getVALUE1() == null)) && ((savedCrtCO.getVALUE2() != null && savedCrtCO
			    .getVALUE2().equals(crtCO.getVALUE2())) || (savedCrtCO.getVALUE2() == null && crtCO
			    .getVALUE2() == null))))
	    {
		return true;
	    }
	}
	return false;
    }

    public String fillLookUpFilterCrt() throws JSONException
    {
	try
	{

	    // Design the Grid by defining the column model and column names
	    String[] name = { "CRITERIA_CODE", "CRITERIA_DESCRIPTION", "CRITERIA_TYPE_CODE", "COMPONENT",
		    "TABLE_NAME1", "TABLE_NAME2" };
	    String[] colType = { "number", "text", "text", "text", "text", "text" };
	    String[] titles = { getText("criteria.crtCode"), getText("reporting.lkpCrtDesc"), "CRITERIA TYPE CODE",
		    "COMPONENT", "TABLE_NAME1", "TABLE_NAME2" };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setShrinkToFit("true");
	    grid.setCaption("Criterias");
	    grid.setRowNum("10");
	    grid.setUrl("/path/templateMaintReport/filterCrtLookUpGrid");

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

		if(name[i].equals("COMPONENT") || name[i].equals("TABLE_NAME1") || name[i].equals("TABLE_NAME2")
			|| name[i].equals("CRITERIA_TYPE_CODE"))
		{
		    gridColumn.setHidden(true);
		}
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, tmplGridSC);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applyFilterCrtDependency() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal crtCode = getCode();
	    if(crtCode == null)
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setCRITERIA_DESCRIPTION(null);
		commonDetVO.setCRITERIA_CODE(null);
	    }
	    else
	    {
		TmplGridSC tmplSC = new TmplGridSC();
		tmplSC.setCOMP_CODE(sessionCO.getCompanyCode());
		tmplSC.setCRITERIA_CODE(crtCode);
		tmplSC.setLANG_CODE(sessionCO.getLanguage());
		commonDetVO = commonLookupBO.getFilterCrtDependency(tmplSC);
		if(commonDetVO == null)
		{
		    commonDetVO = new CommonDetailsVO();
		    commonDetVO.setCRITERIA_DESCRIPTION(null);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookUpFilterCrtDet() throws JSONException
    {
	try
	{

	    // Design the Grid by defining the column model and column names
	    // 28 values
	    String[] name = { "CODE_STR", "BRIEF_DESC_ENG", "BRIEF_DESC_ARAB", "SECTOR_CODE", "ADDITIONAL_REFERENCE",
		    "BAS_EXPRESSION_CLASS_CODE", "BAS_EXPRESSION_CLASS_DESC", "LONG_DESC_ARAB", "CATEGORY",
		    "CONTINENT", "FROM_BRANCH", "FROM_CIF", "FROM_CY", "FROM_GL", "FROM_SL", "SECURITY_TYPE", "SERIAL",
		    "STATUS ", "TRADING_COMPANY", "USEFUL_LIFE", "TO_BRANCH", "TO_CIF", "TO_CY", "TO_GL", "TYPE",
		    "BASE_CURRENCY", "LONG_DESC_ENG", "DEPRECIATION_METHOD", "TO_SL", "CB_NO", "COMP_CODE" };
	    String[] colType = { "text", "text", "text", "number", "text", "text", "text", "text", "text", "text",
		    "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text",
		    "text", "text", "text", "text", "text", "text", "text", "text", "number", "number" };
	    String[] titles = { getText("reporting.lkpCode"), getText("reporting.lkpBriefEng"),
		    getText("reporting.lkpBriefAr"), getText("reporting.lkpSectorCode"),
		    getText("template.additionalRef"), getText("template.bassExpClassCode"),
		    getText("template.bassExpClassDesc"), getText("long_desc_arab_key"), getText("Category_key"),
		    getText("template.continent"), getText("From_Branch_key"), getText("From_CIF_key"),
		    getText("reporting.fromCY"), getText("template.fromGL"), getText("template.fromSL"),
		    getText("security_type_key"), getText("Serial_key"), getText("Status_key"),
		    getText("template.tradingCurrency"), getText("template.UsefulLife"), getText("To_Branch_key"),
		    getText("To_CIF_key"), getText("reporting.toCY"), getText("template.ToGL"), getText("type_key"),
		    getText("Base_Currency_key"), getText("long_desc_eng_key"), getText("template.depreciationMethod"),
		    getText("template.ToSL"), getText("template.scrtNo"), getText("reporting.companyCode") };
	    String criteriaCode;
	    if(RepConstantsCommon.CRITERIA_GOODS.equals(str2) || RepConstantsCommon.CRITERIA_CONTINENT.equals(str2)
		    || RepConstantsCommon.CRITERIA_DEAL_ECONOMIC_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_VAL_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_MAIN_ECO_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_ECONOMIC_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_SECURITY_ECO_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_GL_CODE.equals(str2))
	    {
		criteriaCode = getStr2();
	    }
	    else
	    {
		criteriaCode = getStr1();
	    }
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("");
	    grid.setRowNum("10");
	    grid.setUrl("/path/templateMaintReport/filterCrtDetLookUpGrid");
	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
	    HashMap<String, int[]> mapCols = new HashMap<String, int[]>();
	    mapCols.put(RepConstantsCommon.CRITERIA_COUNTRIES_CTY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_BRANCH_BR, new int[] { 0, 1, 26, 25 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CBK_CIF_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_NO, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_TYPE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CATEG_GOODS, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_GOODS, new int[] { 0, 1, 26, 2, 7 });
	    if(RepConstantsCommon.CRITERIA_CONTINENT.equals(str2))
	    {
		mapCols.put(RepConstantsCommon.CRITERIA_CONTINENT, new int[] { 0, 1, 26, 9, 2, 7 });
	    }
	    else
	    {
		mapCols.put(RepConstantsCommon.CRITERIA_CONTINENT, new int[] { 0, 1, 26, 2, 7 });
	    }
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_PROFILE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_CREDIT_RATING, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_COUNTRY_REGION, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TRANSACTION_PURPOSE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CURRENCY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_ECONOMIC_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_EMPLOYEE_CATEGORY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_FIXED_ASSETS_CAT, new int[] { 0, 1, 26, 19, 27, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_COLLATERAL_TYPE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_FACILITY_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_GL_TERM, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_IBC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ILC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_LEGAL_STATUS, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_LG_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_PRODUCT_ECO_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_NATIONALITY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_OBC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ONE_OBLIGOR, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_OLC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_OCCUPATION_POSITION, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SECURITY_TYPE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_PURPOSE_FINANCING, new int[] { 0, 1, 2 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_RELATION, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_REGION_BRANCH, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_PRIORITY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_LINK_MGMNT, new int[] { 0, 1, 26, 16, 10, 12, 13, 11, 14, 17, 20,
		    22, 23, 21, 28 });
	    mapCols.put(RepConstantsCommon.CRITERIA_PRODUCT_CLASS, new int[] { 0, 1, 26, 8, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_PROV_CATEG, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TRANSACTION_LABEL, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TRANSACTION_TYPE, new int[] { 0, 1, 26, 24, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_VAL_SEC, new int[] { 0, 1, 26, 15, 18 });
	    mapCols.put(RepConstantsCommon.CRITERIA_MAIN_ECO_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ECONOMIC_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CATEG_REGION, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_TENURE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SMART, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ASSET_TRANS_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_COLLATERALIZED_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_FOREX_DEAL_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SEC_CATEG, new int[] { 0, 1, 2 });
	    mapCols.put(RepConstantsCommon.CRITERIA_AMT_BY_GL, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_MAT_DATE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_PERIOD, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEBIT_CREDIT, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TOTAL_DC, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SOURCE_USAGES, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_CAT_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_RESIDENT, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_AMOUNT, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_MBK, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_SECURITY_ISSUER, new int[] { 0, 1, 26, 4, 2, 7, 29 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SECURITY_ECO_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_JOB, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_GL_CODE, new int[] { 0, 1, 26, 8, 24 });
	    mapCols.put(RepConstantsCommon.CRITERIA_JVT, new int[] { 0, 4, 1, 26, 2, 7 });

	    String crtCodeKey;
	    int[] colsToDisplay;
	    Entry<String, int[]> entry;
	    Iterator<Map.Entry<String, int[]>> itMapCols = mapCols.entrySet().iterator();
	    LookupGridColumn gridColumn;
	    while(itMapCols.hasNext())
	    {
		entry = itMapCols.next();
		crtCodeKey = entry.getKey();
		if(crtCodeKey.equals(criteriaCode))
		{
		    colsToDisplay = entry.getValue();
		    if(crtCodeKey.equals(RepConstantsCommon.CRITERIA_SMART))
		    {
			titles[1] = getText("reporting.briefDescription");
			titles[2] = getText("reporting.longDescription");
		    }
		    else if(crtCodeKey.equals(RepConstantsCommon.CRITERIA_JVT))
		    {
			titles[0]=getText("reporting.JVType");
		    }
		    for(int i = 0; i < colsToDisplay.length; i++)
		    {
			gridColumn = new LookupGridColumn();
			gridColumn.setName(name[colsToDisplay[i]]);
			gridColumn.setColType(colType[colsToDisplay[i]]);
			gridColumn.setTitle(titles[colsToDisplay[i]]);
			gridColumn.setSearch(true);
			lsGridColumn.add(gridColumn);
		    }
		}
	    }
	    lookup(grid, lsGridColumn, null, tmplGridSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applyFilterCrtDetDependency() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String detCode = getStr();
	    String tblName = getStr1();
	    String crtTypeCode = getUpdates();
	    if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtTypeCode))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(getUpdates1());
		commonDetVO.setCODE_STR(detCode);
	    }
	    else if(tblName == null || tblName.equals("") || crtTypeCode == null || crtTypeCode.equals("")
		    || detCode == null || detCode.equals(""))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(null);
		commonDetVO.setCODE_STR(null);
	    }
	    else
	    {
		List<CommonDetailsVO> divList;
		COLMNTMPLTSC detSC = new COLMNTMPLTSC();
		detSC.setGrid(false);
		if(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL.equals(tblName.toLowerCase(Locale.ENGLISH)))
		{
		    detSC.setTABLE_NAME(tblName.toUpperCase(Locale.ENGLISH));
		    detSC.setCRITERIA_TYPE_CODE(crtTypeCode);
		    detSC.setCODE_STR(detCode);
		    detSC.setLANG_CODE(sessionCO.getLanguage());
		    divList = columnTemplateBO.getFilterCrtDetList(detSC);
		}
		else
		{
		    if(RepConstantsCommon.ECO_SEC_CRIT_VIEW.equals(tblName.toUpperCase(Locale.ENGLISH)))
		    {
			detSC.setSECTOR_CODE(new BigDecimal(getVALUE()));
		    }
		    else
		    {
			detSC.setSECTOR_CODE(null);
		    }
		    if(RepConstantsCommon.SEC_NO_VIEW2.equals(tblName)
			    || RepConstantsCommon.DES_SUB_VIEW.equals(tblName))
		    {
			// code of the details 2 field
			detSC.setCODE1(new BigDecimal(getVALUE()));
		    }
		    else if(RepConstantsCommon.SEC_NO_VIEW1.equals(tblName) && !"".equals(updates1))
		    {
			detSC.setBRIEF_DESC_ENG(getUpdates1());
		    }

		    detSC.setTABLE_NAME(tblName);
		    detSC.setCODE(new BigDecimal(detCode));
		    if(RepConstantsCommon.GL_TERM_VIEW.equals(tblName)
			    || RepConstantsCommon.REGION_BR_VIEW.equals(tblName)
			    || RepConstantsCommon.MAIN_ECO_SEC_VIEW1.equals(tblName)
			    || RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(tblName)
			    || RepConstantsCommon.SMT_VIEW.equals(tblName))
		    {
			detSC.setCOMP_CODE(null);
		    }
		    else
		    {
			detSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    }
		    if(RepConstantsCommon.GL_TERM_VIEW.equals(tblName))
		    {
			detSC.setCODE_STR(detCode);
		    }
		    if(RepConstantsCommon.SMT_VIEW.equals(tblName))
		    {
			detSC.setOPTION_CODE(getCode());
			detSC.setCOMP_CODE(null);
		    }
		    divList = columnTemplateBO.getFilterCrtDetLList(detSC);
		}
		commonDetVO = new CommonDetailsVO();
		if(divList == null || divList.isEmpty())
		{
		    commonDetVO.setBRIEF_DESC_ENG(null);
		    commonDetVO.setCODE_STR(null);
		}
		else
		{
		    CommonDetailsVO retVal = divList.get(0);
		    commonDetVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    commonDetVO.setCODE_STR(detCode);
		}
	    }
	}
	catch(Exception e)
	{
	    commonDetVO = new CommonDetailsVO();
	    commonDetVO.setBRIEF_DESC_ENG(null);
	    commonDetVO.setCODE_STR(null);
	    return SUCCESS;
	}

	return SUCCESS;
    }

    public String generateSubLnNbCrt() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String tempCode = tempCodeWithLineNb.split("~")[0];
	    String lineNb = tempCodeWithLineNb.split("~")[1];
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    int maxCrtSubLineNb;
	    BigDecimal subLineNb = null;
	    String lineConcatKey = compCode.toString() + tempCode.toString() + lineNb.toString();
	    COLMNTMPLTCO colmntmpltCO = getcolmntmpltCOById(lineConcatKey);
	    maxCrtSubLineNb = colmntmpltCO.getMaxCrtSubLineNb();
	    // get max.subLineNb+1 from the DB
	    // added condition related to deleted map to handle the case where
	    // the user deletes all the gls from the grid
	    // and tries to add a new one(don't get the max from db)
	    if(maxCrtSubLineNb == 0 && colmntmpltCO.getDelCrtMap().isEmpty()) // get
									      // max.subLineNb+1
									      // from
									      // the
									      // DB
	    {
		CommonDetailsSC crtSC = new CommonDetailsSC();
		crtSC.setTEMPLATE_CODE(new BigDecimal(tempCode));
		crtSC.setCOMP_CODE(compCode);
		crtSC.setTEMPLATE_LINE_NO(new BigDecimal(lineNb));
		commonDetVO = columnTemplateBO.getMaxCrtSubLineNb(crtSC);
		if(commonDetVO == null)
		{
		    subLineNb = BigDecimal.ONE;
		    commonDetVO = new CommonDetailsVO();
		    commonDetVO.setSUB_LINE_NO(subLineNb);
		}
		else
		{
		    subLineNb = commonDetVO.getSUB_LINE_NO();
		}
	    }
	    else
	    {
		subLineNb = new BigDecimal(colmntmpltCO.getMaxCrtSubLineNb() + 1);
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setSUB_LINE_NO(subLineNb);
	    }
	}
	catch(BaseException e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    public COLMNTMPLTCO getcolmntmpltCOById(String lineConcatKey)
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();

	List<COLMNTMPLTCO> listColmnTmpltCO = allTempl.getColumnDetails();
	HashMap<BigDecimal, COLMNTMPLTCO> dbLinesMap = new HashMap<BigDecimal, COLMNTMPLTCO>();

	for(int i = 0; i < listColmnTmpltCO.size(); i++)
	{
	    dbLinesMap.put(listColmnTmpltCO.get(i).getConcatKey(), listColmnTmpltCO.get(i));
	}

	COLMNTMPLTCO colmntmpltCO = dbLinesMap.get(new BigDecimal(lineConcatKey));

	return colmntmpltCO;
    }

    public void adjustDisplayCode(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm, BigDecimal readOnly,
	    BigDecimal mandatory, BigDecimal visible, String labelKey, BigDecimal compCode, String elementName,
	    String VOCOReference, String propertyName)
    {
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCode = new SYS_PARAM_SCREEN_DISPLAYVO();
	screenDisplayCode.setIS_MANDATORY(mandatory);
	screenDisplayCode.setIS_READONLY(readOnly);
	screenDisplayCode.setIS_VISIBLE(visible);
	screenDisplayCode.setLABEL_KEY(labelKey);
	screenDisplayCode.setPROG_REF("COL00MT");
	screenDisplayCode.setCOMP_CODE(compCode);
	screenDisplayCode.setELEMENT_NAME(elementName);
	screenDisplayCode.setVO_CO_REFERENCE(VOCOReference);
	screenDisplayCode.setVO_PROPERTY_NAME(propertyName);
	screenDisplayCode.setTRIM_STRING(BigDecimal.ONE);
	if("crtCO.getColumntmpltParamLinkVO.CODE2".equals(elementName) || "crtCO.VALUE1".equals(elementName))
	{
	    screenDisplayCode.setZERO_NOT_ALLOWED(BigDecimal.ZERO);
	}
	else
	{
	    screenDisplayCode.setZERO_NOT_ALLOWED(BigDecimal.ONE);
	}
	screenDisplayCode.setMESSAGE_CODE(null);
	screenDisplayCode.setARABIC_DEPENDANT(BigDecimal.ZERO);
	screenDisplayCode.setAPP_NAME("REP");
	businessHm.put(elementName, screenDisplayCode);
    }

    public void fillBusinessHmCrtCO(COLMNTMPLT_PARAM_LINKCO crtCO) throws Exception // open
    // line
    // Form
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();

	    HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

	    if(crtCO.getCOMPONENT().equals("D") || crtCO.getCOMPONENT().equals("L"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.getColumntmpltParamLinkVO.CODE2", "getColumntmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", null, "VALUE2");

	    }

	    else if(crtCO.getCOMPONENT().equals("T"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.getColumntmpltParamLinkVO.CODE2", "getColumntmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", null, "VALUE2");
	    }

	    else if(crtCO.getCOMPONENT().equals("LL"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.getColumntmpltParamLinkVO.CODE2", "columntmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", null, "VALUE2");
	    }

	    else if(crtCO.getCOMPONENT().equals("DT"))
	    {
		String zOper = crtCO.getCODE1();

		if((">=<=").equals(zOper) || ("><").equals(zOper) || ("><=").equals(zOper) || (">=<").equals(zOper))
		{
		    adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value2",
			    compCode, "crtCO.VALUE2", null, "VALUE2");
		}
		else
		{
		    adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			    compCode, "crtCO.VALUE2", null, "VALUE2");

		}

		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.getColumntmpltParamLinkVO.CODE2", "getColumntmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
	    }
	    else if(crtCO.getCOMPONENT().equals("TDT") || crtCO.getCOMPONENT().equals("DTT"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", "crtCO", "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.getColumntmpltParamLinkVO.CODE2", "getColumntmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", "crtCO", "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", "crtCO", "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", "crtCO", "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", "crtCO", "VALUE2");
	    }

	    else if(crtCO.getCOMPONENT() == null || crtCO.getCOMPONENT().equals(""))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", "crtCO", "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.getColumntmpltParamLinkVO.CODE2", "getColumntmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", "crtCO", "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", "crtCO", "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", "crtCO", "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", "crtCO", "VALUE2");
	    }
	    crtCO.setBusinessHm(businessHm);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }

    public String createLikeUrl() throws Exception
    {
	return SUCCESS;
    }

    public String saveCreateLike() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    COLMNTMPLTCO allTempl = repSessionCO.getCurrentTemplate();
	    SessionCO sessionCO = returnSessionObject();
	    COLMNTMPLTSC sc = new COLMNTMPLTSC();
	    sc.setCODE(newCode);
	    columnTemplateBO.checkRequiredFields(newCode, get_pageRef(), sessionCO.getCompanyCode(), sessionCO
		    .getCurrentAppName(), sessionCO.getLanguage());
	    sc.setCOMP_CODE(allTempl.getColmnTmpltVO().getCOMP_CODE());
	    sc.setOldCode(allTempl.getColmnTmpltVO().getCODE());
	    sc.setUserName(sessionCO.getUserName());
	    columnTemplateBO.saveCreateLike(sc);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return "error";
	}
	return SUCCESS;
    }

    /**
     * Method that applies validation on screen elements
     * @return
     * @throws Exception
     */
    public String applyRequiredFields() throws Exception
    {

	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> briefNameEn = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> fromDate = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> toDate = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> compID = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> brnchCode = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> brnchCodeTo = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> fromRegion = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> toRegion = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> divCodeFrom = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> divCodeTo = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> depCodeFrom = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> toDept = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> fromUnit = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> toUnit = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> readOnly = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	try
	{
	    if(RepConstantsCommon.COL_TEMP_EXP_TYPE.equals(getColType())
		    || RepConstantsCommon.AR_CUR_BRIEF_NAME.equals(getColType())
		    || RepConstantsCommon.EN_CUR_BRIEF_NAME.equals(getColType())
		    || RepConstantsCommon.COL_CALC_PERCENTAGE.equals(getColType()))
	    {

		briefNameEn = returnCommonLibBO().applyDynScreenDisplay("BRIEF_NAME_ENG",
			"singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ENG", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			briefNameEn, null);
		fromDate = returnCommonLibBO().applyDynScreenDisplay("FROM_DATE",
			"singleColumnDetail.colmnTmpltVO.FROM_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			fromDate, null);
		toDate = returnCommonLibBO().applyDynScreenDisplay("TO_DATE",
			"singleColumnDetail.colmnTmpltVO.TO_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "0", toDate,
			null);
		compID = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_compID",
			"singleColumnDetail.colmnTmpltVO.COMP", ConstantsCommon.ACTION_TYPE_MANDATORY, "0", compID,
			null);

		brnchCode = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCode",
			"singleColumnDetail.colmnTmpltVO.FROM_BRANCH", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			brnchCode, null);
		brnchCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCodeTo",
			"singleColumnDetail.colmnTmpltVO.TO_BRANCH", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			brnchCodeTo, null);
		divCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeFrom",
			"singleColumnDetail.colmnTmpltVO.FROM_DIV", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			divCodeFrom, null);
		divCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeTo",
			"singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			divCodeTo, null);
		depCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_depCodeFrom",
			"singleColumnDetail.colmnTmpltVO.FROM_DEPT", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			depCodeFrom, null);
		toDept = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_DEPT",
			"singleColumnDetail.colmnTmpltVO.TO_DEPT", ConstantsCommon.ACTION_TYPE_MANDATORY, "0", toDept,
			null);
		fromUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_UNIT",
			"singleColumnDetail.colmnTmpltVO.FROM_UNIT", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			fromUnit, null);
		toUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_UNIT",
			"singleColumnDetail.colmnTmpltVO.TO_UNIT", ConstantsCommon.ACTION_TYPE_MANDATORY, "0", toUnit,
			null);
		if(RepConstantsCommon.COL_TEMP_FROM_WHERE.equals(getFromWhere()))
		{
		    fromRegion = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_REGION",
			    "singleColumnDetail.colmnTmpltVO.FROM_REGION", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    fromRegion, null);
		    toRegion = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_REGION",
			    "singleColumnDetail.colmnTmpltVO.TO_REGION", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    toRegion, null);
		    brnchCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCodeTo",
			    "singleColumnDetail.colmnTmpltVO.TO_BRANCH", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    brnchCodeTo, null);
		    brnchCode = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCode",
			    "singleColumnDetail.colmnTmpltVO.FROM_BRANCH", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    brnchCode, null);
		    divCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeTo",
			    "singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    divCodeTo, null);
		    divCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeFrom",
			    "singleColumnDetail.colmnTmpltVO.FROM_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    divCodeFrom, null);
		    fromUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_UNIT",
			    "singleColumnDetail.colmnTmpltVO.FROM_UNIT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    fromUnit, null);
		    toUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_UNIT",
			    "singleColumnDetail.colmnTmpltVO.TO_UNIT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    toUnit, null);
		    compID = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_compID",
			    "singleColumnDetail.colmnTmpltVO.COMP", ConstantsCommon.ACTION_TYPE_READONLY, "1", compID,
			    null);
		    fromDate = returnCommonLibBO().applyDynScreenDisplay("FROM_DATE",
			    "singleColumnDetail.colmnTmpltVO.FROM_DATE", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    fromDate, null);
		}

	    }
	    else if(RepConstantsCommon.COL_TYPE_VB.equals(getColType()) || RepConstantsCommon.COL_TYPE_VBF.equals(getColType()) 
		    || RepConstantsCommon.COL_TYPE_AM.equals(getColType()) || RepConstantsCommon.COL_TYPE_AMF.equals(getColType()) 
		    || RepConstantsCommon.COL_TYPE_BM.equals(getColType()))
	    {

		briefNameEn = returnCommonLibBO().applyDynScreenDisplay("BRIEF_NAME_ENG",
			"singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ENG", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			briefNameEn, null);
		fromDate = returnCommonLibBO().applyDynScreenDisplay("FROM_DATE",
			"singleColumnDetail.colmnTmpltVO.FROM_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			fromDate, null);
		toDate = returnCommonLibBO().applyDynScreenDisplay("TO_DATE",
			"singleColumnDetail.colmnTmpltVO.TO_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "1", toDate,
			null);
		compID = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_compID",
			"singleColumnDetail.colmnTmpltVO.COMP", ConstantsCommon.ACTION_TYPE_MANDATORY, "1", compID,
			null);
		brnchCode = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCode",
			"singleColumnDetail.colmnTmpltVO.FROM_BRANCH", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			brnchCode, null);
		brnchCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCodeTo",
			"singleColumnDetail.colmnTmpltVO.TO_BRANCH", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			brnchCodeTo, null);
		divCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeFrom",
			"singleColumnDetail.colmnTmpltVO.FROM_DIV", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			divCodeFrom, null);
		divCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeTo",
			"singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			divCodeTo, null);
		depCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_depCodeFrom",
			"singleColumnDetail.colmnTmpltVO.FROM_DEPT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			depCodeFrom, null);
		toDept = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_DEPT",
			"singleColumnDetail.colmnTmpltVO.TO_DEPT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1", toDept,
			null);
		fromUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_UNIT",
			"singleColumnDetail.colmnTmpltVO.FROM_UNIT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			fromUnit, null);
		toUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_UNIT",
			"singleColumnDetail.colmnTmpltVO.TO_UNIT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1", toUnit,
			null);

	    }
	    else
	    {
		if(RepConstantsCommon.Y.equals(updates))
		{
		    briefNameEn = returnCommonLibBO().applyDynScreenDisplay("BRIEF_NAME_ENG",
			    "singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ENG", ConstantsCommon.ACTION_TYPE_MANDATORY,
			    "1", briefNameEn, null);
		    fromDate = returnCommonLibBO().applyDynScreenDisplay("FROM_DATE",
			    "singleColumnDetail.colmnTmpltVO.FROM_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    fromDate, null);
		    toDate = returnCommonLibBO().applyDynScreenDisplay("TO_DATE",
			    "singleColumnDetail.colmnTmpltVO.TO_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
			    toDate, null);
		    compID = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_compID",
			    "singleColumnDetail.colmnTmpltVO.COMP", ConstantsCommon.ACTION_TYPE_MANDATORY, "1", compID,
			    null);
		    brnchCode = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCode",
			    "singleColumnDetail.colmnTmpltVO.FROM_BRANCH", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    brnchCode, null);
		    brnchCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCodeTo",
			    "singleColumnDetail.colmnTmpltVO.TO_BRANCH", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    brnchCodeTo, null);

		    fromRegion = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_REGION",
			    "singleColumnDetail.colmnTmpltVO.FROM_REGION", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    fromRegion, null);
		    toRegion = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_REGION", "lookuptxt_TO_REGION",
			    ConstantsCommon.ACTION_TYPE_READONLY, "1", toRegion, null);
		    divCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeFrom",
			    "singleColumnDetail.colmnTmpltVO.FROM_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    divCodeFrom, null);
		    divCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeTo",
			    "singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    divCodeTo, null);
		    depCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_depCodeFrom",
			    "singleColumnDetail.colmnTmpltVO.FROM_DEPT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    depCodeFrom, null);
		    toDept = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_DEPT",
			    "singleColumnDetail.colmnTmpltVO.TO_DEPT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    toDept, null);
		    fromUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_UNIT",
			    "singleColumnDetail.colmnTmpltVO.FROM_UNIT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    fromUnit, null);
		    toUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_UNIT",
			    "singleColumnDetail.colmnTmpltVO.TO_UNIT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			    toUnit, null);
		}
		else
		{
		    briefNameEn = returnCommonLibBO().applyDynScreenDisplay("BRIEF_NAME_ENG",
			    "singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ENG", ConstantsCommon.ACTION_TYPE_MANDATORY,
			    "1", briefNameEn, null);
		    fromDate = returnCommonLibBO().applyDynScreenDisplay("FROM_DATE",
			    "singleColumnDetail.colmnTmpltVO.FROM_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    fromDate, null);
		    if(RepConstantsCommon.CALC_ACT_DAY_BAL_CV.equals(getColType())
			    || RepConstantsCommon.CALC_ACT_DAY_BAL_FC.equals(getColType())
			    || RepConstantsCommon.CALC_ACT_MON_BAL_CV.equals(getColType())
			    || RepConstantsCommon.CALC_ACT_MON_BAL_FC.equals(getColType()))
		    {
			toDate = returnCommonLibBO().applyDynScreenDisplay("TO_DATE",
				"singleColumnDetail.colmnTmpltVO.TO_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
				toDate, null);
			toDate = returnCommonLibBO().applyDynScreenDisplay("TO_DATE",
				"singleColumnDetail.colmnTmpltVO.TO_DATE", ConstantsCommon.ACTION_TYPE_READONLY, "0",
				toDate, null);
		    }
		    else
		    {
			toDate = returnCommonLibBO().applyDynScreenDisplay("TO_DATE",
				"singleColumnDetail.colmnTmpltVO.TO_DATE", ConstantsCommon.ACTION_TYPE_MANDATORY, "0",
				toDate, null);
		    }
		    compID = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_compID",
			    "singleColumnDetail.colmnTmpltVO.COMP", ConstantsCommon.ACTION_TYPE_MANDATORY, "1", compID,
			    null);
		    brnchCode = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCode",
			    "singleColumnDetail.colmnTmpltVO.FROM_BRANCH", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    brnchCode, null);
		    brnchCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCodeTo",
			    "singleColumnDetail.colmnTmpltVO.TO_BRANCH", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    brnchCodeTo, null);
		    divCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeFrom",
			    "singleColumnDetail.colmnTmpltVO.FROM_DIV", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    divCodeFrom, null);
		    divCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeTo",
			    "singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    divCodeTo, null);
		    depCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_depCodeFrom",
			    "singleColumnDetail.colmnTmpltVO.FROM_DEPT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    depCodeFrom, null);
		    toDept = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_DEPT",
			    "singleColumnDetail.colmnTmpltVO.TO_DEPT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    toDept, null);
		    fromUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_UNIT",
			    "singleColumnDetail.colmnTmpltVO.FROM_UNIT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    fromUnit, null);
		    toUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_UNIT",
			    "singleColumnDetail.colmnTmpltVO.TO_UNIT", ConstantsCommon.ACTION_TYPE_MANDATORY, "1",
			    toUnit, null);
		}
	    }

	    getAdditionalScreenParams().putAll(briefNameEn);
	    getAdditionalScreenParams().putAll(fromDate);
	    getAdditionalScreenParams().putAll(toDate);
	    getAdditionalScreenParams().putAll(compID);
	    getAdditionalScreenParams().putAll(brnchCode);
	    getAdditionalScreenParams().putAll(brnchCodeTo);
	    getAdditionalScreenParams().putAll(divCodeFrom);
	    getAdditionalScreenParams().putAll(divCodeTo);
	    getAdditionalScreenParams().putAll(depCodeFrom);
	    getAdditionalScreenParams().putAll(toDept);
	    getAdditionalScreenParams().putAll(fromUnit);
	    getAdditionalScreenParams().putAll(toUnit);
	    getAdditionalScreenParams().putAll(fromRegion);
	    getAdditionalScreenParams().putAll(toRegion);
	    if(!RepConstantsCommon.COL_TEMP_FROM_WHERE.equals(getFromWhere()))
	    {
		readOnly = returnCommonLibBO()
			.applyDynScreenDisplay(
				"lookuptxt_brnchCode~lookuptxt_brnchCodeTo~lookuptxt_divCodeFrom~lookuptxt_divCodeTo~lookuptxt_depCodeFrom~lookuptxt_TO_DEPT~lookuptxt_FROM_UNIT~lookuptxt_TO_UNIT",
				"singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1",
				readOnly, null);
		getAdditionalScreenParams().putAll(readOnly);

		brnchCode = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_brnchCode",
			"singleColumnDetail.colmnTmpltVO.FROM_BRANCH", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			brnchCode, null);
		divCodeTo = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeTo",
			"singleColumnDetail.colmnTmpltVO.TO_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1", divCodeTo,
			null);
		divCodeFrom = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_divCodeFrom",
			"singleColumnDetail.colmnTmpltVO.FROM_DIV", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			divCodeFrom, null);
		fromUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_FROM_UNIT",
			"singleColumnDetail.colmnTmpltVO.FROM_UNIT", ConstantsCommon.ACTION_TYPE_READONLY, "1",
			fromUnit, null);
		toUnit = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_TO_UNIT",
			"singleColumnDetail.colmnTmpltVO.TO_UNIT", ConstantsCommon.ACTION_TYPE_READONLY, "1", toUnit,
			null);

		getAdditionalScreenParams().putAll(brnchCode);
		getAdditionalScreenParams().putAll(divCodeTo);
		getAdditionalScreenParams().putAll(divCodeFrom);
		getAdditionalScreenParams().putAll(fromUnit);
		getAdditionalScreenParams().putAll(toUnit);
	    }
	    // control added for the visibility of the days and months drpdwn
	    SYS_PARAM_SCREEN_DISPLAYVO dispMonths = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO dispWeeks = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(RepConstantsCommon.CALC_ACT_DAY_BAL_CV.equals(getColType())
		    || RepConstantsCommon.CALC_ACT_DAY_BAL_FC.equals(getColType()))
	    {
		dispWeeks.setIS_VISIBLE(BigDecimal.ONE);
		dispWeeks.setIS_MANDATORY(BigDecimal.ONE);
		dispWeeks.setIS_READONLY(BigDecimal.ZERO);
		dispMonths.setIS_VISIBLE(BigDecimal.ZERO);
		dispMonths.setIS_MANDATORY(BigDecimal.ZERO);
		dispMonths.setIS_READONLY(BigDecimal.ZERO);
	    }
	    else if(RepConstantsCommon.CALC_ACT_MON_BAL_CV.equals(getColType())
		    || RepConstantsCommon.CALC_ACT_MON_BAL_FC.equals(getColType()))
	    {
		dispMonths.setIS_VISIBLE(BigDecimal.ONE);
		dispMonths.setIS_MANDATORY(BigDecimal.ONE);
		dispMonths.setIS_READONLY(BigDecimal.ZERO);
		dispWeeks.setIS_VISIBLE(BigDecimal.ZERO);
		dispWeeks.setIS_MANDATORY(BigDecimal.ZERO);
		dispWeeks.setIS_READONLY(BigDecimal.ZERO);
	    }
	    else
	    {
		dispWeeks.setIS_VISIBLE(BigDecimal.ZERO);
		dispWeeks.setIS_MANDATORY(BigDecimal.ZERO);
		dispWeeks.setIS_READONLY(BigDecimal.ZERO);
		dispMonths.setIS_VISIBLE(BigDecimal.ZERO);
		dispMonths.setIS_MANDATORY(BigDecimal.ZERO);
		dispMonths.setIS_READONLY(BigDecimal.ZERO);
	    }
	    getAdditionalScreenParams().put("colDaysOfWeekId", dispWeeks);
	    getAdditionalScreenParams().put("colListMonthsId", dispMonths);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that a template doesn't have any line
     * 
     * @param currentTemplate
     * @throws Exception
     */
    private void checkIfWithoutLines(COLMNTMPLTCO currentTemplate) throws Exception
    {
	if(currentTemplate == null || currentTemplate.getColumnDetails().size() == 0)
	{
	    BOException e = new BOException();
	    e.setMsgIdent("no lines in template being created");
	    throw e;
	}
    }
    
    /**
     * Method that checks for ABD calc type in column template
     * @return
     */
    public String checkColTempCalcType()
    {
	try
	{
	    if(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(code))
	    {
		code = BigDecimal.valueOf(-1);
	    }
	    if(columnTemplateBO.checkColTempCalcType(code, returnSessionObject().getCompanyCode())>0)
	    {
		updates1=RepConstantsCommon.ONE;
	    }
	}
	catch(BaseException e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
}
