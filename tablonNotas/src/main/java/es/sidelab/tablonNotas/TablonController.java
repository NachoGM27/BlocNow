package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TablonController {

	@Autowired
	private Usuario usuario;
	private List<Nota> notas = new ArrayList<>();
	
	public TablonController() {
	}

	@GetMapping("/")
	public String tablon(Model model, HttpSession session) {

		model.addAttribute("bienvenida", session.isNew());
		model.addAttribute("notas", notas);
		
		return "inicio";
	}
	
	@PostMapping("/")
	public String guardarNota(Model model, Nota nota) {

		nota.setNombre(usuario.getNombre());
		notas.add(nota);
		model.addAttribute("notas", notas);

		return "inicio";
	}
	
	@PostMapping("/username")
	public String setUserName(Model model, Usuario user) {

		usuario.setNombre(user.getNombre());

		return "inicio";
	}
	
	@GetMapping("/crear_nota")
	public String nuevaNota(Model model){
	
		model.addAttribute("nombre", usuario.getNombre());
		
		return "crear_nota";
	}
}