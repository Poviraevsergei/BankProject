package by.park.controller;

import by.park.controller.request.CreateUserRequest;
import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(@Qualifier("userService") UserService userService) {
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
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user loading"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
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
    public User save(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUserName(createUserRequest.getUserName());
        user.setSurname(createUserRequest.getSurname());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setLogin(createUserRequest.getLogin());
        user.setPassword(createUserRequest.getPassword());
        user.setPassportNumber(createUserRequest.getPassportNumber());
        return userService.save(user);
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
        User user = new User();
        user.setId(updateUserRequest.getId());
        user.setUserName(updateUserRequest.getUserName());
        user.setSurname(updateUserRequest.getSurname());
        user.setBirthDate(updateUserRequest.getBirthDate());
        user.setLogin(updateUserRequest.getLogin());
        user.setPassword(updateUserRequest.getPassword());
        user.setPassportNumber(updateUserRequest.getPassportNumber());
        user.setDeleted(updateUserRequest.getDeleted());
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user deleting"),
            @ApiResponse(code = 204, message = "Successful user deleting"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
    })
    public User delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }
}