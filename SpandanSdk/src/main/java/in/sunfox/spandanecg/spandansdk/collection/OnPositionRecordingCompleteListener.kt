package `in`.sunfox.spandanecg.spandansdk.collection

import `in`.sunfox.spandanecg.spandansdk.enums.EcgPosition


interface OnPositionRecordingCompleteListener {
    fun onPositionRecordingComplete(ecgPosition: EcgPosition)
}