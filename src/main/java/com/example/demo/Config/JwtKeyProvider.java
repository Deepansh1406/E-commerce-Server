package com.example.demo.Config;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtKeyProvider {
    public static final SecretKey SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
}
