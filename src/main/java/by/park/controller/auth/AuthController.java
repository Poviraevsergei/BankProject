package by.park.controller.auth;

import by.park.controller.request.AuthRequest;
import by.park.controller.request.CreateUserRequest;
import by.park.controller.response.AuthResponse;
import by.park.domain.User;
import by.park.security.util.TokenUtils;
import by.park.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(UserService userService, TokenUtils tokenUtils, AuthenticationManager authenticationManager, @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @ApiOperation(value = "Login user by username and password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful login user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getLogin(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<>(
                AuthResponse.builder()
                        .login(authRequest.getLogin())
                        .jwtToken(tokenUtils.generateToken(userDetailsService.loadUserByUsername(authRequest.getLogin())))
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "User registration")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful user registration"),
            @ApiResponse(code = 201, message = "Successful user registration"),
            @ApiResponse(code = 401, message = "Don't have authorization"),
            @ApiResponse(code = 403, message = "Don't have authority"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public User register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.register(createUserRequest);
    }
}