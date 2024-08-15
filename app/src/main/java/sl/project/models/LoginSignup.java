package sl.project.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="portfolio")
public class LoginSignup {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

	@Column(name = "name", columnDefinition = "TEXT")
	private String name;
	
	@Column(name = "email", columnDefinition = "TEXT")
	private String email;
	
	@Column(name = "username", columnDefinition = "TEXT")
	private String username;
	
	@Column(name = "password", columnDefinition = "TEXT")
	private String password;
	
	@Column(name = "contact", columnDefinition = "TEXT")
	private String contact;

	public LoginSignup(String name, String email, String username, String password, String contact) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.contact = contact;
	}

	public LoginSignup() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "LoginSignup [id=" + id + ", name=" + name + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", contact=" + contact + "]";
	}

		
}
