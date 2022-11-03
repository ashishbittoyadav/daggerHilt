package `in`.sunfox.spandanecg.spandansdk.conclusion

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReportUser(
    @SerializedName("first_name") val firstName: String="",
    @SerializedName("last_name") val lastName: String="",
    @SerializedName("height") val height: String?=null,
    @SerializedName("weight") val weight: String?=null,
    @SerializedName("gender") val gender: String?=null,
    @SerializedName("age") val age: Int?=null,
    @SerializedName("phone_number") val phoneNumber:String=""
): Serializable