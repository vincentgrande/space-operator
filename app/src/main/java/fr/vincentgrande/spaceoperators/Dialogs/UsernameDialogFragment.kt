package fr.vincentgrande.spaceoperators.Dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import fr.vincentgrande.spaceoperators.R
import fr.vincentgrande.spaceoperators.databinding.DialogNameBinding
import fr.vincentgrande.spaceoperators.viewmodels.UsernameViewModel

class UsernameDialogFragment : DialogFragment() {

    interface UsernameDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }
    private lateinit var binding: DialogNameBinding;


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNameBinding.inflate(layoutInflater)
        binding.username.hint = requireActivity().getSharedPreferences("Game",Context.MODE_PRIVATE).getString("name", "")
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Set username")
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_name, null))
                .setPositiveButton("Let's go",
                    DialogInterface.OnClickListener { dialog, id ->
                        val sharedPrefs = requireActivity().getSharedPreferences("Game",Context.MODE_PRIVATE)
                        with (sharedPrefs.edit()) {
                            putString("name", binding.username.text.toString())
                            apply()
                        }
                        val model: UsernameViewModel by viewModels()
                        model.setUsername(binding.username.text.toString())
                        (requireActivity() as UsernameDialogListener).onDialogPositiveClick(dialog = this)
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