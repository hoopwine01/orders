package com.anz.orders.security;

public class JwtGenerator {

    public static void main (String[] args) {
        String token = JwtUtil.generateToken("postman-client", "password123");
        System.out.println("Generated JWT Token: " + token);
    }

}
