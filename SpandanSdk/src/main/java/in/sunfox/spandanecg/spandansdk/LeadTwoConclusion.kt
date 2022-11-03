package `in`.sunfox.spandanecg.spandansdk

import com.google.gson.annotations.SerializedName

data class LeadTwoConclusion(
    @SerializedName("detection")  val detection: String,
    @SerializedName("ecg_type")  val ecgType: String,
    @SerializedName("qrs_type")  val qrsType: String,
    @SerializedName("p_wave_type")  val pWaveType: String,
    @SerializedName("baseline_wandering")  val baselineWandering: Boolean,
    @SerializedName("power_line_interference")  val powerLineInterference: Boolean
): Conclusion()