data class Birth(val year: Int, val month: Int, val day: Int) {
    override fun toString(): String {
        return "($day.$month.$year)"
    }
}

data class Contact(val name: String, var phone: String, val birthDate: Birth) {
    fun printContact() {
        println("Name: $name, Mobile: $phone, Date: $birthDate")
    }
}

class AgendaTelefon {
    private val contacts = mutableListOf<Contact>()

    fun addContact(contact: Contact) {
        contacts.add(contact)
        println("-> Contact adaugat: ${contact.name}")
    }

    fun printAll() {
        println("\n=== Agenda Curenta ===")
        if (contacts.isEmpty()) {
            println("Agenda este goala.")
        } else {
            for (contact in contacts) {
                contact.printContact()
            }
        }
        println("======================\n")
    }

    fun searchByName(numeCautat: String): Contact? {
        return contacts.find { it.name.equals(numeCautat, ignoreCase = true) }
    }

    fun updatePhone(nume: String, noulNumar: String): Boolean {
        val contact = searchByName(nume)
        if (contact != null) {
            contact.phone = noulNumar
            println("-> Numar actualizat cu succes pentru $nume.")
            return true
        }
        println("-> Eroare: Contactul $nume nu a fost gasit.")
        return false
    }

    fun deleteContact(nume: String): Boolean {
        val contact = searchByName(nume)
        if (contact != null) {
            contacts.remove(contact)
            println("-> Contactul $nume a fost sters.")
            return true
        }
        println("-> Eroare: Contactul $nume nu a fost gasit pentru stergere.")
        return false
    }
}

fun main() {
    val agendaMea = AgendaTelefon()

    agendaMea.addContact(Contact("Mihai", "0744321987", Birth(1900, 11, 25)))
    agendaMea.addContact(Contact("George", "0761332100", Birth(2002, 3, 14)))
    agendaMea.addContact(Contact("Liviu", "0231450211", Birth(1999, 7, 30)))

    agendaMea.printAll()

    println("--- Cautare contact 'George' ---")
    val contactGasit = agendaMea.searchByName("George")
    contactGasit?.printContact() ?: println("Nu a fost gasit.")

    println("\n--- Actualizare numar 'Mihai' ---")
    agendaMea.updatePhone("Mihai", "0700000000")
    agendaMea.printAll()

    println("--- Stergere contact 'Liviu' ---")
    agendaMea.deleteContact("Liviu")
    agendaMea.printAll()
}