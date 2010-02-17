package edu.wustl.query.htmlprovider;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class GenerateHtmlTestCase extends TestCase
{
	public void testGenerateHTMLForRadioButton()
	{
		List<String> values = new ArrayList<String>();
		GenerateHtml.generateHTMLForRadioButton("isAvailable13761", null);
		values.add(null);
		GenerateHtml.generateHTMLForRadioButton("isAvailable13761", values);
		values = new ArrayList<String>();
		values.add("false");
		GenerateHtml.generateHTMLForRadioButton("isAvailable13761", values);
	}

	public void testGenerateCheckBox()
	{
		GenerateHtml.generateCheckBox("isAvailable13761", true);
	}

	public void testGetTags()
	{
		GenerateHtml.getTags(new StringBuffer());
	}

	public void testGetHtmlHeader()
	{
		String attributeCollection = ";activityStatus93;birthDate94;deathDate95;empiId33071;empiIdStatus33072;ethnicity96;firstName97;gender98;id99;lastName100;metaPhoneCode33073;middleName101;sexGenotype102;socialSecurityNumber103;vitalStatus104";
		GenerateHtml.getHtmlHeader("Participant", "91", attributeCollection, false);
		GenerateHtml.getHtmlHeader("Participant", "91", attributeCollection, true);

		GenerateHtml.generatePreHtml(attributeCollection, "edu.wustl.clinportal.domain.Participant", false);
		GenerateHtml.generatePreHtml(attributeCollection, "edu.wustl.clinportal.domain.Participant", true);
	}
}
