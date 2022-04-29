package pageObjects;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HitApi {
	
	public static void main(String[] args) throws IOException {
	    HitApi.MyGETRequest1();
	    //GetAndPost.MyPOSTRequest();
	}
	public static void MyGETRequest() throws IOException {
	    URL urlForGetRequest = new URL("https://dev-adityabirla.emcollab.com/api/states/101");
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty("authorization", "Bearer f24ffc9f04494ee42efcc4ebb92dfcff5f23e5c4c7ebd691025d9f79b47c9a19");
	    //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
	    int responseCode = conection.getResponseCode();
	    
	    System.out.println(conection.getResponseCode());
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	    	
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        
	        
	        int i=0;
	       // System.out.println("Redaline " + readLine);
	        while ((readLine = in .readLine()) != null) {
	        	i++;
	            response.append(readLine);
	            //list.add(readLine);
	            //System.out.println("List Length " + i);
	        } in .close();
	        // print result
	        //System.out.println("List Length " + i);
	     // System.out.println(response.getClass().getSimpleName()+"    Helo");
	        System.out.println("JSON String Result " +response );
	        //GetAndPost.POSTRequest(response.toString());
	        
	      List<String> list=new ArrayList<String>(); 
	      
	        
	        
	        
	        
	        
	    } else {
	        System.out.println("GET NOT WORKED");
	    }

	}
	public static void POSTRequest() throws IOException {



	    final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
	        "    \"id\": 101,\r\n" +
	        "    \"title\": \"Test Title\",\r\n" +
	        "    \"body\": \"Test Body\"" + "\n}";
	    System.out.println(POST_PARAMS);
	    URL obj = new URL("https://jsonplaceholder.typicode.com/posts");
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("userId", "a1bcdefgh");
	    postConnection.setRequestProperty("Content-Type", "application/json");


	    postConnection.setDoOutput(true);
	    OutputStream os = postConnection.getOutputStream();
	    os.write(POST_PARAMS.getBytes());
	    os.flush();
	    os.close();


	    int responseCode = postConnection.getResponseCode();
	    System.out.println("POST Response Code :  " + responseCode);
	    System.out.println("POST Response Message : " + postConnection.getResponseMessage());

	    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	            postConnection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in .readLine()) != null) {
	            response.append(inputLine);
	        } in .close();

	        // print result
	        System.out.println(response.toString());
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
	}
	

	public static void MyGETRequest1() throws IOException {
		//inline will store the JSON data streamed in string format
				String inline = "";
			
				try
				{
					URL url = new URL("https://dev-adityabirla.emcollab.com/api/states/101");
					//Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					//Set the request to GET or POST as per the requirements
					conn.setRequestMethod("GET");
					conn.setRequestProperty("authorization", "Bearer f24ffc9f04494ee42efcc4ebb92dfcff5f23e5c4c7ebd691025d9f79b47c9a19");
					//Use the connect method to create the connection bridge
					conn.connect();
					//Get the response status of the Rest API
					int responsecode = conn.getResponseCode();
					System.out.println("Response code is: " +responsecode);
					
					//Iterating condition to if response code is not 200 then throw a runtime exception
					//else continue the actual process of getting the JSON data
					if(responsecode != 200)
						throw new RuntimeException("HttpResponseCode: " +responsecode);
					else
					{
						//Scanner functionality will read the JSON data from the stream
						Scanner sc = new Scanner(url.openStream());
						while(sc.hasNext())
						{
							inline+=sc.nextLine();
						}
						System.out.println("\nJSON Response in String format"); 
						System.out.println(inline);
						//Close the stream when reading the data has been finished
						sc.close();
					}
					
					//JSONParser reads the data from string object and break each data into key value pairs
					JSONParser parse = new JSONParser();
					//Type caste the parsed json data in json object
					JSONObject jobj = (JSONObject)parse.parse(inline);
					//Store the JSON object in JSON array as objects (For level 1 array element i.e Results)
					JSONArray jsonarr_1 = (JSONArray) jobj.get("results");
					//Get data for Results array
					for(int i=0;i<jsonarr_1.length();i++)
					{
						//Store the JSON objects in an array
						//Get the index of the JSON object and print the values as per the index
						JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
						//Store the JSON object in JSON array as objects (For level 2 array element i.e Address Components)
						JSONArray jsonarr_2 = (JSONArray) jsonobj_1.get("address_components");
						System.out.println("Elements under results array");
						System.out.println("\nPlace id: " +jsonobj_1.get("place_id"));
						System.out.println("Types: " +jsonobj_1.get("types"));
						//Get data for the Address Components array
						System.out.println("Elements under address_components array");
						System.out.println("The long names, short names and types are:");
						for(int j=0;j<jsonarr_2.length();j++)
						{
							//Same just store the JSON objects in an array
							//Get the index of the JSON objects and print the values as per the index
							JSONObject jsonobj_2 = (JSONObject) jsonarr_2.get(j);
							//Store the data as String objects
							String str_data1 = (String) jsonobj_2.get("long_name");
							System.out.println(str_data1);
							String str_data2 = (String) jsonobj_2.get("short_name");
							System.out.println(str_data2);
							System.out.println(jsonobj_2.get("types"));
							System.out.println("\n");
						}
						
					}
					//Disconnect the HttpURLConnection stream
					conn.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
	
}

