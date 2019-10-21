package com.example.findit

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.findit.adapters.ItemAdapter
import com.example.findit.databinding.FragmentItemsBinding
import com.example.findit.models.Item
import com.example.findit.viewmodels.LoginViewModel

/**
 * Lost and found items
 */
class ItemsFragment : Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()
    private lateinit var adapter: ItemAdapter
    private val lostItems: List<Item>
    private val foundItems: List<Item>

    init {
        lostItems = initLostItems()
        foundItems = initFoundItems()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentItemsBinding.inflate(inflater, container, false)

        val navController = findNavController()

        // Setup ActionBar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.setupActionBarWithNavController(navController)

        // Initialize and set Adapter
        adapter = ItemAdapter()
        binding.itemList.adapter = adapter
        (binding.itemList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        // Open the fragment to add more lost and found items
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_itemsFragment_to_itemCreateFragment)
        }

        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId == R.id.lost_button && isChecked) {
                datasetChanged(binding.itemList, lostItems)
            }

            if (checkedId == R.id.found_button && isChecked) {
                datasetChanged(binding.itemList, foundItems)
            }
        }

        binding.toggleButtonGroup.check(R.id.lost_button)

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

    private fun initLostItems(): List<Item> {
        val items = mutableListOf<Item>()
        // Dummy data to add to the adapter to showcase
        for (i in 1..30) {
            val item = Item("$i", "Lost Title $i", "Lost Description $i", "Lost Location $i", "Lost Date $i", "USD $i", "https://dummyimage.com/600x400&text=Lost $i", "owner")
            items.add(item)
        }

        return items
    }

    private fun initFoundItems(): List<Item> {
        val items = mutableListOf<Item>()
        // Dummy data to add to the adapter to showcase
        for (i in 1..50) {
            val item = Item("$i", "Found Title $i", "Found Description $i", "Found Location $i", "Found Date $i", "None", "https://dummyimage.com/600x400&text=Found $i", "owner")
            items.add(item)
        }

        return items
    }

    private fun datasetChanged(recyclerView: RecyclerView, items: List<Item>) {
        adapter.submitList(items)

        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_from_bottom)
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}
