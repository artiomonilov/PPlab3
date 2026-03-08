import java.io.File

class TranslatorTrivial {

    private val dictionar = hashMapOf(
        "Once" to "Odata", "upon" to "ca", "a" to "", "time" to "niciodata",
        "there" to "acolo", "was" to "a fost", "an" to "o", "old" to "batrana",
        "woman" to "femeie", "who" to "care", "loved" to "iubea",
        "baking" to "sa gateasca", "gingerbread" to "turta dulce", "She" to "Ea",
        "would" to "ar fi", "bake" to "gatit", "cookies" to "biscuiti",
        "cakes" to "prajituri", "houses" to "case", "and" to "si",
        "people" to "oameni", "all" to "toti", "decorated" to "decorati",
        "with" to "cu", "chocolate" to "ciocolata", "peppermint" to "menta",
        "caramel" to "caramel", "candies" to "bomboane", "colored" to "colorate",
        "ingredients" to "ingrediente"
    )


    fun adaugaCuvant(engleza: String, romana: String) {
        dictionar[engleza] = romana
        println("-> Cuvant adaugat în dictionar: $engleza = $romana")
    }


    fun traduce(poveste: String): String {

        val textCuratat = poveste.replace("\n", " ")
        val cuvinte = textCuratat.split(" ")


        val povesteTradusa = java.lang.StringBuilder()

        for (cuvant in cuvinte) {

            val cuvantCuratat = cuvant.trim(',', '.')

            if (cuvantCuratat.isNotEmpty()) {
                if (dictionar.containsKey(cuvantCuratat)) {
                    povesteTradusa.append(dictionar[cuvantCuratat]).append(" ")
                } else {
                    povesteTradusa.append("[$cuvantCuratat]").append(" ")
                }
            }
        }
        return povesteTradusa.toString().trim()
    }


    fun salveazaInFisier(numeFisier: String, continut: String) {
        try {
            File(numeFisier).writeText(continut)
            println("-> Povestea tradusa a fost salvata cu succes in fisierul: $numeFisier")
        } catch (e: Exception) {
            println("-> Eroare la salvarea fisierului: ${e.message}")
        }
    }
}

fun main(args: Array<String>) {
    val poveste = """
        Once upon a time there was an old woman who loved baking
        gingerbread. She would bake gingerbread cookies, cakes, houses and
        gingerbread people, all decorated with chocolate and peppermint, caramel
        candies and colored ingredients.
    """.trimIndent()

    val translator = TranslatorTrivial()


    println("=== TRADUCERE INITIALA ===")
    val traducere1 = translator.traduce(poveste)
    println(traducere1)


    println("\n=== ADĂUGARE CUVINTE NOI ===")
    translator.adaugaCuvant("apple", "mar")


    println("\n=== SALVARE IN FISIER ===")
    translator.salveazaInFisier("PovesteTradusa.txt", traducere1)
}