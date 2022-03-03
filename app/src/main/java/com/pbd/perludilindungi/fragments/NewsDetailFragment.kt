package com.pbd.perludilindungi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.pbd.perludilindungi.R


class NewsDetailFragment : Fragment() {
    private lateinit var webView: WebView

    companion object {
        var EXTRA_URL = "extra_URL"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webView)
        if (arguments != null) {
            val url = arguments?.getString(EXTRA_URL)
            webView.loadUrl(url?:"www.google.com")
        }

    }

}