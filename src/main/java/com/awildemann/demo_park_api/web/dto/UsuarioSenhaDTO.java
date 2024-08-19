package com.awildemann.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDTO {

    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;
}
