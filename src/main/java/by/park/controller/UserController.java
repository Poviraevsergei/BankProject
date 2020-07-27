package by.park.controller;

import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.service.UserService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@ApiResponses({
        @ApiResponse(code = 401, message = "Don't have authorization"),
        @ApiResponse(code = 403, message = "Don't have authority"),
        @ApiResponse(code = 404, message = "Resource not found")
})
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
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token"),
            @ApiImplicitParam(name = "page", value = "Page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page", example = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort", example = "id", dataType = "string", paramType = "query")
    })
    public Page<User> findAll(@ApiIgnore Pageable pageable) {
        return userService.findAllUsers(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finding user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public User findById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<User> informationAboutUser(Principal principal) {
        return userService.userInformation(principal);
    }

    @GetMapping("/find-by-login")
    @ApiOperation(value = "Finding user by login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user loading"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public User findByLogin(@RequestParam("login") String login) {
        return userService.findUserByLogin(login);
    }

    @PutMapping
    @ApiOperation(value = "Updating user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user updating"),
            @ApiResponse(code = 201, message = "Successful user updating"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public User update(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User was deleted"),
            @ApiResponse(code = 204, message = "User was deleted"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}