package com.alirizakocas.filestorage.security;


import com.alirizakocas.filestorage.jwt.JwtAuthenticationEntryPoint;
import com.alirizakocas.filestorage.jwt.JwtTokenFilter;
import com.alirizakocas.filestorage.jwt.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // web security enable ettik
@Configuration // bean tanımlamak için gerekli
@EnableGlobalMethodSecurity(prePostEnabled = true) // tüm methodları security yaptık
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //JwtTokenFilter'ı çağırdık
    private final JwtTokenFilter jwtTokenFilter;

    //Jwt authentication entrypoint'i çağırdık
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final PasswordEncoder passwordEncoder;


    // AuthenticationManager'dan bean oluşturduk
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrfi disable ettik
        http.authorizeRequests().antMatchers("/h2-console/**","/login","/user/create","/v2/api-docs/**","/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/swagger-resources/**").permitAll(). // bu requestlere izin verdik
                anyRequest().authenticated(); // login ile başlayan istekleri permit ettik ve h2 console'u açtık

        http.headers().frameOptions().disable(); // frame option'ı h2 console için disable ettik

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // session'ı stateless yaptık
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint); // spring default'ta 403 döner,401 dönmesi için ayarladık. Custom entrypointi çağırdık.

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // filtremizi verdik.
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);//password encoder atadık
    }

}