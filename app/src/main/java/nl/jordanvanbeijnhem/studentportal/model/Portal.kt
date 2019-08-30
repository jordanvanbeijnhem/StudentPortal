package nl.jordanvanbeijnhem.studentportal.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Portal(val title: String, val url: String) : Parcelable