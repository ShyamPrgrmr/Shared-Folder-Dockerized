package com.sharedfolder.client;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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
	

	//add folder
	public DefaultTreeModel addNewFolder(DataStorage dst) {
		String nameOfFolder = dst.getNameOfFolder();
		String path[] = dst.getCurrentPath();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
		
		for(String p : path) {
			jarray.add(p);
		}
		
		json.put("nameOfFolder", nameOfFolder);
		json.put("path", jarray);
		DefaultTreeModel dmtn;
		
		HttpPost request = new HttpPost(this.serverPath+"/addfolder");
		StringEntity params;
		try {
			params = new StringEntity(json.toString());
			request.setEntity(params);
			request.addHeader("content-type", "application/json");
			response = httpClient.execute(request);
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	
                }
                else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	dmtn = new DefaultTreeModel(getTreeNodeFromJSON(js));
                	return dmtn;
                }
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//add files
	public DefaultTreeModel addFolder(DataStorage dst) {
		String nameOfFolder = dst.getNameOfFolder();
		String path[] = dst.getCurrentPath();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
		
		for(String p : path) {
			jarray.add(p);
		}
		
		json.put("nameOfFolder", nameOfFolder);
		json.put("path", jarray);
		DefaultTreeModel dmtn;
		
		HttpPost request = new HttpPost(this.serverPath+"/addfile");
		StringEntity params;
		try {
			params = new StringEntity(json.toString());
			request.setEntity(params);
			request.addHeader("content-type", "application/json");
			response = httpClient.execute(request);
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	
                }
                else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	dmtn = new DefaultTreeModel(getTreeNodeFromJSON(js));
                	return dmtn;
                }
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//delete files and folder
	public DefaultTreeModel deleteFolder(DataStorage dst) {
		DefaultTreeModel dtm;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		String nameOfFolder = dst.getNameOfFolder();
		String path[] = dst.getCurrentPath();
		JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
		json.put("nameOfFolder", nameOfFolder);
		json.put("path", jarray);
		for(String p : path) {
			jarray.add(p);
		}
		try {
			StringEntity params;
			HttpPost request = new HttpPost(this.serverPath+"/deletefile");
			request.addHeader("content-type", "application/json");
			params = new StringEntity(json.toString());
			request.setEntity(params);
			response = httpClient.execute(request);
			
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	
                }
                else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	dtm = new DefaultTreeModel(getTreeNodeFromJSON(js));
                	return dtm;
                }
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DefaultTreeModel uploadFile(String nameofFile,String[] path,File file) {
		DefaultTreeModel dtm;
		String filepath = "";
		
		for(String p : path) {
			filepath += p+"/"; 
		}
		
		JSONObject json = new JSONObject();
		json.put("path", filepath);
		json.put("filename", nameofFile);
		
		HttpEntity entity= MultipartEntityBuilder.create().
				addTextBody("filedata",json.toJSONString())
				.addBinaryBody("file", file)
				.build(); 
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(this.serverPath+"/upload");
		httpPost.setEntity(entity);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	
                }
                else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	dtm = new DefaultTreeModel(getTreeNodeFromJSON(js));
                	return dtm;
                }
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//getting tree
	public DefaultTreeModel getTree() {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		DefaultTreeModel dmtn;
		try {
			HttpGet request = new HttpGet(this.serverPath+"/getfolders");
			request.addHeader("content-type", "application/json");
			response = httpClient.execute(request);
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	
                }
                else {
                	JSONParser parser = new JSONParser();
                	JSONObject js = (JSONObject) parser.parse(EntityUtils.toString(response.getEntity()));
                	dmtn = new DefaultTreeModel(getTreeNodeFromJSON(js));
                	return dmtn;
                }
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	//helper class
	private DefaultMutableTreeNode getTreeNodeFromJSON(JSONObject json) {
		DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(json.get("nameOfFolder"));
		JSONArray jsarray = (JSONArray) json.get("files");
		int size = jsarray.size();
		for(int i=0;i<size;i++) {
			JSONObject tempJson = (JSONObject) jsarray.get(i);
			boolean isFolder = (boolean) tempJson.get("folder");
			DefaultMutableTreeNode tempNode;
			if(isFolder) {
				tempNode = getTreeNodeFromJSON(tempJson);
				dmtn.add(tempNode);
			}			
			else {
				tempNode = new DefaultMutableTreeNode(tempJson.get("nameOfFolder"));
				dmtn.add(tempNode);
			}
		}
		
		return dmtn;
	}
	

	//download files
	public byte[] downloadFile(DataStorage dst) {
		String notFound ="file not found!";
		String nameOfFolder = dst.getNameOfFolder();
		String path[] = dst.getCurrentPath();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		JSONObject json = new JSONObject();
		String absolutePath = "";
		for(String p : path) {
			absolutePath += p+"/"; 
		}
		
		json.put("nameOfFolder", nameOfFolder);
		json.put("path", absolutePath.substring(0,absolutePath.length()-1));
		
		DefaultTreeModel dmtn;
		
		HttpPost request = new HttpPost(this.serverPath+"/download");
		StringEntity params;
		try {
			params = new StringEntity(json.toString());
			request.setEntity(params);
			request.addHeader("content-type", "application/json");
			request.addHeader("accept","*/*");
			response = httpClient.execute(request);
			if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200) {
                	return notFound.getBytes();
                }
                else {
                	byte[] content = EntityUtils.toByteArray(response.getEntity());
                	return content;
                }
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
