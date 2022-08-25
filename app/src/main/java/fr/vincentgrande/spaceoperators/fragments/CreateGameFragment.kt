package fr.vincentgrande.spaceoperators.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import fr.vincentgrande.spaceoperators.Disonnect
import fr.vincentgrande.spaceoperators.R
import fr.vincentgrande.spaceoperators.databinding.ActivityCreateGameBinding
import fr.vincentgrande.spaceoperators.users.User
import fr.vincentgrande.spaceoperators.users.UserListAdapter
import fr.vincentgrande.spaceoperators.viewmodels.SocketViewModel
import fr.vincentgrande.spaceoperators.viewmodels.UsernameViewModel


class CreateGameFragment: Fragment() {

    private var binding: ActivityCreateGameBinding? = null
    private val socket: SocketViewModel by activityViewModels()
    private val username: UsernameViewModel by activityViewModels()
    private val args: CreateGameFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityCreateGameBinding.inflate(inflater, container, false)

        if(args.owner){
            socket.ipAddress.observe(viewLifecycleOwner) { ip ->
                binding?.tvIpAddress?.text = "IP : $ip !"
            }
        }else{
            binding?.tvIpAddress?.text = ""
            binding?.tvPort?.text = "Ready to play ?"
            binding?.btnStartGame?.text = "Ready ?"
            binding?.btnStartGame?.alpha = 1f
        }

        socket.players.observe(viewLifecycleOwner) { players ->
            Log.d("FATAL", "$players")
            binding?.UserListRecyclerView?.adapter = UserListAdapter(players)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.UserListRecyclerView?.layoutManager = LinearLayoutManager(context)

        binding?.btnBack?.setOnClickListener {
            socket.sendUDPData(Disonnect(socket.ipAddress.value.toString()).toString(),socket.host.value.toString())
            findNavController().navigate(R.id.action_createGameFragment_to_menuFragment)
        }
        binding?.btnStartGame?.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}