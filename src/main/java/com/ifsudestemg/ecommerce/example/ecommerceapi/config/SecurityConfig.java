package com.ifsudestemg.ecommerce.example.ecommerceapi.config;

import com.ifsudestemg.ecommerce.example.ecommerceapi.security.JwtAuthFilter;
import com.ifsudestemg.ecommerce.example.ecommerceapi.security.JwtService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtService jwtService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, loginService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(loginService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/clientes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/administradores/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/fornecedoresPessoaJuridica/**")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/fornecedoresPessoaFisica/**")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/cartoes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/categorias/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/compraProdutos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/cupons/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/enderecos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/estoques/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/itensCompra/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/itensVenda/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/pagamentosParcelado/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/pagamentosVista/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/produtos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/vendas/**")
                .hasAnyRole("USER", "ADMIN")
                /*.antMatchers("/api/v1/turmas/**")
                .hasRole("ADMIN")*/
                .antMatchers(HttpMethod.POST, "/api/v1/logins/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
