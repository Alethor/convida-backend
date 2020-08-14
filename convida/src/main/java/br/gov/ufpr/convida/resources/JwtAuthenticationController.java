package br.gov.ufpr.convida.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.gov.ufpr.convida.config.JwtTokenUtil;
import br.gov.ufpr.convida.domain.AccountCredentials;
import br.gov.ufpr.convida.domain.JwtResponse;
import br.gov.ufpr.convida.domain.User;
import br.gov.ufpr.convida.repository.UserRepository;
import br.gov.ufpr.convida.security.LdapConnection;
import br.gov.ufpr.convida.services.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    UserRepository user;
    @Autowired
    private PasswordEncoder bcrypt;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AccountCredentials authenticationRequest)
            throws Exception {

        if (authenticationRequest.getUsername().endsWith("@ufpr.br")) {
            

            LdapConnection auth = new LdapConnection();
            if (auth.connectToLDAP(authenticationRequest.getUsername(), authenticationRequest.getPassword()) == true) {
                User newUser = user.findByLogin(authenticationRequest.getUsername());
                
                if (newUser == null) {
                    User u = new User();
                    u.setLogin((authenticationRequest.getUsername()));
                    u.setPassword(bcrypt.encode(authenticationRequest.getPassword()));
                    u.setEmail(authenticationRequest.getUsername());
                    user.insert(u);
                    String idUsuario = u.getId();

                    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
                    final UserDetails userDetails = userDetailsService
                            .loadUserByUsername(authenticationRequest.getUsername());
                    
                    System.out.println("Username: " + userDetails.getUsername());
                    System.out.println("Password: " + userDetails.getPassword());
                    
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    return ResponseEntity.ok(new JwtResponse(token));

                } else {
                	
                    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
                    final UserDetails userDetails = userDetailsService
                            .loadUserByUsername(authenticationRequest.getUsername());
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    return ResponseEntity.ok(new JwtResponse(token));
                }
            } else {
                return ResponseEntity.status(405).build();
            }
        } else {

            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}