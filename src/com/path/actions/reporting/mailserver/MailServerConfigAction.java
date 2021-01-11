package com.path.actions.reporting.mailserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.JSONException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.mailserver.MailServerConfigBO;
import com.path.dbmaps.vo.IRP_MAIL_SERVER_CONFIGVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.common.email.MailCO;
import com.path.vo.common.email.MailServerCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;

public class MailServerConfigAction extends ReportingLookupBaseAction
{
    private MailServerConfigBO mailServerConfigBO;
    IRP_MAIL_SERVER_CONFIGSC mailServerSC = new IRP_MAIL_SERVER_CONFIGSC();
    private IRP_MAIL_SERVER_CONFIGCO mailServerCO;
    private String updates;
    private BigDecimal code;

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public IRP_MAIL_SERVER_CONFIGCO getMailServerCO()
    {
	return mailServerCO;
    }

    public void setMailServerCO(IRP_MAIL_SERVER_CONFIGCO mailServerCO)
    {
	this.mailServerCO = mailServerCO;
    }

    public Object getModel()
    {
	return mailServerSC;
    }

    public void setMailServerConfigBO(MailServerConfigBO mailServerConfigBO)
    {
	this.mailServerConfigBO = mailServerConfigBO;
    }

    public String loadMailServerList() throws Exception
    {
	set_showSmartInfoBtn("false");
	return "successList";
    }

    public String loadMailServerGrid() throws JSONException
    {
	try
	{
	    copyproperties(mailServerSC);
	    int msCnt = mailServerConfigBO.retMailServerCount(mailServerSC);
	    setRecords(msCnt);
	    List<IRP_MAIL_SERVER_CONFIGCO> msList = mailServerConfigBO.retMailServerList(mailServerSC);
	    setGridModel(msList);
	}
	catch(Exception e)
	{
	   // log.error(e, "Error in method loadMailServerGrid in MailServerAction");
	    handleException(e, "Error Loading mail server list", "error loading the mail server grid");
	}
	return SUCCESS;
    }

    public String retMailServerById() throws JSONException
    {
	try
	{
	    IRP_MAIL_SERVER_CONFIGSC msSC = new IRP_MAIL_SERVER_CONFIGSC();
	    msSC.setMsCode(code);
	    mailServerCO = mailServerConfigBO.retMailServerById(msSC);
	    applyRetrieveAudit(AuditConstant.MAIL_SERVER_CONFIG, mailServerCO.getMailServerVO(), mailServerCO
		    .getMailServerVO());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "successMnt";
    }

    public String saveMailserver() throws JSONException
    {
	try
	{
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.MAIL_SERVER_CONFIG);
	    mailServerCO.setAuditRefCO(refCO);
	    if(!(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE).equals(mailServerCO.getMailServerVO().getMAIL_SERVER_CODE()))
	    {
		IRP_MAIL_SERVER_CONFIGVO oldMsVO = (IRP_MAIL_SERVER_CONFIGVO) returnAuditObject(IRP_MAIL_SERVER_CONFIGVO.class);
		mailServerCO.setAuditObj(oldMsVO);
	    }

	    // save
	    mailServerConfigBO.saveMailServer(mailServerCO);
	    // empty form
	    mailServerCO = new IRP_MAIL_SERVER_CONFIGCO();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    // return "error";
	}
	return SUCCESS;
    }

    public String deleteMailServer() throws JSONException
    {
	try
	{
	    // apply audit
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.DELETE);
	    refCO.setKeyRef(AuditConstant.MAIL_SERVER_CONFIG);
	    mailServerCO = new IRP_MAIL_SERVER_CONFIGCO();
	    mailServerCO.setAuditRefCO(refCO);
	    mailServerCO.getMailServerVO().setMAIL_SERVER_CODE(code);
	    mailServerConfigBO.deleteMailServer(mailServerCO);
	    // empty form
	    mailServerCO = new IRP_MAIL_SERVER_CONFIGCO();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "successMnt";
    }

