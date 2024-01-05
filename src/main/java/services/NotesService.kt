package services

import com.google.gson.Gson
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.client.model.Filters
import exceptions.NotAllowedException
import exceptions.ResourceUnavailableException
import models.Notes
import org.bson.conversions.Bson
import org.json.JSONObject
import repositories.NotesRepository
import java.util.UUID
import javax.inject.Inject

class NotesService @Inject constructor(
    private val gson: Gson,
    private val notesRepository: NotesRepository
) {
    fun createNotes(request: String, userId: String): Notes{
        try{
            val notes = gson.fromJson(request, Notes::class.java)
            validateOrThrow(notes)
            notes.ensureFields()
            notesRepository.createNotes(notes)
            return notes
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }

    private fun validateOrThrow(notes: Notes) {
        notes.checkMandateFields()
        if(notes.note!!.length > 500){
            throw NotAllowedException("Note length cannot be greater than 500 characters")
        }
        if(notes.title!!.length > 100){
            throw NotAllowedException("Title length cannot be greater than 100 characters")
        }
    }

    private fun Notes.checkMandateFields(){
        if(this.note.isNullOrEmpty()){
            throw NotAllowedException("Note cannot be blank")
        }
        if(this.title.isNullOrBlank()){
            throw NotAllowedException("Title cannot be blank")
        }
        if(this.userId.isNullOrBlank()){
            throw NotAllowedException("Please Login.")
        }
    }

    private fun Notes.ensureFields(){
        this.uuid = UUID.randomUUID().toString()
        this.createTime = System.currentTimeMillis()
    }

    fun getNotes(filters: String?, search: String?, projections: List<String>, limit: Int, offset: Int, userId: String): List<Notes> {
        try{
            val query = fetchNotesQuery(
                filters = filters,
                search = search,
                userId = userId
            )
            return notesRepository.getNotes(
                query = query,
                projections = projections,
                limit = limit,
                offset = offset
            )
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }

    fun fetchNotesQuery(filters: String?, search: String?, userId: String): Bson{
        val query = BasicDBList()
        query.add(
            Filters.or(
                Filters.eq("userId", userId),
                Filters.eq("sharedUsers", userId)
            )
        )
        if(filters!=null){
            val filtersObj = JSONObject(filters)
            filtersObj.keySet().forEach { key ->
                if(filtersObj.isNull(key).not() && filtersObj.getJSONArray(key).length() > 0){
                    query.add(
                        BasicDBObject(key, BasicDBObject("\$in", filtersObj.getJSONArray(key)))
                    )
                }
            }
        }

        if(search.isNullOrBlank().not()){
            val text = search!!.replace("(","\\\\(").replace(")","\\\\)")
            val searchTitleQuery = BasicDBObject.parse("{\"title\":{\"\$regex\":\"$text\",\"options\":'i'")
            val searchNoteQuery = BasicDBObject.parse("{\"note\":{\"\$regex\":\"$text\",\"options\":'i'")
            query.addAll(listOf(searchTitleQuery, searchNoteQuery))
        }

        return BasicDBObject("\$and", query)
    }

    fun getNoteById(id: String, userId: String): Notes {
        try{
            val query = Filters.or(
                Filters.eq("userId", userId),
                Filters.eq("sharedUsers", userId)
            )
            val filters = Filters.and(
                Filters.eq("uuid", id),
                query
            )
            val notes = notesRepository.getNotes(
                query = filters,
                limit = 1,
                offset = 0,
                projections = listOf()
            )
            if(notes.isEmpty()){
                throw ResourceUnavailableException("Note not found for the user")
            }
            return notes.first()
        }catch (e :Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }

    fun updateNote(request: String, userId: String): Notes {
        try{
            val notes = gson.fromJson(request, Notes::class.java)
            validateOrThrow(notes)
            if(notes.uuid.isNullOrBlank()){
                throw NotAllowedException("User Invalid")
            }
            notes.createTime = notes.createTime ?: System.currentTimeMillis()
            return notesRepository.updateNotes(notes)
        }catch (e: Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }

    fun deleteNote(id: String, userId: String): Notes {
        try {
            return notesRepository.deleteNote(id, userId)
        }catch (e:Exception){
            e.printStackTrace()
            println(e.message)
            throw e
        }
    }
}