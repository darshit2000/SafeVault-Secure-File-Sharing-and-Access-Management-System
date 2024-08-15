package sl.project.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sl.project.models.Session;
import sl.project.repository.SessionRepository;

@Service
public class SessionService implements ISessionService {
	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public Session findSession(String uid) {
		Session s = sessionRepository.findByUserid(uid);
		return s;
	}

	@Override
	public void saveSession(String uid) {
		Session s = new Session(uid);
		sessionRepository.save(s);
	}

	@Transactional
	@Override
	public void deleteSession(String uid) {
		sessionRepository.deleteByUserid(uid);
	}
	
}
