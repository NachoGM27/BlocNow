package es.sidelab.tablonNotas;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TablonRepository tablonRepository;
	@Autowired
	private NotaRepository notaRepository;
	
	public AppController() {
	}

	//============================================
	//  Pagina de inicio
	//============================================

	@GetMapping("/")
	public String paginaInicio(Model model, HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("user");
		if(session.isNew() || user == null)
			return usuarioAnonimo(model, session);
		else
			return usuarioRegistrado(model, session, user);
	}
	
	public String usuarioAnonimo(Model model, HttpSession session){
		model.addAttribute("debugInTitle", ": Usuario Anonimo");
		return "inicio";
	}
	
	public String usuarioRegistrado(Model model, HttpSession session, Usuario user){
		model.addAttribute("nombre", user.getName());
		model.addAttribute("notas_privadas", user.getTablonPrivado().getNotas());
		model.addAttribute("notas_publicas", user.getTablonPublico().getNotas());		
		return "pagina_usuario";
	}
	
	//============================================
	//  Creacion de notas
	//============================================
	
	@RequestMapping("/crear_nota")
	public String nuevaNota(Model model, HttpSession session, @RequestParam boolean privada){
		Usuario usuario = (Usuario) session.getAttribute("user");

		session.setAttribute("privada", privada);
		model.addAttribute("nombre", usuario.getName());
		model.addAttribute("privada", privada ? "privada" : "publica");
		
		return "crear_nota";
	}
	
	@PostMapping("/guardar_nota")
	public String guardarNota(Model model, HttpSession session, Nota nota) {
		Usuario usuario = (Usuario) session.getAttribute("user");
		boolean privada = (boolean) session.getAttribute("privada");
		
		Tablon tablon = privada ? usuario.getTablonPrivado() : usuario.getTablonPublico();
		
		nota.setTablon(tablon);
		nota = notaRepository.save(nota);
		
		tablon.getNotas().add(nota);
		tablonRepository.save(tablon);

		return "redirect:/";
	}
	
	//============================================
	//  Registro de usuario
	//============================================
	
	@RequestMapping("/registro")
	public String paginaRegistro(Model model){
		return "registro";
	}
	
	@PostMapping("/registro_completo")
	public String setUserName(Model model, HttpSession session, Usuario user) {

		List<Usuario> lista = usuarioRepository.findAll();
		for(Usuario u : lista){
			if(u.getName().equals(user.getName())){
				return usuarioEncontrado(model, session, u);
			}
		}
		
		Tablon publico = new Tablon(user.getName(), false);
		Tablon privado = new Tablon(user.getName(), true);

		publico = tablonRepository.save(publico);
		privado = tablonRepository.save(privado);

		user.setTablonPublico(publico);
		user.setTablonPrivado(privado);
		
		user = usuarioRepository.save(user);
		session.setAttribute("user", user);

		return "redirect:/";
	}
	
	public String usuarioEncontrado(Model model, HttpSession session, Usuario user){

		Tablon privado = tablonRepository.findByUserNameAndPrivado(user.getName(), true);
		privado.setNotas(notaRepository.findByTablon(privado));
		
		Tablon publico = tablonRepository.findByUserNameAndPrivado(user.getName(), false);
		publico.setNotas(notaRepository.findByTablon(publico));
		
		user.setTablonPrivado(privado);
		user.setTablonPublico(publico);

		System.out.println(user.getTablonPrivado().getNotas().size() + " " + privado.getNotas().size());
		for(Nota n : user.getTablonPrivado().getNotas())
			System.out.println(n);
		
		session.setAttribute("user", user);
		return "redirect:/";
	}
}