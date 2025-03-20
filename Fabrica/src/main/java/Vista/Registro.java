package Vista;

import Modelo.SQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    public ModelAndView mostrarFormularioRegistro() {
        return new ModelAndView("registrar");  
    }
	
	@PostMapping
	public ModelAndView registrarse(@RequestParam String Usuario, @RequestParam String Correo, @RequestParam String Contrasenia) {
		
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
		
		/*txt con el nombre del usuario*/
		String ruta = "usuarios/" + Usuario + ".txt";
		File Archivo = new File(ruta);
		
		if(Archivo.exists()) {
			
			//si existe devuelve mensaje de "Ya existe"
			ModelAndView modelAndView = new ModelAndView("registrar"); 
            modelAndView.addObject("mensaje", "El nombre de usuario ya existe, por favor elige otro.");
            System.out.println("El usuario " + Usuario + " ya esta en uso");
            return modelAndView;
            
		}
		
		for(File archivo : archivos) {
			
			try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                
               usuario = br.readLine();
               contrasenia = br.readLine();
               correo = br.readLine();
               
               if(correo.equals(Correo)) {
       				ModelAndView modelAndView = new ModelAndView("registrar"); 
       	            modelAndView.addObject("mensaje", "El correo de usuario ya esta en uso, por favor use otro o inicie sesion.");
       	            System.out.println("El correo " + Correo + " ya esta en uso");
       	            return modelAndView;
   				} 
   				
                
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
				
		}
		

		
		//revision del archivo, guarda el usuario y la contraseña en el archivo
		try(FileWriter fw = new FileWriter(Archivo, false); PrintWriter pw = new PrintWriter(fw)){
			
			pw.println(sql.getUsuario());
			pw.println(sql.getContrasenia());
			pw.println(sql.getCorreo());
			
		}catch(IOException e) {
			System.out.println("Algo fallo al escribir el archivo");
			return new ModelAndView("404");
		}
		
		ModelAndView modelo = new ModelAndView("/login");
		modelo.addObject("mensaje", "Registro exitoso, ya puede iniciar sesion");
		
		System.out.println("Recibida solicitud POST con usuario: " + Usuario);
		
		return modelo;
	}
	
}
