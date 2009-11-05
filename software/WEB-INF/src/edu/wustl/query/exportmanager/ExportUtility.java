
package edu.wustl.query.exportmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import edu.wustl.common.util.ExportReport;
import edu.wustl.common.util.logger.Logger;

/**
 * 
 * @author ravindra_jain
 * @version 1.0
 * @since March 3, 2009 
 *
 */
public class ExportUtility
{

	/**
	 * To create CSV file from a List
	 * @param dataList
	 * @throws IOException 
	 */
	public static void createCSV(List dataList, String fileName) throws IOException
	{
		ExportReport report = new ExportReport(fileName);
		report.writeData(dataList, edu.wustl.common.util.global.Constants.DELIMETER);
		report.closeFile();
	}
	
	
	/**
	 * To create a ZIP file
	 * @param csvFilePath
	 * @param zipFilePath
	 * @throws IOException 
	 */
	public static void createZip(String filePath, String csvFileName, String zipFileName) throws IOException
	{
		File csvFile = new File(filePath + "/" + csvFileName);
		String zipFilePath = filePath + "/" + zipFileName;
		if (!csvFile.exists())
		{
			Logger.out.info("CSV File not found..");
		}

		if (!zipFilePath.endsWith(".zip"))
		{
			zipFilePath = zipFilePath + ".zip";
		}
		byte[] buffer = new byte[18024];

		
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));
		out.setLevel(Deflater.DEFAULT_COMPRESSION);
		FileInputStream in = new FileInputStream(csvFile);
		ZipEntry zipEntry = new ZipEntry(csvFile.getName());
		out.putNextEntry(zipEntry);
		int len;
		while ((len = in.read(buffer)) > 0)
		{
			out.write(buffer, 0, len);
		}
		out.closeEntry();
		in.close();
		out.close();
		csvFile.delete();
		if(csvFile.delete())
		{
		 Logger.out.info("CSV DELETED....");
		}
		Logger.out.info("ZIP FILE GENERATED....");
	}
}
