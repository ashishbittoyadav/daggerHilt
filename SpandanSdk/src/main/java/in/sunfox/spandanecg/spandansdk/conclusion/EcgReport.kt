package `in`.sunfox.spandanecg.spandansdk.conclusion


import `in`.sunfox.spandanecg.spandansdk.Characteristics
import `in`.sunfox.spandanecg.spandansdk.Conclusion
import `in`.sunfox.spandanecg.spandansdk.enums.EcgTestType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EcgReport(
    @SerializedName("report_type") val reportType: EcgTestType,
    @SerializedName("report_timestamp") var timeStamp: String,
//    @SerializedName("pdf_file_path") var pdfFilePath: String,
//    @SerializedName("user_data") var userData: ReportUser,
//    @SerializedName("ecg_data") var ecgData: EcgData,
    @SerializedName("characteristics") val ecgCharacteristics: Characteristics,
    @SerializedName("conclusions") var conclusion: Conclusion,
//    @SerializedName("img_data") var imgData: ImageData,
//    @SerializedName("header") var header: String = "SPDN",
//    @SerializedName("version") var version: String = "2.0",
//    @SerializedName("device_id") var deviceId: String = "SL_V1",
//    @SerializedName("user_type") var userType : Int = 1 /** Constant.PRIMARY_USER = 1*/
):Serializable