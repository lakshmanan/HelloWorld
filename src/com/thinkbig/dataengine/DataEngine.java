package com.thinkbig.dataengine;

//Lakshmanan Muthuraman@Think Big Analytics. 1/30/2012.
//Data Engine is the main controller class. Data Engine funcionalities are
//1. To get the member id's from the helper class Member Info
//2. Uses that Member Info to retrieve the data from Facebook API and  return a populated FbookData class
//3. Calls the File Storage class to write the data to the file.


import java.util.List;

import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.FacebookResponseStatusException;

public class DataEngine {

	//Access_Token is specific for each user. It should be modified to each user.
	private static String ACCESS_TOKEN = "AAAAAAITEghMBALboPm6ZACG7IRRtjBSfxeMOxZCbET5VMXUHVZAbdtm0UP3ZADx7hKNVy4tGzMq92NOjpTT9i6hxfXQZBVWUkVuWMrTpbMwZDZD";
	private static String GROUP_ID = "189758685202";
	public static void main(String[] args) 
	{
		try
		{
			MemberInfo memberInfo  = new MemberInfo(ACCESS_TOKEN);
			List<String> memberIds = memberInfo.getMemberIds(GROUP_ID);//Pass the Group Id to get the list of members.
			//List<String> memberIds = new ArrayList<String>();
			//memberIds.add("575307457");

			DataRequest dataRequest = new DataRequest(ACCESS_TOKEN,memberIds); // Request the data with access token and member id of the facebook user
			List<FbookData> DataList = dataRequest.MakeRequest();

			Storage dataStorage  = new CSVFileStorage("FaceBookData.csv");
			dataStorage.writeToStorage(DataList);	
		}
		catch(FacebookOAuthException e)
		{
			//Authentication Failed error.
			System.out.println("Authentication Failed  "+e.getErrorMessage());
		}
		catch (FacebookNetworkException e) 
		{
			// An error occurred at the network level
			System.out.println("An Error occured at the Network Level " + e.getMessage());
		} 
		catch (FacebookGraphException e) {
			// The Graph API returned a specific error
			System.out.println("Facebook Graph Call failed. GraphAPI says: " + e.getErrorMessage());
		} 
		catch (FacebookResponseStatusException e)
		{  // Old-style Facebook error response.
			// The Graph API only throws these when FQL calls fail.
			if (e.getErrorCode() == 200)
				System.out.println("Permission denied!");
		} 
		catch (FacebookJsonMappingException e) 
		{
			System.out.println("Failed to Map Json Data from Facebook"+e.getMessage());
		}
		catch (FacebookException e) 
		{
			System.out.println("General Facebook Exception"+e.getMessage());
		}

	}//end of main()

}//end of class Data Engine
