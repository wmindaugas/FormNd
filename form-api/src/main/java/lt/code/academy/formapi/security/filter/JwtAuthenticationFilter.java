package lt.code.academy.formapi.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lt.code.academy.formapi.security.data.Login;
import lt.code.academy.formapi.security.service.JwtService;
import lt.code.academy.formapi.user.dto.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;


// kvieciamas tik tada kai bus siunciamas (post i /login)
    public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private final ObjectMapper mapper;
        private final JwtService jwtService;

        public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
            super(authenticationManager);

            this.jwtService = jwtService;
            mapper = new ObjectMapper();
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
            try {
                Login login = mapper.readValue(request.getReader(), Login.class);
                Authentication authentication = new UsernamePasswordAuthenticationToken(login.username(), login.password());

                return getAuthenticationManager().authenticate(authentication);
            } catch(IOException e) {
                throw new BadCredentialsException("Incorrect user credentials ", e);
            }
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
                throws IOException, ServletException {
            SecurityContextHolder.getContext().setAuthentication(authResult);

            String token = jwtService.generateToken((User) authResult.getPrincipal());
            response.addHeader("Authorization", token);

            chain.doFilter(request, response);
        }
    }

