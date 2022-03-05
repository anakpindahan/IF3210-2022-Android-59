package com.pbd.perludilindungi.model

data class QrCodeResponseModel(val success: String, val code : Int, val message: String, val data: CheckInResponseData)

data class CheckInResponseData (val userStatus: String, val reason: String )
