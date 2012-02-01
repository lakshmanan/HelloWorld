package com.thinkbig.dataengine;

//Lakshmanan Muthuraman@Think Big Analytics. 1/30/2012.
//Class which writes the information to the CSV File Storage.


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.restfb.types.User;

public class CSVFileStorage implements Storage{

	String fileName="";
	public CSVFileStorage(String FileName)
	{
		fileName = FileName;

	}
	//function to write the information to the file.
	public void writeToStorage(List<FbookData> DataList)
	{
		BufferedWriter bufferedWriter = null;

		try {

			//Construct the BufferedWriter object
			bufferedWriter = new BufferedWriter(new FileWriter(fileName));
			System.out.println("File Writing Started");
			for(int i=0;i<DataList.size();i++)
			{
				FbookData fbookDataObj = DataList.get(i);
				String facebookUniqueId = fbookDataObj.userData.getId();
				writeUserData(fbookDataObj.userData,bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"ACTIVITIES,");
				WriterConnectionData(fbookDataObj.activities, bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"BOOKS,");
				WriterConnectionData(fbookDataObj.books, bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"INTERESTS,");
				WriterConnectionData(fbookDataObj.interests, bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"LIKES,");
				WriterConnectionData(fbookDataObj.likes, bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"MOVIES,");
				WriterConnectionData(fbookDataObj.movies, bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"MUSIC,");
				WriterConnectionData(fbookDataObj.music, bufferedWriter);
				bufferedWriter.write(facebookUniqueId+","+"TV,");
				WriterConnectionData(fbookDataObj.tv, bufferedWriter);             

			}
			System.out.println("File writing Successful");

		} 
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			//Close the BufferedWriter
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}//end of function writeToFile()

	private void writeUserData(User UserData,BufferedWriter WriterObject) throws IOException
	{
		WriterObject.write(UserData.getId()+",");
		WriterObject.write("BASIC_INFO");
		WriterObject.write(",");
		WriterObject.write("[");
		WriterObject.write(UserData.getFirstName());
		WriterObject.write(",");
		WriterObject.write(UserData.getLastName());
		WriterObject.write(",");
		String gender = UserData.getGender();
		if(gender!=null)
		{
			WriterObject.write(UserData.getGender());
		}
		WriterObject.write(",");
		WriterObject.write(UserData.getLocale());
		WriterObject.write("]");
		WriterObject.newLine();

	}//end of WriteUserData

	private void WriterConnectionData(List<String> DataList, BufferedWriter WriterObject) throws IOException
	{
		WriterObject.write("[");
		for(int i=0;i<DataList.size();i++)
		{
			WriterObject.write(DataList.get(i));
			WriterObject.write(",");		
		}

		WriterObject.write("]");
		WriterObject.newLine();

	}//end of WriterConnectionData


}//end of FileStorage

