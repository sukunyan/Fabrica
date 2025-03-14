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
	
	@GetMapping("/Principal")
	public ModelAndView Principal() {
		return new ModelAndView("Principal");
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
