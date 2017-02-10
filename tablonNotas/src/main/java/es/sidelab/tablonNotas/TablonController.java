package es.sidelab.tablonNotas;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
			//model.addAttribute("notas", usuario.getNotas());
		}
		
		model.addAttribute("encontrado", (usuario == null ? "": usuario.getName()));
		
		return "inicio";
	}
	
	/*@PostMapping("/")
	public String guardarNota(Model model, Nota nota) {

		model.addAttribute("encontrado", flagEncontrado+"");
		
		/*usuario.getNotas().add(nota);
		if(usuario != null){
			model.addAttribute("notas", usuario.getNotas());
		}

		return "inicio";
	}*/
	
	@GetMapping("/crear_nota")
	public String nuevaNota(Model model, HttpSession session){

		session.invalidate();
		model.addAttribute("nombre", usuario.getName());
		usuario = null;
		
		return "crear_nota";
	}
	
	@RequestMapping("/registro")
	public String paginaRegistro(Model model){
		return "registro";
	}
	
	@PostMapping("/")
	public String setUserName(Model model, HttpSession session, Usuario user) {

		Usuario userEncontrado = null;
		List<Usuario> lista = usuarioRepository.findAll();
		for(Usuario u : lista){
			if(u.getName().equals(user.getName())){
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
		model.addAttribute("encontrado", (usuario == null ? "": usuario.getName()) + ": " + (!flagEncontrado ? "Registrado" : "Logeado"));

		return "inicio";
	}
}