package github.idmeetrious.pokemon.presentation.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import github.idmeetrious.pokemon.R
import github.idmeetrious.pokemon.databinding.FragmentMenuBinding

private const val TAG = "MenuFragment"

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initView()

        return rootView
    }

    private fun initView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Move to search screen
        binding.menuToSearchScreenBtn.setOnClickListener {
            Log.i(TAG, "--> onViewCreated: Move to search screen")
            view.findNavController().navigate(R.id.action_menuFragment_to_searchFragment)
        }
        // Move to random screen
        binding.menuToRandomScreenBtn.setOnClickListener {
            Log.i(TAG, "--> onViewCreated: Move to random screen")
            view.findNavController().navigate(R.id.action_menuFragment_to_randomFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}