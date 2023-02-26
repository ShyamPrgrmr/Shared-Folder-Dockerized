package com.sharedfolder;

public class GetEndpointData {
	String listOfEndpoints[] = {
	               "/login",
	               "/",
	               "/downlaod",
	               "/addfolder",
	               "/deletefile",
	               "/addfile",
	               "/getfolders",
	               "/upload",
	               "/getuser",
	               "/updateuser",
	               "/adduser",
	               "/deleteuser"
				  };

	public String[] getData() {
		return listOfEndpoints;
	}

	public void setData(String[] listOfEndpoints) {
		this.listOfEndpoints = listOfEndpoints;
	}
	
	

}
