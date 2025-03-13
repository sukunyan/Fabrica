package Vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Consola {
	
	@PostMapping("/registro")
	public ModelAndView registrarse(@RequestParam String Usuario, @RequestParam String Contraseña) {
		
		/*txt con el nombre del usuario*/
		String ruta = "usuarios/" + Usuario + ".txt";
		File Archivo = new File(ruta);
		
		if(Archivo.exists()) {
			//si existe devuelve mensaje de "Ya existe"
			ModelAndView modelAndView = new ModelAndView("register_error"); 
            modelAndView.addObject("mensaje", "El nombre de usuario ya existe, por favor elige otro.");
            return modelAndView;
		}
		
		return modelAndView;
	}
	
}
