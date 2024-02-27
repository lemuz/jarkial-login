package com.jarkial.login.configuration.security;

import com.jarkial.login.configuration.filter.JwtAuthFilter;
import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import com.jarkial.login.services.ctg.CtgCatalogoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${login.ldap.domain}")
    private String domain;

    @Value("${login.ldap.url}")
    private String url;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    MyAccessDeniedHandler accessDeniedHandler;

    @Autowired
    SgdUsuarioDetailsServiceImpl sgdUsuarioDetailsService;

    @Autowired
    CtgCatalogoService ctgCatalogoService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

/*    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.sgdUsuarioDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
*/

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider authenticationProvider(){
        ActiveDirectoryLdapAuthenticationProvider authProvider = new ActiveDirectoryLdapAuthenticationProvider(domain, url);
        authProvider.setConvertSubErrorCodesToExceptions(true);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
     }

     @Bean
     @Override
     public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
     }

     @Override
     protected void configure(HttpSecurity httpSecurity) throws Exception{
        try{
            List<String> allRoles = ctgCatalogoService.findAll().stream().distinct().map(CtgCatalogo::getCtgCatalogoNombre).collect(Collectors.toList());
            httpSecurity.cors().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            httpSecurity.headers()
            .and()
            .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            /*
            .antMatchers("/").permitAll()
            .antMatchers("/resources/**").permitAll()
            .antMatchers("/*.ico").permitAll()
            .antMatchers("/assets/images/*").permitAll()
            .antMatchers("/runtime.*.js").permitAll()
            .antMatchers("/polyfills.*.js").permitAll()
            .antMatchers("/main.*.js").permitAll()
            .antMatchers("/*.*.js").permitAll()
            .antMatchers("/material-icons.*.woff").permitAll()
            .antMatchers("/material-icons.*.woff2").permitAll()
            .antMatchers("/material-icons-outlined.*.woff").permitAll()
            .antMatchers("/material-icons-outlined.*.woff2").permitAll()

            //agregar una pleca despues de los primeros **
            .antMatchers("/assets/**js/*.min.js").permitAll()
            .antMatchers("/assets/**js/*.min.js.map").permitAll()
            .antMatchers("/assets/**css/*.min.css").permitAll()
            .antMatchers("/assets/**css/*.min.css.map").permitAll()
            .antMatchers("/assets/**css/*.css").permitAll()
            .antMatchers("/assets/**fonts/*").permitAll()
            .antMatchers("/assets/**fonts-awesome-4.7.0/**").permitAll()
            //
            
            .antMatchers("/assets/roboto/*.ttf").permitAll()
            .antMatchers("/assets/bootstrap/**").permitAll()
            .antMatchers("/styles.*.css").permitAll()
            .antMatchers("/assets/material-icons/iconfont/**").permitAll()
            */
            .antMatchers("/security/login").permitAll()
            .antMatchers("/security/prueba").hasAnyAuthority(allRoles.toArray(new String[allRoles.size()]))
            .anyRequest().authenticated()
            .and().formLogin().disable()
            .logout().disable();
        }catch(Exception e){
            e.printStackTrace();
        }
     }

     @Override
     public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").exposedHeaders("token").allowedMethods("*");
     }

     @Bean
     CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE","PUT","PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization","token"));
        configuration.setExposedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization","token"));
        configuration.addAllowedHeader("token");
        configuration.addExposedHeader("token");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
     }
}