package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.wustl.query.spreadsheet.SpreadSheetViewGenerator;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;


public class SpreadsheetGeneratorFactory
{
	/**
	 * This method instantiates class to generate spreadsheet.
	 * @param viewType ViewType
	 * @return instance of SpreadSheetViewGenerator
	 * @throws QueryModuleException
	 */
	public static SpreadSheetViewGenerator configureDefaultSpreadsheetGenerator(ViewType viewType)
						throws QueryModuleException
	{
		 String spreadSheetGeneratorClass = Variables.spreadSheetGeneratorClassName;
		 SpreadSheetViewGenerator spreadSheetViewGenerator = null;
		 QueryModuleException queryModuleException = null;
         if(spreadSheetGeneratorClass!=null)
         {
            try
 			{
 				Class className = Class.forName(spreadSheetGeneratorClass);
 				if(className != null)
		        {
		         	Class[] parameterTypes = {ViewType.class};
		         	Constructor declaredConstructor = className.getDeclaredConstructor(parameterTypes);
					spreadSheetViewGenerator =  (SpreadSheetViewGenerator)declaredConstructor.newInstance(viewType);
		        }
			}
			catch (ClassNotFoundException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.CLASS_NOT_FOUND);
				throw queryModuleException;
			}
			catch (IllegalArgumentException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.GENERIC_EXCEPTION);
				throw queryModuleException;
			}
			catch (InstantiationException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.GENERIC_EXCEPTION);
				throw queryModuleException;
			}
			catch (IllegalAccessException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.GENERIC_EXCEPTION);
				throw queryModuleException;
			}
			catch (InvocationTargetException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.GENERIC_EXCEPTION);
				throw queryModuleException;
			}
			catch (NoSuchMethodException e)
			{
				queryModuleException = new QueryModuleException(e.getMessage(),QueryModuleError.GENERIC_EXCEPTION);
				throw queryModuleException;
			}
         }
		return spreadSheetViewGenerator;
	}
}
