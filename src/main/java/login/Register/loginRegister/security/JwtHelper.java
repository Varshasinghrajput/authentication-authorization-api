//package login.Register.loginRegister.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtHelper {
//
//    private final String secret = "abcdefghijklmnopqrstabcdefghijklmnopqrstabcdefghijkladaeweweweecvfrqwehthfvv";
//    private final long expiration = 3600000; // 1 hour
//
//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
////        return extractExpiration(token).before(new Date());
////    }
////
////    private Date extractExpiration(String token) {
////        return extractClaim(token, Claims::getExpiration);
////    }
////
////    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
////        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
////        return claimsResolver.apply(claims);
////    }
////}
//
//
//
//package login.Register.loginRegister.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Base64;
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtHelper {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);
//
//    // Generate a strong HS512-compatible key (512 bits)
//    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//
//    private final long expiration = 3600000; // 1 hour
//
//    public String generateToken(UserDetails userDetails) {
//        logger.info("Generating JWT token for user: {}", userDetails.getUsername());
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(secretKey, SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        Date expirationDate = extractExpiration(token);
//        return expirationDate != null && expirationDate.before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token.trim().replace("\n", "").replace("\r", ""))
//                    .getBody();
//            return claimsResolver.apply(claims);
//        } catch (Exception e) {
//            logger.error("Error extracting claims from token: {}", e.getMessage());
//            return null; // Return null in case of invalid token
//        }
//    }
//}
//
//


//package login.Register.loginRegister.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Base64;
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtHelper {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);
//
//    // Store a consistent secret key (use an environment variable in production)
//    private static final String BASE64_SECRET_KEY = "abcdefghijklmnopqrstabcdefghijklmnopqrstabcdefghijkladaeweweweecvfrqwehthfvv"; // 64-byte key
//
//    private static final SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(BASE64_SECRET_KEY));
//
//    private final long expiration = 3600000; // 1 hour
//
//    public String generateToken(UserDetails userDetails) {
//        logger.info("Generating JWT token for user: {}", userDetails.getUsername());
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(secretKey, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        Date expirationDate = extractExpiration(token);
//        return expirationDate != null && expirationDate.before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token.trim().replace("\n", "").replace("\r", "")) // Fix spacing issues
//                    .getBody();
//            return claimsResolver.apply(claims);
//        } catch (Exception e) {
//            logger.error("Error extracting claims from token: {}", e.getMessage());
//            return null; // Return null in case of invalid token
//        }
//    }
//}
//


//package login.Register.loginRegister.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Base64;
//import java.util.Date;
//import java.util.function.Function;
//
//@Component
//public class JwtHelper {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);
//
//    @Value("${jwt.secret}")
//    private String base64SecretKey;
//
//    private SecretKey getSecretKey() {
//        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64SecretKey));
//    }
//
//    private final long expiration = 3600000; // 1 hour
//
//    public String generateToken(UserDetails userDetails) {
//        logger.info("Generating JWT token for user: {}", userDetails.getUsername());
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        Date expirationDate = extractExpiration(token);
//        return expirationDate != null && expirationDate.before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(getSecretKey())
//                    .build()
//                    .parseClaimsJws(token.trim().replace("\n", "").replace("\r", ""))
//                    .getBody();
//            return claimsResolver.apply(claims);
//        } catch (Exception e) {
//            logger.error("Error extracting claims from token: {}", e.getMessage());
//            return null;
//        }
//    }
//}
//


package login.Register.loginRegister.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtHelper {

    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    @Value("${jwt.secret}")
    private String base64SecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64SecretKey));
    }

    private final long expiration = 3600000; // 1 hour

    public String generateToken(UserDetails userDetails) {
        logger.info("Generating JWT token for user: {}", userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate != null && expirationDate.before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token.trim())
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            logger.error("Error extracting claims from token: {}", e.getMessage());
            return null;
        }
    }
}


