package sl.project.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="access")
public class AccessManagement {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

	@Column(name = "requesteduserid", columnDefinition = "TEXT")
	private String requesteduserid;
	
	@Column(name = "requesteduser", columnDefinition = "TEXT")
	private String requesteduser;
	
	@Column(name = "fileid", columnDefinition = "TEXT")
	private String fileid;
	
	@Column(name = "filename", columnDefinition = "TEXT")
	private String filename;
	
	@Column(name = "ownerid", columnDefinition = "TEXT")
	private String ownerid;
	
	@Column(name = "ownername", columnDefinition = "TEXT")
	private String ownername;
	
	@Column(name = "passkey", columnDefinition = "TEXT")
	private String passkey;
	
	@Column(name = "granted", columnDefinition = "TEXT")
	private String granted;
	
	public AccessManagement() {
		super();
	}

	public AccessManagement(String requesteduserid, String requesteduser, String fileid, String filename,
			String ownerid, String ownername, String passkey, String granted) {
		super();
		this.requesteduserid = requesteduserid;
		this.requesteduser = requesteduser;
		this.fileid = fileid;
		this.filename = filename;
		this.ownerid = ownerid;
		this.ownername = ownername;
		this.passkey = passkey;
		this.granted = granted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequesteduserid() {
		return requesteduserid;
	}

	public void setRequesteduserid(String requesteduserid) {
		this.requesteduserid = requesteduserid;
	}

	public String getRequesteduser() {
		return requesteduser;
	}

	public void setRequesteduser(String requesteduser) {
		this.requesteduser = requesteduser;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getPasskey() {
		return passkey;
	}

	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}

	public String getGranted() {
		return granted;
	}

	public void setGranted(String granted) {
		this.granted = granted;
	}

	@Override
	public String toString() {
		return "AccessManagement [id=" + id + ", requesteduserid=" + requesteduserid + ", requesteduser="
				+ requesteduser + ", fileid=" + fileid + ", filename=" + filename + ", ownerid=" + ownerid
				+ ", ownername=" + ownername + ", passkey=" + passkey + ", granted=" + granted + "]";
	}
	
	
}
