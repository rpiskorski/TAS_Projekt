package project.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.sql.DataSource;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;


    @Override
    public  void configure(AuthenticationManagerBuilder authManBuild) throws Exception{

//        authManBuild.userDetailsService(customUserDetailsServiceImpl)
//                .passwordEncoder(this.bCryptPasswordEncoder);
        authManBuild.jdbcAuthentication()
                .dataSource(this.dataSource)
                .passwordEncoder(this.bCryptPasswordEncoder)
                .usersByUsernameQuery("select name,password,enabled from users where name=?")
                .authoritiesByUsernameQuery("select name,role from users where name=?");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/products").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/services").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/categories").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/products/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/services/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/categories/*").hasAuthority("ADMIN")
                .antMatchers("/register**").anonymous()
                .antMatchers("/services**").permitAll()
                .antMatchers("/products**").permitAll()
                .antMatchers("/categories**").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/users/*").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .usernameParameter("name")
                .passwordParameter("password")
                .and()
                .httpBasic()
                .and()
                .logout()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable();


    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
