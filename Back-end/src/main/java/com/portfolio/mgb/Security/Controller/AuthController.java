package com.portfolio.mgb.Security.Controller;

import com.portfolio.mgb.Security.Dto.JwtDTO;
import com.portfolio.mgb.Security.Dto.LoginUsuario;
import com.portfolio.mgb.Security.Dto.NuevoUsuario;
import com.portfolio.mgb.Security.Entity.Rol;
import com.portfolio.mgb.Security.Entity.Usuario;
import com.portfolio.mgb.Security.Enums.RolNombre;
import com.portfolio.mgb.Security.Service.RolService;
import com.portfolio.mgb.Security.Service.UsuarioService;
import com.portfolio.mgb.Security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager  authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?>nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new Mensaje("Campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())){
            return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"),HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail())){
            return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"),HttpStatus.BAD_REQUEST);
        }

        Usuario usuario=new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(),passwordEncoder.encode(nuevoUsuario.getPassword()));

    Set<Rol> roles=new HashSet<>();
    roles.add(rolService.getByRolName(RolNombre.ROLE_USER).get());

    if(nuevoUsuario.getRoles().contains("admin"))
        roles.add(rolService.getByRolName(RolNombre.ROL_ADMIN).get());
        usuario.setRoles((HashSet<Rol>) roles);
        usuarioService.save(usuario);

        return new ResponseEntity(new Mensaje("Usuario guardado"),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO>login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
            if(bindingResult.hasErrors())
                return new ResponseEntity(new Mensaje("Campos mal puestos"),HttpStatus.BAD_REQUEST);

            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginUsuario.getNombreUsuario(),loginUsuario.getPassword()
            ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        UserDetails userDetails=(UserDetails) authentication.getPrincipal();

        JwtDTO jwtDTO;
        jwtDTO = new JwtDTO(jwt,
                userDetails.getUsername(),
                userDetails.getAuthorities());

        return new ResponseEntity(jwtDTO,HttpStatus.OK);
    }

}
