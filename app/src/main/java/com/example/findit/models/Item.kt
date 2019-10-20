package com.example.findit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
        val id: String,
        val title: String,
        val description: String,
        val placeLastSeen: String,
        val dateLastSeen: String,
        val reward: String,
        val imageUrl: String,
        val owner: String): Parcelable