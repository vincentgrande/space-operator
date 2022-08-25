package fr.vincentgrande.spaceoperators.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import fr.vincentgrande.spaceoperators.Dialogs.JoinGameDialogFragment
import fr.vincentgrande.spaceoperators.Dialogs.UsernameDialogFragment
import fr.vincentgrande.spaceoperators.R
import fr.vincentgrande.spaceoperators.databinding.ActivityMenuFragmentBinding
import fr.vincentgrande.spaceoperators.users.User
import fr.vincentgrande.spaceoperators.viewmodels.SocketViewModel
import fr.vincentgrande.spaceoperators.viewmodels.UsernameViewModel
import kotlin.system.exitProcess


class MenuFragment: Fragment() {

    private var binding: ActivityMenuFragmentBinding? = null
    private val model: UsernameViewModel by activityViewModels()
    private val socket: SocketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityMenuFragmentBinding.inflate(inflater, container, false)
        val nameObserver = Observer<String> { username ->
            // Update the UI, in this case, a TextView.
            binding?.tvHello?.text = "Hello $username !"
        }
        model.username.observe(viewLifecycleOwner, nameObserver)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedPrefs = requireActivity().getSharedPreferences("Game",Context.MODE_PRIVATE)
        var username = sharedPrefs.getString("name", "")
        var ip = sharedPrefs.getString("ip", "")

        if( ip == ""){
            socket.ipAddress.observe(viewLifecycleOwner){
                with (sharedPrefs.edit()) {
                    putString("ip", it)
                    apply()
                }
            }
        }
        if (username == "") {
            with (sharedPrefs.edit()) {
                putString("name", "Astronaut")
                apply()
            }
            binding?.tvHello?.text = "Hello Astronaut !"
        }else {
            binding?.tvHello?.text = "Hello $username !"
        }


        binding?.btnLeave?.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are you sure to leave the game ?")
            builder.setPositiveButton("YES") { _, _ ->
                requireActivity().finish()
                exitProcess(0)
            }
            builder.setNeutralButton("NO") { _, _ ->
            }
            builder.show()
        }

        binding?.ivEdit?.setOnClickListener{
            val dialog = UsernameDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, "UsernameDialogListener")
        }

        binding?.btnCreateGame?.setOnClickListener {
            socket.players.postValue(
                mutableListOf<User>(
                    User(
                        model.username.value.toString(),
                        model.ipAddress.value.toString(),
                        8888,
                        true
                    )
                )
            )
            val action = MenuFragmentDirections.actionMenuFragmentToCreateGameFragment(true)
            findNavController().navigate(action)
        }
        binding?.btnJoinGame?.setOnClickListener {
            val dialog = JoinGameDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, "JoinGameDialogListener")
        }
        binding?.btnHistory?.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_historyFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}