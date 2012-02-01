package com.thinkbig.dataengine;

import java.util.List;
//Lakshmanan Muthuraman@Think Big Analytics. 1/30/2012.
//Interface which is used for writing information to the storage medium.


public interface Storage {

	public void writeToStorage(List<FbookData> DataList);
	
}
