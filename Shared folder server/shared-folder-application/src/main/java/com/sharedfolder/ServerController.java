package com.sharedfolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class ServerController {
	
	@Autowired
	DatabaseOperation dbo;
	
	@Value("${volume.path}")
	String folderPath;
	
	@GetMapping("/getusers")
	public GetUserResponse getUsers(){
		GetUserResponse gur = new GetUserResponse();
		gur.setData(dbo.getUserInfo());
		gur.setStatus(200);
		return gur;
	}
	
	@GetMapping("/")
	public GetEndpointData getLogin(){
		return new GetEndpointData();
	}
	
	@PostMapping("/login")
	public UserLogin getLogin(@RequestBody UserLogin user) throws SQLException {
		return dbo.getLogin(user);
	}
	
	@PostMapping("/upload")
	public FolderResponse upload(@RequestParam("file") MultipartFile file,@RequestParam("filedata") String fileData) {
		this.startedRequest("Upload Folder");
		try {
			
			byte[] data = file.getBytes();
			JSONObject json = new JSONObject();
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(fileData);
			
			this.printer("Path at which file to be uploaded :: "+ json.get("path").toString());
			this.printer("Name of file :: "+json.get("filename").toString());
			
			if(uploadFileHelper( data,json.get("path").toString(),json.get("filename").toString() )) {
				this.stopRequest("Upload Folder");
				return getFolders(new File(this.getFolderPath()+"/shared"));
			}else {
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.stopRequest("Upload Folder");
		return null;
	}
	
	public void printer(String val) {
		System.out.println(val);
	}
	
	public String printArray(String arr[]) {
		String temp = "";
		for(int i=0;i<arr.length;i++) {
			temp = temp + arr[i];
		}
		return(temp);
	}
	
	public void startedRequest(String val) {
		this.printer("Started :: "+val);
	}
	
	public void stopRequest(String val) {
		this.printer("Request Ended :: "+val);
	}
	
	@PostMapping("/addfolder")
	public FolderResponse addFolder(@RequestBody AddFolderRequest afr) {
		this.startedRequest("Add Folder");
		this.printer("Creating folder :: "+afr.nameOfFolder);
		this.printer("Creating At :: "+this.printArray(afr.getPath()));
		String name = afr.getNameOfFolder();
		String[] path = afr.getPath();
		this.printer("Calling Add folder Helper");
		addFolderHelper(name,path);
		this.printer("Folder path :: "+this.getFolderPath()+"/shared");
		File file = new File(this.getFolderPath()+"/shared");
		this.stopRequest("Add Folder");
		return getFolders(file);
	}
	
	
	@PostMapping("/deletefile")
	public FolderResponse deletefile(@RequestBody AddFolderRequest afr) {
		this.startedRequest("Delete Folder");
		String name = afr.getNameOfFolder();
		String[] path = afr.getPath();
		deleteFileHelper(name,path);
		File file = new File(this.getFolderPath()+"/shared");
		this.stopRequest("Delete Folder");
		return getFolders(file);
	}
	
	
	@PostMapping("/addfile")
	public FolderResponse addFile(@RequestBody AddFolderRequest afr) {
		this.startedRequest("Add File");
		String name = afr.getNameOfFolder();
		this.printer("Adding file + "+name);
		String[] path = afr.getPath();
		this.printer("Adding file at"+this.printArray(path));
		addFileHelper(name,path);
		File file = new File(this.getFolderPath()+"/shared");
		this.stopRequest("Add File");
		return getFolders(file);
	}
	
	
	@GetMapping("/getfolders")
	public FolderResponse getFolders() {
		File file = new File(this.getFolderPath()+"/shared");
		return getFolders(file);
	}
	
	@PostMapping("/download")
	public @ResponseBody byte[] getFile(@RequestBody String path) {
		this.startedRequest("Download File");
		try {
			JSONParser parser = new JSONParser();
			JSONObject js = (JSONObject) parser.parse(path);
			this.printer("Downloading file at :: " +this.getFolderPath()+js.get("path"));
			File f = new File(this.getFolderPath()+js.get("path"));
			InputStream in = new FileInputStream(f);
			this.stopRequest("Download file");
			return org.apache.commons.io.IOUtils.toByteArray(in);
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException npe) {
			npe.printStackTrace();
		}
		this.stopRequest("Download file");
		return null;
	}
	
	@PostMapping("/updateuser")
	public UserTableData updateUser(@RequestBody UserTableData userdata) {
		if(dbo.updateUser(userdata)) {
			return userdata;
		}
		return new UserTableData();
	}
	
	@PostMapping("/adduser")
	public AddUserRequest addUser(@RequestBody AddUserRequest userdata) {
		if(dbo.addUser(userdata)) {
			return userdata;
		}
		return new AddUserRequest();
	}
	
	@GetMapping("/deleteuser")
	public String deleteUser(@RequestParam("username") String uname) {
		dbo.deleteUser(uname); 
		return uname;
	}
	
	private boolean deleteFileHelper(String name,String path[]) {
		this.startedRequest("Delete Folder Helper");
		String absolutePath =this.getFolderPath();
		
	//	for(String s: path) {absolutePath += s +"/"; }
		
		for(int i =0;i<path.length;i++) {
		
			if(i<path.length-1)
				absolutePath += path[i] +"/";
			else
				absolutePath += path[i];
		}
		
		
		this.printer("Deleting file Path :: "+absolutePath);

		File f = new File(absolutePath);
		if(f.delete()) {
			this.printer("Deleted");
			this.stopRequest("Delete Folder Helper");
			return true;
		}else {
			this.printer("Failed in deletion");
			this.stopRequest("Delete FOlder Helper");
			return false;
		}
	}
	
	private boolean addFolderHelper(String name,String path[]) {
		this.startedRequest("Add Folder Helper");
		String absolutePath =this.getFolderPath();
		for(String s: path) {absolutePath += s +"/"; }
		this.printer("Folder will be created at :: "+absolutePath+"/"+name);
		File f = new File(absolutePath+"/"+name);
		if(f.mkdir()) {
			this.printer("Able to create");
			this.stopRequest("Add Folder Helper");
			return true;
		}else {
			this.printer("Not able to create");
			this.stopRequest("Add Folder Helper");
			return false;
		}
	}
	
	private boolean addFileHelper(String name , String path[])  {
		this.startedRequest("Add folder helper");
		String absolutePath =this.getFolderPath();
		for(String s: path) {absolutePath += s +"/"; }
		File f = new File(absolutePath+"/"+name);
		
		try {
			if(f.createNewFile()) {
				this.printer("Adding file success");
				this.stopRequest("Add folder helper");
				return true;
			}else {
				this.printer("Adding file failed");
				this.stopRequest("Add folder helper");
				return false;
			}
		}catch(IOException e) {
			System.out.println(e);
		}
		this.stopRequest("Add folder helper");
		return false;
	}
	
	private String getFolderPath() {
		
		return this.folderPath.replaceAll("\"", "");
	}
	
	private FolderResponse getFolders(File f){
		
		FolderResponse fr = new FolderResponse();
		FolderResponse files[] = new FolderResponse[f.list().length];
		int index = 0;
		
		for(String s : f.list()) {
			File file = new File(f.getAbsolutePath()+"/"+s); 
			
			if(file.isDirectory()) {
				FolderResponse temp = new FolderResponse();
				temp =  getFolders(file);
				temp.setFolder(true);
				files[index++] = temp;
			
			}else {
				FolderResponse temp = new FolderResponse();
				temp.setFolder(false);
				temp.setNameOfFolder(s);
				files[index++] = temp;				
			}
		}
		
		fr.setNameOfFolder(f.getName());
		fr.setFiles(files);
		fr.setFolder(true);
		return fr;
	}

	private boolean uploadFileHelper(byte[] data,String path,String fileName) {
		this.startedRequest("Upload Folder Helper");
		this.printer("Uploading file at ::"+this.getFolderPath()+path+"/"+fileName);
		String absolutePath = this.getFolderPath()+path+"/"+fileName;
		File file =new File(absolutePath);
		try {
			if(file.createNewFile()) {
				try {
					OutputStream os = new FileOutputStream(file);
					os.write(data);
					os.close();
					this.printer("File uploaded successfully");
					this.stopRequest("Upload Folder Helper");
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.printer("File not uploaded");
		this.stopRequest("Upload Folder Helper");
		return false;
	}
}
