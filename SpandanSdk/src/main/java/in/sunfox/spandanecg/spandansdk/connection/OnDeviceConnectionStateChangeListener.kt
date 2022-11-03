package `in`.sunfox.spandanecg.spandansdk.connection

import `in`.sunfox.spandanecg.spandansdk.enums.DeviceConnectionState


interface OnDeviceConnectionStateChangeListener {
    fun onDeviceConnectionStateChanged(deviceConnectionState: DeviceConnectionState)
}