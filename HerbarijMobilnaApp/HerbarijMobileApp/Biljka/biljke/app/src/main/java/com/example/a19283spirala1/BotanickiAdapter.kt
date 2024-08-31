package com.example.a19283spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BotanickiAdapter(
    var biljke: List<Biljka>,
    private val biljkaRepository: BiljkaRepository,
    private val pretragaUpaljena: Boolean = false,
    private val onClickListener: (Biljka) -> Unit
) : RecyclerView.Adapter<BotanickiAdapter.BiljkaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanickipogled,parent , false)
        return BiljkaViewHolder(view);
    }

    override fun getItemCount(): Int {
        return biljke.size
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.naziv.text = biljke[position].naziv
        holder.porodicaItem.text = biljke[position].family
        var klima: String = String()
        var zemlja: String = String()
        for(i in biljke[position].klimatskiTipovi){
            if(i == biljke[position].klimatskiTipovi.last())
                zemlja += i.opis
            else
                zemlja += i.opis+", "
        }
        for(i in biljke[position].zemljisniTipovi){
            if(i == biljke[position].zemljisniTipovi.last())
                zemlja += i.naziv
            else
                zemlja += i.naziv+", "
        }
        holder.zemljisniTipItem.text = zemlja
        holder.klimatskiTipItem.text = klima


        val context: Context = holder.slika.context

        CoroutineScope(Dispatchers.Main).launch {
            val biljkaBitmap = biljke[position].id?.let {
                biljkaRepository.getImage(it)
            }
            if (biljkaBitmap != null) {
                holder.slika.setImageBitmap(biljkaBitmap.bitmap)
            } else {
                val bitmap = TrefleDAO(context).getImage(biljke[position], biljkaRepository)
                holder.slika.setImageBitmap(bitmap)
            }
        }
        if (pretragaUpaljena){
        holder.itemView.setOnClickListener {
            val clickedBiljka = biljke[position]
            onClickListener(clickedBiljka)
            filter(clickedBiljka)
        }}
    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }

    fun filter(referentnaBiljka: Biljka) {
        val filteredList = biljke.filter { biljka ->
            biljka.family == referentnaBiljka.family &&
                    biljka.klimatskiTipovi.any { referentnaBiljka.klimatskiTipovi.contains(it) } &&
                    biljka.zemljisniTipovi.any { referentnaBiljka.zemljisniTipovi.contains(it) }
        }
        updateBiljke(filteredList)
    }




    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slika : ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv : TextView = itemView.findViewById(R.id.nazivItem)
        val porodicaItem : TextView = itemView.findViewById(R.id.porodicaItem)
        val zemljisniTipItem : TextView = itemView.findViewById(R.id.zemljisniTipItem)
        val klimatskiTipItem : TextView = itemView.findViewById(R.id.klimatskiTipItem)
    }

}