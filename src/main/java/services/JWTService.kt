package services

import exceptions.NotAllowedException
import exceptions.UnAuthorizedException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import models.Session
import repositories.TokenRepository
import utils.EXPIRY_TIME
import utils.JWT_SIGNATURE_KEY
import utils.RATE_LIMIT
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class JWTService @Inject constructor(
    @Named(JWT_SIGNATURE_KEY) private val key: String,
    @Named(EXPIRY_TIME) private val expiryTime: Long,
    @Named(RATE_LIMIT) private val rateLimit: Long,
    private val tokenRepository: TokenRepository
) {
    fun createToken(userId: String): String {
        ensureExistingSession(userId)
        val currentTime = System.currentTimeMillis()
        val token =  Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date(currentTime))
            .setExpiration(Date(currentTime + expiryTime))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        storeSession(token, userId)
        return token
    }

    private fun ensureExistingSession(userId: String) {
        try {
            val session = tokenRepository.getSession(userId)
        }catch (e: Exception){
            return
        }
        throw NotAllowedException("Active Sessions Found")
    }

    private fun storeSession(token: String, userId: String){
        try {
            val session = Session()
            session.apply {
                this.sessionId = UUID.randomUUID().toString()
                this.sessionStartTime = System.currentTimeMillis()
                this.token = token
                this.requestRemaining = rateLimit
                this.userId = userId
            }
            tokenRepository.saveSession(userId, session.toString())
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }
    fun parseTokenAndGetUser(token: String): String{
        try {
            val claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).body
            val expTime = claims.expiration.time
            if (expTime < System.currentTimeMillis()) {
                tokenRepository.deleteSession(token)
                throw UnAuthorizedException("Token has expired")
            }
            updateRateLimit(claims.subject)
            return claims.subject
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw UnAuthorizedException(e.message)
        }
    }

    fun updateRateLimit(userId: String){
        val session = tokenRepository.getSession(userId)
        var count = session.requestRemaining!!
        if(count == 0L){
            throw NotAllowedException("Rate Limit Exceeded")
        }
        count--
        session.requestRemaining = count
        tokenRepository.saveSession(userId, session.toString())
    }

    fun deleteToken(userId: String) {
        tokenRepository.deleteSession(userId)
    }
}