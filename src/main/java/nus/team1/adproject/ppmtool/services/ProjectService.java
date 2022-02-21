package nus.team1.adproject.ppmtool.services;

import nus.team1.adproject.ppmtool.domain.Backlog;
import nus.team1.adproject.ppmtool.domain.Project;
import nus.team1.adproject.ppmtool.domain.Projectmember;
import nus.team1.adproject.ppmtool.exceptions.ProjectIdException;
import nus.team1.adproject.ppmtool.repositories.BacklogRepository;
import nus.team1.adproject.ppmtool.repositories.ProjectMemberRepository;
import nus.team1.adproject.ppmtool.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectMemberRepository pmRepository;

	public Project UpdateProject(Project project) {
		String identifier = project.getProjectIdentifier().toUpperCase();
		try {
			project.setProjectIdentifier(identifier);
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}

			if (project.getId() != null) {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);

		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + identifier + "' already exists");
		}
	}

	public Project saveOrUpdateProject(Project project, String username) {
		String identifier = project.getProjectIdentifier().toUpperCase();
		try {
			project.setProjectIdentifier(identifier);
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
				Projectmember pm = new Projectmember(identifier);
				pm.setUserName(username);
				pmRepository.save(pm);
			}

			if (project.getId() != null) {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);

		} catch (Exception e) {
			throw new ProjectIdException("Project ID '" + identifier + "' already exists");
		}
	}

	public Project findProjectByIdentifier(String projectId) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID does not exist");
		}
		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
//		return projectRepository.findAll();
		List<Projectmember> members = pmRepository.findProjectMemberByuserName(username);
		List<Project> projects = new ArrayList<Project>();
		for (Projectmember member : members) {
			String pid = member.getProjectIdentifier();
			Project project = projectRepository.findByProjectIdentifier(pid);
			projects.add(project);
		}
		return projects;
	}

	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException(
					"Cannot delete Project with ID '" + projectId + "'. This project does not exist.");
		}
		String projectIdentifier = project.getProjectIdentifier();
		List<Projectmember> members = pmRepository.findProjectMemberByProjectIdentifier(projectIdentifier);
		for (Projectmember member : members) {
			pmRepository.delete(member);
		}
		projectRepository.delete(project);

	}
}