package fr.vincentgrande.spaceoperators

import com.google.gson.GsonBuilder
import fr.vincentgrande.spaceoperators.users.User

class Disonnect(ip: String) {
    val type: String = "disconnect"
    val data: String = ip

    override fun toString(): String {
        return GsonBuilder().create().toJson(this)
    }
}