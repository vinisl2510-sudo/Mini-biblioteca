package com.minibiblioteca.config;

import com.minibiblioteca.model.Livro;
import com.minibiblioteca.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final LivroRepository livroRepository;

    public DataSeeder(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public void run(String... args) {
        if (livroRepository.count() > 0) {
            return;
        }

        livroRepository.save(new Livro("Dom Casmurro", "Machado de Assis",
                "Um clássico da literatura brasileira sobre a desconfiança de Bentinho.",
                29.90, "Clássicos", 8,
                "https://covers.openlibrary.org/b/isbn/8525406958-L.jpg"));

        livroRepository.save(new Livro("Clean Code", "Robert C. Martin",
                "Boas práticas para escrever código legível e sustentável.",
                89.90, "Tecnologia", 3,
                "https://covers.openlibrary.org/b/isbn/0132350882-L.jpg"));

        livroRepository.save(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry",
                "A história de um príncipe que viaja por diferentes planetas.",
                24.50, "Infantil", 7,
                "https://covers.openlibrary.org/b/isbn/8574068791-L.jpg"));

        livroRepository.save(new Livro("1984", "George Orwell",
                "Um romance distópico sobre vigilância e controle totalitário.",
                34.90, "Ficção", 4,
                "https://covers.openlibrary.org/b/isbn/0451524934-L.jpg"));

        livroRepository.save(new Livro("Effective Java", "Joshua Bloch",
                "Boas práticas essenciais para escrever código Java robusto.",
                100.00, "Tecnologia", 2,
                "https://covers.openlibrary.org/b/isbn/0134685997-L.jpg"));
    }
}