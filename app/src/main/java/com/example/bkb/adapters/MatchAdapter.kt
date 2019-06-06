package com.example.bkb.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bkb.R
import com.example.bkb.database.entities.Match
import com.example.bkb.interfaces.RecyclerViewClickListener
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class MatchAdapter(var items: List<Match>, var listener: View.OnClickListener) :
    RecyclerView.Adapter<MatchAdapter.MatchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        var holder = MatchHolder(view)
        holder.onClick(listener)

        return holder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        holder.bind(items.get(position))
    }

    class MatchHolder(itemView: View) : RecyclerView.ViewHolder(itemView), RecyclerViewClickListener {

        override fun onClick(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

        fun bind(item: Match) = with(itemView) {
            tv_teamA.text = item.homeTeam
            tv_teamB.text = item.guestTeam

            if (item.isOver) {
                tv_state.text = "Finalizado"
                tv_state.setTextColor(Color.parseColor("#CC2222"))
            } else {
                tv_state.text = "En juego"
                tv_state.setTextColor(Color.parseColor("#229922"))
            }

            val calendar = Calendar.getInstance()
            calendar.time = item.date

            var minutos = ""
            if (calendar.get(Calendar.MINUTE).toString().length == 1) {
                minutos = "0" + calendar.get(Calendar.MINUTE).toString()
            } else {
                minutos = calendar.get(Calendar.MINUTE).toString()
            }
        }

    }

    fun setData(newList: List<Match>) {
        items = newList
        notifyDataSetChanged()
    }

}