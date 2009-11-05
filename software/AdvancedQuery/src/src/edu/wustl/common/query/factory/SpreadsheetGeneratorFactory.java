package edu.wustl.common.query.factory;

import java.lang.reflect.Constructor;

import edu.wustl.query.spreadsheet.SpreadSheetViewGenerator;
import edu.wustl.query.util.global.Variables;
import edu.wustl.query.util.querysuite.QueryModuleError;
import edu.wustl.query.util.querysuite.QueryModuleException;
import edu.wustl.query.viewmanager.ViewType;

/**
 * Factory to return the SpreadSheetGenerator instance.
 *
 * @author gaurav_sawant
 *
 */
public final class SpreadsheetGeneratorFactory
{
    /**
     * Private Constructor.
     */
    private SpreadsheetGeneratorFactory()
    {
        // empty constructor.
    }

    /**
     * This method instantiates class to generate spreadsheet.
     *
     * @param viewType
     *            ViewType.
     * @return instance of SpreadSheetViewGenerator.
     * @throws QueryModuleException
     *             Query Module Exception.
     */
    public static SpreadSheetViewGenerator configureDefaultSpreadsheetGenerator(
            ViewType viewType) throws QueryModuleException
    {
        String spreadSheetGeneratorClass = Variables.spreadSheetGeneratorClassName;
        SpreadSheetViewGenerator spreadSheetViewGenerator = null;
        if (spreadSheetGeneratorClass != null)
        {
            try
            {
                Class className = Class.forName(spreadSheetGeneratorClass);
                if (className != null)
                {
                    Class[] parameterTypes = { ViewType.class };
                    Constructor declaredConstructor = className
                            .getDeclaredConstructor(parameterTypes);
                    spreadSheetViewGenerator = (SpreadSheetViewGenerator) declaredConstructor
                            .newInstance(viewType);
                }
            } catch (ClassNotFoundException cnfe)
            {
                throw new QueryModuleException(cnfe.getMessage(), cnfe,
                        QueryModuleError.CLASS_NOT_FOUND);
            } catch (Exception e)
            {
                throw new QueryModuleException(e.getMessage(), e,
                        QueryModuleError.SQL_EXCEPTION);
            }
        }
        return spreadSheetViewGenerator;
    }
}

