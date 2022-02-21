package nus.team1.adproject.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nus.team1.adproject.ppmtool.domain.Projectmember;
import nus.team1.adproject.ppmtool.domain.User;
import nus.team1.adproject.ppmtool.services.MapValidationErrorService;
import nus.team1.adproject.ppmtool.services.ProjectMemberService;

@RestController
@RequestMapping("/api/member")
@CrossOrigin
public class MemberController {

	@Autowired
	private ProjectMemberService pmService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/{project_id}/{user_name}")
	public ResponseEntity<?> addMembertoProject(@PathVariable String project_id, @PathVariable String user_name) {

		Projectmember newmember = pmService.addProjectMember(project_id, user_name);
		return new ResponseEntity<Projectmember>(newmember, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		pmService.Register(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@GetMapping("/user/{username}")
	public User findUserByname(@PathVariable String username) {
		return pmService.findUserByusername(username);
	}

	@GetMapping("/{project_id}")
	public Iterable<User> getAllMember(@PathVariable String project_id) {
		return pmService.findMemberByProjectId(project_id);
	}

	@GetMapping("/all")
	public Iterable<User> getAllUsers() {
		return pmService.findAllUsers();
	}


	@PostMapping("/{user_name}/{project_id}/remove")
	public ResponseEntity<?> removeMember(@PathVariable String user_name, @PathVariable String project_id) {
		pmService.RemoveMember(project_id, user_name);
		return new ResponseEntity<String>("Member " + user_name + " was removed successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		pmService.saveUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
}