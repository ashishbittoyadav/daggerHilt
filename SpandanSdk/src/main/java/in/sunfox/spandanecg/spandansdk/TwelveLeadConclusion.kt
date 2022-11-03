package `in`.sunfox.spandanecg.spandansdk

import com.google.gson.annotations.SerializedName

data class TwelveLeadConclusion(
    @SerializedName("detection") val detection: String,
    @SerializedName("ecg_type") val ecgType: String,
    @SerializedName("recommendation") val recommendation: String,
    @SerializedName("anomalies") val anomalies: String,
    @SerializedName("risk") val risk: Risk
) : Conclusion()