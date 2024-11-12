package com.clasee.Crudd.controller;

import com.clasee.Crudd.Servicio.ServicioCarro;
import com.clasee.Crudd.modelo.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carros")
public class ControladorCarro {
    private final ServicioCarro servicioCarro;

    public ControladorCarro(ServicioCarro servicioCarro) {
        this.servicioCarro = servicioCarro;
    }

    @GetMapping
    public List<Carro> getAllCarros() {
        return servicioCarro.getCarros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarroById(@PathVariable Long id) {
        Optional<Carro> carro = servicioCarro.getCarroById(id);
        return carro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Carro crearCarro(@RequestBody Carro carro) {

        return servicioCarro.crearCarro(carro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> ActualizarCarro(@PathVariable Long id, @RequestBody Carro DetalllesCarro) {
        Optional<Carro> carro = servicioCarro.actualizarCarro(id, DetalllesCarro);
        return carro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarCarro(@PathVariable Long id) {
        if (servicioCarro.EliminarCarro(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();

        }

    }

    @GetMapping ("/reporte")
    public ResponseEntity<FileSystemResource> generarYDescargarReporte() {
        List<Carro> carros = servicioCarro.getCarros();
        File reporte = new File("reporte_carros.txt");

        // Genera el archivo reporte_carros.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reporte_carros.txt"))) {
            writer.write("Reporte de Carros:\n");
            writer.write("=====================\n");
            for (Carro carro : carros) {
                writer.write("ID: " + carro.getId() + "\n");
                writer.write("Marca: " + carro.getMarca() + "\n");
                writer.write("Modelo: " + carro.getModelo() + "\n");
                writer.write("Placa: " + carro.getPlaca() + "\n");
                writer.write("---------------------\n");
            }
            writer.write("Fin del reporte\n");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

        // Verifica si el archivo existe antes de enviarlo
        if (!reporte.exists()) {
            return ResponseEntity.status(404).body(null);
        }

        FileSystemResource resource = new FileSystemResource(reporte);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_carros.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Usa OCTET_STREAM para forzar la descarga

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(reporte.length())
                .body(resource);
    }
}
    /*@GetMapping("/reporte")
    public ResponseEntity<String> generarReporte() {
        List<Carro> carros = servicioCarro.getCarros();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reporte_carros.txt"))) {
            writer.write("Reporte de Carros:\n");
            writer.write("=====================\n");
            for (Carro carro : carros) {
                writer.write("ID: " + carro.getId() + "\n");
                writer.write("Marca: " + carro.getMarca() + "\n");
                writer.write("Modelo: " + carro.getModelo() + "\n");
                writer.write("Placa: " + carro.getPlaca() + "\n");


                writer.write("---------------------\n");
            }
            writer.write("Fin del reporte\n");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al generar el reporte");
        }

        return ResponseEntity.ok("Reporte generado exitosamente en 'reporte_carros.txt'");
    }*/





