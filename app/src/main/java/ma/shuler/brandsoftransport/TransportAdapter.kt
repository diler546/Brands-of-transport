package ma.shuler.brandsoftransport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TransportAdapter(private val context: Context, private val transports: List<Transport>) : BaseAdapter() {

    override fun getCount(): Int {
        return transports.size
    }

    override fun getItem(position: Int): Any {
        return transports[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
            holder = ViewHolder()
            holder.text = view.findViewById(android.R.id.text1)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = transports[position]
        holder.text.text = item.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    private class ViewHolder {
        lateinit var text: TextView
    }
}