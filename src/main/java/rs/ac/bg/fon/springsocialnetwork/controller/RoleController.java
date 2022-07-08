package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.springsocialnetwork.model.Role;
import rs.ac.bg.fon.springsocialnetwork.service.RoleService;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @PostMapping
    public ResponseEntity addRole(@RequestBody Role role){
        roleService.addRole(role);
        return new ResponseEntity(HttpStatus.OK);
    }
}
