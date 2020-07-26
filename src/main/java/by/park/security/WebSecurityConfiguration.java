package by.park.security;

import by.park.security.filter.AuthenticationTokenFilter;
import by.park.security.util.TokenUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private TokenUtils tokenUtils;
    private UserDetailsService userDetailsService;

    public WebSecurityConfiguration(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService, TokenUtils tokenUtils) {
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean(AuthenticationManager authenticationManager) {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter(tokenUtils, userDetailsService);
        authenticationTokenFilter.setAuthenticationManager(authenticationManager);
        return authenticationTokenFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/configuration/ui/", "/swagger-resources/", "/configuration/security/", "/swagger-ui.html", "/webjars/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/users/info").hasAnyRole("USER", "ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/banks/info").hasAnyRole("ADMIN", "USER")
                .antMatchers("/banks/**").hasRole("ADMIN")
                .antMatchers("/cards/info").hasAnyRole("ADMIN", "USER")
                .antMatchers("/cards/**").hasRole("ADMIN")
                .antMatchers("/bankAccounts/info").hasAnyRole("ADMIN", "USER")
                .antMatchers("/bankAccounts/create").hasAnyRole("ADMIN", "USER")
                .antMatchers("/bankAccounts/**").hasRole("ADMIN")

                .antMatchers("/transactions/info").hasAnyRole("ADMIN", "USER")
                .antMatchers("/transactions/paying").hasAnyRole("ADMIN", "USER")
                .antMatchers("/transactions/transfer").hasAnyRole("ADMIN", "USER")
                .antMatchers("/transactions/info").hasAnyRole("ADMIN", "USER")
                .antMatchers("/transactions/**").hasRole("ADMIN")

                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }
}
