package Vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public class Consola {
	
	@PostMapping("/registro")
	public ModelAndView registrarse(@RequestParam String Usurio, @RequestParam String Contraseña) {
		
		return modelAndView;
	}
	
}
