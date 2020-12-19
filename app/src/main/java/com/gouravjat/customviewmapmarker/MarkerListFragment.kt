package com.gouravjat.customviewmapmarker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MarkerListFragment(itemList: ArrayList<String>) : Fragment() {
    var itemList: ArrayList<String>
    lateinit var recyclerView: RecyclerView
    var mapCustomAdpater: MapCustomAdpater? = null

    init {
        this.itemList = itemList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        var view = inflater.inflate(R.layout.map_container, null)
        recyclerView = view!!.findViewById(R.id.map_item_recycler)
        recyclerView.setLayoutManager(mLayoutManager)
        mapCustomAdpater = MapCustomAdpater(activity as MapsActivity, itemList)
        recyclerView.setAdapter(mapCustomAdpater)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}