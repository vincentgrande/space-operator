package fr.vincentgrande.spaceoperators.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.vincentgrande.spaceoperators.Connect
import fr.vincentgrande.spaceoperators.PlayersList
import fr.vincentgrande.spaceoperators.users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.*

class SocketViewModel : ViewModel() {
    private var serverSocket = DatagramSocket(null)
    val ipAddress = MutableLiveData<String>(null)
    val host = MutableLiveData<String>(null)

    val listenData = MutableLiveData<String>(null)

    var players =  MutableLiveData<MutableList<User>>(mutableListOf())

    init {
        serverSocket.reuseAddress = true
        serverSocket.bind(InetSocketAddress(8888))
        viewModelScope.launch(Dispatchers.IO) {
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            val ip =
                networkInterfaces.toList()
                    .find { it.displayName == "wlan0" }
                    ?.inetAddresses?.toList()
                    ?.find { it is Inet4Address }
                    ?.hostAddress ?: "127.0.0.1"
            ipAddress.postValue(ip)
        }
        listenSocket()
    }

    private fun listenSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            val buffer = ByteArray(256)
            val packet = DatagramPacket(buffer, buffer.size)
            serverSocket.receive(packet)
            val data = String(packet.data)
            listenData.postValue(data)
            handlePacketRetrieve(packet)
            listenSocket()
        }
    }
    private fun handlePacketRetrieve(packet: DatagramPacket){
        val data = JSONObject(packet.data.decodeToString())
        when(data.getString("type")){
            "connect" -> addPlayer(packet)
            "players" -> updatePlayers(packet)
            "disconnect" -> removePlayer(packet)
        }
    }

     fun sendUDPData(data: String, ip:String) {
        viewModelScope.launch(Dispatchers.IO) {
            DatagramSocket().use {
                val dataBytes = data.toByteArray()
                val address = InetAddress.getByName(ip)
                val packet = DatagramPacket(dataBytes, dataBytes.size, address, 8888)
                it.send(packet)
            }
        }
    }

    private fun addPlayer(packet: DatagramPacket){
        val data = JSONObject(packet.data.decodeToString()).getJSONObject("data")
        val usr = User(
            data.getString("username"),
            packet.address.toString().replace("/",""),
            8888
        )
        val lst = players.value
        lst?.add(usr)
        players.postValue(lst!!)

        sendPlayers()
    }

    private fun sendPlayers(){
        players.value!!.map {
            if(it.ip != ipAddress.value) sendUDPData(PlayersList(players.value!!).toString(),it.ip)
        }
    }

    private fun updatePlayers(packet: DatagramPacket){
        val data = JSONObject(packet.data.decodeToString()).toString()
        val gson = GsonBuilder().create()
        val lst = gson.fromJson(data,PlayersList::class.java)
        players.postValue(lst.data)
    }

    private fun removePlayer(packet:DatagramPacket){
        val data = String(packet.data,0,packet.length)
        val dat = JSONObject(data)
        players.postValue(players.value?.minus(players.value?.find {
            it.ip == dat.getString("data").toString()
        }) as MutableList<User>?)
        sendPlayers()
    }

}