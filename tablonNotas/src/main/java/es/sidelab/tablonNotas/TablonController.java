package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class TablonController {

	public TablonController() {
	}

	@GetMapping("/")
	public String tablon(Model model) {
		return "inicio";
	}
}