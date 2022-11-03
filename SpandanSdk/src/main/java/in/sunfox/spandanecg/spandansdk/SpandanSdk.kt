//package `in`.sunfox.spandanecg.spandansdk
//
//
//import `in`.sunfox.spandanecg.spandansdk.collection.EcgTestCallback
//import `in`.sunfox.spandanecg.spandansdk.conclusion.EcgReport
//import `in`.sunfox.spandanecg.spandansdk.connection.OnDeviceConnectionStateChangeListener
//import `in`.sunfox.spandanecg.spandansdk.connection.usb_connection.UsbConnectionContract
//import `in`.sunfox.spandanecg.spandansdk.connection.usb_connection.UsbConnectionHelper
//import `in`.sunfox.spandanecg.spandansdk.enums.DeviceConnectionState
//import `in`.sunfox.spandanecg.spandansdk.enums.EcgPosition
//import `in`.sunfox.spandanecg.spandansdk.enums.EcgTestType
//import android.content.Context
//import kotlinx.coroutines.*
//import kotlinx.coroutines.Dispatchers.IO
//import java.io.InputStream
//import kotlin.collections.ArrayList
//
//
//open class SpandanSdk private constructor(
//) : UsbConnectionContract {
//
//    private var onDeviceConnectionStateChangeListener: OnDeviceConnectionStateChangeListener? = null
//
//    private lateinit var report: EcgReport
//
//    companion object {
//        @Volatile
//        private var INSTANCE: SpandanSdk? = null
//
//        fun getInstance(): SpandanSdk {
//            synchronized(this) {
//                if (INSTANCE == null)
//                    INSTANCE = SpandanSdk()
//                return INSTANCE!!
//            }
//        }
//    }
//
//    private lateinit var ecgTestType: EcgTestType
//    private lateinit var inputStream: InputStream
//    private lateinit var ecgTestInstance: EcgTest
//
//    fun setOnDeviceConnectionStateChangedListener(onDeviceConnectionStateChangeListener: OnDeviceConnectionStateChangeListener) {
//        this.onDeviceConnectionStateChangeListener = onDeviceConnectionStateChangeListener
//    }
//
//    fun bind(context: Context) {
//        inputStream = context.resources.openRawResource(R.raw.reference_signal)
//        UsbConnectionHelper.bind(this, context)
//    }
//
//    fun createTest(ecgTestType: EcgTestType, ecgTestCallback: EcgTestCallback): EcgTest {
//        this.ecgTestType = ecgTestType
//        ecgTestInstance =
//            EcgTest(inputStream = inputStream, ecgTestType, ecgTestCallback)
//        return ecgTestInstance
//    }
//
//    override fun onDeviceDisconnected() {
//        onDeviceConnectionStateChangeListener?.onDeviceConnectionStateChanged(DeviceConnectionState.DISCONNECTED)
//        if(::ecgTestInstance.isInitialized)
//            ecgTestInstance.onDeviceDisconnected()
//    }
//
//    override fun onDeviceConnected() {
//        onDeviceConnectionStateChangeListener?.onDeviceConnectionStateChanged(DeviceConnectionState.CONNECTED)
//    }
//
//    override fun onUsbPermissionGranted() {
//
//    }
//
//    override fun onReceiveData(data: String?) {
//        if (data != null) {
//            ecgTestInstance.onReceiveData(data)
//        }
//    }
//
//    override fun onDeviceVerified() {
//        onDeviceConnectionStateChangeListener?.onDeviceConnectionStateChanged(DeviceConnectionState.VERIFIED)
//    }
//
//    override fun onVerificationTimeout() {
//        onDeviceConnectionStateChangeListener?.onDeviceConnectionStateChanged(DeviceConnectionState.VERIFICATION_TIME_OUT)
//    }
//
//    override fun onUsbPermissionDenied() {
//        onDeviceConnectionStateChangeListener?.onDeviceConnectionStateChanged(DeviceConnectionState.USB_CONNECTION_PERMISSION_DENIED)
//    }
//
//    override fun onDeviceReconnect() {
//    }
//
//    fun unbind(context: Context) {
//        UsbConnectionHelper.stopTransmission()
//        UsbConnectionHelper.unBind(context)
//    }
//
//    fun generateReport(userAge:Int,ecgData: HashMap<EcgPosition,ArrayList<Double>>,reportGenerationStatusListener: OnReportGenerationStateListener){
//        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//            reportGenerationStatusListener.onReportGenerationFailed(Const.ERROR_TEST_NOT_VALID,throwable.message.toString())
//        }
//        CoroutineScope(IO).launch(exceptionHandler) {
//            supervisorScope {
//                val reportGenerationTask = async {
//                    ecgTestInstance.checkForDataValidation(ecgData).let {
//                        if (it != null)
//                            throw it
//                        else {
//                            report = ecgTestInstance.proceedReport(userAge,ecgData)
//                        }
//                    }
//                }
//                reportGenerationTask.await()
//                reportGenerationStatusListener.onReportGenerationSuccess(report)
//            }
//        }
//    }
//
////    fun setReport(ecgReport: EcgReport){
////        report = ecgReport
////    }
//
////    fun getReport() : EcgReport? {
////        return if(::report.isInitialized)
////            report
////        else
////            null
////    }
//}
//interface OnReportGenerationStateListener{
//    fun onReportGenerationSuccess(ecgReport: EcgReport)
//    fun onReportGenerationFailed(errorCode:Int, errorMsg:String)
//}
//
