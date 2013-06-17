package edu.wustl.query.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.common.util.logger.LoggerConfig;
import edu.wustl.query.bizlogic.QueryExportImport;

public class ImportQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final org.apache.log4j.Logger LOGGER = LoggerConfig
			.getConfiguredLogger(ImportQueryServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryName = null;

			SessionDataBean sessionDataBean = (SessionDataBean) request.getSession()
					.getAttribute(edu.wustl.common.util.global.Constants.SESSION_DATA);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			
			StringWriter writer = new StringWriter();
			InputStream nameStream = items.get(0).getInputStream();		
			IOUtils.copy(nameStream, writer);
			queryName = writer.toString();
			nameStream.close();
			 

			InputStream xmlStream = items.get(1).getInputStream();	
			QueryExportImport queryExim = new QueryExportImport();
			queryExim.importQuery(sessionDataBean, queryName, xmlStream);
			
			response.getWriter().write("Success :'" + queryName + "'");
		} catch (Exception e) {
			 try {
				response.getWriter().write("Error:");
				throw new RuntimeException("Error occured during importing query", e);
			 } catch (IOException e1) {
				LOGGER.error(e1.getMessage());
			}
		}
	}
}
