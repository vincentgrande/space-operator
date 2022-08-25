package fr.vincentgrande.spaceoperators

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import fr.vincentgrande.spaceoperators.users.User

class PlayersList(users:MutableList<User>) {
    val type: String = "players"

    val data: MutableList<User> = users


    override fun toString(): String {
        return GsonBuilder().create().toJson(this)
    }
}