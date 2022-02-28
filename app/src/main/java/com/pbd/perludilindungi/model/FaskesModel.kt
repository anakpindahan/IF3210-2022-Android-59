package com.pbd.perludilindungi
import com.google.gson.annotations.SerializedName


data class FaskesModel (

    @SerializedName("success"     ) var success    : Boolean,
    @SerializedName("message"     ) var message    : String?         = null,
    @SerializedName("count_total" ) var countTotal : Int?            = null,
    @SerializedName("data"        ) var data       : List<Data> = arrayListOf()

)

data class Detail (

    @SerializedName("id"               ) var id             : Int?    = null,
    @SerializedName("kode"             ) var kode           : String? = null,
    @SerializedName("batch"            ) var batch          : String? = null,
    @SerializedName("divaksin"         ) var divaksin       : Int?    = null,
    @SerializedName("divaksin_1"       ) var divaksin1      : Int?    = null,
    @SerializedName("divaksin_2"       ) var divaksin2      : Int?    = null,
    @SerializedName("batal_vaksin"     ) var batalVaksin    : Int?    = null,
    @SerializedName("batal_vaksin_1"   ) var batalVaksin1   : Int?    = null,
    @SerializedName("batal_vaksin_2"   ) var batalVaksin2   : Int?    = null,
    @SerializedName("pending_vaksin"   ) var pendingVaksin  : Int?    = null,
    @SerializedName("pending_vaksin_1" ) var pendingVaksin1 : Int?    = null,
    @SerializedName("pending_vaksin_2" ) var pendingVaksin2 : Int?    = null,
    @SerializedName("tanggal"          ) var tanggal        : String? = null

)


data class Data (

    @SerializedName("id"           ) var id          : Int?              = null,
    @SerializedName("kode"         ) var kode        : String?           = null,
    @SerializedName("nama"         ) var nama        : String?           = null,
    @SerializedName("kota"         ) var kota        : String?           = null,
    @SerializedName("provinsi"     ) var provinsi    : String?           = null,
    @SerializedName("alamat"       ) var alamat      : String?           = null,
    @SerializedName("latitude"     ) var latitude    : String?           = null,
    @SerializedName("longitude"    ) var longitude   : String?           = null,
    @SerializedName("telp"         ) var telp        : String?           = null,
    @SerializedName("jenis_faskes" ) var jenisFaskes : String?           = null,
    @SerializedName("kelas_rs"     ) var kelasRs     : String?           = null,
    @SerializedName("status"       ) var status      : String?           = null,
    @SerializedName("detail"       ) var detail      : ArrayList<Detail> = arrayListOf(),
    @SerializedName("source_data"  ) var sourceData  : String?           = null

)