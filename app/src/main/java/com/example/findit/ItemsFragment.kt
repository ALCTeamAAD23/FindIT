package com.example.findit


import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.findit.adapters.ItemAdapter
import com.example.findit.databinding.FragmentItemsBinding
import com.example.findit.models.Item
import com.example.findit.viewmodels.LoginViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

/**
 * Lost and found items
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

        val adapter = ItemAdapter()

        // Add the Animator to the recyclerview
        // TODO: Needs to be reviewed as it seems not to work
        binding.itemList.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        binding.itemList.adapter = adapter

        // Dummy data to add to the adapter to showcase
        val items = mutableListOf<Item>()
        for (i in 1..30) {
            val item = Item("$i", "Title $i", "Description $i", "Location $i", "Date $i", "USD $i", "https://dummyimage.com/600x400&text=Lost $i", "owner")
            items.add(item)
        }
        adapter.submitList(items)

        // Open the fragment to add more lost and found items
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_itemsFragment_to_itemCreateFragment)
        }

        // Need to show the menu item
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
