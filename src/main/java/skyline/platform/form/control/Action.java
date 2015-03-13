package skyline.platform.form.control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;
import worktile.repository.model.Dept;
import worktile.repository.model.Oper;
import skyline.platform.db.ConnectionManager;
import skyline.platform.db.DatabaseConnection;
import skyline.platform.db.RecordSet;
import skyline.platform.deb.DebugInfo;
import skyline.platform.form.util.event.LogAssistor;
import skyline.platform.utils.ActionRequest;
import skyline.platform.utils.LogManager;
import skyline.platform.utils.SQLResponse;

public class Action {
	 protected RecordSet rs;
	 protected SQLResponse res;
	 protected ActionRequest req;
	 protected DatabaseConnection dc;
	 protected SessionContext sc;
	 protected Oper operator;
	 protected Dept dept;
	 protected LogManager logMamager;
	 protected String reqxml;
	 static  Vector PARAM_MOTHOD = new Vector();

	 public Action() {
	 }
	 public int execute(ActionRequest req, SQLResponse res) {
		  this.req = req;
		  this.res = res;
		  int result = 0;
		  ConnectionManager cm = ConnectionManager.getInstance();
		  try {
			   logMamager = new LogManager();
			   dc = cm.get();
			   String actionName = this.getClass().getName();
			   DebugInfo di = (DebugInfo)ConnectionManager.badconn.get(dc.hashCode()+"");
			   if (di!=null) {
                   di.setAction(actionName);
               }
			   
			   dc.begin();
			   if(!LogAssistor.log()) {
					this.setSuccess("");
			   }

			   if (!req.getmethodName().trim().equals(""))
					result = call(req.getmethodName().trim());
			   else
					result = doBusiness();
		  } catch(Exception e) {
			   e.printStackTrace();
			   result = -1;
			   res.setResult(false);
			   res.setType(0);
			   res.setMessage(e.getMessage());
		  } finally {
			   try {
					if(result < 0) {
						 dc.rollback();
					} else {
						 dc.commit();
					}
					cm.release();
			   } catch(Exception e) {
					res.setResult(false);
					res.setType(0);
					res.setMessage(e.getMessage());
			   }
			   return 0;
		  }
	 }

	 public int doBusiness()
		  throws Exception {
		  System.out.println("Now in in Action.dddd");
		  return 0;
	 }

	 public void setSc(SessionContext sc) {
		  this.sc = sc;
	 }

    public Oper getOperator() {
        return operator;
    }

    public void setOperator(Oper operator) {
        this.operator = operator;
    }

    public DatabaseConnection getDatabaseConnection()
	 {
		 return dc;
	 }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setError(String message) {
		  setData(false, res.MESSAGE_TYPE, message);
	 }

	 public void setSuccess(String message) {
		  setData(true, res.MESSAGE_TYPE, message);
	 }

	 public void setXml(String xmlStr) {
		  setData(true, res.SELF_XML_TYPE, xmlStr);
	 }

	 public void setHtml(String htmlStr) {
		  setData(true, res.HTML_TYPE, htmlStr);
	 }

	 public void setData(boolean isSuccess, int type, String message) {
		  this.res.setResult(isSuccess);
		  this.res.setType(type);
		  this.res.setMessage(message);
	 }

	 public void memoryManager() {
		  long freeMem = Runtime.getRuntime().freeMemory();
		  long maxMem = Runtime.getRuntime().maxMemory();
		  long totalMem = Runtime.getRuntime().totalMemory();
		  double memrate = (double)freeMem / (double)maxMem;
		  if( memrate < 0.2) {
			   System.gc();
		  }
	 }

	 public int call(String methodname) throws Exception {
		 Method method = null;
		 try {
			 method = this.getClass().getMethod(methodname, new Class[] {});
		 } catch (SecurityException ex) {
			 throw new Exception("��������Ȩ�޲�����" + methodname);
		 } catch (NoSuchMethodException ex) {
			 throw new Exception("���������ڣ�" + methodname);
		 }

		 Object obj = null;
		 try {
			 obj = method.invoke(this, PARAM_MOTHOD.toArray());
			 if (obj != null) {
				 return Integer.parseInt(obj.toString());
			 }
		 } catch (InvocationTargetException ex1) {
			 ex1.getTargetException().printStackTrace();
			 throw new Exception(ex1.getTargetException().getMessage());
		 } catch (IllegalArgumentException ex1) {
			 throw new Exception("���ݵķ�����������:" + methodname);
		 } catch (IllegalAccessException ex1) {
			 throw new Exception("��������Ȩ�޲�����" + methodname);
		 }
		 return 0;
	 }

	 private void getLogMessage(){
		  if (logMamager.getErrorType().equals(logMamager.ERR_TYPE_HINT)){
			   this.res.setType(0);
			   this.res.setResult(true);
			   this.res.setMessage(logMamager.getMessage());
		 }else if (logMamager.getErrorType().equals(logMamager.ERR_TYPE_COMMON)||
                  logMamager.getErrorType().equals(logMamager.ERR_TYPE_BAD)){
			  this.res.setType(this.res.SELF_XML_TYPE);
			  this.res.setResult(false);
			  this.res.setMessage(logMamager.getMessage());
		 }else{
			   this.res.setType(0);
			   this.res.setResult(false);
			   this.res.setMessage(logMamager.getMessage());
		 }
	 }

	 public void setMessage(String moudleID,String errorType,String errorindex,boolean isWriteLog,Exception exp){
		  logMamager.setMessage(moudleID,errorType,errorindex,isWriteLog,exp);
		  getLogMessage();
	}

	public void setMessage(String moudleID,String errorType,String errorindex,boolean isWriteLog){
		  logMamager.setMessage(moudleID,errorType,errorindex,isWriteLog);
		  getLogMessage();
	}

	public void setMessage(String moudleID,String errorType,String errorindex){
		  logMamager.setMessage(moudleID,errorType,errorindex);
		  getLogMessage();
   }
   public void setReqXml(String reqxml){
		this.reqxml = reqxml;
   }
   public String getReqXml(){
	   return this.reqxml;
  }
}
