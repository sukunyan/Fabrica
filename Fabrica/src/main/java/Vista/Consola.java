package Vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class Consola {
	
	@GetMapping("/")
	public ModelAndView Principal() {
		return new ModelAndView("index");
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
	
	@GetMapping("/registro")
	public ModelAndView MostrarRegistro() {
		return new ModelAndView("registro");
	}
	
	@GetMapping("/IniciarSesion")
	public ModelAndView InciarSesion() {
		return new ModelAndView("IniciarSesion");
	}
	
	@PostMapping("/registro")
	public ModelAndView registrarse(@RequestParam String Usuario, @RequestParam String Contraseña) {
		
		/*txt con el nombre del usuario*/
		String ruta = "usuarios/" + Usuario + ".txt";
		File Archivo = new File(ruta);
		
		if(Archivo.exists()) {
			
			//si existe devuelve mensaje de "Ya existe"
			ModelAndView modelAndView = new ModelAndView("registro"); 
            modelAndView.addObject("mensaje", "El nombre de usuario ya existe, por favor elige otro.");
            return modelAndView;
		}
		
		//revision del archivo, guarda el usuario y la contraseña en el archivo
		try(FileWriter fw = new FileWriter(Archivo, false); PrintWriter pw = new PrintWriter(fw)){
			
			pw.println(Usuario);
			pw.println(Contraseña);
			
		}catch(IOException e) {
			return new ModelAndView("Error");
		}
		
		ModelAndView modelo = new ModelAndView("IniciarSesion");
		modelo.addObject("mensaje", "Registro exitoso, ya puede iniciar sesion");
		
		return modelo;
	}
	
}
