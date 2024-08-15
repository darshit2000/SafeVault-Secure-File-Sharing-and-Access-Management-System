package sl.project.controllers;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sl.project.models.AccessManagement;
import sl.project.services.AccessService;

@CrossOrigin
@RestController
@RequestMapping(path= "/access")
public class AccessController {
	
	@Autowired
	private AccessService accessService;
	
	@CrossOrigin
	@PostMapping(path = "/ask")
	public ResponseEntity<String> askForPermission(@RequestParam("requesteduserid") String requesteduserid, @RequestParam("filename") String filename) {
		String res = accessService.askAccess(requesteduserid, filename);
		
		return new ResponseEntity<>(res, HttpStatus.OK); 
	}	

	@CrossOrigin
	@PostMapping(path = "/check")
	public ResponseEntity<ArrayList<AccessManagement>> checkForNewReq(@RequestParam("uid") String uid) {
		
		ArrayList<AccessManagement> my_list1 = new ArrayList<>();
		my_list1 = accessService.checkForNewRequests(uid);
		
		ArrayList<AccessManagement> my_list2 = new ArrayList<>();
		my_list2 = accessService.checkIfAccessGot(uid);
		
		ArrayList<AccessManagement> my_list3 = new ArrayList<AccessManagement>();
		for(AccessManagement am : my_list1) {
			my_list3.add(am);
		}
		for(AccessManagement am : my_list2) {
			my_list3.add(am);
		}

	    return new ResponseEntity<>(my_list3, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(path = "/editaccess")
	public ResponseEntity<Boolean> editPermissions(@RequestParam("filename") String filename, @RequestParam("requesteduserid") String requesteduserid, @RequestParam("grant") String grant) {
		
		Boolean res = accessService.editAccess(filename, requesteduserid, grant);

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
