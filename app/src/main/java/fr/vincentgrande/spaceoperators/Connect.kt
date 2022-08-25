package fr.vincentgrande.spaceoperators

import com.google.gson.GsonBuilder
import fr.vincentgrande.spaceoperators.users.User

class Connect(user: User) {
    val type: String = "connect"
    val data: User = user

    override fun toString(): String {
        return GsonBuilder().create().toJson(this)
    }
}