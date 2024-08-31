package com.example.a19283spirala1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var biljkeRecyclerView: RecyclerView
    private lateinit var botanickiAdapter: BotanickiAdapter
    private lateinit var medicinskiAdapter: MedicinskiAdapter
    private lateinit var kuharskiAdapter: KuharskiAdapter
    private lateinit var spinner: Spinner
    private lateinit var button: Button
    private lateinit var varijabla: EditText
    private lateinit var dodajBiljkuButton: Button
    private lateinit var pretraziBiljkuButton: Button
    private lateinit var bojeSpinner: Spinner
    private var trenutno: String = "Medicinski"
    private var biljkeLista = dajBiljke()
    private var biljkeFilter = dajBiljke()
    private lateinit var biljkaRepository: BiljkaRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        val biljkaDatabase = BiljkaDatabase.getInstance(this)
        biljkaRepository = BiljkaRepository(biljkaDatabase.biljkaDao())

        CoroutineScope(Dispatchers.Main).launch {
            biljkeLista = dajBiljke()
            biljkeLista.forEach { biljka -> biljkaRepository.saveBiljka(biljka) }
            biljkeLista = biljkaRepository.getAllBiljkas()
        }

        varijabla = findViewById(R.id.pretragaET)
        bojeSpinner = findViewById(R.id.bojaSPIN)
        pretraziBiljkuButton = findViewById(R.id.brzaPretraga)
        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        spinner = findViewById(R.id.modSpinner)
        button = findViewById(R.id.button1)
        dodajBiljkuButton = findViewById(R.id.novaBiljkaBtn)

        val arraySpinner = listOf("Medicinski", "Botanicki", "Kuharski")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        medicinskiAdapter = MedicinskiAdapter(listOf(),biljkaRepository) { biljka -> medicinskiAdapter.filter(biljka) }
        botanickiAdapter = BotanickiAdapter(listOf(),biljkaRepository) { biljka -> botanickiAdapter.filter(biljka) }
        kuharskiAdapter = KuharskiAdapter(listOf(),biljkaRepository) { biljka -> kuharskiAdapter.filter(biljka) }

        biljkeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        biljkeRecyclerView.adapter = medicinskiAdapter
        medicinskiAdapter.updateBiljke(biljkeLista)

        val adapterBoja = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("red", "blue", "purple", "yellow", "orange", "brown", "green"))
        adapterBoja.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bojeSpinner.adapter = adapterBoja

        dodajBiljkuButton.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent)
        }
        nePrikazuj(false)

        button.setOnClickListener {
            biljkeFilter = dajBiljke()
            when (trenutno) {
                "Medicinski" -> {
                    biljkeRecyclerView.adapter = MedicinskiAdapter(biljkeFilter,biljkaRepository) { biljka -> medicinskiAdapter.filter(biljka) }
                    medicinskiAdapter.updateBiljke(biljkeFilter)
                    medicinskiAdapter.notifyDataSetChanged()
                }
                "Botanicki" -> {
                    biljkeRecyclerView.adapter = BotanickiAdapter(biljkeFilter,biljkaRepository) { biljka -> botanickiAdapter.filter(biljka) }
                    botanickiAdapter = BotanickiAdapter(biljkeFilter,biljkaRepository) { biljka -> botanickiAdapter.filter(biljka) }
                    botanickiAdapter.updateBiljke(biljkeFilter)
                    botanickiAdapter.notifyDataSetChanged()
                }
                else -> {
                    //kuharski
                    biljkeRecyclerView.adapter = KuharskiAdapter(biljkeFilter,biljkaRepository) { biljka -> kuharskiAdapter.filter(biljka) }
                    kuharskiAdapter.updateBiljke(biljkeFilter)
                    kuharskiAdapter.notifyDataSetChanged()
                }
            }
        }

        pretraziBiljkuButton.setOnClickListener {
            val pretragaText = varijabla.text.toString().trim()
            val selectedColor = bojeSpinner.selectedItem.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                if (pretragaText.isNotEmpty()) {
                    System.out.println("usaoo si kenanneeee")
                    val listaBiljkiIsteBoje = TrefleDAO(this@MainActivity).getPlantsWithFlowerColor(selectedColor, pretragaText)
                    if (listaBiljkiIsteBoje.isNotEmpty()) {
                        launch(Dispatchers.Main) {
//                            botanickiAdapter.updateBiljke(listaBiljkiIsteBoje)
                            botanickiAdapter = BotanickiAdapter(listaBiljkiIsteBoje,biljkaRepository,true){}
                        }
                    } else {
                        println("wow")
                    }
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "nije uneseno nista", Toast.LENGTH_SHORT).show()
                        varijabla.error = "nije uneseseno nista"
                    }
                }
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (spinner.selectedItem.toString()) {
                    "Medicinski" -> {
                        if (trenutno == "Botanicki") biljkeFilter = botanickiAdapter.biljke
                        if (trenutno == "Kuharski") biljkeFilter = kuharskiAdapter.biljke
                        biljkeRecyclerView.adapter = MedicinskiAdapter(biljkeFilter,biljkaRepository) { biljka -> medicinskiAdapter.filter(biljka) }
                        medicinskiAdapter.updateBiljke(biljkeFilter)
                        nePrikazuj(false)
                        medicinskiAdapter.notifyDataSetChanged()
                        trenutno = "Medicinski"
                    }
                    "Kuharski" -> {
                        if (trenutno == "Medicinski") biljkeFilter = medicinskiAdapter.biljke
                        if (trenutno == "Botanicki") biljkeFilter = botanickiAdapter.biljke
                        biljkeRecyclerView.adapter = KuharskiAdapter(biljkeFilter,biljkaRepository) { biljka -> kuharskiAdapter.filter(biljka) }
                        kuharskiAdapter.updateBiljke(biljkeFilter)
                        nePrikazuj(false)
                        kuharskiAdapter.notifyDataSetChanged()
                        trenutno = "Kuharski"
                    }
                    else -> {
                        if (trenutno == "Medicinski") biljkeFilter = medicinskiAdapter.biljke
                        if (trenutno == "Kuharski") biljkeFilter = kuharskiAdapter.biljke
                        botanickiAdapter = BotanickiAdapter(biljkeFilter,biljkaRepository) { biljka -> botanickiAdapter.filter(biljka) }
                        botanickiAdapter.updateBiljke(biljkeFilter)
                        nePrikazuj(true)
                        botanickiAdapter.notifyDataSetChanged()
                        trenutno = "Botanicki"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }
    }

    private fun nePrikazuj(show: Boolean) {
        if (show) {
            varijabla.visibility = View.VISIBLE
            bojeSpinner.visibility = View.VISIBLE
            pretraziBiljkuButton.visibility = View.VISIBLE
        } else {
            varijabla.visibility = View.GONE
            pretraziBiljkuButton.visibility = View.GONE
            bojeSpinner.visibility = View.GONE
        }
    }
}
