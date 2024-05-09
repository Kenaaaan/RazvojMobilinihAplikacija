package com.example.a19283spirala1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KuharskiAdapter(
    var biljke: List<Biljka>,
    private val onClickListener :  (Biljka) -> Unit
) : RecyclerView.Adapter<KuharskiAdapter.BiljkaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kuharskipogled,parent , false)
        return BiljkaViewHolder(view);
    }

    override fun getItemCount(): Int {
        return biljke.size
    }


    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv
        holder.profilOkusaItem.text = biljke[position].profilOkusa.opis

        if (position < biljke.size) {
            val currentHerb = biljke[position]
            if (currentHerb.jela.isNotEmpty()) {
                holder.jelo1Item.text = currentHerb.jela[0]
                holder.jelo2Item.text = if (currentHerb.jela.size > 1) currentHerb.jela[1] else ""
                holder.jelo3Item.text = if (currentHerb.jela.size > 2) currentHerb.jela[2] else ""
            } else {
                holder.jelo1Item.text = ""
                holder.jelo2Item.text = ""
                holder.jelo3Item.text = "" // Or display a custom message like "No dishes available"
            }
        } else {
            Log.e("KuharskiAdapter", "Invalid position: $position")
        }

        val context: Context = holder.slika.context
        var id: Int = context.resources
            .getIdentifier("ic_launcher_background", "drawable", context.packageName)
        if (id == 0) id = context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        holder.slika.setImageResource(id)

        holder.itemView.setOnClickListener {
            onClickListener(biljke[position])
            filter(biljke[position])
        }
    }

    fun filter(biljka: Biljka) {
        val filteredList = mutableListOf<Biljka>()
        for (index in dajBiljke().indices) {
            val otherBiljka = dajBiljke()[index]
            if (biljka.profilOkusa == otherBiljka.profilOkusa ||
                biljka.jela.isNotEmpty() && otherBiljka.jela.any { jelo -> biljka.jela.contains(jelo) }) {
                filteredList.add(otherBiljka)
            }
        }
        if (filteredList.isEmpty()) {
            filteredList.add(biljka)
        }
        updateBiljke(filteredList)
    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }


    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slika : ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv : TextView = itemView.findViewById(R.id.nazivItem)
        val profilOkusaItem : TextView = itemView.findViewById(R.id.profilOkusaItem)
        val jelo1Item : TextView = itemView.findViewById(R.id.jelo1Item)
        val jelo2Item : TextView = itemView.findViewById(R.id.jelo2Item)
        val jelo3Item : TextView = itemView.findViewById(R.id.jelo3Item)
    }


}