package com.example.projectt2.service;

import com.example.projectt2.entity.Person;
import com.example.projectt2.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository pRepo;

    @Value("${file.upload-dir}")
    private String uploadDirPath;

    public void save(Person p, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadDir = Paths.get(uploadDirPath);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        try {
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            p.setImgUrl("/images/" + fileName); // Kök dizinindeki /images klasörüne göre ayarlayın

        } catch (IOException e) {
            throw new IOException("Uploaded file could not be saved: " + fileName, e);
        }

        pRepo.save(p);
    }

    public List<Person> getAllPerson() {
        return pRepo.findAll();
    }

    public Person getPersonById(int id) {
        return pRepo.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        pRepo.deleteById(id);
    }
}
