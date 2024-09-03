package com.awildemann.demo_park_api.service;

import com.awildemann.demo_park_api.entity.Vaga;
import com.awildemann.demo_park_api.exception.CodigoUniqueViolationException;
import com.awildemann.demo_park_api.repository.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException(
                    String.format("Vaga com código %s já cadastrada", vaga.getCodigo()));
        }
    }

    @Transactional
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código %s não foi encontrada", codigo))
        )
    }
}
