package com.ridzi.pokemon1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class CustomListAdapter(
    private val context: Context,
    private val dataList: ArrayList<HashMap<String, String>>,
    private val onItemClick: (HashMap<String, String>) -> Unit
) : BaseAdapter(), Filterable {

    private var filteredData = dataList

    override fun getCount(): Int {
        return filteredData.size
    }

    override fun getItem(position: Int): Any {
        return filteredData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = getItem(position) as HashMap<String, String>
        viewHolder.bind(item)

        // Set an item click listener for each item view
        view.setOnClickListener {
            onItemClick(item)
        }

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredList = ArrayList<HashMap<String, String>>()

                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(dataList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in dataList) {
                        if (item["name"]?.toLowerCase()?.contains(filterPattern) == true) {
                            filteredList.add(item)
                        }
                    }
                }

                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as ArrayList<HashMap<String, String>>
                notifyDataSetChanged()
            }
        }
    }

    private class ViewHolder(view: View) {
        private val nameTextView: TextView = view.findViewById(R.id.name)

        fun bind(item: HashMap<String, String>) {
            nameTextView.text = item["name"]
        }
    }
}
