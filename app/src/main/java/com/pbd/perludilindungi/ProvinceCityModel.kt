package com.pbd.perludilindungi

import java.util.*
//{
//    "curr_val":"Provinsi",
//    "message":"<label for='sw_win'>No message returned</label>",
//    "results":[
//    {
//        "key":"ACEH",
//        "value":"ACEH"
//    },
//    {
//        "key":"BALI",
//        "value":"BALI"
//    },
//    {
//        "key":"BANTEN",
//        "value":"BANTEN"
//    },
//    ]
//}
data class ProvinceCityModel (
    val curr_val: String,
    val message: String,
    val results: List<Place>

    )
data class Place(
    val key : String,
    val value : String

    )