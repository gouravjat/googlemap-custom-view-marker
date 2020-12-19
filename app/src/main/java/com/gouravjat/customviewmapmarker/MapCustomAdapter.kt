package com.gouravjat.customviewmapmarker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class MapCustomAdpater( activity: MapsActivity, itemList: ArrayList<String>) :  RecyclerView.Adapter<MapCustomAdpater.ViewHolder>() {

    private val itemList: ArrayList<String>
    private val activity: MapsActivity

    init {
        this.activity = activity
        this.itemList = itemList;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.marker_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemName: String = itemList.elementAt(position)
        holder.name.setText(itemName)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView

        init {
            name = itemView.findViewById(R.id.name)
            itemView.setOnClickListener {
                Toast.makeText(activity, name.text, Toast.LENGTH_SHORT).show()
            }
        }
    }


}
