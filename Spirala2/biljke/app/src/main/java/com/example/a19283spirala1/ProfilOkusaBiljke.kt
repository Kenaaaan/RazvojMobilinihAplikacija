package com.example.a19283spirala1

enum class ProfilOkusaBiljke(val opis: String) {
    MENTA("Mentol - osvježavajući, hladan ukus"),
    CITRUSNI("Citrusni - osvježavajući, aromatičan"),
    SLATKI("Sladak okus"),
    BEZUKUSNO("Obični biljni okus - travnat, zemljast ukus"),
    LJUTO("Ljuto ili papreno"),
    KORIJENASTO("Korenast - drvenast i gorak ukus"),
    AROMATICNO("Začinski - topli i aromatičan ukus"),
    GORKO("Gorak okus"),
}


fun getOkusiStrings(): List<String> {
    return ProfilOkusaBiljke.values().map { it.name }
}