package services

import exceptions.NotAllowedException
import exceptions.UnAuthorizedException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import repositories.TokenRepository
import utils.JWT_SIGNATURE_KEY
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class JWTService @Inject constructor(
    @Named(JWT_SIGNATURE_KEY) private val key: String,
    private val tokenRepository: TokenRepository
) {
    fun createToken(userId: String): String {
        val expiryTime = 60 * 60 * 1000 // 1h
        val currentTime = System.currentTimeMillis()
        val token =  Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date(currentTime))
            .setExpiration(Date(currentTime + expiryTime))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()
        tokenRepository.setRateLimit(token,100)
        return token
    }

    fun parseTokenAndGetUser(token: String): String{
        try {
            val claims = Jwts.parser().setSigningKey(key).parseClaimsJwt(token).body
            val expTime = claims.expiration.time
            if (expTime > System.currentTimeMillis()) {
                tokenRepository.deleteToken(token)
                throw UnAuthorizedException("Token has expired")
            }
            updateRateLimit(token)
            return claims.subject
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw UnAuthorizedException(e.message)
        }
    }

    fun updateRateLimit(token: String){
        var count = tokenRepository.getRateLimit(token)
        if(count == 0){
            throw NotAllowedException("Rate Limit Exceeded")
        }
        count--
        tokenRepository.setRateLimit(token, count)
    }

    fun deleteToken(token: String) {
        tokenRepository.deleteToken(token)
    }
}