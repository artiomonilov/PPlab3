package org.example

class Birth(val year: Int, val Month: Int, val Day: Int) {
    override fun toString(): String {
        return "($Day.$Month.$year)"
    }
}

class Contact(val Name: String, var Phone: String, val BirthDate: Birth) {
    fun Print() {
        println("Name: $Name, Mobile: $Phone, Date: $BirthDate")
    }
}

class Agenda {
    private val contacte = mutableListOf<Contact>()

    fun adauga(contact: Contact) {
        contacte.add(contact)
    }

    fun sterge(contact: Contact) {
        contacte.remove(contact)
    }

    fun cauta(criteriu: String): List<Contact> {
        return contacte.filter {
            it.Name.equals(criteriu, ignoreCase = true) || it.Phone == criteriu
        }
    }

    fun actualizeazaTelefon(nume: String, telefonNou: String): Boolean {
        val contact = contacte.find { it.Name.equals(nume, ignoreCase = true) }

        if (contact != null) {
            contact.Phone = telefonNou
            return true
        }
        return false
    }

    fun afiseazaToate() {
        if (contacte.isEmpty()) {
            println("Agenda este goala.")
        } else {
            for (contact in contacte) {
                contact.Print()
            }
        }
    }
}

fun main(args: Array<String>) {
    val agenda = Agenda()

    agenda.adauga(Contact("Mihai", "0744321987", Birth(1900, 11, 25)))
    agenda.adauga(Contact("George", "0761332100", Birth(2002, 3, 14)))
    agenda.adauga(Contact("Liviu", "0231450211", Birth(1999, 7, 30)))
    agenda.adauga(Contact("Popescu", "0211342787", Birth(1955, 5, 12)))

    println("=== Agenda Initiala ===")
    agenda.afiseazaToate()

    println("\n=== Cautare dupa nume ('Liviu') ===")
    val rezultateNume = agenda.cauta("Liviu")
    rezultateNume.forEach { it.Print() }

    println("\n=== Cautare dupa telefon ('0761332100') ===")
    val rezultateTelefon = agenda.cauta("0761332100")
    rezultateTelefon.forEach { it.Print() }

    println("\n=== Actualizare numar pentru 'Mihai' ===")
    val success = agenda.actualizeazaTelefon("Mihai", "0700111222")
    if (success) {
        println("Numarul a fost actualizat cu succes!")
    } else {
        println("Contactul nu a fost gasit.")
    }

    println("\n=== Agenda Dupa Actualizare ===")
    agenda.afiseazaToate()
}