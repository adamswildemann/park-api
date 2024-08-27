package com.awildemann.demo_park_api.service;

import com.awildemann.demo_park_api.entity.Cliente;
import com.awildemann.demo_park_api.exception.CpfUniqueViolationException;
import com.awildemann.demo_park_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                    String.format("CPF %s já consta no sistema.", cliente.getCpf()));
        }
    }
}
