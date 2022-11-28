package edu.jong.board.role.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;

@FeignClient(name = "role-service")
public interface RoleFeignClient {

	@PostMapping(value = "/roles", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RoleDetails> addRole(
			@RequestBody RoleAddParam param);

	@PutMapping(value = "/roles/{roleNo}", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<RoleDetails> modifyRole(
			@PathVariable long roleNo,
			@RequestBody RoleModifyParam param);

	@DeleteMapping(value = "/roles/{roleNo}")
	ResponseEntity<Void> removeRole(
			@PathVariable long roleNo);

	@GetMapping(value = "/roles/{roleNo}")
	ResponseEntity<RoleDetails> getRole(
			@PathVariable long roleNo);

	@GetMapping(value = "/roles")
	ResponseEntity<List<RoleDetails>> searchRoles(
			RoleSearchCond cond);

}
