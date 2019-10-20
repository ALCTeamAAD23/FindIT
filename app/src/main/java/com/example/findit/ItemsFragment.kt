package com.example.findit


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.findit.databinding.FragmentItemsBinding
import com.example.findit.viewmodels.LoginViewModel

/**
 * A simple [Fragment] subclass.
 */
class ItemsFragment : Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentItemsBinding.inflate(inflater, container, false)

        val navController = findNavController()

        // Setup ActionBar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.setupActionBarWithNavController(navController)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_logout -> {
                    viewModel.signOut()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
