package com.awildemann.demo_park_api.web.dto.mapper;

import com.awildemann.demo_park_api.entity.Vaga;
import com.awildemann.demo_park_api.web.dto.VagaCreateDTO;
import com.awildemann.demo_park_api.web.dto.VagaResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDTO dto){
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDTO toDTO(Vaga vaga){
        return new ModelMapper().map(vaga, VagaResponseDTO.class);
    }
}
