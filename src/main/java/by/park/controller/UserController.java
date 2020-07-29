package by.park.controller;

import by.park.controller.request.UpdateUserRequest;
import by.park.domain.User;
import by.park.exeption.ResourceNotFoundException;
import by.park.service.UserService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiImplicitParam;
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
            @ApiResponse(code = 200, message = "Users loaded successfully"),
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
            @ApiResponse(code = 200, message = "User found successfully by id"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<User> findById(@PathVariable("id") Long id) {
        User user = Optional.ofNullable(userService.findUserById(id)).orElseThrow(ResourceNotFoundException::new);
        return Optional.of(user);
    }

    @GetMapping("/find-by-login")
    @ApiOperation(value = "Finding user by login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found successfully by login"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<User> findByLogin(@RequestParam("login") String login) {
        User user = Optional.ofNullable(userService.findUserByLogin(login)).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return Optional.of(user);
    }

    @GetMapping("/info")
    @ApiOperation(value = "Information about User")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Information loaded successfully"),
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<User> informationAboutUser(Principal principal) {
        return Optional.ofNullable(userService.userInformation(principal));
    }

    @PutMapping
    @ApiOperation(value = "Updating user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User has been successfully updated"),
            @ApiResponse(code = 201, message = "User has been successfully updated")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public Optional<User> update(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return Optional.ofNullable(userService.updateUser(updateUserRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User was deleted successfully"),
            @ApiResponse(code = 204, message = "User was deleted successfully")
    })
    @ApiImplicitParam(name = "X-Auth_Token", required = true, dataType = "string", paramType = "header", value = "token")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}