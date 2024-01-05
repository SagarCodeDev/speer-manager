package repositories

import exceptions.DatabaseException
import io.lettuce.core.api.StatefulRedisConnection
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val redisConnection: StatefulRedisConnection<String,String>
) {

    fun getRateLimit(token: String): Int{
        try{
            return redisConnection.sync().get(token)?.toInt() ?: 0
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }

    fun setRateLimit(token: String, count: Int){
        try{
            redisConnection.sync().set(token, count.toString())
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }

    fun deleteToken(token: String){
        try{
            redisConnection.sync().del(token)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }
}