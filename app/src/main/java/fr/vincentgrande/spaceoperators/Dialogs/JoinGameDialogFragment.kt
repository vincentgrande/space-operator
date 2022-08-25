package fr.vincentgrande.spaceoperators.Dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.vincentgrande.spaceoperators.Connect
import fr.vincentgrande.spaceoperators.R
import fr.vincentgrande.spaceoperators.databinding.DialogJoinGameBinding
import fr.vincentgrande.spaceoperators.fragments.MenuFragmentDirections
import fr.vincentgrande.spaceoperators.users.User
import fr.vincentgrande.spaceoperators.viewmodels.SocketViewModel
import fr.vincentgrande.spaceoperators.viewmodels.UsernameViewModel


class JoinGameDialogFragment: DialogFragment()  {

    private lateinit var binding: DialogJoinGameBinding

    private val socket: SocketViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogJoinGameBinding.inflate(layoutInflater)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Flight information")
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_name, null))
                .setPositiveButton("Let's go",
                    DialogInterface.OnClickListener { dialog, id ->
                        val model: UsernameViewModel by viewModels()
                        socket.sendUDPData(Connect(User(
                            model.username.value.toString(),
                            model.ipAddress.value.toString(),
                            8888,
                            false
                        )).toString(),binding.ip.text.toString())
                        socket.host.value = binding.ip.text.toString()
                        val action = MenuFragmentDirections.actionMenuFragmentToCreateGameFragment(false)
                        findNavController().navigate(action)
                    })
                .setNegativeButton("Cancel :(",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}