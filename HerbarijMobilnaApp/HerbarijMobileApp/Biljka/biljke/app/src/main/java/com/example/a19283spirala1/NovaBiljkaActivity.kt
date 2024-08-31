package com.example.a19283spirala1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AbsListView.CHOICE_MODE_SINGLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.contains
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.core.view.size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NovaBiljkaActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var buttonSlikaj : Button
    private lateinit var buttonDodajJelo : Button
    private lateinit var buttonDodajBiljku : Button
    private lateinit var editTextMedUpozorenje : EditText
    private lateinit var editTextNaziv : EditText
    private lateinit var editTextPorodica : EditText
    private lateinit var editTextJela : EditText
    private lateinit var listaJela : ListView
    private lateinit var listaMedicinskaKorist : ListView
    private lateinit var listaZemljisniTip : ListView
    private lateinit var listaKlimatskiTip: ListView
    private lateinit var listaProfilOkusa : ListView
    private lateinit var biljkaRepository: BiljkaRepository
    private var jela : ArrayList<String> = ArrayList<String>()
    private var trenutnoKliknut : Int = -1
    private lateinit var slika : ImageView
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_biljka)
        val biljkaDatabase = BiljkaDatabase.getInstance(this)
        biljkaRepository = BiljkaRepository(biljkaDatabase.biljkaDao())////////////////
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //buttoni
        buttonSlikaj = findViewById(R.id.uslikajBiljkuBtn)
        buttonDodajBiljku = findViewById(R.id.dodajBiljkuBtn)
        buttonDodajJelo = findViewById(R.id.dodajJeloBtn)

        //imageView
        slika = findViewById(R.id.slika)

        //editTextovi
        editTextPorodica = findViewById(R.id.porodicaET)
        editTextNaziv = findViewById(R.id.nazivET)
        editTextMedUpozorenje = findViewById(R.id.medicinskoUpozorenjeET)
        editTextJela = findViewById(R.id.jeloET)

        //listViewovi
        listaMedicinskaKorist = findViewById(R.id.medicinskaKoristLV)
        listaJela = findViewById(R.id.jelaLV)
        listaProfilOkusa = findViewById(R.id.profilOkusaLV)
        listaKlimatskiTip = findViewById(R.id.klimatskiTipLV)
        listaZemljisniTip = findViewById(R.id.zemljisniTipLV)

        //set odabir modova
        listaMedicinskaKorist.setChoiceMode(CHOICE_MODE_MULTIPLE)
        listaKlimatskiTip.setChoiceMode(CHOICE_MODE_MULTIPLE)
        listaZemljisniTip.setChoiceMode(CHOICE_MODE_MULTIPLE)
        listaProfilOkusa.setChoiceMode(CHOICE_MODE_SINGLE)


        listaMedicinskaKorist.setAdapter(ArrayAdapter<MedicinskaKorist>(this, android.R.layout.select_dialog_multichoice, MedicinskaKorist.values()))
        listaKlimatskiTip.setAdapter(ArrayAdapter<KlimatskiTip>(this, android.R.layout.select_dialog_multichoice, KlimatskiTip.values()))
        listaJela.setAdapter(ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, jela))
        listaProfilOkusa.setAdapter(ArrayAdapter<ProfilOkusaBiljke>(this, android.R.layout.select_dialog_multichoice, ProfilOkusaBiljke.values()))
        listaZemljisniTip.setAdapter(ArrayAdapter<Zemljiste>(this, android.R.layout.select_dialog_multichoice, Zemljiste.values()))

        buttonDodajJelo.setOnClickListener{
            val newJelo = editTextJela.text.toString().trim().toLowerCase() 
            if (newJelo.isNotEmpty() && !jela.any { it.toLowerCase() == newJelo } && buttonDodajJelo.text != "izmijeni jelo") {
                jela.add(newJelo)
            } else if (buttonDodajJelo.text == "izmijeni jelo") {
                if (newJelo.isEmpty()) {
                    jela.removeAt(trenutnoKliknut)
                    trenutnoKliknut = -1
                } else {
                    jela.set(trenutnoKliknut, newJelo)
                    trenutnoKliknut = -1
                }
            }
            buttonDodajJelo.text = "Dodaj jelo"
            listaJela.adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, jela)
            editTextJela.text.clear()

        }
        listaJela.onItemClickListener = this


        buttonDodajBiljku.setOnClickListener{
            if(validirajZaDodajBiljku()){
                val varijablaMedKorist: List<MedicinskaKorist> = checkedList(listaMedicinskaKorist)
                val varijablaKlima: List<KlimatskiTip> = checkedList(listaKlimatskiTip)
                val varijablaZemlja: List<Zemljiste> = checkedList(listaZemljisniTip)
                val okus : ProfilOkusaBiljke = listaProfilOkusa.adapter.getItem(listaProfilOkusa.checkedItemPosition) as ProfilOkusaBiljke
                val biljka = Biljka(
                    editTextNaziv.text.toString().trim(), editTextPorodica.text.toString().trim(),
                    editTextMedUpozorenje.text.toString().trim(), varijablaMedKorist,
                    okus, jela.toList(), varijablaKlima,varijablaZemlja)


                CoroutineScope(Dispatchers.Main).launch {
                    biljke.add(TrefleDAO(this@NovaBiljkaActivity).fixData(biljka))
                    biljkaRepository.saveBiljka(biljka)
                }

                ///////////////////////////////////////////
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{
                val invalidEditTexts = listOf(editTextNaziv, editTextPorodica, editTextMedUpozorenje)
                invalidEditTexts.forEach { editText ->
                    if (editText.text.toString().trim().length>=2 && editText.text.toString().trim().length<=20) {
                        editText.error = null
                    } else {
                        setError(editText, "Polje je obavezno i mora biti najmanje dva znaka!")
                    }
                }
            }

        }

        buttonSlikaj.setOnClickListener {
            uslikajBiljku()
        }
    }

    private fun setError(editText: EditText, errorMessage: String) {
        editText.error = errorMessage
    }

    private fun uslikajBiljku() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                slika.setImageBitmap(it)
            }
        }
    }

    fun validirajZaDodajBiljku() : Boolean{
        //prvi uslov za manje od 2 ili vece od 20
        if(editTextNaziv.text.toString().length<2 || editTextMedUpozorenje.text.toString().length<2
            || editTextPorodica.text.toString().length<2 || editTextNaziv.text.toString().length>40
            || editTextPorodica.text.toString().length>20 || editTextMedUpozorenje.text.toString().length>20){
            return false
        }

     //treci uslov za nista ne selektovano u listi
        if(listaJela.size<1 || listaKlimatskiTip.checkedItemCount<1
            || listaProfilOkusa.checkedItemCount<1 || listaMedicinskaKorist.checkedItemCount<1
            || listaZemljisniTip.checkedItemCount<1) {
            return false
        }

        if(listaZemljisniTip.checkedItemCount<1 || listaKlimatskiTip.checkedItemCount<1
            || listaMedicinskaKorist.checkedItemCount<1 || listaProfilOkusa.checkedItemCount<1
            || jela.isEmpty()){
            return false
        }

        return true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //jela dodavanje
        if (parent != null && parent === listaJela) {
            val selectedItem = parent.getItemAtPosition(position) as String
            editTextJela.setText(selectedItem)
            buttonDodajJelo.text="izmijeni jelo"
            trenutnoKliknut = position
        }
    }

    inline fun <reified T> checkedList(listview: ListView): List<T> {
        val checkedPositions = listview.checkedItemPositions
        val list = mutableListOf<T>()

        for (i in 0 until checkedPositions.size()) {
            val position = checkedPositions.keyAt(i)
            val isChecked = checkedPositions.valueAt(i)

            if (isChecked) {
                val item = listview.adapter.getItem(position)
                if (item is T) {
                    list.add(item)
                }
            }
        }
        return list
    }
}
