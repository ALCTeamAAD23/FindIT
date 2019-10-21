package com.example.findit


import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.findit.databinding.FragmentItemCreateBinding

/**
 * A simple [Fragment] subclass.
 */
class ItemCreateFragment : Fragment() {

    private val args: ItemCreateFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentItemCreateBinding.inflate(inflater, container, false)

        navController = findNavController()

        // Setup ActionBar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.setupActionBarWithNavController(navController)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        activity.supportActionBar?.title = if (args.lost) "Add Lost Item" else "Add Found Item"

        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.itemsFragment, false)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_create, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    navController.popBackStack(R.id.itemsFragment, false)
                    true
                }
                R.id.action_save -> {
                    // viewModel.signOut()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}
