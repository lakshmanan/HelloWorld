package com.thinkbig.dataengine;
//Lakshmanan Muthuraman@Think Big Analytics. 1/30/2012.
//Class to make request to the Facebook API. Maps the JSON data from Facebook Calls to Java class FbookData.


import java.util.ArrayList;
import java.util.List;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.batch.BatchRequest.BatchRequestBuilder;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.*;

public class DataRequest
{
	String accessToken ="";
	FacebookClient facebookClient = null;
	List<String> memberIds = null;
	BatchRequest user,groups,activities,books,interest,likes,movies,music,tv;
	BatchResponse userRep,groupsRep,activitiesRep,booksRep,interestRep,likesRep,moviesRep,musicRep,tvRep;
	List<FbookData> fbookDataList = null;

	public DataRequest(String AccessToken,List<String>MemberIds)
	{
		accessToken = AccessToken;
		memberIds   = MemberIds;
		fbookDataList   = new ArrayList<FbookData>();
		facebookClient = new DefaultFacebookClient(accessToken);

	}

//Function creates a batch request to access data from the Facebook Graph API
	private void createBatchRequest(String MemberId)
	{
		user 		= new BatchRequestBuilder(MemberId).build();
		groups   	= new BatchRequestBuilder(MemberId+"/groups").build();	

		activities 	= new BatchRequestBuilder(MemberId+"/activities").build();
		books 		= new BatchRequestBuilder(MemberId+"/books").build();	 
		interest 	= new BatchRequestBuilder(MemberId+"/interests").build();
		likes 		= new BatchRequestBuilder(MemberId+"/likes").build();
		movies 		= new BatchRequestBuilder(MemberId+"/movies").build();
		music 		= new BatchRequestBuilder(MemberId+"/music").build();
		tv			= new BatchRequestBuilder(MemberId+"/television").build();
	}

	
	private void getBatchResponses()
	{
        //Executes a batch request to retrieve the data. Batch request helps in retrieving more objects in a single request than making multiple HTTP request.
		List<BatchResponse> batchResponses = facebookClient.executeBatch(user,groups,activities,books,interest,likes,movies,music,tv);

		userRep 		= batchResponses.get(0);
		groupsRep 		= batchResponses.get(1);
		activitiesRep 	= batchResponses.get(2);
		booksRep 		= batchResponses.get(3);	
		interestRep 	= batchResponses.get(4);
		likesRep 		= batchResponses.get(5);
		moviesRep 		= batchResponses.get(6);
		musicRep  		= batchResponses.get(7);
		tvRep			= batchResponses.get(8);


	}

	//Function which stores the retrieved data to the Facebook Data class
	private void storeData()
	{
        FbookData fbookDataObj = new FbookData();
		JsonMapper jsonMapper = new DefaultJsonMapper();
      
		User userData = jsonMapper.toJavaObject(userRep.getBody(), User.class);
		fbookDataObj.userData = userData;


		//Group groupData = jsonMapper.toJavaObject(groupsRep.getBody(), Group.class);
		//fbookDataObj.groupData = groupData;

		//System.out.println("Activities data");
		connectionJsonMapper(activitiesRep,fbookDataObj.activities);
		//System.out.println("Books data");
		connectionJsonMapper(booksRep,fbookDataObj.books);
		//System.out.println("Interests data");
		connectionJsonMapper(interestRep,fbookDataObj.interests);
		//System.out.println("Likes data");
		connectionJsonMapper(likesRep,fbookDataObj.likes);
		//System.out.println("Movies data");
		connectionJsonMapper(moviesRep,fbookDataObj.movies);
		//System.out.println("Music data");
		connectionJsonMapper(musicRep,fbookDataObj.music);
		//System.out.println("Tv data");
		connectionJsonMapper(tvRep,fbookDataObj.tv);
		
		fbookDataList.add(fbookDataObj);

	}//end of StoreData()

	//Since the RestFB API does not give java classess to map these data types, 
	//Manually Parse the JSON data to convert to required information.
	private void connectionJsonMapper(BatchResponse BatchResponseArg,List<String> dataType)
	{
		JsonMapper jsonMapper = new DefaultJsonMapper();
		JsonObject dataForConnection = jsonMapper.toJavaObject(BatchResponseArg.getBody(), JsonObject.class);

		JsonArray dataArray = dataForConnection.getJsonArray("data");

		int count = dataArray.length();
		for(int i=0;i<count;i++)
		{
			String nameInfo  = dataArray.getJsonObject(i).getString("name");
			dataType.add(nameInfo);
			//System.out.println(nameInfo);
		}

	}//end of connectionJsonMapper

//Function which controls creating request, getting response and storing the data 
	public List<FbookData> MakeRequest()
	{

			int memberCount = memberIds.size();
			for(int i=0;i<memberCount;i++)
			{
				createBatchRequest(memberIds.get(i));
				getBatchResponses();
				storeData();
			}
		
		return fbookDataList;

	}//end of MakeRequest()


}



