package com.example.Biblioteca.service;

import com.example.Biblioteca.exception.ResourceNotFoundException;
import com.example.Biblioteca.model.Autor;
import com.example.Biblioteca.repository.AutorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com id " + id));
    }

    public Autor criar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Long id, Autor autorAtualizado) {
        Autor autorExistente = buscarPorId(id);
        autorExistente.setNome(autorAtualizado.getNome());
        autorExistente.setNacionalidade(autorAtualizado.getNacionalidade());
        return autorRepository.save(autorExistente);
    }

    public void remover(Long id) {
        Autor autor = buscarPorId(id);
        autorRepository.delete(autor);
    }
}
