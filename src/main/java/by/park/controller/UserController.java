package by.park.controller;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Finding all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading users"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token",required = true,dataType="string",paramType = "header",value = "token")
    public List<User> findAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token",required = true,dataType="string",paramType = "header",value = "token")
    public User findById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token",required = true,dataType="string",paramType = "header",value = "token")
    public User informationAboutUser(Principal principal){
        return userService.userInformation(principal);
    }

    @GetMapping("/find-by-login")
    @ApiOperation(value = "Finding user by login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token",required = true,dataType="string",paramType = "header",value = "token")
    public User findByLogin(@RequestParam("login") String login) {
        return userService.findUserByLogin(login);
    }

    @PutMapping
    @ApiOperation(value = "Updating user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user updating"),
            @ApiResponse(code = 201, message = "Successful user updating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @ApiImplicitParam(name = "X-Auth_Token",required = true,dataType="string",paramType = "header",value = "token")
    public User update(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User was deleted"),
            @ApiResponse(code = 204, message = "User was deleted"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    @ApiImplicitParam(name = "X-Auth_Token",required = true,dataType="string",paramType = "header",value = "token")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}