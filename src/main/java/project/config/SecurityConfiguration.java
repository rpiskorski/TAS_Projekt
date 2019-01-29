package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.services.UserServiceImpl;

import java.util.Arrays;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authManBuild) throws Exception{
        authManBuild
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/products").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/services").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/categories").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/products/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/services/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/categories/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/products/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/products/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/products/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/products/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/services/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/services/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/services/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/services/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/profile**").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/login").anonymous()
                .antMatchers("/register").anonymous()
                .antMatchers("/services**").permitAll()
                .antMatchers("/products**").permitAll()
                .antMatchers("/categories**").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/users/*").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
//                .exceptionHandling().authenticationEntryPoint(unauthHandler)
//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);

    }


    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_SCHEME = "Bearer ";
    public static final String TOKEN_KEY = "TAS_Projekt";
    public static final long TOKEN_VALIDITY = 5*60;




    @Autowired
    private JwtAuthenticationEntryPoint unauthHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){

        return new JwtAuthenticationFilter();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }


    @Autowired
    private AuthenticationManager authenticationManager;










}
