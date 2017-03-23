package es.sidelab.tablonNotas;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private MensajeRepository mensajeRepository;
	
	public AppController() {
	}

	//============================================
	//  Pagina de inicio
	//============================================

	@GetMapping("/")
	public String paginaInicio(Model model, HttpSession session) {
		String userName = (String) session.getAttribute("userName");
		session.setAttribute("loginError", false);
		if(session.isNew() || userName.equals("anonimo"))
			return usuarioAnonimo(model, session);
		else
			return usuarioRegistrado(model, session, usuarioRepository.findByName(userName));
	}
	
	public String usuarioAnonimo(Model model, HttpSession session){

		model.addAttribute("anonima", session.getAttribute("contenido_anonima"));
		session.setAttribute("userName", "anonimo");
		return "inicio";
	}
	
	public String usuarioRegistrado(Model model, HttpSession session, Usuario user){		
		Tablon privado = tablonRepository.findByUserNameAndPrivado(user.getName(), true).get(0);
		List<Nota> notasPrivadas = notaRepository.findByTablon(privado);
		
		Tablon publico = tablonRepository.findByUserNameAndPrivado(user.getName(), false).get(0);
		List<Nota> notasPublicas = notaRepository.findByTablon(publico);

		model.addAttribute("nombre", user.getName());
		model.addAttribute("notas_privadas", notasPrivadas);
		model.addAttribute("notas_publicas", notasPublicas);	

		model.addAttribute("amigos", user.getAmigos());	
		
		return "pagina_usuario";
	}
	
	//============================================
	//  Registro de usuario
	//============================================
	
	@RequestMapping("/registro")
	public String paginaRegistro(Model model){
		return "registro";
	}

	@PostMapping("/registro_completo")
	public String setUserName(Model model, HttpSession session, Usuario usuario, @RequestParam("password") String pass ) {
		Usuario searchUser = usuarioRepository.findByName(usuario.getName());
		if(searchUser != null) return "redirect:/registro";
		
		usuario.setPasswordHash(new BCryptPasswordEncoder().encode(pass));
		
		Tablon publico = new Tablon(usuario.getName(), false);
		Tablon privado = new Tablon(usuario.getName(), true);

		publico = tablonRepository.save(publico);
		privado = tablonRepository.save(privado);

		usuario.setTablonPublico(publico);
		usuario.setTablonPrivado(privado);
		
		usuario = usuarioRepository.save(usuario);

		return "redirect:/login";
	}
	
	//============================================
	//  Log in de usuario
	//============================================
	
	@GetMapping("/login")
	public String paginaLogin(Model model, HttpSession session){
		if(session.getAttribute("loginError") == null) session.setAttribute("loginError", false);
		model.addAttribute("error", (boolean) session.getAttribute("loginError"));
		session.setAttribute("loginError", false);
		return "login";
	}
	
	@GetMapping("/loginerror")
	public String paginaLoginError(HttpSession session){
		session.setAttribute("loginError", true);
		return "redirect:/login";
	}
	
	@GetMapping("/logincorrecto")
	public String paginaLoginCorrecto(HttpSession session){

		String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Usuario usuario = usuarioRepository.findByName(userName);
		
		session.setAttribute("userName", usuario.getName());
		
		Tablon privado = tablonRepository.findByUserNameAndPrivado(usuario.getName(), true).get(0);
		privado.setNotas(notaRepository.findByTablon(privado));
		
		Tablon publico = tablonRepository.findByUserNameAndPrivado(usuario.getName(), false).get(0);
		publico.setNotas(notaRepository.findByTablon(publico));
		
		usuario.setTablonPrivado(privado);
		usuario.setTablonPublico(publico);
		
		return "redirect:/";
	}
	
	//============================================
	//  Creacion de notas
	//============================================
	
	@RequestMapping("/crear_nota")
	public String nuevaNota(Model model, HttpSession session, @RequestParam boolean privada){
		String userName = (String) session.getAttribute("userName");
		Usuario user = usuarioRepository.findByName(userName);

		session.setAttribute("privada", privada);
		model.addAttribute("nombre", user.getName());
		model.addAttribute("privada", privada ? "privada" : "publica");
		model.addAttribute("anonima", "");
		
		return "crear_nota";
	}
	
	@PostMapping("/guardar_nota")
	public String guardarNota(Model model, HttpSession session, Nota nota) {
		String userName = (String) session.getAttribute("userName");
		Usuario user = usuarioRepository.findByName(userName);
		
		boolean privada = (boolean) session.getAttribute("privada");
		
		Tablon tablon = privada ? user.getTablonPrivado() : user.getTablonPublico();
		
		nota.setTablon(tablon);
		nota = notaRepository.save(nota);
		
		tablon.getNotas().add(nota);
		tablonRepository.save(tablon);

		return "redirect:/";
	}
	
	@RequestMapping("/crear_nota_anonima")
	public String nuevaNotaAnonima(Model model, HttpSession session){
		model.addAttribute("privada", "anónima");
		model.addAttribute("anonima", "_anonima");
		
		return "crear_nota";
	}
	
	@PostMapping("/guardar_nota_anonima")
	public String guardarNotaAnonima(Model model, HttpSession session, Nota nota) {
		session.setAttribute("contenido_anonima", nota.getContenido());
		return "redirect:/";
	}
	
	//============================================
	//  Ver notas
	//============================================
	
	@RequestMapping("/nota")
	public String verNota(Model model, HttpSession session, @RequestParam boolean publico, @RequestParam long id){
		
		Nota nota = notaRepository.findById(id).get(0);
		Tablon tablon = tablonRepository.findById(nota.getTablon().getId()).get(0);
		
		session.setAttribute("notaId", id);
		
		model.addAttribute("nota_contenido", nota.getContenido());
		model.addAttribute("publico", publico);
		model.addAttribute("nombre", tablon.getUserName());
		
		if(publico)
			model.addAttribute("comentario", nota.getComentarios());
		
		return "nota";
	}
	
	@RequestMapping("/nota_anonima")
	public String verNotaAnonima(Model model, HttpSession session){		
		model.addAttribute("nota_contenido", session.getAttribute("contenido_anonima"));
		model.addAttribute("publico", false);
		model.addAttribute("nombre", "anónimo");
		
		return "nota";
	}
		
	//============================================
	//  Crear comentario
	//============================================
	
	@RequestMapping("/comentario")
	public String nuevoComentario(Model model, HttpSession session){
		
		return "comentario";
	}
	
	@PostMapping("/guardar_comentario")
	public String guardarComentario(Model model, HttpSession session, Comentario comentario) {
		String userName = (String) session.getAttribute("userName");
		
		long notaId = (long)session.getAttribute("notaId");
		Nota nota = notaRepository.findById(notaId).get(0);
		
		comentario.setUsuario(userName);
		comentario.setNota(nota);
		comentario = comentarioRepository.save(comentario);
		nota.getComentarios().add(comentario);
		
		nota = notaRepository.save(nota);

		return "redirect:/nota?publico=true&id=" + notaId;
	}
	
	//============================================
	//  Amigos
	//============================================
	
	@PostMapping("/add_amigo")
	public String addAmigoRequest(Model model, HttpSession session, @RequestParam String friendName) {
		String userName = (String) session.getAttribute("userName");
		Usuario user = usuarioRepository.findByName(userName);
		
		Usuario userFriend = usuarioRepository.findByName(friendName);
		if(userFriend != null)
			return addAmigo(session, user, userFriend);
		
		return "redirect:/";
	}
	
	private String addAmigo(HttpSession session, Usuario user, Usuario friend){
		user.getAmigos().add(friend);
		friend.getAmigos().add(user);

		user = usuarioRepository.save(user);
		friend = usuarioRepository.save(friend);

		return "redirect:/";
	}
	
	@RequestMapping("/ver_amigo")
	public String verAmigo(Model model, HttpSession session, @RequestParam String friendName) {
		String userName = (String) session.getAttribute("userName");
		Usuario user = usuarioRepository.findByName(userName);
		
		session.setAttribute("receptor", friendName);
		
		Usuario friend = usuarioRepository.findByName(friendName);
		Tablon publico = tablonRepository.findByUserNameAndPrivado(friendName, false).get(0);

		model.addAttribute("nombre", friendName);
		model.addAttribute("notas_publicas", publico.getNotas());

		List<Mensaje> mensajesEnviados = mensajeRepository.findByEmisorAndReceptor(user, friend);
		List<Mensaje> mensajesRecibidos = mensajeRepository.findByEmisorAndReceptor(friend, user);
		
		for(Mensaje m : mensajesEnviados) m.setIsMio(true);
		for(Mensaje m : mensajesRecibidos) m.setIsMio(false);
		
		List<Mensaje> allMensajes = new ArrayList<Mensaje>();
		allMensajes.addAll(mensajesEnviados);
		allMensajes.addAll(mensajesRecibidos);
		
		allMensajes.sort(new Comparator<Mensaje>(){
			@Override
			public int compare(Mensaje arg0, Mensaje arg1) {
				return arg0.getId() > arg1.getId() ? 1 : -1;
			}
		});
		
		model.addAttribute("mensajes", allMensajes);
		
		return "pagina_amigo";
	}
	
	@PostMapping("/enviar_mensaje")
	public String enviarMensaje(Model model, HttpSession session, Mensaje mensaje) {

		String receptor = (String) session.getAttribute("receptor");
		Usuario friend = usuarioRepository.findByName(receptor);
		
		if(mensaje.getContenido().equals(""))
			return "redirect:/ver_amigo?friendName=" + friend.getName();
		
		String userName = (String) session.getAttribute("userName");
		Usuario user = usuarioRepository.findByName(userName);
		
		mensaje.setEmisor(user);
		mensaje.setReceptor(friend);
		mensaje = mensajeRepository.save(mensaje);

		user.getMensajes().add(mensaje);
		usuarioRepository.save(user);
		
		friend.getMensajes().add(mensaje);
		usuarioRepository.save(friend);
		
		sendEmail(friend.getEmail(), user.getName() + " te ha enviado un mensaje", mensaje.getContenido());
		
		return "redirect:/ver_amigo?friendName=" + friend.getName();
	}
	
	//============================================
	//  Servicios
	//============================================
	
	private void sendEmail(String email, String asunto, String cuerpo)
	{
		try {
	    	Socket socket = new Socket("127.0.0.1", 9999);
			PrintWriter escribirServidor = new PrintWriter(socket.getOutputStream(),true);
	    	escribirServidor.println(email);
	    	escribirServidor.println(asunto);
	    	escribirServidor.println(cuerpo);
	    	escribirServidor.close();
	    	socket.close();
		} catch (IOException e) {}
	}
}