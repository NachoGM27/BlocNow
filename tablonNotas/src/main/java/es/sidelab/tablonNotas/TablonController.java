package es.sidelab.tablonNotas;

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
	private UsuarioRepository usuarioRepository;
	private Usuario usuario;
	
	private boolean flagEncontrado;
	
	public TablonController() {
	}

	@GetMapping("/")
	public String tablon(Model model, HttpSession session) {

		model.addAttribute("bienvenida", session.isNew());
		
		if(session.isNew()){
			
		}
		else
		{
			usuario = (Usuario) session.getAttribute("user");
			model.addAttribute("notas", usuario.getNotas());
		}
		
		model.addAttribute("encontrado", flagEncontrado+" "+session.isNew()+" "+(session.isNew() == false ? usuario.getNombre() : ""));
		
		return "inicio";
	}
	
	@PostMapping("/")
	public String guardarNota(Model model, Nota nota) {

		model.addAttribute("encontrado", flagEncontrado+"");
		
		usuario.getNotas().add(nota);
		if(usuario != null){
			model.addAttribute("notas", usuario.getNotas());
		}

		return "inicio";
	}
	
	@PostMapping("/username")
	public String setUserName(Model model, HttpSession session, Usuario user) {

		Usuario userEncontrado = null;
		List<Usuario> lista = usuarioRepository.findAll();
		for(Usuario u : lista){
			if(u.getNombre().equals(user.getNombre())){
				userEncontrado = u;
				break;
			}
		}
		if(userEncontrado != null){
			flagEncontrado = true;
			usuario = userEncontrado;
		}
		else
		{
			flagEncontrado = false;
			usuario = user;
			usuarioRepository.save(user);
		}

		session.setAttribute("user", usuario);
		model.addAttribute("encontrado", flagEncontrado+"");

		return "inicio";
	}
	
	@GetMapping("/crear_nota")
	public String nuevaNota(Model model){
	
		model.addAttribute("nombre", usuario.getNombre());
		
		return "crear_nota";
	}
}