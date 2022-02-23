package com.pbd.perludilindungi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.pbd.perludilindungi.News
import com.pbd.perludilindungi.NewsModel
import com.pbd.perludilindungi.R
import com.pbd.perludilindungi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private val TAG: String = "NewsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNewsDataFromApi()
        Log.d(TAG, "TES FRAGMENT")
        val newsText : TextView = view.findViewById(R.id.news_text)
        newsText.setOnClickListener{
            Toast.makeText(context, "News Fragment", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNewsDataFromApi() {
        ApiService.endpoint.getNews()
            .enqueue(object : Callback<NewsModel> {
                override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let { printItem(it.results) }
                    }
                }

                override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                    printLog(t.toString())
                }

            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun printItem(items: List<News>){
        for (news in items){
            printLog("News : ${news.link[0]} ")
        }
    }
}