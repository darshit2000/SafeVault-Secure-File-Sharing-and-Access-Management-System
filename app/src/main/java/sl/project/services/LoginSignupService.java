package sl.project.services;

import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sl.project.models.LoginSignup;
import sl.project.repository.LoginSignupRepository;

@Service
public class LoginSignupService implements ILoginSignupService {
	
	@Autowired
	private LoginSignupRepository loginsignuprepository;
	
	@Autowired
	private SessionService sessionService;
	
	@Override
	public LoginSignup savelogin( LoginSignup portfolio) {
		try {		    
		    
		    LoginSignup ls = loginsignuprepository.findByUsername(portfolio.getUsername());
		    
		    if(ls != null) {
		        return null;
		    } else {
		    	String pass = portfolio.getPassword();
		    	portfolio.setPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));
		        LoginSignup savedlogin = loginsignuprepository.save(portfolio);
                if(savedlogin != null) {
                    return savedlogin;
                }
                else {
                    return null;
                }
		    }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;

	}

	@Override
	public LoginSignup loginAndGetUid(String username, String password) {
		LoginSignup loginSignup = loginsignuprepository.findByUsername(username);
		
		if(loginSignup != null) {
			String hashedPassword = loginSignup.getPassword();
			if(BCrypt.checkpw(password, hashedPassword)) {
				System.out.println("Login successfull");
				sessionService.saveSession(loginSignup.getId());
				return loginSignup;
			} else {
				System.out.println("Pass not match");
				return null;
			}
		} else {
			System.out.println("Usernaem not found");
			return null;
		}
	}

	@Transactional
	public String logoutUser(String userid) {
		sessionService.deleteSession(userid);
		return "User Deleted";
	}
}
