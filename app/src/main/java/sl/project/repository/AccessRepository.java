package sl.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sl.project.models.AccessManagement;

@Repository
public interface AccessRepository extends JpaRepository<AccessManagement, String> {
    
	List<AccessManagement> findAllByRequesteduserid(String requesteduserid);
	List<AccessManagement> findAllByOwnerid(String ownerid);
	
	AccessManagement findAllByFilenameAndRequesteduserid(String filename, String requesteduserid);
}
