package fr.vincentgrande.spaceoperators.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class UsernameViewModel(application: Application) : AndroidViewModel(application) {
    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val ipAddress: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    var app = application
    init{
        loadUsername()
    }

    private fun loadUsername() {
        var sharedPrefs = app.applicationContext.getSharedPreferences("Game",Context.MODE_PRIVATE)
        username.value = sharedPrefs?.getString("name", "Astronaut")
        ipAddress.value = sharedPrefs?.getString("ip", "")
    }

    fun setUsername(uname:String){
        username.postValue(uname)
    }
}


