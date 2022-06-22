package rs.ac.bg.fon.springsocialnetwork.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import org.springframework.security.core.userdetails.User;

/**
 * @author UrosVesic
 */
@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws KeyStoreException {
        keyStore = KeyStore.getInstance("JKS");
        InputStream resourceAsStream = getClass().getResourceAsStream("/springsm.jks");
        try {
            keyStore.load(resourceAsStream,"uros99".toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new MyRuntimeException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        try {
            return keyStore.getKey("springsm","uros99".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new MyRuntimeException("Exception occured while reading private key from keystore");
        }
    }

    public boolean validateToken(String token){
        Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(token);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springsm").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RuntimeException("Exception while reading public key");
        }

    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(getPublicKey())
                .build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
