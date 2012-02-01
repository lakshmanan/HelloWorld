package com.thinkbig.dataengine;
//Lakshmanan Muthuraman@Think Big Analytics. 1/30/2012.
//Class retrieves all the Member Id's from a particular group. This class is kind of helper class to Data Engine.


import java.util.ArrayList;
import java.util.List;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

public class MemberInfo 
{
	String accessToken = "";
	FacebookClient facebookClient = null;
	public MemberInfo(String AccessToken)
	{
		accessToken = AccessToken;
		facebookClient = new DefaultFacebookClient(accessToken);

	}


	//Function to retrieve the member id's from a particular group.
	public List<String> getMemberIds(String groupId)
	{	
		List<String> memberIds = null;

		memberIds = new ArrayList<String>();	
		//Since RestFB API does not give class to map the data for the group members, I read the JSON data
		// and then from that extract the member id's.
		JsonObject memberObject = facebookClient.fetchObject(groupId+"/members", JsonObject.class);
		JsonArray  arrayJson =  memberObject.getJsonArray("data");

		for(int i=0;i<arrayJson.length();i++)
		{
			String mids = arrayJson.getJsonObject(i).getString("id");
			memberIds.add(mids);		
		}

		return memberIds;				

	}//getMemberIds()

}
