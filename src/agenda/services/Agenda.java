package agenda.services;

import agenda.models.Contacto;
import java.util.ArrayList;

/**
 * Clase para gestionar la agenda de contactos
 * @author pesu
 */
public class Agenda {
    private ArrayList<Contacto> contactos;

    public Agenda() {
        contactos = new ArrayList<>();
    }

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
    }

    public void eliminarContacto(int index) {
        if (index >= 0 && index < contactos.size()) {
            contactos.remove(index);
        }
    }
    
    public void editarContacto(int index, String nombre, String telefono, String email) {
        if (index >= 0 && index < contactos.size()) {
            Contacto contacto = contactos.get(index);
            contacto.setNombre(nombre);
            contacto.setTelefono(telefono);
            contacto.setEmail(email);
        }
    }

    public ArrayList<Contacto> getContactos() {
        agregarContacto(new Contacto("Pesu", "11-345689", "pesu@gmail.com"));
        agregarContacto(new Contacto("Otro", "11-39903", "otro@gmail.com"));
        return contactos;
    }
}
