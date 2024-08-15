package sl.project.services;

import sl.project.models.Session;

public interface ISessionService {

	public void saveSession(String uid);
	public Session findSession(String uid);
	public void deleteSession(String uid);

}
