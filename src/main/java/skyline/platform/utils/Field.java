package skyline.platform.utils;

import skyline.util.ToolUtil;
import java.io.*;

public class Field implements Serializable {
  
	String getUpdateField()	{
	    StringBuffer sb = new StringBuffer();
	    if (Basic.getDbType().equals("DB2"))
	    {
	    	 switch(type) 
	    	 {
		         case 1: 
		         case 3:
		         case 4:
		        	 if(value.trim().equals(""))
			        		value = null;
		        	 	if (value != null)
		        	 		sb.append(name).append(relative).append("'").append(value).append("'");
		        	 	else
		        	 		sb.append(name).append(relative).append(value);	        	 	
		        	 	break;
		         case 2:
		        	 if(value==null || value.trim().equals(""))
		        		value = null;
		        	 sb.append(name).append(relative).append(value); break;
		      }
	    }
	    else
	    {
	    	 switch(type) 
	    	 {
		         case 1: 
		        	 if (value != null)
		        	 		sb.append(name).append(relative).append("'").append(value).append("'");		        	 	
		        	 	else
		        	 		 sb.append(name).append(relative).append(value);	        	 	
		        	 	break;
		         case 2: 
		        	 if(value==null || value.trim().equals(""))
			        	value = null;
		        	 sb.append(name).append(relative).append(value); break;
		         case 3:
		         case 4:	
		        	 	if (value != null)
		        	 		sb.append(name).append(relative).append("to_date('").append(value).append("','YYYY-MM-DD HH24:MI:SS')");
		        	 	else
		        	 		sb.append(name).append(relative).append(value);	        	 	
		        	 	break;
		    }
	    }
	    return sb.toString();
  }

  String getInsertField(){
	    StringBuffer sb = new StringBuffer();
	    if (Basic.getDbType().equals("DB2")){
	    	 switch(type){
		    	 case 1:
		    	 case 3:
		    	 case 4:	 
		    		 if(value.trim().equals(""))
			        	value = null;
		    		 if (value != null)
		        			sb.append(" '").append(value).append("'");
		        		else
		        			sb.append(" ").append(value); 	        		
		        		break;
		     	 case 2: 
		     		if(value==null || value.trim().equals(""))
		        		value = null;		     		 
		     		 sb.append(" ").append(value); break;
		      }
	    }
	    else
	    {
	    	switch(type) 
	    	{
	        	case 1:
	        		if (value != null)
	        			sb.append(" '").append(value).append("'");
	        		else
	        			sb.append(" ").append(value); 	        		
	        		break;
	        	case 2: 
	        		if(value==null || value.trim().equals(""))
		        		value = null;
	        		sb.append(" ").append(value); break;
	        	case 3: 
	        	case 4: 	
	        		 if (value != null)
	        			 sb.append(" to_date('").append(value).append("','YYYY-MM-DD HH24:MI:SS')");
	        		 else
	 	        		 sb.append(" ").append(value); 	        		
	 	        	break;
	        }
	    }
	    return sb.toString();
  }

  String getQueryField() {
       StringBuffer sb = new StringBuffer();
       if (Basic.getDbType().equals("DB2")){
	    	 switch(type){
	    	 	case 1:
	    	 	case 3: 
	    	 	case 4:  
	    	 		     sb.append(name).append(relative).append("'").append(value).append("'"); break;
	         	case 2: sb.append(name).append(relative).append(value); break;
		      }
	    }
	    else{
	    	switch(type) {
	         	case 1: sb.append(name).append(relative).append("'").append(value).append("'"); break;
	         	case 2: sb.append(name).append(relative).append(value); break;
	         	case 3: sb.append(name).append(relative).append("to_date('").append(value).append("','YYYY-MM-DD HH24:MI:SS')"); break;
	         	case 4: sb.append(name).append(relative).append("to_date('").append(value).append("','YYYY-MM-DD HH24:MI:SS')"); break;
	        }
	    }
       if(value.equals("") || value == null) {
            sb = new StringBuffer();
            sb.append(" 1=1 ");
       }
       return sb.toString();
  }

  String getDeleteField() {
       StringBuffer sb = new StringBuffer();
       if (Basic.getDbType().equals("DB2")){
	    	 switch(type) 
	    	 {
	    	 	case 1:
	    	 	case 3: 
	    	 	case 4:  
	    	 			sb.append(name).append(relative).append("'").append(value).append("'"); break;
	         	case 2: sb.append(name).append(relative).append(value); break;
		      }
	    }
	    else
	    {
	    	switch(type) 
	    	{
	         	case 1: sb.append(name).append(relative).append("'").append(value).append("'"); break;
	         	case 2: sb.append(name).append(relative).append(value); break;
	         	case 3: sb.append(name).append(relative).append("to_date('").append(value).append("','YYYY-MM-DD HH24:MI:SS')"); break;
	         	case 4: sb.append(name).append(relative).append("to_date('").append(value).append("','YYYY-MM-DD HH24:MI:SS')"); break;
	       }
	    }
       if(value.equals("") || value == null) {
            sb = new StringBuffer();
            sb.append(name).append(" is null ");
       }
       return sb.toString();
  }


  public int getType(){ return type; }

  public void setType(int type){ this.type = type; }

  public String getValue(){ return value; }

  public void setValue(String value){   
	  if (this.type==TEXT){
           this.value = DbUtil.sqlEncode(value);
          // System.out.println(this.value);
	  }
       else if (this.type == DATE || this.type == DATETIME) {
    	   this.value = value;
    	   if (value.trim().length()==10) {
    		   this.value = value.trim()+" " + ToolUtil.getStrLastUpdTime();
    	   }
       } else {
    	   this.value = value;
       }

  }


  public String getName(){ return name; }

  public void setName(String name){ this.name = name; }

  public Field(String name,String value,int type,String relative) {
    this.name = name;
    this.type = type;
    setValue(value);
    this.relative = relative;
  }

  private int type;
  private String value;
  private String name;
  private String relative;

  public static int DATETIME=4;
  public static int NUMBER=2;
  public static int DATE=3;
  public static int TEXT=1;
}
