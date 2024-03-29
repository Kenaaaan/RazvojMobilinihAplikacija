package com.example.a19283spirala1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*

class MainActivity : AppCompatActivity() {
    private lateinit var biljkeRecyclerView : RecyclerView
    private lateinit var botanickiAdapter : BotanickiAdapter
    private lateinit var medicinskiAdapter : MedicinskiAdapter
    private lateinit var kuharskiAdapter : KuharskiAdapter
    private lateinit var spinner: Spinner
    private var biljkeLista = dajBiljke()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biljkeRecyclerView = findViewById(R.id.recyclerView1)
        spinner = findViewById(R.id.spinner1)

        val arraySpinner = listOf(
            "Medicinski",
            "Botanicki",
            "Kuharski"
        )
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        medicinskiAdapter = MedicinskiAdapter(listOf())
        botanickiAdapter = BotanickiAdapter(listOf() )
        kuharskiAdapter = KuharskiAdapter(listOf())

        biljkeRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        biljkeRecyclerView.adapter = medicinskiAdapter
        medicinskiAdapter.updateBiljke(biljkeLista)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinner.selectedItem.toString() == "Medicinski") {
                    biljkeRecyclerView.adapter = medicinskiAdapter
                    medicinskiAdapter.updateBiljke(biljkeLista)
                } else if (spinner.selectedItem.toString() == "Kuharski") {
                    biljkeRecyclerView.adapter = kuharskiAdapter
                    kuharskiAdapter.updateBiljke(biljkeLista)
                } else {
                    biljkeRecyclerView.adapter = botanickiAdapter
                    botanickiAdapter.updateBiljke(biljkeLista)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



    }
}