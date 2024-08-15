package sl.project.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sl.project.models.Login;
import sl.project.models.LoginSignup;
import sl.project.services.LoginSignupService;

@CrossOrigin
@RestController
@RequestMapping(path= "/portfolio")
public class LoginSignupController {
	
	@Autowired
	private LoginSignupService loginSignupService;
	
	@CrossOrigin
	@PostMapping(path = "/register", consumes = {"application/json"})
	public ResponseEntity<LoginSignup> doSignup(@RequestBody LoginSignup portfolio ) {
		LoginSignup loginSignup = loginSignupService.savelogin(portfolio);
		
		 if(loginSignup != null) {
	         return new ResponseEntity<>(loginSignup, HttpStatus.OK);
	     } else {
	         return new ResponseEntity<>(null, HttpStatus.OK);
	     } 
	}	

	@CrossOrigin
	@PostMapping(path = "/login")
	public ResponseEntity<LoginSignup> doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		LoginSignup ls = loginSignupService.loginAndGetUid(username, password);
		if(ls == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	    return new ResponseEntity<>(ls, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(path = "/logout")
	public ResponseEntity<String> doLogin(@RequestBody String userid) {
		
		String status = loginSignupService.logoutUser(userid);

	    return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
