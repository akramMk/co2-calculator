package fr.univlyon1.mif10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = {
        "http://localhost", "http://localhost:8080", "http://localhost:8080/",
        "http://127.0.0.1:5500", "https://127.0.0.1:5500", "http://127.0.0.1:5500/", "https://127.0.0.1:5500/",
        "http://127.0.0.1", "https://127.0.0.1", "http://127.0.0.1/", "https://127.0.0.1/",
        "http://192.168.75.108", "https://192.168.75.108", "http://192.168.75.108/", "https://192.168.75.108/"
})
public interface ControllerBase {
}
