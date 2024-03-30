package com.example.a19283spirala1

val biljke = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Kurkuma (Curcuma longa)",
        porodica = "Zingiberaceae (Djumbirovke)",
        medicinskoUpozorenje = "Trebali biste biti svjesni rizika od ozljede jetre u rijetkim slučajevima. Iako je ozljeda jetre rijedak neželjeni događaj, može biti ozbiljna.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Curry", "Juha/Supa", "Marinirana piletina s kurkumom i limunom", "Riža"),
        klimatskiTipovi = listOf(KlimatskiTip.TROPSKA),
        zemljisniTipovi =listOf(Zemljiste.ILOVACA, Zemljiste.GLINENO)
    ),
    Biljka(
        naziv = "Timijan (Thymus vulgaris)",
        porodica = " Lamiaceae (usnate)",
        medicinskoUpozorenje = "Timijan je obično siguran za upotrebu, ali treba izbjegavati velike količine tokom trudnoće.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("krompir","Riža", "Pizza"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ), Biljka(
        naziv = "Marihuana (Cannabis sativa)",
        porodica = "Cannabaceae (konopljovke)",
        medicinskoUpozorenje = "Upotreba u medicinske svrhe mora biti pod strogim liječničkim nadzorom.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Kolačići"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.TROPSKA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.CRNICA)
    ), Biljka(
        naziv = "Origano (Origanum vulgare)",
        porodica = " Lamiaceae (usnate)",
        medicinskoUpozorenje = " Origano je obično siguran za upotrebu.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE,MedicinskaKorist.SMIRENJE),
        profilOkusa = ProfilOkusaBiljke.LJUTO,
        jela = listOf("Pizza","Pasta","Lazanja"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ), Biljka(
        naziv = "Aloa vera (Aloe Barbadensis)",
        porodica = "genus Aloe",
        medicinskoUpozorenje = "Nije preporucljivo za konzumiranje",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,MedicinskaKorist.PODRSKAIMUNITETU,MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("obloge"),
        klimatskiTipovi = listOf(KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.ILOVACA)
    ), Biljka(
        naziv = "Majčina dušica (Thymus serpyllum)",
        porodica = " Lamiaceae (usnate)",
        medicinskoUpozorenje = " Majčina dušica je sigurna za upotrebu",
        medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU,MedicinskaKorist.SMIRENJE),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("čaj","Umak", "Supa"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO)
    )

)

fun dajBiljke() : List<Biljka> {
    return biljke;
}

fun dajBiljkeSize() : Int {
    return biljke.size
}