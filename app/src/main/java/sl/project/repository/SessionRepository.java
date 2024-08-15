package sl.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sl.project.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
	
	void deleteByUserid(String userid);

	Session findByUserid(String userid);
	
}
