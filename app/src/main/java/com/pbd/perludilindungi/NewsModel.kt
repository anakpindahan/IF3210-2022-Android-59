package com.pbd.perludilindungi

import java.util.*

//{
//    "success": true,
//    "message": "Success",
//    "count_total": 10,
//    "results": [
//    {
//        "title": "Situasi COVID-19 di Indonesia (Update per 12 Februari 2022)",
//        "link": [
//        "https://covid19.go.id/artikel/2022/02/12/situasi-covid-19-di-indonesia-update-12-februari-2022"
//        ],
//        "guid": "https://covid19.go.id/artikel/2022/02/12/situasi-covid-19-di-indonesia-update-12-februari-2022",
//        "pubDate": "Sat, 12 Feb 2022 19:45:00 +0700",
//        "description": {
//        "__cdata": "<p>Situasi COVID-19 di Indonesia (Update per 12 Februari 2022)</p>"
//    },
//        "enclosure": {
//        "_url": "https://covid19.go.id/storage/app/uploads/public/620/7ab/d42/6207abd427c6f835861278.jpeg",
//        "_length": "70000",
//        "_type": "image/jpeg"
//    }
//    }
//    ]
//}
data class NewsModel ( val success: String, val message: String?, val count_total: String, val results: List<News> )

data class News(val title: String, val link: List<String>, val pubDate: String, val enclosure: Map<String, String>)