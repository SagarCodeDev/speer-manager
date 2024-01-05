package services

import com.google.gson.Gson
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.client.model.Filters
import exceptions.NotAllowedException
import exceptions.SpeerException
import exceptions.UnAuthorizedException
import models.AuthenticateRequest
import models.User
import org.bson.conversions.Bson
import repositories.UserRepository
import utils.USER_NOT_EXIST
import java.util.UUID
import java.util.logging.Filter
import javax.inject.Inject

class UserService @Inject constructor(
    private val jwtService: JWTService,
    private val userRepository: UserRepository,
    private val gson: Gson
) {
    fun createUser(request: String): String {
        try{
            val user = gson.fromJson(request, User::class.java)
            validateOrThrow(user)
            user.ensure()
            userRepository.createUser(user)
            val token =  jwtService.createToken(user.uuid!!)
            return token
        }catch (e: Exception){
            println(e.message)
            throw SpeerException(e.message)
        }
    }

    private fun validateOrThrow(user: User){
        user.checkMandatoryFields()
        user.checkDuplicateFields()
    }

    private fun User.checkDuplicateFields(){
        val query = buildDuplicateQuery(this.emailId, this.contactNumber)
        val users = userRepository.getUsersByFilter(query)
        if(users.isEmpty().not()){
            throw NotAllowedException("User with email or contact number already exist")
        }
    }

    private fun buildDuplicateQuery(emailId: String?, contactNumber: String?): Bson {
        try{
            val list = BasicDBList()
            list.add(BasicDBObject("emailId", emailId))
            list.add(BasicDBObject("contactNumber", contactNumber))
            val filters = BasicDBObject(
                "\$or", list
            )
            return filters
        }catch (e: Exception){
            throw NotAllowedException(e.message)
        }
    }

    private fun User.checkMandatoryFields(){
        if(this.emailId.isNullOrBlank() && this.contactNumber.isNullOrBlank()){
            throw NotAllowedException("Phone number or Email is mandatory")
        }

        if(this.firstName.isNullOrBlank()){
            throw NotAllowedException("Please provide your name")
        }

        if(this.dob == null  || this.dob!! < 0){
            throw NotAllowedException("Please provide your date of birth")
        }

        if(this.password.isNullOrBlank()){
            throw NotAllowedException("Password cannot be blank")
        }

    }

    private fun User.ensure(){
        this.uuid = UUID.randomUUID().toString()
        this.createTime = System.currentTimeMillis()
    }

    fun authenticateUser(request: String): String{
        try{
            val reqObj = gson.fromJson(request, AuthenticateRequest::class.java)
            val filter = Filters.eq("emailId", reqObj.email)
            val users = userRepository.getUsersByFilter(filter)
            if(users.first().password != reqObj.password){
                throw UnAuthorizedException("Invalid Password")
            }
            val token =  jwtService.createToken(userId = users.first().uuid!!)
            return token
        }catch (e: Exception){
            println(e.message)
            throw UnAuthorizedException(e.message)
        }
    }

    fun verifyUserId(userId: String){
        val filter = Filters.eq("_id", userId)
        val users = userRepository.getUsersByFilter(filter)
        if(users.isEmpty()){
            throw UnAuthorizedException(USER_NOT_EXIST)
        }
    }

    fun parseTokenAndGetUser(token: String): String {
        return jwtService.parseTokenAndGetUser(token)
    }
}