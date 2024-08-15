package sl.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "secure_storage")
public class FileUploadDownload {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "filename", columnDefinition = "TEXT")
    private String filename;
    
    @Column(name = "ownerid", columnDefinition = "TEXT")
    private String ownerid;
    
    @Column(name = "passkey", columnDefinition = "TEXT")
    private String passkey;

	@Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;
    
    public FileUploadDownload() {
        super();
    }
    
    public FileUploadDownload(String filename, String ownerid, String passkey, byte[] content) {
		super();
		this.filename = filename;
		this.ownerid = ownerid;
		this.passkey = passkey;
		this.content = content;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return filename;
    }

    public void setName(String filename) {
        this.filename = filename;
    }
    
    public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

    public String getPasskey() {
		return passkey;
	}

	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}

	public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
}
