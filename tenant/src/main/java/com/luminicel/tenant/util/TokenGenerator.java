package com.luminicel.tenant.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TokenGenerator {
	private static final String SALT = "LgS9Ec8rqfqeJJKY7NhwozVkLxDkkN7gJSWpQEi3s5fxnYzxJe";

	private final transient String secret;
	private final String subject;
	private final Map<String, Object> claims;
	private final ZonedDateTime expiration;

	public static final class Builder {

		private String secret;
		private String subject;
		private Map<String, Object> claims = new HashMap<>();
		private ZonedDateTime expiration;

		private Builder() {
		}

		public Builder secret(final String secret) {
			this.secret = secret;
			return this;
		}

		public Builder subject(final String subject) {
			this.subject = subject;
			return this;
		}

		public Builder claims(final Map<String, Object> claims) {
			this.claims = claims;
			return this;
		}

		public Builder expiration(final ZonedDateTime expiration) {
			this.expiration = expiration;
			return this;
		}

		public TokenGenerator build() {
			return new TokenGenerator(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	private TokenGenerator(final Builder builder) {
		secret = builder.secret;
		subject = builder.subject;
		claims = builder.claims;
		expiration = builder.expiration;
	}

	public String generateSignedToken() {
		final JwtBuilder builder = getBuilder();
		final byte[] bytes = getSaltedSecret();
		final SecretKey secretKey = Keys.hmacShaKeyFor(bytes);
		builder.signWith(secretKey, SignatureAlgorithm.HS256);
		return builder.compact();
	}

	public String generateUnsignedToken() {
		final JwtBuilder builder = getBuilder();
		return builder.compact();
	}

	private JwtBuilder getBuilder() {
		final Date expirationAsDate = getExpiration();
		final JwtBuilder builder = Jwts.builder()
				.setSubject(subject)
				.setExpiration(expirationAsDate);
		claims.forEach(builder::claim);
		return builder;
	}

	private byte[] getSaltedSecret() {
		final String saltedSecret = SALT + secret;
		return saltedSecret.getBytes(StandardCharsets.UTF_8);
	}

	private Date getExpiration() {
		final Instant instant = expiration.toInstant();
		return Date.from(instant);
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Claims extractAllClaims(String token) {
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(getSaltedSecret())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String getEmail(String token){
		Claims claims1 = extractAllClaims(token);
		return (String) claims1.get("email");
	}


}