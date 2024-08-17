package com.awildemann.demo_park_api.web.dto.mapper;

import com.awildemann.demo_park_api.entity.Usuario;
import com.awildemann.demo_park_api.web.dto.UsuarioCreateDTO;
import org.modelmapper.ModelMapper;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO createDto) {
        return new ModelMapper().map(createDto, Usuario.class);
    }
}
