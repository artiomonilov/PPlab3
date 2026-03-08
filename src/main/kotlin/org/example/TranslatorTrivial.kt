package org.example

import java.io.File
import kotlin.math.roundToInt

class TranslatorTrivial {
    private val dictionar = hashMapOf<String, String>()

    fun adaugaCuvant(engleza: String, romana: String) {
        dictionar[engleza] = romana
    }

    fun incarcaDictionarDinFisier(numeFisier: String) {
        val fisier = File(numeFisier)
        if (fisier.exists()) {
            fisier.useLines { linii ->
                linii.forEach { linie ->
                    val parti = linie.split("=")
                    if (parti.size == 2) {
                        dictionar[parti[0].trim()] = parti[1].trim()
                    }
                }
            }
        }
    }

    fun extrageDictionarDinEbook(numeFisier: String) {
        val fisier = File(numeFisier)
        if (fisier.exists()) {
            val text = fisier.readText()
            val cuvinte = text.split(Regex("\\W+")).filter { it.isNotBlank() }

            for (cuvant in cuvinte) {
                val cuvantMic = cuvant.lowercase()
                if (!dictionar.containsKey(cuvantMic) && !dictionar.containsKey(cuvant)) {
                    dictionar[cuvantMic] = "[NECUNOSCUT]"
                }
            }
        }
    }

    fun salveazaInFisier(numeFisier: String, continut: String) {
        File(numeFisier).writeText(continut)
    }

    fun traduce(text: String): String {
        val cuvinte = text.replace("\n", " ").split(" ")
        val rezultat = StringBuilder()

        for (cuvant in cuvinte) {
            val curatat = cuvant.trim(',', '.', '!', '?')
            if (curatat.isNotEmpty()) {
                if (dictionar.containsKey(curatat)) {
                    rezultat.append(dictionar[curatat]).append(" ")
                } else if (dictionar.containsKey(curatat.lowercase())) {
                    rezultat.append(dictionar[curatat.lowercase()]).append(" ")
                } else {
                    rezultat.append("[$curatat]").append(" ")
                }
            }
        }
        return rezultat.toString().trim()
    }

    fun calculeazaAcoperire(text: String): Int {
        val cuvinte = text.replace("\n", " ").split(" ").map { it.trim(',', '.', '!', '?') }.filter { it.isNotEmpty() }
        if (cuvinte.isEmpty()) return 0

        var traduse = 0
        for (cuvant in cuvinte) {
            if (dictionar.containsKey(cuvant) || dictionar.containsKey(cuvant.lowercase())) {
                val traducere = dictionar[cuvant] ?: dictionar[cuvant.lowercase()]
                if (traducere != "[NECUNOSCUT]") {
                    traduse++
                }
            }
        }
        return ((traduse.toDouble() / cuvinte.size) * 100).roundToInt()
    }
}

fun main(args: Array<String>) {
    File("dictionar_intrare.txt").writeText("Once=Odata\nupon=ca\na=\ntime=niciodata\nthere=acolo\nwas=a fost\nan=o\nold=batrana\nwoman=femeie")
    File("ebook_test.txt").writeText("Once upon a time there was an old woman who loved baking.")

    val translator = TranslatorTrivial()

    translator.incarcaDictionarDinFisier("dictionar_intrare.txt")

    translator.adaugaCuvant("loved", "iubea")

    translator.extrageDictionarDinEbook("ebook_test.txt")

    val poveste = "Once upon a time there was an old woman who loved baking."

    val povesteTradusa = translator.traduce(poveste)
    println(povesteTradusa)

    val acoperire = translator.calculeazaAcoperire(poveste)
    println("Acoperire traducere: $acoperire%")

    translator.salveazaInFisier("PovesteTradusa.txt", povesteTradusa)
}