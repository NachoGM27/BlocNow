package es.sidelab.tablonNotas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UsuarioRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/inicio").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/loginerror").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		http.authorizeRequests().antMatchers("/registro").permitAll();
		http.authorizeRequests().antMatchers("/registroerror").permitAll();
		http.authorizeRequests().antMatchers("/registro_completo").permitAll();

		// Private pages (all other pages)
		http.authorizeRequests().anyRequest().authenticated();
		 
		// Login form
		http.formLogin().loginPage("/login");
		http.formLogin().failureUrl("/loginerror");
		
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		
		http.formLogin().defaultSuccessUrl("/logincorrecto");
		
		//Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");
		
		http.csrf().disable();
		
	 }
}
