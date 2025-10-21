import java.sql.*;
import java.util.ArrayList;

public class Operaciones {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ad-postgres.c5w4wgldbqa9.us-east-1.rds.amazonaws.com:5432/hogwarts";
        String usuario = "postgres";
        String contraseña = "123456789pos.";

        try (Connection conexion = DriverManager.getConnection(url, usuario, contraseña)) {
            System.out.println("✅ Conexión exitosa a la base de datos RDS!");
            Statement stm = conexion.createStatement();
            ArrayList<Asignatura> lista = devuelveAsignaturas(stm);
            System.out.println();
            estudiantesPorCasa(conexion,"Gryffindor");
            System.out.println();
            MascotaPorEstudianteEspecifico(conexion,"Hermione","Granger");
            System.out.println();
            numeroEstudiantesPorCasa(stm);
            System.out.println();

        } catch (SQLException e) {
            System.out.println("❌ Error de conexión:");
            e.printStackTrace();
        }
    }
    public static ArrayList<Asignatura> devuelveAsignaturas( Statement stm ){
        ArrayList<Asignatura> listaAsig = new ArrayList<>();
        try {
            ResultSet rs = stm.executeQuery("select * from Asignatura");
            System.out.println("Lista Obtenida");
            while (rs.next()) {
                listaAsig.add(new Asignatura(rs.getInt("id_asignatura"),rs.getString("nombre_asignatura"),rs.getString("aula"),rs.getBoolean("obligatoria")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAsig;
    }
    public static void estudiantesPorCasa (Connection conexion,String nombreCasa) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT estudiante.nombre,estudiante.apellido from estudiante INNER JOIN casa on casa.id_casa = estudiante.id_casa where nombre_casa = ?");
            ps.setString(1,nombreCasa);
            ResultSet rs = ps.executeQuery();
            System.out.println("Nombre | Apellido");
            while (rs.next()){
                System.out.println(rs.getString(1) + "  " +  rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void MascotaPorEstudianteEspecifico(Connection conexion,String nombre,String apellido){
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT mascota.nombre_mascota from mascota INNER JOIN estudiante on estudiante.id_estudiante = mascota.id_estudiante where estudiante.nombre = ? and estudiante.apellido = ?");
            ps.setString(1,nombre);
            ps.setString(2,apellido);
            ResultSet rs = ps.executeQuery();
            System.out.println("Nombre De La Mascota");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void numeroEstudiantesPorCasa(Statement st) {
        try {
            ResultSet rs = st.executeQuery("SELECT count(estudiante.nombre) from casa inner join estudiante on estudiante.id_casa = casa.id_casa group by casa.nombre_casa");

            while (rs.next()){
                System.out.println(rs.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
