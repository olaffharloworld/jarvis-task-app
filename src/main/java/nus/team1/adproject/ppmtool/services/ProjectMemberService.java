package nus.team1.adproject.ppmtool.services;

import nus.team1.adproject.ppmtool.domain.Project;
import nus.team1.adproject.ppmtool.domain.Projectmember;
import nus.team1.adproject.ppmtool.domain.User;
import nus.team1.adproject.ppmtool.exceptions.ProjectNotFoundException;
import nus.team1.adproject.ppmtool.exceptions.UserNotFoundException;
import nus.team1.adproject.ppmtool.repositories.ProjectMemberRepository;
import nus.team1.adproject.ppmtool.repositories.ProjectRepository;
import nus.team1.adproject.ppmtool.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectMemberRepository pmRepository;

	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findUserByusername(String name) {
		return userRepository.findUserByuserName(name);
	}

	public void saveUser(User user) {

		userRepository.save(user);
	}

	public Projectmember addProjectMember(String projectIdentifier, String username) {

		User user = userRepository.findUserByuserName(username);
		if (user == null) {
			throw new UserNotFoundException("User: '" + username + "' does not existed");
		}
		Projectmember pm = pmRepository.findProjectmemberByPIdentifierUName(projectIdentifier, username);
		if (pm != null) {
			throw new UserNotFoundException("User: '" + username + "' is already in Project: " + projectIdentifier);
		}
		Projectmember pm2 = new Projectmember(projectIdentifier);
		pm2.setUserName(username);
		return pmRepository.save(pm2);
	}

	public Iterable<User> findMemberByProjectId(String id) {

		List<Projectmember> members = pmRepository.findProjectMemberByProjectIdentifier(id);
		List<User> users = new ArrayList<User>();
		for (Projectmember pm : members) {
			String userName = pm.getUserName();
			User user = userRepository.findUserByuserName(userName);
			users.add(user);
		}
		return users;
	}
	
	public void Register(User nuser) {
		User newuser=nuser;
		String username=newuser.getUserName();
		User uservalidate=userRepository.findUserByuserName(username);
		if(uservalidate!=null) {
			throw new UserNotFoundException("Username: '" + username + "' is already existed, please choose another one");
		}
		userRepository.save(newuser);
	}
	

	public User updateByProjectIdName(User updatedmember, String project_id, String user_name) {

		User member = userRepository.findUserByuserName(user_name);

		member = updatedmember;

		return userRepository.save(member);
	}


	public void RemoveMember(String project_id, String user_name) {

		Projectmember pm = pmRepository.findProjectmemberByPIdentifierUName(project_id, user_name);
		pmRepository.delete(pm);
	}
}