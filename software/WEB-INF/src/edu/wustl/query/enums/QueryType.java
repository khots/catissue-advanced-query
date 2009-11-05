package edu.wustl.query.enums;

import edu.wustl.query.util.global.Constants;

public enum QueryType 
{
  GET_COUNT(Constants.QUERY_TYPE_GET_COUNT),GET_DATA(Constants.QUERY_TYPE_GET_DATA);
  
  public String type = "";
  
  private QueryType(String type)
  {
	  this.type = type;
	  
  }
  
}
