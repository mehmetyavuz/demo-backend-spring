package com.my.demoApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;

@Service
public class JwtProvider {

    private Key key;
//    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        try {
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("demoapp.jks");
//            keyStore.load(resourceAsStream, "demoapp".toCharArray());
//        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
//            throw new DemoAppException("Exception occurred while loading keystore");
//        }
    }

    public String generateToken(Authentication authentication) {
        User principle = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principle.getUsername())
                .signWith(key)
//                .signWith(getPrivateKey())
                .compact();
    }

//    private PrivateKey getPrivateKey() {
//        try {
//            return (PrivateKey) keyStore.getKey("demoapp", "demoapp".toCharArray());
//        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
//            throw new DemoAppException("Exception occurred while retrieving private key from keystore");
//        }
//    }

    public boolean validateToke(String jwt) {
        Jwts.parserBuilder()
                .setSigningKey(key)
//                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(jwt);
        return true;
    }

//    private PublicKey getPublicKey() {
//        try {
//            return keyStore.getCertificate("demoapp").getPublicKey();
//        } catch (KeyStoreException e) {
//            throw new DemoAppException("Exception occurred while retrieving public key from keystore");
//        }
//    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
//                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
