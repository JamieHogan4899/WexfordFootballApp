package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
//whats being stored
@IgnoreExtraProperties
@Parcelize
data class TeamModel(var uid: String = "",
                     var name: String = "",
                     var location: String = "",
                     var amount: Int= 0,
                     var image: String = "",
                     var email: String? = "jamiehogan4848@gmail.com"

) : Parcelable


{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "location" to location,
            "amout" to amount,
            "image" to image,
            "email" to email
        )
    }
}