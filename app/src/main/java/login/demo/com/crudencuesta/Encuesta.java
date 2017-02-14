/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.demo.com.crudencuesta;


import java.util.Set;

public class Encuesta {

    private int idEncuesta;
    private String pregunta;
    private Set<Opcion> opciones;

    public Encuesta() {
        super();
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public Encuesta(int idEncuesta, String pregunta) {
        super();
        this.idEncuesta = idEncuesta;
        this.pregunta = pregunta;
    }
    

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Set<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(Set<Opcion> opciones) {
        this.opciones = opciones;
    }
    
    
    
    
    
    
}
