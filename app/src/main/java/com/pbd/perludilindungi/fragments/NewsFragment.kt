package com.pbd.perludilindungi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pbd.perludilindungi.News
import com.pbd.perludilindungi.NewsAdapter
import com.pbd.perludilindungi.NewsModel
import com.pbd.perludilindungi.R
import com.pbd.perludilindungi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private val TAG: String = "NewsFragment"
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getNewsDataFromApi()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener{
            override fun onClick(result: News) {
                val detailFragment = NewsDetailFragment()
                val mBundle = Bundle()
                mBundle.putString(NewsDetailFragment.EXTRA_URL, result.link[0] ?: "www.google.com")

                detailFragment.arguments = mBundle
                val mFragmentManager = parentFragmentManager
                mFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_container, detailFragment, NewsDetailFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }

            }
        })
        val recyclerView: RecyclerView = requireView().findViewById(R.id.news_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = newsAdapter
        }
    }

    private fun getNewsDataFromApi() {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        ApiService.endpoint.getNews()
            .enqueue(object : Callback<NewsModel> {
                override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful){
                        val results = response.body()
                        results?.let { showData(it) }
                    }
                }

                override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    printLog(t.toString())
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showData(items: NewsModel){
        if (items.success){
            val news = items.results
            newsAdapter.setData(news)
        }

    }
}