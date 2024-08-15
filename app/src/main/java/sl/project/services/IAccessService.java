package sl.project.services;

import java.util.ArrayList;

import sl.project.models.AccessManagement;

public interface IAccessService {

	public String askAccess(String requesteduserid, String filename);
	
	public ArrayList<AccessManagement> checkForNewRequests(String ownerid);

	public Boolean editAccess(String filename, String requesteduserid, String grant);
		
	public ArrayList<AccessManagement> checkIfAccessGot(String requesteduserid);
}
