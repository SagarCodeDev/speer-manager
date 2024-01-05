package repositories

import com.google.gson.Gson
import exceptions.DatabaseException
import io.lettuce.core.api.StatefulRedisConnection
import models.Session
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val redisConnection: StatefulRedisConnection<String,String>,
    private val gson: Gson
) {

    fun getSession(userId: String): Session{
        try{
            val sesObj =  redisConnection.sync().get(userId)
            return gson.fromJson(sesObj, Session::class.java)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }

    fun saveSession(userId: String, session: String){
        try{
            redisConnection.sync().set(userId, session)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }

    fun deleteSession(userId: String){
        try{
            redisConnection.sync().del(userId)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }
}