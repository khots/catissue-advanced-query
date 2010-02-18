package edu.wustl.common.query.queryobject.impl.metadata;

import junit.framework.TestCase;

public class SelectedColumnsMetadataTestCase extends TestCase
{
	public void testAllMethods()
	{
		SelectedColumnsMetadata metadata = new SelectedColumnsMetadata();
		metadata.setCurrentSelectedObject(null);
		metadata.setDefinedView(false);
		metadata.setSelColNVBeanList(null);
		metadata.setSelectedAttributeMetaDataList(null);
		metadata.setSelectedOutputAttributeList(null);

		metadata.isDefinedView();
		metadata.getAttributeList();
		metadata.getCurrentSelectedObject();
		metadata.getSelColNVBeanList();
		metadata.getSelectedAttributeMetaDataList();
		metadata.getSelectedOutputAttributeList();
	}
}
