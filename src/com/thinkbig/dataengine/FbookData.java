package com.thinkbig.dataengine;

import java.util.ArrayList;
import java.util.List;

import com.restfb.types.*;

public class FbookData {
	
	User userData;
	Group groupData;
	List<String> activities;
	List<String> books;
	List<String> interests;
	List<String> likes;
	List<String> movies;
	List<String> music;
	List<String> tv;
	
	
	public FbookData()
	{	
		 userData = new User();
		 groupData = new Group();
		 activities = new ArrayList<String>();
		 books  = new ArrayList<String>();
		 interests  = new ArrayList<String>();
		 likes  = new ArrayList<String>();
		 movies  = new ArrayList<String>();
		 music  = new ArrayList<String>();
		 tv  = new ArrayList<String>();	
		
	}

	
	
	
}
