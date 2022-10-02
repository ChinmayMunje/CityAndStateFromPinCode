package com.gtappdevelopers.cityandstate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostOfficeRVAdapter(
    private val postOfficeList: List<PostOfficeRVModal>,
    private val context: Context
) :
    RecyclerView.Adapter<PostOfficeRVAdapter.PostOfficeItemViewHolder>() {
    class PostOfficeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deliveryIV: ImageView = itemView.findViewById(R.id.idIVDelivery)
        val postOfficeNameTV: TextView = itemView.findViewById(R.id.idTVPostOfficeName)
        val postOfficeDescTV: TextView = itemView.findViewById(R.id.idTVPostOfficeBranch)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostOfficeRVAdapter.PostOfficeItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.postoffice_rv_item,
            parent, false
        )
        return PostOfficeItemViewHolder(itemView)

    }

    override fun onBindViewHolder(
        holder: PostOfficeRVAdapter.PostOfficeItemViewHolder,
        position: Int
    ) {
        val postOffice = postOfficeList.get(position)
        holder.postOfficeDescTV.text = postOffice.postOfficeBranch
        holder.postOfficeNameTV.text = postOffice.postOfficeName
        if (postOffice.deliveryStatus.equals("Non-Delivery")) {
            holder.deliveryIV.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return postOfficeList.size
    }
}