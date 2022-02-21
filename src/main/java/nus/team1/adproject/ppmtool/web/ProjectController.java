package nus.team1.adproject.ppmtool.web;

import nus.team1.adproject.ppmtool.domain.Project;
import nus.team1.adproject.ppmtool.services.MapValidationErrorService;
import nus.team1.adproject.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/create/{username}")
	public ResponseEntity<?> createNewProject(@PathVariable String username, @Valid @RequestBody Project project,
			BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Project project1 = projectService.saveOrUpdateProject(project, username);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@PostMapping("")
	public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Project project1 = projectService.UpdateProject(project);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

		Project project = projectService.findProjectByIdentifier(projectId);
		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}

	@GetMapping("/{username}/all")
	public Iterable<Project> getAllProjects(@PathVariable String username) {
		return projectService.findAllProjects(username);

	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
		projectService.deleteProjectByIdentifier(projectId);
		return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);

	}
}