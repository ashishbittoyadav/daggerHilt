//package `in`.sunfox.spandanecg.spandansdk.collection
//
//
//import `in`.sunfox.spandanecg.spandansdk.connection.usb_connection.UsbConnectionHelper
//import `in`.sunfox.spandanecg.spandansdk.*
//import `in`.sunfox.spandanecg.spandansdk.conclusion.EcgReport
//import `in`.sunfox.spandanecg.spandansdk.connection.SpandanEngineException
//import `in`.sunfox.spandanecg.spandansdk.enums.EcgPosition
//import `in`.sunfox.spandanecg.spandansdk.enums.EcgTestType
//import `in`.sunfox.spandanecg.spandansdk.enums.SpandanException
//import android.os.CountDownTimer
//import android.util.Log
//import java.io.InputStream
//import java.util.*
//import kotlin.collections.ArrayList
//import kotlin.collections.HashMap
//
//class EcgTest(
//    private val inputStream: InputStream,
//    private val ecgTestType: EcgTestType,
//    private val ecgTestCallback: EcgTestCallback,
////    private val onPositionRecordingCompleteListener: OnPositionRecordingCompleteListener,
//) {
//
//    private val TAG = "EcgTest.TAG"
//
//    companion object {
//        private const val SINGLE_LEAD_TEST_DURATION = 10 * 1000L //10000 milliseconds => 10 seconds
//        private const val HRV_LEAD_TEST_DURATION =
//            60 * 5 * 1000L // 300000 milliseconds => 300 seconds => 5 minutes
//    }
//
//    private var isTestInProgress = false
//
//    private val testDuration =
//        SINGLE_LEAD_TEST_DURATION
//
//    //        if (ecgTestType == EcgTestType.HRV) HRV_LEAD_TEST_DURATION else SINGLE_LEAD_TEST_DURATION
//    private val countDownTimer = object : CountDownTimer(
//        testDuration,
//        1000
//    ) {
//        override fun onTick(millisUntilFinished: Long) {
//            val time = millisUntilFinished / 1000
//            ecgTestCallback.onElapsedTimeChanged(
//                (testDuration / 1000) - time,
//                testDuration / 1000
//            )
//        }
//
//        override fun onFinish() {
//            isTestInProgress = false
//            UsbConnectionHelper.stopTransmission()
////            ecgTestCallback.onPositionRecordingComplete(currentSelectedEcgPosition)
////            onPositionRecordingCompleteListener.onPositionRecordingComplete(
////                currentSelectedEcgPosition
////            )
//
//            if (isTestValid())
//                ecgTestCallback.onPositionRecordingComplete(
//                    currentSelectedEcgPosition,
//                    ecgData[currentSelectedEcgPosition]
//                )
////                ecgTestCallback.onTestComplete(ecgData[currentSelectedEcgPosition])
//            else
//                ecgTestCallback.onTestFailed(Const.ERROR_TEST_NOT_VALID)
//        }
//    }
//
//    private lateinit var currentSelectedEcgPosition: EcgPosition
//    private var ecgData: EnumMap<EcgPosition, ArrayList<Double>> = EnumMap(EcgPosition::class.java)
//
//    fun start(ecgPosition: EcgPosition) {
//        if (isTestInProgress)
//            throw SpandanEngineException("${SpandanException.IllegalStateException}: ${currentSelectedEcgPosition.name} lead already in progress.")
//        this.currentSelectedEcgPosition = ecgPosition
////        if ((ecgTestType == EcgTestType.HRV || ecgTestType == EcgTestType.LEAD_TWO) && ecgPosition != EcgPosition.LEAD_2)
////            throw SpandanEngineException("${SpandanException.IllegalLeadException}: for $ecgTestType selected lead must be Lead_II")
////        else if (ecgTestType == EcgTestType.SEVEN_LEAD && this.currentSelectedEcgPosition == EcgPosition.LEAD_1)
////            throw SpandanEngineException("${SpandanException.IllegalLeadException}: for $ecgTestType only v1 to v6 and lead II is valid lead")
////        else
//        if (!UsbConnectionHelper.isDeviceConnected())
//            throw SpandanEngineException("${SpandanException.DeviceIsNotConnected}: please connect the device.")
//        else {
//            UsbConnectionHelper.startTransmission()
//            if (ecgData[ecgPosition] == null)
//                ecgData[ecgPosition] = arrayListOf()
//            else
//                ecgData[ecgPosition]!!.clear()
//            countDownTimer.start()
//            ecgTestCallback.onTestStarted(currentSelectedEcgPosition)
//            isTestInProgress = true
//        }
//    }
//
//    private fun setEcgData(ecgPosition: EcgPosition, data: Double) {
//        val list = this.ecgData[ecgPosition]
//        if (list == null) {
//            this.ecgData[ecgPosition] = ArrayList()
//        }
//        this.ecgData[ecgPosition]!!.add(data)
//    }
//
//    private fun getEcgData(ecgPosition: EcgPosition): ArrayList<Double> {
//        return ecgData[ecgPosition]!!
//    }
//
//    fun getEcgDataOfSelectedLead() =
//        if (::currentSelectedEcgPosition.isInitialized) ecgData[currentSelectedEcgPosition] else arrayListOf()
//
//    fun isTestValid(): Boolean =
//        ECGProcessing.isEcgSignalCompatibleForProcessing(getEcgData(currentSelectedEcgPosition))
//
//    fun onReceiveData(data: String?) {
//        if (data != null) {
//            setEcgData(currentSelectedEcgPosition, data.toDouble())
//            ecgTestCallback.onReceivedData(data)
//        }
//    }
//
//    fun onDeviceDisconnected() {
//        if (isTestInProgress) {
//            isTestInProgress = false
//            countDownTimer.cancel()
//            ecgTestCallback.onTestFailed(Const.ERROR_DEVICE_DISCONNECTED)
//        }
//    }
//
//    fun cancel() {
//        if (isTestInProgress) {
//            isTestInProgress = false
//            countDownTimer.cancel()
//            UsbConnectionHelper.stopTransmission()
//            ecgTestCallback.onTestFailed(Const.TEST_CANCELED_BY_USER)
//        }
//    }
//
//    private fun checkTwelveLeadTestCompletion(twelveLeadData: TwelveLeadData): Boolean {
//        return twelveLeadData.lead2Data.size != 0
//                &&
//                twelveLeadData.lead1Data.size != 0
//                &&
//                twelveLeadData.v1Data.size != 0
//                &&
//                twelveLeadData.v2Data.size != 0
//                &&
//                twelveLeadData.v3Data.size != 0
//                &&
//                twelveLeadData.v4Data.size != 0
//                &&
//                twelveLeadData.v5Data.size != 0
//                &&
//                twelveLeadData.v6Data.size != 0
//    }
//
//    private fun checkTwelveLeadTestCompletion(): Boolean {
//        return ecgData[EcgPosition.LEAD_2].let {
//            if (it != null) it.size != 0 else false
//        }
//                &&
//                ecgData[EcgPosition.LEAD_1].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V1].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V2].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V3].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V4].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V5].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V6].let {
//                    if (it != null) it.size != 0 else false
//                }
//    }
//
//    private fun checkSevenLeadTestCompletion(sevenLeadData: SevenLeadData): Boolean {
//        return sevenLeadData.lead2Data.size != 0
//                &&
//                sevenLeadData.v1Data.size != 0
//                &&
//                sevenLeadData.v2Data.size != 0
//                &&
//                sevenLeadData.v3Data.size != 0
//                &&
//                sevenLeadData.v4Data.size != 0
//                &&
//                sevenLeadData.v5Data.size != 0
//                &&
//                sevenLeadData.v6Data.size != 0
//    }
//
//    private fun checkSevenLeadTestCompletion(): Boolean {
//        return ecgData[EcgPosition.LEAD_2].let {
//            if (it != null) it.size != 0 else false
//        }
//                &&
//                ecgData[EcgPosition.V1].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V2].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V3].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V4].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V5].let {
//                    if (it != null) it.size != 0 else false
//                }
//                &&
//                ecgData[EcgPosition.V6].let {
//                    if (it != null) it.size != 0 else false
//                }
//    }
//
//    private fun checkForLeadII(leadData: LeadTwoData): Boolean {
//        return leadData.lead2Data.size != 0
//    }
//
//    private fun checkForLeadII(): Boolean {
//        return ecgData[EcgPosition.LEAD_2].let {
//            if (it != null) it.size != 0 else false
//        }
//    }
//
//    fun checkForDataValidation(): SpandanEngineException? {
//        Log.d(TAG, "checkForDataValidation: $ecgTestType ${checkTwelveLeadTestCompletion()}")
//        if (ecgTestType == EcgTestType.LEAD_TWO && !checkForLeadII())
//            return SpandanEngineException("${SpandanException.InsufficientDataException}: Lead II position data is not available.")
////        else if(ecgTestType == EcgTestType.SEVEN_LEAD && !checkSevenLeadTestCompletion())
////            return SpandanEngineException("${SpandanException.InsufficientDataException}: Seven Lead data is not completely available to generate report.")
//        else if(ecgTestType == EcgTestType.TWELVE_LEAD && !checkTwelveLeadTestCompletion())
//            return SpandanEngineException("${SpandanException.InsufficientDataException}:Twelve Lead data is not completely available to generate report.")
////        else if(ecgTestType == EcgTestType.HRV && !checkForLeadII())
////            return SpandanEngineException("${SpandanException.InsufficientDataException}: Hrv position data is not available.")
//        return null
//    }
//
//    private fun getEcgDataFromHashMap(ecgPoints: HashMap<EcgPosition, ArrayList<Double>>): EcgData {
//        return when (ecgTestType) {
//            EcgTestType.LEAD_TWO -> {
//                LeadTwoData(ecgPoints[EcgPosition.LEAD_2] as ArrayList<Double> /* = java.util.ArrayList<kotlin.Double> */)
//            }
//            EcgTestType.TWELVE_LEAD -> {
//                TwelveLeadData(
//                    v1Data = ecgPoints[EcgPosition.V1] as java.util.ArrayList<Double>,
//                    v2Data = ecgPoints[EcgPosition.V2] as java.util.ArrayList<Double>,
//                    v3Data = ecgPoints[EcgPosition.V3] as java.util.ArrayList<Double>,
//                    v4Data = ecgPoints[EcgPosition.V4] as java.util.ArrayList<Double>,
//                    v5Data = ecgPoints[EcgPosition.V5] as java.util.ArrayList<Double>,
//                    v6Data = ecgPoints[EcgPosition.V6] as java.util.ArrayList<Double>,
//                    lead1Data = ecgPoints[EcgPosition.LEAD_1] as java.util.ArrayList<Double>,
//                    lead2Data = ecgPoints[EcgPosition.LEAD_2] as java.util.ArrayList<Double>,
//                    avfData = arrayListOf(),
//                    avrData = arrayListOf(),
//                    lead3Data = arrayListOf(),
//                    avlData = arrayListOf()
//                )
//            }
////            EcgTestType.SEVEN_LEAD -> {
////                SevenLeadData(
////                    v1Data = ecgPoints[EcgPosition.V1] as java.util.ArrayList<Double>,
////                    v2Data = ecgPoints[EcgPosition.V2] as java.util.ArrayList<Double>,
////                    v3Data = ecgPoints[EcgPosition.V3] as java.util.ArrayList<Double>,
////                    v4Data = ecgPoints[EcgPosition.V4] as java.util.ArrayList<Double>,
////                    v5Data = ecgPoints[EcgPosition.V5] as java.util.ArrayList<Double>,
////                    v6Data = ecgPoints[EcgPosition.V6] as java.util.ArrayList<Double>,
////                    lead2Data = ecgPoints[EcgPosition.LEAD_2] as java.util.ArrayList<Double>
////                )
////            }
////            EcgTestType.HRV -> {
////                HrvData(fiveMinuteData = ecgPoints[EcgPosition.LEAD_2] as java.util.ArrayList<Double>,
////                fftData = FFTComputation.computeFFT((ecgPoints[EcgPosition.LEAD_2] as java.util.ArrayList<Double>).toDoubleArray()))
////            }
//        }
//    }
//
//    fun checkForDataValidation(ecgData: HashMap<EcgPosition, ArrayList<Double>>): SpandanEngineException? {
//        if (ecgTestType == EcgTestType.LEAD_TWO) {
//            if (!checkForLeadII(getEcgDataFromHashMap(ecgData) as LeadTwoData))
//                return SpandanEngineException("${SpandanException.InsufficientDataException}: Lead II position data is not available.")
//        }
////        else if(ecgTestType == EcgTestType.SEVEN_LEAD && !checkSevenLeadTestCompletion(ecgData as SevenLeadData))
////            return SpandanEngineException("${SpandanException.InsufficientDataException}: Seven Lead data is not completely available to generate report.")
////        else if(ecgTestType == EcgTestType.TWELVE_LEAD && !checkTwelveLeadTestCompletion(ecgData as TwelveLeadData))
////            return SpandanEngineException("${SpandanException.InsufficientDataException}:Twelve Lead data is not completely available to generate report.")
////        else if(ecgTestType == EcgTestType.HRV && !checkForLeadII(ecgData as LeadTwoData))
////            return SpandanEngineException("${SpandanException.InsufficientDataException}: Hrv position data is not available.")
//        return null
//    }
//
//    fun proceedReport(userAge: Int, ecgData: HashMap<EcgPosition,ArrayList<Double>>): EcgReport {
//        return generateReport(userAge, ecgData)
//    }
//
//    private fun generateReport(userAge: Int, ecgData: HashMap<EcgPosition,ArrayList<Double>>): EcgReport =
//        when (ecgTestType) {
//            EcgTestType.LEAD_TWO -> openLeadIIReport(getEcgDataFromHashMap(ecgData) as LeadTwoData)
////        EcgTestType.HRV -> openHrvReport(userAge,ecgData)
//        EcgTestType.TWELVE_LEAD -> openTwelveLeadReport(userAge,getEcgDataFromHashMap(ecgData) as TwelveLeadData)
////        else -> openSevenLeadReport(ecgData)
//        }
//
//    private fun openLeadIIReport(leadIIData: LeadTwoData): EcgReport {
////        val leadIIData = ecgData as LeadTwoData
//        var processorResult: EcgProcessorResult? = null
//        processorResult =
//            EcgProcessor(
//                ProcessorType.LEAD_TWO, leadIIData
//            ).process()
//        val temp = processorResult.characteristics[0]!!
//        val characteristics = Characteristics(
//            pr = temp.pr,
//            qrs = temp.qrs,
//            qt = temp.qt,
//            qtc = temp.qtc,
//            rr = temp.rr,
//            heartRate = temp.heartRate,
//            stElevation = temp.stElevation,
//            qrsIntervals = temp.qrsIntervals,
//            rrIntervals = temp.rrIntervals,
//            prStartIndices = temp.prStartIndices,
//            prStopIndices = temp.prStopIndices,
//            pWavePoints = temp.pWavePoints,
//            qWavePoints = temp.qWavePoints,
//            sWavePoints = temp.sWavePoints,
//            tWaveEndPoints = temp.tWaveEndPoints,
//            tWavePoints = temp.tWavePoints,
//            rPeakPoints = temp.rPeakPoints,
//            averagePAmplitudeInLead = temp.averagePAmplitudeInLead,
//            averageQAmplitudeInLead = temp.averageQAmplitudeInLead,
//            averageRAmplitudeInLead = temp.averageRAmplitudeInLead,
//            averageSAmplitudeInLead = temp.averageSAmplitudeInLead,
//            averageTAmplitudeInLead = temp.averageTAmplitudeInLead,
//            pWidth = temp.pWidth,
//            tWidth = temp.tWidth,
//            qrsDirectionUpward = temp.qrsDirectionUpward,
//            ratioRS = temp.ratioRS,
//            ventricularActivationLOR = temp.ventricularActivationLOR,
//            ventricularActivationROR = temp.ventricularActivationROR,
//            concavity = temp.concavity,
//            frequencyOfPatternInQRS = temp.frequencyOfPatternInQRS,
//            frequencyOfPatternInRR = temp.frequencyOfPatternInRR,
//            pAmplitudeArrayInMv = temp.pAmplitudeArrayInMv,
//            TRRatioSatisfy = temp.TRRatioSatisfy,
//            TSRatioSatisfy = temp.TRRatioSatisfy
//        )
//        val t =
//            processorResult.conclusion as `in`.sunfox.healthcare.commons.java.ecg_processor.conclusions.LeadTwoConclusion
//        val conclusion = LeadTwoConclusion(
//            detection = t.detection,
//            ecgType = t.ecgType,
//            qrsType = t.qrsType,
//            pWaveType = t.pWaveType,
//            baselineWandering = t.baselineWandering,
//            powerLineInterference = t.powerLineInterference
//        )
//        return EcgReport(
//            reportType = EcgTestType.LEAD_TWO,
//            timeStamp = System.currentTimeMillis().toString(),
//            ecgCharacteristics = characteristics,
//            conclusion = conclusion
//        )
//    }
//
////    private fun openHrvReport(userAge:Int,ecgData: EcgData): EcgReport {
////        val hrvRawData = getEcgData(EcgPosition.LEAD_2)
//////        val finalHrvData = HrvData(
//////            hrvRawData,
//////            FFTComputation.computeFFT(hrvRawData.toDoubleArray())
//////        )
////        val finalHrvData = ecgData as HrvData
////        var processorResult: EcgProcessorResult? = null
////        processorResult =
////            EcgProcessor(
////                ProcessorType.HRV, finalHrvData
////            ).process(age = userAge)
////
////        return EcgReport(
////            reportType = EcgTestType.HRV,
////            timeStamp = System.currentTimeMillis().toString(),
////            ecgData = finalHrvData,
////            ecgCharacteristics = processorResult.characteristics[0]!!,
////            conclusion = processorResult.conclusion,
////            imgData = ImageData()
////        )
////    }
//
////    private fun openSevenLeadReport(ecgData: EcgData): EcgReport {
//////        val sevenLeadData = SevenLeadData(
//////            v1Data = getEcgData(EcgPosition.V1),
//////            v2Data = getEcgData(EcgPosition.V2),
//////            v3Data = getEcgData(EcgPosition.V3),
//////            v4Data = getEcgData(EcgPosition.V4),
//////            v5Data = getEcgData(EcgPosition.V5),
//////            v6Data = getEcgData(EcgPosition.V6),
//////            lead2Data = getEcgData(EcgPosition.LEAD_2)
//////        )
////        val sevenLeadData = ecgData as SevenLeadData
////        val processorResult: EcgProcessorResult?
////        processorResult =
////            EcgProcessor(
////                ProcessorType.SEVEN_LEAD, sevenLeadData
////            ).process(inputStream = inputStream)
////        return EcgReport(
////            reportType = EcgTestType.SEVEN_LEAD,
////            timeStamp = System.currentTimeMillis().toString(),
////            ecgData = sevenLeadData,
////            ecgCharacteristics = processorResult.characteristics[0]!!,
////            conclusion = processorResult.conclusion,
////            imgData = ImageData()
////        )
////    }
////
//    private fun openTwelveLeadReport(userAge: Int,ecgData: EcgData): EcgReport {
////        val twelveLeadData = TwelveLeadData(
////            v1Data = getEcgData(EcgPosition.V1),
////            v2Data = getEcgData(EcgPosition.V2),
////            v3Data = getEcgData(EcgPosition.V3),
////            v4Data = getEcgData(EcgPosition.V4),
////            v5Data = getEcgData(EcgPosition.V5),
////            v6Data = getEcgData(EcgPosition.V6),
////            lead1Data = getEcgData(EcgPosition.LEAD_1),
////            lead2Data = getEcgData(EcgPosition.LEAD_2),
////            lead3Data = ArrayList(),
////            avrData = ArrayList(),
////            avfData = ArrayList(),
////            avlData = ArrayList()
////        )
//        val twelveLeadData = ecgData as TwelveLeadData
//        twelveLeadData.lead1Data = Filters.movingAverage(twelveLeadData.lead1Data)
//        twelveLeadData.lead2Data = Filters.movingAverage(twelveLeadData.lead2Data)
//
//        val augmentedLeadGenerator = EcgProcessor.getAugmentedLeadGenerator(
//            twelveLeadData.lead1Data,
//            twelveLeadData.lead2Data
//        )
//        twelveLeadData.lead3Data = augmentedLeadGenerator.lead3Points
//        twelveLeadData.avfData = augmentedLeadGenerator.aVfPoints
//        twelveLeadData.avlData = augmentedLeadGenerator.aVlPoints
//        twelveLeadData.avrData = augmentedLeadGenerator.aVrPoints
//
//        var processorResult: EcgProcessorResult? = null
//        processorResult =
//            EcgProcessor(
//                ProcessorType.TWELVE_LEAD,
//                twelveLeadData,
//                applyFilter = false,
//                adjustRPeaks = false
//            ).process(
//                augmentedLeadGenerator = augmentedLeadGenerator,
//                inputStream = inputStream,
//                userAge
//            )
//
//    val t =
//        processorResult.conclusion as `in`.sunfox.healthcare.commons.java.ecg_processor.conclusions.TwelveLeadConclusion
//    val r = when(t.risk){
//        `in`.sunfox.healthcare.commons.java.ecg_processor.processing.Risk.LOW_RISK -> Risk.LOW_RISK
//        `in`.sunfox.healthcare.commons.java.ecg_processor.processing.Risk.HIGH_RISK -> Risk.HIGH_RISK
//        `in`.sunfox.healthcare.commons.java.ecg_processor.processing.Risk.MILD_RISK -> Risk.MILD_RISK
//        `in`.sunfox.healthcare.commons.java.ecg_processor.processing.Risk.MODERATE_RISK -> Risk.MODERATE_RISK
//    }
//    val conclusion = TwelveLeadConclusion(
//        detection = t.detection,
//        ecgType = t.ecgType,
//        recommendation = t.recommendation,
//        anomalies = t.anomalies,
//        risk = r
//    )
//
//    val temp = processorResult.characteristics[TwelveLeadDetection.EcgPosition.LEAD_2]!!
//    val characteristics = Characteristics(
//        pr = temp.pr,
//        qrs = temp.qrs,
//        qt = temp.qt,
//        qtc = temp.qtc,
//        rr = temp.rr,
//        heartRate = temp.heartRate,
//        stElevation = temp.stElevation,
//        qrsIntervals = temp.qrsIntervals,
//        rrIntervals = temp.rrIntervals,
//        prStartIndices = temp.prStartIndices,
//        prStopIndices = temp.prStopIndices,
//        pWavePoints = temp.pWavePoints,
//        qWavePoints = temp.qWavePoints,
//        sWavePoints = temp.sWavePoints,
//        tWaveEndPoints = temp.tWaveEndPoints,
//        tWavePoints = temp.tWavePoints,
//        rPeakPoints = temp.rPeakPoints,
//        averagePAmplitudeInLead = temp.averagePAmplitudeInLead,
//        averageQAmplitudeInLead = temp.averageQAmplitudeInLead,
//        averageRAmplitudeInLead = temp.averageRAmplitudeInLead,
//        averageSAmplitudeInLead = temp.averageSAmplitudeInLead,
//        averageTAmplitudeInLead = temp.averageTAmplitudeInLead,
//        pWidth = temp.pWidth,
//        tWidth = temp.tWidth,
//        qrsDirectionUpward = temp.qrsDirectionUpward,
//        ratioRS = temp.ratioRS,
//        ventricularActivationLOR = temp.ventricularActivationLOR,
//        ventricularActivationROR = temp.ventricularActivationROR,
//        concavity = temp.concavity,
//        frequencyOfPatternInQRS = temp.frequencyOfPatternInQRS,
//        frequencyOfPatternInRR = temp.frequencyOfPatternInRR,
//        pAmplitudeArrayInMv = temp.pAmplitudeArrayInMv,
//        TRRatioSatisfy = temp.TRRatioSatisfy,
//        TSRatioSatisfy = temp.TRRatioSatisfy
//    )
//
//        return EcgReport(
//            reportType = EcgTestType.TWELVE_LEAD,
//            timeStamp = System.currentTimeMillis().toString(),
//            ecgCharacteristics = characteristics,
//            conclusion = conclusion
//        )
//    }
//
//    fun getEcgTestType() = ecgTestType
//}