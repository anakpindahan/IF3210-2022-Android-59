package com.pbd.perludilindungi

data class NewsModel ( val success: String, val message: String?, val count_total: String, val results: List<News> )

data class News(val title: String, val link: List<String>, val pubDate: String, val enclosure: Map<String, String>)