package by.park.controller.auth;

import by.park.controller.request.AuthRequest;
import by.park.controller.response.AuthResponse;
import by.park.security.util.TokenUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

   private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager, @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }
    @ApiOperation(value = "Login user by username and password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful login user"),
            @ApiResponse(code = 500, message = "Server error, something wrong"),
    })

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<AuthResponse>(
                AuthResponse.builder()
                        .login(authRequest.getUsername())
                        .jwtToken(tokenUtils.generateToken(userDetailsService.loadUserByUsername(authRequest.getUsername())))
                        .build(), HttpStatus.OK);
    }
}
