package com.sharedfolder.server;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class RequestController {
	
	String serverPath;
	
	RequestController(String serverPath){
		this.serverPath = serverPath;
	}
	
	@SuppressWarnings("unchecked")
	public LoginResponse loginRequest(String username,String password) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		LoginResponse lr =new LoginResponse();
		
		try {
			JSONObject json = new JSONObject();
			json.put("username", username);
			json.put("password", password);
			json.put("type","null");
			
			HttpPost request = new HttpPost(this.serverPath+"/login");
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			response = httpClient.execute(request);
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	lr.setLogin(false);
                	return lr;
                }else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	if(js.get("username").equals(null)) {
                		lr.setLogin(false);
                	}
                	else {
                		lr.setLogin(true);
                		lr.setUsername(js.get("username").toString());
                		lr.setType(js.get("type").toString());
                		return lr;
                	}
                }
                
            }
			
		}
		catch(NullPointerException npe) {
			
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		lr.setLogin(false);
		return lr;
	}
	
	@SuppressWarnings("unchecked")
	public DefaultTableModel addUser(AddUserRequest user) {
		JSONObject json = new JSONObject();
		json.put("username", user.getUsername());
		json.put("name", user.getName());
		json.put("mobile", user.getMobile());
		json.put("type", user.getType());
		json.put("gender", user.getGender());
		json.put("email", user.getEmail());
		json.put("password",user.getPassword());
		
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(this.serverPath+"/adduser");
			request.addHeader("accept", "application/json");
			request.addHeader("content-type", "application/json");
			StringEntity params = new StringEntity(json.toString());
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	httpClient.close();
                }
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return getTableData();
	}
	
	@SuppressWarnings("unchecked")
	public DefaultTableModel updateUser(UserData user,DefaultTableModel noChange) {
		JSONObject json = new JSONObject();
		json.put("username", user.getUsername());
		json.put("name", user.getName());
		json.put("mobile", user.getMobile());
		json.put("userType", user.getType());
		json.put("gender", user.getGender());
		json.put("email", user.getEmail());
		
		try {
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(this.serverPath+"/updateuser");
			request.addHeader("accept", "application/json");
			request.addHeader("content-type", "application/json");
			StringEntity params = new StringEntity(json.toString());
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	httpClient.close();
                }
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return getTableData();
	}
	
	@SuppressWarnings({ "null" })
	public boolean deleteUser(String username) {
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(this.serverPath+"/deleteuser?username="+username);
			request.addHeader("accept", "application/json");
			@SuppressWarnings("unused")
			HttpResponse response = httpClient.execute(request);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public DefaultTableModel getTableData() {
		DefaultTableModel dtm=new DefaultTableModel();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		dtm.addColumn("Username"); 
		dtm.addColumn("Name");
		dtm.addColumn("Mobile");
		dtm.addColumn("Gender");
		dtm.addColumn("Email");
		dtm.addColumn("User Type");
		
		HttpGet request = new HttpGet(this.serverPath+"/getusers");
		request.addHeader("content-type", "application/json");
		request.addHeader("accept", "application/json");
		try {
			HttpResponse response = httpClient.execute(request);
			
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	
                }else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	JSONArray jarray = new JSONArray();
                	jarray = (JSONArray) js.get("data");
                	for(int i=0;i<jarray.size();i++) {
                		Object obj = jarray.get(i);
                		JSONObject temp = (JSONObject) parser.parse(obj.toString());
                		String username = temp.get("username").toString();
                		String name = temp.get("name").toString();
                		String type = temp.get("userType").toString();
                		String mobile = temp.get("mobile").toString();
                		String email = temp.get("email").toString();
                		String gender = temp.get("gender").toString();
                		if(type.equals("1")) type="Admin";
                		else type="User";
                		dtm.addRow(new Object[] { username,name,mobile,gender,email,type });
                	}
                }
              }
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return dtm;
	}
	
}
