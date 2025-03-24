package Vista;

import Modelo.SQL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registrar")
public class Registro {

    SQL sql = new SQL();

    @GetMapping
    public ModelAndView mostrarFormularioRegistro() {
        return new ModelAndView("registrar");
    }

    @PostMapping
    @ResponseBody // Esto indica que vamos a devolver una respuesta en formato JSON
    public ResponseEntity<Object> registrarse(@RequestParam String Usuario, @RequestParam String Correo, @RequestParam String Contrasenia) {

        sql.setUsuario(Usuario);
        sql.setCorreo(Correo);
        sql.setContrasenia(Contrasenia);

        sql.setUser(sql);

        String lector = "usuarios/";
        File carpeta = new File(lector);
        File[] archivos = carpeta.listFiles();
        String usuario = "";
        String contrasenia = "";
        String correo = "";

        // Archivo con el nombre del usuario
        String ruta = "usuarios/" + Usuario + ".txt";
        File Archivo = new File(ruta);

        Map<String, String> response = new HashMap<>();
        
        if (Archivo.exists()) {
            // Si el archivo ya existe, devuelve un error
        	response.put("error", "El nombre de usuario ya existe, por favor elige otro.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        for (File archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                usuario = br.readLine();
                contrasenia = br.readLine();
                correo = br.readLine();

                if (correo.equals(Correo)) {
                    // Si el correo ya está en uso, devuelve un error
                	response.put("error", "El correo de usuario ya está en uso, por favor use otro o inicie sesión.");
        	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }

            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }

        // Guardar el usuario y la contraseña en el archivo
        try (FileWriter fw = new FileWriter(Archivo, false); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(sql.getUsuario());
            pw.println(sql.getContrasenia());
            pw.println(sql.getCorreo());
        } catch (IOException e) {
            System.out.println("Algo fallo al escribir el archivo");
            return ResponseEntity.status(500).body("Algo falló al escribir el archivo.");
        }

        // Si todo sale bien, responde con éxito
        response.put("success", "Registro exitoso, ya puede iniciar sesión.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
