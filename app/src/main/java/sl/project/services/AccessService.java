package sl.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import sl.project.models.AccessManagement;
import sl.project.models.FileUploadDownload;
import sl.project.models.LoginSignup;
import sl.project.repository.AccessRepository;
import sl.project.repository.FileUploadDownloadRepository;
import sl.project.repository.LoginSignupRepository;

@Service
public class AccessService implements IAccessService {
	
	@Autowired
	private AccessRepository accessrepository;
	
	@Autowired
	private LoginSignupRepository loginSignuprepository;
	
	@Autowired
	private FileUploadDownloadService fileUploadDownloadService;
	
	@Autowired
	private FileUploadDownloadRepository fileUploadDownloadrepository;
	
	@Autowired
    private Environment env;

	@Override
	public String askAccess(String requesteduserid, String filename) {
		Optional<LoginSignup> ls_op1 = loginSignuprepository.findById(requesteduserid);
		LoginSignup ls1 = ls_op1.get();
		
		FileUploadDownload fd = fileUploadDownloadrepository.findByFilename(filename);
		if(fd == null) {
			return "No such file";
		}
		
		Optional<LoginSignup> ls_op2 = loginSignuprepository.findById(fd.getOwnerid());
		LoginSignup ls2 = ls_op2.get();
		
		AccessManagement am = new AccessManagement(requesteduserid, ls1.getUsername(), fd.getId(), filename, fd.getOwnerid(), ls2.getUsername() ,fd.getPasskey(), "PENDING");
		accessrepository.save(am);
		
		return "Asked for access";
	}

	@Override
	public ArrayList<AccessManagement> checkForNewRequests(String ownerid) {
		List<AccessManagement> am_list = new ArrayList<>();
		am_list = accessrepository.findAllByOwnerid(ownerid);
		return (ArrayList<AccessManagement>) am_list;
	}

	@Override
	public Boolean editAccess(String filename, String requesteduserid, String grant) {
		AccessManagement am = accessrepository.findAllByFilenameAndRequesteduserid(filename, requesteduserid);
		if(am == null) {
			System.out.println(am);
			return false;
		}
		accessrepository.delete(am);
		
		am.setGranted(grant);
		accessrepository.save(am);
		return true;
	}
	
	@Override
	public ArrayList<AccessManagement> checkIfAccessGot(String requesteduserid) {
		List<AccessManagement> am_lst = new ArrayList<AccessManagement>();
		am_lst = accessrepository.findAllByRequesteduserid(requesteduserid);
		
		List<AccessManagement> res = new ArrayList<AccessManagement>();
		
		for(AccessManagement am : am_lst) {
			if(am.getGranted().equals("YES")) {
				String rawPasskey = fileUploadDownloadService.decryptData(am.getPasskey(), env.getProperty("initkey.pass"));
				am.setPasskey(rawPasskey);
				res.add(am);
				accessrepository.deleteById(am.getId());
			} else if(am.getGranted().equals("NO")) {
				am.setPasskey("Access Denied");
				res.add(am);
				accessrepository.deleteById(am.getId());
			}
		}
		return (ArrayList<AccessManagement>) res;
	}

	
	
}
