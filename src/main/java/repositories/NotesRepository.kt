package repositories

import com.google.gson.Gson
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import exceptions.DatabaseException
import exceptions.ResourceUnavailableException
import models.Notes
import org.bson.Document
import org.bson.conversions.Bson
import org.glassfish.grizzly.http.Note
import utils.NOTES_COLLECTION_NAME
import javax.inject.Inject
import javax.inject.Named

class NotesRepository @Inject constructor(
    private val mongoDatabase: MongoDatabase,
    @Named(NOTES_COLLECTION_NAME) private val collectionName: String,
    private val gson: Gson
) {
    private val collection = mongoDatabase.getCollection(collectionName)

    fun createNotes(notes: Notes): Notes{
        try {
            val doc = Document.parse(notes.toString())
            doc["_id"] = notes.uuid
            collection.insertOne(doc)
            return notes
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }

    fun getNotes(query: Bson, projections: List<String>, limit: Int, offset: Int): List<Notes> {
        try{
            val notes = mutableListOf<Notes>()
            val it = if(projections.isEmpty()){
                collection.find(query).sort(Sorts.descending("createTime"))
                    .limit(limit).skip(limit*offset).iterator()
            }else{
                val include = BasicDBObject()
                projections.forEach{
                    include.append(it, 1)
                }
                collection.find(query).sort(Sorts.descending("createTime"))
                    .projection(include).limit(limit).skip(limit*offset).iterator()
            }
            while(it.hasNext()){
                val doc = it.next()
                doc.remove("_id")
                val note = gson.fromJson(doc.toJson(), Notes::class.java)
                notes.add(note)
            }
            return notes
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw DatabaseException(e.message)
        }
    }

    fun updateNotes(notes: Notes): Notes {
        try{
            val doc = Document.parse(notes.toString())
            doc["_id"] = notes.uuid
            val updatedDoc = collection.findOneAndUpdate(Filters.eq("_id", notes.userId), doc)
                ?: throw ResourceUnavailableException("Notes Not Found")
            return gson.fromJson(updatedDoc.toJson(), Notes::class.java)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }

    fun deleteNote(id: String, userId: String): Notes {
        try{
            val filter = Filters.and(
                Filters.eq("_id", id),
                Filters.eq("userId", userId)
            )
            val doc = collection.findOneAndDelete(filter) ?: throw ResourceUnavailableException("note not found")
            return gson.fromJson(doc.toJson(), Notes::class.java)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }
}