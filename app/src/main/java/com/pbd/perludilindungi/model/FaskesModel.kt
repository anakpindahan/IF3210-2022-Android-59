package com.pbd.perludilindungi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class FaskesModel (

    var success    : Boolean,
    var message    : String?,
    var countTotal : Int?,
    var data       : List<Data>

)

@Parcelize
data class Data (

    var id          : Int,
    var kode        : String?,
    var nama        : String?,
    var kota        : String?,
    var provinsi    : String?,
    var alamat      : String?,
    var latitude    : String?,
    var longitude   : String?,
    var telp        : String?,
    var jenis_faskes : String?,
    var kelas_rs     : String?,
    var status      : String?,

) : Parcelable