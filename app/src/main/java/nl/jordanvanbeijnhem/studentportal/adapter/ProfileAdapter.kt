package nl.jordanvanbeijnhem.studentportal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_portal.view.*
import nl.jordanvanbeijnhem.studentportal.R
import nl.jordanvanbeijnhem.studentportal.model.Portal

class PortalAdapter(private val portals: List<Portal>, val clickListener: (Portal) -> Unit) :
    RecyclerView.Adapter<PortalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_portal,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return portals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portals[position], clickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(portal: Portal, clickListener: (Portal) -> Unit) {
            itemView.tvTitle.text = portal.title
            itemView.tvUrl.text = portal.url
            itemView.setOnClickListener { clickListener(portal) }
        }
    }
}