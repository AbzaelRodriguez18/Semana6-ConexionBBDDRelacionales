public class Asignatura {
    private int id_asignatura;
    private String nombre_asignatura;
    private String aula;
    private Boolean obligatoria;


    public Asignatura(int id_asignatura, String nombre_asignatura, String aula, Boolean obligatoria) {
        this.id_asignatura = id_asignatura;
        this.nombre_asignatura = nombre_asignatura;
        this.aula = aula;
        this.obligatoria = obligatoria;
    }
    public Boolean getObligatoria() {
        return obligatoria;
    }
}
