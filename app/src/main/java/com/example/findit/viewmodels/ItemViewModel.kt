package com.example.findit.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.findit.models.Item

class ItemViewModel (item: Item): ViewModel() {

    val title = ObservableField(item.title)
    val placeLastSeen = ObservableField(item.placeLastSeen)
    val dateLastSeen = ObservableField(item.dateLastSeen)
    val imageUrl = ObservableField(item.imageUrl)
}