    public String emptyMsForm()
    {
	try
	{
	    mailServerCO = new IRP_MAIL_SERVER_CONFIGCO();
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "successMnt";
    }

    public String testMailserver()
    {
	try
	{
	    // NOjeil@path-solutions.com
	    MailCO mailCO = new MailCO();
	    mailCO.setFrom(mailServerCO.getFROM());
	    mailCO.setSubject(mailServerCO.getSUBJECT());
	    String[] to = mailServerCO.getTO().split(";");
	    mailCO.setTo(to);
	    StringBuffer sb = new StringBuffer();
	    sb.append(mailServerCO.getBODY());
	    mailCO.setBodyMail(sb);

	    List<MailCO> mailList = new ArrayList<MailCO>();

	    MailServerCO mServerCO = new MailServerCO();
	    mServerCO.setHost(mailServerCO.getMailServerVO().getHOST());
	    mServerCO.setPort(mailServerCO.getMailServerVO().getPORT().intValue());
	    mServerCO.setUserName(mailServerCO.getMailServerVO().getSERVER_USER_NAME());
	    String password=mailServerCO.getMailServerVO().getSERVER_PASS();
	    if(!BigDecimal.ZERO.equals(NumberUtil.nullToZero(mailServerCO.getMailServerVO().getMAIL_SERVER_CODE())) &&
		    (mailServerCO.getSERVER_OLD_PASS().equals(mailServerCO.getMailServerVO().getSERVER_PASS())))
	    {
		password=SecurityUtils.decodeB64(mailServerCO.getMailServerVO().getSERVER_PASS());
	    }
	    mServerCO.setPassword(password);
	    mServerCO.setProtocol("smtp");
	    mServerCO
		    .setSslEnabled(BigDecimal.ONE.equals(mailServerCO.getMailServerVO().getSSL_ENABLE_YN()) ? ConstantsCommon.TRUE
			    : ConstantsCommon.FALSE);
	    if(!NumberUtil.isEmptyDecimal(mailServerCO.getMailServerVO().getSSL_PORT_NBR()))
	    {
		mServerCO.setSslSocketPort(mailServerCO.getMailServerVO().getSSL_PORT_NBR().intValue());
	    }

	    mailList.add(mailCO);

	    returnCommonLibBO().sendEmail(mailList, mServerCO);
	}
	catch(MailAuthenticationException e)
	{
	    handleException(null, getText("ms.authenticationError"),null);
	}
	catch(MailSendException e)
	{
	    handleException(null, getText("ms.mailSendError"),null);
	}
	catch(BaseException e)
	{
	    if(e.getMessage().contains(MessageCodes.INVALID_MISSING_COMMON_MSG.toString()))
	    {
		handleException(null, e.getMessage(), null);
	    }
	    else
	    {
		handleException(e, getText("ms.error"), null);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, getText("ms.error"),null);
	}
	return SUCCESS;
    }

    public String msConfigLookup()
    {
	try
	{
	    fillLookup("msGridId", "/path/mailServerConfig/mailServerConfig_loadMailServerGrid", "");
	}
	catch(Exception e)
	{
	   // log.error("Error filling mailServerConfig lookup");
	    handleException(e, "Error filling mailServerConfig lookup", "Error filling mailServerConfig lookup");
	}
	return SUCCESS;
    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "mailServerVO.MAIL_SERVER_CODE", "mailServerVO.HOST", "mailServerVO.PORT",
		    "mailServerVO.MAIL_FROM" };
	    String[] colType = { "number", "text", "number", "text" };
	    String[] titles = { getText("reporting.lkpCode"), getText("ms.host"), getText("ms.port"),
		    getText("ms.from") };

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

		// if(name[i].equals("mailServerVO.MAIL_SERVER_CODE"))
		// {
		// gridColumn.setHidden(true);
		// gridColumn.setWidth("1");
		// }
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, mailServerSC);
	}
	catch(Exception e)
	{
	  //  log.error("Error filling mailServerConfig lookup");
	    handleException(e, "Error mailServerConfig reports lookup", "Error mailServerConfig reports lookup");
	}
	return SUCCESS;
    }

    public String fillReportGrid() throws BaseException
    {
	copyproperties(mailServerSC);
	setSearchFilter(mailServerSC);
	List<IRP_MAIL_SERVER_CONFIGCO> msList = mailServerConfigBO.retMailServerList(mailServerSC);
	if(checkNbRec(mailServerSC))
	{
	    int msCnt = mailServerConfigBO.retMailServerCount(mailServerSC);
	    setRecords(msCnt);
	}
	setGridModel(msList);
	return SUCCESS;
    }
    
    public String retMailServerDependency()
    {
		try
		{
			IRP_MAIL_SERVER_CONFIGSC msSC = new IRP_MAIL_SERVER_CONFIGSC();
			msSC.setMsCode(code);
			mailServerCO = mailServerConfigBO.retMailServerById(msSC);
		}
		catch(Exception e)
		{
			handleException(e, null, null);
	
		}
		return SUCCESS;    	
}
    
    public String checkMailUsage(){
    	
        try
        	{
        	    IRP_MAIL_SERVER_CONFIGSC mailServerSC= new IRP_MAIL_SERVER_CONFIGSC();
        	    mailServerSC.setMsCode(code);
        	    int count= mailServerConfigBO.retMailServerReportCount(mailServerSC);
        	    setUpdates(String.valueOf(count));
        	  
        	}
        	catch(Exception e)
        	{
        	    handleException(e, "Error in checkMailUsage", "error in checkMailUsage");
        	}
        	return SUCCESS; 
        }
    
    /**
     * Method that shows/hide the ssl port number field based on the ssl enabled
     * flag
     * 
     * @return
     */
    public String showHideSslPortDependency()
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO vo = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(updates.equals(RepConstantsCommon.ONE))
	    {
		vo.setIS_MANDATORY(BigDecimal.ONE);
		vo.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    else
	    {
		vo.setIS_MANDATORY(BigDecimal.ZERO);
		vo.setIS_VISIBLE(BigDecimal.ZERO);
		vo.setValue("");
	    }
	    getAdditionalScreenParams().put("msSslPort",vo);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
}
