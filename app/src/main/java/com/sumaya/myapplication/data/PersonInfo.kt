package com.sumaya.myapplication.data

import android.os.Parcelable
import android.widget.TextView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonInfo(
    val name: String,
    val birthday:String,
    val phone: String,
    val gender:String
): Parcelable
