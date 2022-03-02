package com.pbd.perludilindungi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule

@GlideModule
class NewsAdapter(val results: ArrayList<News>, val listener: OnAdapterListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.tvTitle.text = result.title
        holder.tvDate.text = result.pubDate
        Glide.with(holder.view)
            .load(result.enclosure["_url"])
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .centerCrop()
            .into(holder.imgPhoto)

        holder.view.setOnClickListener{
            listener.onClick(result)
        }
    }

    override fun getItemCount() = results.size

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {
        var imgPhoto: ImageView = view.findViewById(R.id.img_item_photo)
        var tvTitle: TextView = view.findViewById(R.id.tv_item_title)
        var tvDate: TextView = view.findViewById(R.id.tv_item_date)
    }

    fun setData (data : List<News>){
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result : News)
    }
}