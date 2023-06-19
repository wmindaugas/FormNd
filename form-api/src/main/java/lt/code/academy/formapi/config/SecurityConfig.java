package lt.code.academy.formapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, AuthenticationConfiguration authenticationConfiguration, JwtService jwtService) throws Exception {
        security
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/products", "/products/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/users")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                .addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService));

        return security.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return  web -> web.ignoring().requestMatchers(PathRequest.toH2Console())
                .requestMatchers("/swagger-ui/**")
                .requestMatchers("/v3/**");
    }
}