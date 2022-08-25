package fr.vincentgrande.spaceoperators.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.vincentgrande.spaceoperators.R
import fr.vincentgrande.spaceoperators.databinding.ActivityCreateGameBinding
import fr.vincentgrande.spaceoperators.databinding.ActivityHistoryBinding

class HistoryFragment: Fragment()  {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityHistoryBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_menuFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}