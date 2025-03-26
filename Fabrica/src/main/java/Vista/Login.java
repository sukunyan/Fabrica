package Vista;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

import Modelo.SQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@Controller
@RequestMapping("/login")
public class Login {
	
	SQL sql = new SQL();
	
	@GetMapping
	private ModelAndView Principal() {
		return new ModelAndView("login");
	}
	
	public void abrirNavegador(String url) {
		
		 try {
		        String os = System.getProperty("os.name").toLowerCase();
		        ProcessBuilder processBuilder;

		        if (os.contains("win")) {
		            // En Windows
		            processBuilder = new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url);
		        } else if (os.contains("mac")) {
		            // En MacOS
		            processBuilder = new ProcessBuilder("open", url);
		        } else if (os.contains("nix") || os.contains("nux")) {
		            // En Linux
		            processBuilder = new ProcessBuilder("xdg-open", url);
		        } else {
		            throw new UnsupportedOperationException("Unsupported OS");
		        }

		        processBuilder.start();  // Inicia el proceso para abrir el navegador
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		 
    }
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Map<String, String>> iniciarSesion(@RequestParam String Usuario, @RequestParam String Contrasenia) {
	    String lector = "usuarios/";
	    File carpeta = new File(lector);
	    File[] archivos = carpeta.listFiles();
	    String usuario = "", contrasenia = "", correo = "";

	    Map<String, String> response = new HashMap<>();

	    if (archivos == null) {
	        response.put("error", "No se encontraron archivos de usuarios");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }

	    for (File archivo : archivos) {
	        if (archivo.isFile() && archivo.getName().equals(Usuario + ".txt")) {
	            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
	                String linea;
	                while ((linea = br.readLine()) != null) {
	                    if (usuario.isEmpty()) usuario = linea.trim();
	                    else if (contrasenia.isEmpty()) contrasenia = linea.trim();
	                    else if (correo.isEmpty()) correo = linea.trim();
	                }

	                if (Usuario.equals(usuario) && Contrasenia.equals(contrasenia)) {
	                    sql.setUsuario(usuario);
	                    sql.setContrasenia(contrasenia);
	                    sql.setCorreo(correo);
	                    sql.setUser(sql);

	                    response.put("mensaje", "Inicio de sesión exitoso");
	                    return ResponseEntity.ok(response);
	                }
	            } catch (IOException e) {
	                response.put("error", "Error al leer el archivo");
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	            }
	        }
	    }

	    response.put("error", "Usuario o contraseña incorrectos. Intenta de nuevo.");
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
}
