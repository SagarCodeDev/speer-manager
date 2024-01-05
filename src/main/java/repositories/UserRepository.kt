package repositories

import com.google.gson.Gson
import com.mongodb.client.MongoDatabase
import exceptions.DatabaseException
import models.User
import org.bson.Document
import org.bson.conversions.Bson
import utils.USER_COLLECTION_NAME
import javax.inject.Inject
import javax.inject.Named

class UserRepository @Inject constructor(
    private val gson: Gson,
    private val db: MongoDatabase,
    @Named(USER_COLLECTION_NAME) private val collectionName: String
) {
    private val collection = db.getCollection(collectionName)
    fun createUser(user: User): User {
        try {
            val doc = Document.parse(user.toString())
            doc["_id"] = user.uuid
            collection.insertOne(doc)
            return user
        }catch (e: Exception){
            println(e.message)
            throw e
        }
    }

    fun getUsersByFilter(query: Bson): List<User> {
        try {
            val users = mutableListOf<User>()
            val it = collection.find(query).iterator()
            while(it.hasNext()){
                val doc = it.next()
                doc.remove("_id")
                val user = gson.fromJson(doc.toJson(), User::class.java)
                users.add(user)
            }
            return users
        }catch (e: Exception){
            throw DatabaseException(e.message)
        }
    }
}