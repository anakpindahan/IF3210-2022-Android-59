package com.pbd.perludilindungi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FaskesAdapter(val results: ArrayList<Data>, val listener: FaskesAdapter.OnAdapterListener) : RecyclerView.Adapter<FaskesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FaskesAdapter.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_faskes, parent, false)
    )
    override fun getItemCount() = results.size
    override fun onBindViewHolder(holder: FaskesAdapter.ViewHolder, position: Int) {
        val result = results[position]
        holder.tvTitle.text = result.nama
        holder.tvAddress.text = result.alamat
        holder.tvPhone.text = result.telp
        holder.tvCode.text = result.kode
        holder.tvType.text = result.jenis_faskes
        holder.view.setOnClickListener{
            listener.onClick(result)
        }
    }


    interface OnAdapterListener {
        fun onClick(result : Data)
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.findViewById(R.id.faskes_carditem_title)
        var tvAddress: TextView = view.findViewById(R.id.faskes_carditem_address)
        var tvPhone: TextView = view.findViewById(R.id.faskes_carditem_phone)
        var tvCode: TextView = view.findViewById(R.id.faskes_carditem_code)
        var tvType: TextView = view.findViewById(R.id.faskes_carditem_type)
    }
    fun setData (data : List<Data>){
        System.out.println("Halo dari setData faskesAdapter")
        System.out.println(data)
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
}