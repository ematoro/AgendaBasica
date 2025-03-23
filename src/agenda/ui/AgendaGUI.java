package agenda.ui;

import agenda.models.Contacto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import agenda.services.Agenda;
/**
 * Clase principal con la Interfaz Gráfica
 * @author pesu
 */
public class AgendaGUI extends JFrame{
    private Agenda agenda;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaContactos;
    private JTextField txtNombre, txtTelefono, txtEmail;

    public AgendaGUI() {
        agenda = new Agenda();
        setTitle("Agenda de Contactos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloLista = new DefaultListModel<>();
        listaContactos = new JList<>(modeloLista);
        listaContactos.addListSelectionListener(e -> cargarDatosContacto());
        add(new JScrollPane(listaContactos), BorderLayout.CENTER);
        cargarLista();
        
        JPanel panelForm = new JPanel(new GridLayout(5, 2));
        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarContacto());
        panelForm.add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarContacto());
        panelForm.add(btnEliminar);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarContacto());
        panelForm.add(btnEditar);
        
        JButton btnBuscar = new JButton("Buscar");
        //btnBuscar.addActionListener(e -> buscarContacto());
        btnBuscar.addActionListener(e -> mostrarBusqueda());
        panelForm.add(btnBuscar);

        add(panelForm, BorderLayout.SOUTH);
    }
    
    private void cargarLista() {
        modeloLista.clear();
        for (Contacto c : agenda.getContactos()) {
            modeloLista.addElement(c.toString());
        }
    }
    
    private void agregarContacto() {
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        Contacto nuevo = new Contacto(nombre, telefono, email);
        agenda.agregarContacto(nuevo);
        modeloLista.addElement(nuevo.toString());
        limpiarCampos();
    }

    private void eliminarContacto() {
        int index = listaContactos.getSelectedIndex();
        if (index != -1) {
            agenda.eliminarContacto(index);
            modeloLista.remove(index);
            limpiarCampos();
        }
    }

    private void editarContacto() {
        int index = listaContactos.getSelectedIndex();
        if (index != -1) {
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            agenda.editarContacto(index, nombre, telefono, email);
            modeloLista.set(index, nombre + " - " + telefono + " - " + email);
            limpiarCampos();
        }
    }
    
    private void mostrarBusqueda() {
        String criterio = JOptionPane.showInputDialog(this, "Ingrese el nombre a buscar:");
        if (criterio != null && !criterio.trim().isEmpty()) {
            criterio = criterio.toLowerCase();
            for (int i = 0; i < modeloLista.size(); i++) {
                if (modeloLista.get(i).toLowerCase().contains(criterio)) {
                    listaContactos.setSelectedIndex(i);
                    listaContactos.ensureIndexIsVisible(i);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Contacto no encontrado");
        }
    }
    /*
    private void buscarContacto() {
        String criterio = txtNombre.getText().toLowerCase();
        for (int i = 0; i < modeloLista.size(); i++) {
            if (modeloLista.get(i).toLowerCase().contains(criterio)) {
                listaContactos.setSelectedIndex(i);
                listaContactos.ensureIndexIsVisible(i);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Contacto no encontrado");
    }
    */
    private void cargarDatosContacto() {
        int index = listaContactos.getSelectedIndex();
        if (index != -1) {
            Contacto contacto = agenda.getContactos().get(index);
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtEmail.setText(contacto.getEmail());
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgendaGUI().setVisible(true));
    }
}
