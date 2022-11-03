package `in`.sunfox.spandanecg.spandansdk.collection


import `in`.sunfox.spandanecg.spandansdk.enums.EcgPosition

interface EcgTestCallback {
//    fun onTestComplete(ecgData: ArrayList<Double>?)
    fun onTestFailed(statusCode:Int)
    fun onTestStarted(ecgPosition: EcgPosition)
    fun onElapsedTimeChanged(elapsedTime: Long, remainingTime: Long)
    fun onReceivedData(data: String)
    fun onPositionRecordingComplete(ecgPosition: EcgPosition, ecgPoints: ArrayList<Double>?)
}