package com.example.Biblioteca.service;

import com.example.Biblioteca.exception.ResourceNotFoundException;
import com.example.Biblioteca.model.Autor;
import com.example.Biblioteca.model.Categoria;
import com.example.Biblioteca.model.Livro;
import com.example.Biblioteca.repository.AutorRepository;
import com.example.Biblioteca.repository.CategoriaRepository;
import com.example.Biblioteca.repository.LivroRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository,
                        CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id " + id));
    }

    public Livro criar(Livro livro) {
        Autor autor = recuperarAutor(livro);
        Categoria categoria = recuperarCategoria(livro);

        livro.setAutor(autor);
        livro.setCategoria(categoria);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livroExistente = buscarPorId(id);

        if (livroAtualizado.getTitulo() != null) {
            livroExistente.setTitulo(livroAtualizado.getTitulo());
        }
        if (livroAtualizado.getIsbn() != null) {
            livroExistente.setIsbn(livroAtualizado.getIsbn());
        }
        if (livroAtualizado.getAutor() != null && livroAtualizado.getAutor().getId() != null) {
            livroExistente.setAutor(recuperarAutor(livroAtualizado));
        }
        if (livroAtualizado.getCategoria() != null && livroAtualizado.getCategoria().getId() != null) {
            livroExistente.setCategoria(recuperarCategoria(livroAtualizado));
        }

        return livroRepository.save(livroExistente);
    }

    public void remover(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }

    private Autor recuperarAutor(Livro livro) {
        Long autorId = livro.getAutor() != null ? livro.getAutor().getId() : null;
        if (autorId == null) {
            throw new ResourceNotFoundException("Autor deve ser informado");
        }
        return autorRepository.findById(autorId)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com id " + autorId));
    }

    private Categoria recuperarCategoria(Livro livro) {
        Long categoriaId = livro.getCategoria() != null ? livro.getCategoria().getId() : null;
        if (categoriaId == null) {
            throw new ResourceNotFoundException("Categoria deve ser informada");
        }
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id " + categoriaId));
    }
}
