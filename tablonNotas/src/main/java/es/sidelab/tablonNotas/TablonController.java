package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TablonController {

	private List<Nota> notas = new ArrayList<>();
	
	public TablonController() {
		notas.add(new Nota("Prueba 1"));
	}

	@GetMapping("/")
	public String tablon(Model model) {
		
		model.addAttribute("notas", notas);
		
		return "inicio";
	}
	
	@PostMapping("/")
	public String guardarNota(Model model, Nota nota) {

		notas.add(nota);
		model.addAttribute("notas", notas);

		return "inicio";
	}
	
	@GetMapping("/crear_nota")
	public String nuevaNota(Model model){
		
		return "crear_nota";
	}
}