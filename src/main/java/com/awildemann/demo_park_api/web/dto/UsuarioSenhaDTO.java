package com.awildemann.demo_park_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDTO {

    @NotBlank
    @Size(min = 6, max = 12)
    private String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 12)
    private String novaSenha;

    @NotBlank
    @Size(min = 6, max = 12)
    private String confirmaSenha;
}
