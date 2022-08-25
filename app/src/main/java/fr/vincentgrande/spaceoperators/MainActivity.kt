package fr.vincentgrande.spaceoperators

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import fr.vincentgrande.spaceoperators.Dialogs.UsernameDialogFragment
import fr.vincentgrande.spaceoperators.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), UsernameDialogFragment.UsernameDialogListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val finalHost = NavHostFragment.create(R.navigation.app_nav)
        supportFragmentManager.beginTransaction()
            .replace(R.id.FragmentContainerView, finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }

}