package by.park.controller;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

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
    public Optional<User> findById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/find-by-username")
    @ApiOperation(value = "Finding user by username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public User findByUsername(@RequestParam("username") String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping
    @ApiOperation(value = "Saving user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user creating"),
            @ApiResponse(code = 201, message = "Successful user creating"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public User register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.register(createUserRequest);
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
    public User update(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user deleting"),
            @ApiResponse(code = 204, message = "Successful user deleting"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}