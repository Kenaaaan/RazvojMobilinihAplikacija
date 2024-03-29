package com.example.a19283spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MedicinskiAdapter(
    private var biljke: List<Biljka>
) : RecyclerView.Adapter<MedicinskiAdapter.BiljkaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicinskipogled, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return biljke.size
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv
        holder.upozorenje.text = biljke[position].medicinskoUpozorenje
        holder.korist1.text = biljke[position].medicinskeKoristi[0].opis
        if(biljke[position].medicinskeKoristi.size>1){

            holder.korist2.text = biljke[position].medicinskeKoristi[1].opis

        }else
        {
            holder.korist2.text = ""
        }

        if(biljke[position].medicinskeKoristi.size>2){

            holder.korist3.text = biljke[position].medicinskeKoristi[2].opis

        }else
        {
            holder.korist3.text = ""
        }

        val context: Context = holder.slika.context
        var id: Int = context.resources
            .getIdentifier("ic_launcher_background", "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        holder.slika.setImageResource(id)

    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }


    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val upozorenje : TextView = itemView.findViewById(R.id.upozorenjeItem)
        val slika : ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv : TextView = itemView.findViewById(R.id.nazivItem)
        val korist1 : TextView = itemView.findViewById(R.id.korist1Item)
        val korist2 : TextView = itemView.findViewById(R.id.korist2Item)
        val korist3 : TextView = itemView.findViewById(R.id.korist3Item)
    }


}