package com.pbd.perludilindungi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pbd.perludilindungi.BookmarkAdapter
import com.pbd.perludilindungi.Data
import com.pbd.perludilindungi.R
import com.pbd.perludilindungi.room.Bookmark
import com.pbd.perludilindungi.room.BookmarkDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookmarkFragment : Fragment() {

    //attribute
    val db by lazy { BookmarkDB(requireActivity()) }
    lateinit var bookmarkAdapter: BookmarkAdapter


    //method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getFaskesFromDB()
    }

    private fun getFaskesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarkList = db.bookmarkDao().getBookmarks()
            CoroutineScope(Dispatchers.Main).launch {
                showData(bookmarkList)
            }
        }

    }

    private fun setupRecyclerView() {
        bookmarkAdapter = BookmarkAdapter(arrayListOf(), object : BookmarkAdapter.OnAdapterListener {
            override fun onClick(result: Bookmark) {
                val detailLocationFragment = BookmarkFragment()

            }
        })
        val recyclerView: RecyclerView = requireView().findViewById(R.id.bookmark_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = bookmarkAdapter
        }
    }

    private fun showData(items: List<Bookmark>) {
        bookmarkAdapter.setData(items)
    }

}