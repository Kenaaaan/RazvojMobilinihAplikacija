package com.example.a19283spirala1


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a19283spirala1.BotanickiAdapter.BiljkaViewHolder


class MainActivity : AppCompatActivity() {
    private lateinit var biljkeRecyclerView : RecyclerView
    private lateinit var botanickiAdapter : BotanickiAdapter
    private lateinit var medicinskiAdapter : MedicinskiAdapter
    private lateinit var kuharskiAdapter : KuharskiAdapter
    private lateinit var spinner : Spinner
    private lateinit var button : Button
    private var trenutno : String = "Medicinski"
    private var biljkeLista = dajBiljke()
    private var biljkeFilter = dajBiljke()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        biljkeRecyclerView = findViewById(R.id.recyclerView1)
        spinner = findViewById(R.id.spinner1)
        button = findViewById(R.id.button1)

        val arraySpinner = listOf(
            "Medicinski",
            "Botanicki",
            "Kuharski"
        )

        //za spinner
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        //samo im dajem prazne liste i reklarisem ih mada sam to mogao i dole ali ajd sto da ne
        medicinskiAdapter = MedicinskiAdapter(listOf()){biljka -> medicinskiAdapter.filter(biljka) }
        botanickiAdapter = BotanickiAdapter(listOf()){ biljka -> botanickiAdapter.filter(biljka) }
        kuharskiAdapter = KuharskiAdapter(listOf() ){biljka -> kuharskiAdapter.filter(biljka) }


        //pocetni prikaz medicinski pogled
        biljkeRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        biljkeRecyclerView.adapter = medicinskiAdapter
        medicinskiAdapter.updateBiljke(biljkeLista)

        button.setOnClickListener{
            biljkeFilter = dajBiljke()
            if(trenutno=="Medicinski")
            {
                biljkeRecyclerView.adapter = MedicinskiAdapter(biljkeFilter){biljka -> medicinskiAdapter.filter(biljka) }
                medicinskiAdapter.updateBiljke(biljkeFilter)
                medicinskiAdapter.notifyDataSetChanged()

            }else if(trenutno == "Botanicki"){

                biljkeRecyclerView.adapter = BotanickiAdapter(biljkeFilter){biljka -> botanickiAdapter.filter(biljka) }
                botanickiAdapter.updateBiljke(biljkeFilter)
                botanickiAdapter.notifyDataSetChanged()

            }else{

                biljkeRecyclerView.adapter = KuharskiAdapter(biljkeFilter ){biljka -> kuharskiAdapter.filter(biljka) }
                kuharskiAdapter.updateBiljke(biljkeFilter)
                kuharskiAdapter.notifyDataSetChanged()

            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinner.selectedItem.toString() == "Medicinski") {

                    if(trenutno == "Botanicki")biljkeFilter = botanickiAdapter.biljke
                    if(trenutno == "Kuharski")biljkeFilter = kuharskiAdapter.biljke

                    biljkeRecyclerView.adapter = MedicinskiAdapter(biljkeFilter){biljka -> medicinskiAdapter.filter(biljka) }
                    medicinskiAdapter.updateBiljke(biljkeFilter)
                    medicinskiAdapter.notifyDataSetChanged()
                    trenutno="Medicinski"
                } else if (spinner.selectedItem.toString() == "Kuharski") {

                    if(trenutno == "Medicinski")biljkeFilter = medicinskiAdapter.biljke
                    if(trenutno == "Botanicki")biljkeFilter = botanickiAdapter.biljke

                    biljkeRecyclerView.adapter = KuharskiAdapter(biljkeFilter ){biljka -> kuharskiAdapter.filter(biljka) }
                    kuharskiAdapter.updateBiljke(biljkeFilter)
                    kuharskiAdapter.notifyDataSetChanged()
                    trenutno="Kuharski"
                } else {

                    if(trenutno == "Medicinski")biljkeFilter = medicinskiAdapter.biljke
                    if(trenutno == "Kuharski")biljkeFilter = kuharskiAdapter.biljke

                    biljkeRecyclerView.adapter = BotanickiAdapter(biljkeFilter){biljka -> botanickiAdapter.filter(biljka) }
                    botanickiAdapter.updateBiljke(biljkeFilter)
                    botanickiAdapter.notifyDataSetChanged()
                    trenutno="Botanicki"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("ne implementiraj nista")
            }

        }




    }
}