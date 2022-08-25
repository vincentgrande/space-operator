package fr.vincentgrande.spaceoperators.users

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    val ip: String,
    val port:Int,
    var status: Boolean = false
)