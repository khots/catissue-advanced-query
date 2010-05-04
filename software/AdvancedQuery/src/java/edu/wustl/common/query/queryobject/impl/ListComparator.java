package edu.wustl.common.query.queryobject.impl;

import java.util.Comparator;
import java.util.List;

public class ListComparator implements Comparator<List<String>>
{
	private final int mainEntityIndex;
	public ListComparator(int mainEntityIndex)
	{
		this.mainEntityIndex = mainEntityIndex;
	}
	public int compare(List<String> list1, List<String> list2)
	{
		int returnValue;
		Integer index1 = Integer.parseInt(list1.get(mainEntityIndex));
		Integer index2 = Integer.parseInt(list2.get(mainEntityIndex));
		if(index1>index2)
		{
			returnValue = 1;
		}
		else if(index1<index2)
		{
			returnValue = -1;
		}
		else
		{
			returnValue = 0;
		}
		return returnValue;
	}
}
