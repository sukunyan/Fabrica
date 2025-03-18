package Vista;

import Modelo.SQL;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registrar")
public class Registro {
	
	SQL sql = new SQL();
	
	@GetMapping
    public String mostrarFormularioRegistro() {
        return "registrar";  
    }
	
	@PostMapping
	public ModelAndView registrarse(@RequestParam String Usuario, @RequestParam String Correo, @RequestParam String Contraseña) {
		
		System.out.println("Recibida solicitud POST con usuario: " + Usuario);
		
		sql.setUsuario(Usuario);
		sql.setCorreo(Correo);
		sql.setContraseña(Contraseña);
		
		sql.setUser(sql);
		
		/*txt con el nombre del usuario*/
		String ruta = "usuarios/" + Usuario + ".txt";
		File Archivo = new File(ruta);
		
		if(Archivo.exists()) {
			
			//si existe devuelve mensaje de "Ya existe"
			ModelAndView modelAndView = new ModelAndView("registrar"); 
            modelAndView.addObject("mensaje", "El nombre de usuario ya existe, por favor elige otro.");
            return modelAndView;
		}

		
		//revision del archivo, guarda el usuario y la contraseña en el archivo
		try(FileWriter fw = new FileWriter(Archivo, false); PrintWriter pw = new PrintWriter(fw)){
			
			pw.println(sql.getUsuario());
			pw.println(sql.getContraseña());
			pw.println(sql.getCorreo());
			
		}catch(IOException e) {
			return new ModelAndView("404");
		}
		
		ModelAndView modelo = new ModelAndView("/login");
		modelo.addObject("mensaje", "Registro exitoso, ya puede iniciar sesion");
		
		return modelo;
	}
	
}
