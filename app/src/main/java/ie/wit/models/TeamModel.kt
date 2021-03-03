package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamModel(var id: Long = 0,
                     var name: String = "",
                     var location: String = "",
                     var amount: Int= 0,
                     var image: String = ""

) : Parcelable
