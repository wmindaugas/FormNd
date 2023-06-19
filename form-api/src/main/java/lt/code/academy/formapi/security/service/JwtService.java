package lt.code.academy.formapi.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lt.code.academy.formapi.security.exception.ExpiredTokenException;
import lt.code.academy.formapi.security.exception.InvalidTokenException;
import lt.code.academy.formapi.user.dto.Role;
import lt.code.academy.formapi.user.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;


@Service
public class JwtService {

    private final byte[] secretKey;
    private final long tokenValidMs;

    public JwtService(@Value("${security.jwt.secret.key}") byte[] secretKey,
                      @Value("#{${security.jwt.token.valid.min} * 60000}") long tokenValidMs) {
        this.secretKey = secretKey;
        this.tokenValidMs = tokenValidMs;
    }

    public String generateToken(User user) {
        Date date = new Date();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setAudience("eshop-ui")
                .setIssuer("eshop-api")
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenValidMs))
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getAuthority).toList())
                .signWith(Keys.hmacShaKeyFor(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication parseToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();

            Jws<Claims> headerClaimsJwt = jwtParser.parseClaimsJws(token);
            Claims body = headerClaimsJwt.getBody();

            validateToken(body);

            String userName = body.getSubject();
            List<SimpleGrantedAuthority> roles = ((List<String>) body.get("roles")).stream().map(SimpleGrantedAuthority::new).toList();

            return new UsernamePasswordAuthenticationToken(userName, null, roles);

        } catch(ExpiredTokenException e) {
            throw e;
        } catch(Exception e) {
            throw new InvalidTokenException();
        }
    }

    private void validateToken(Claims claims) {
        Date expirationDate = claims.getExpiration();

        if(expirationDate.before(new Date())) {
            throw new ExpiredTokenException();
        }
    }

    private String generateSecretKey() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Encoders.BASE64.encode(secretKey.getEncoded());
    }
}
