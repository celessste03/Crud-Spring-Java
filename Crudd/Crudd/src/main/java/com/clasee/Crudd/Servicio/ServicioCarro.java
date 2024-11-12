package com.clasee.Crudd.Servicio;
import com.clasee.Crudd.modelo.Carro;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ServicioCarro {

    private List<Carro> carros = new ArrayList<>();
    private Long nextId = 1L;

    public List<Carro> getCarros(){
        return carros;
    }

    public Optional<Carro> getCarroById(Long id){
        return carros.stream().filter(carro -> carro.getId().equals(id)).findFirst();
    }
    public Carro crearCarro(Carro carro) {
        carro.setId(nextId++);
        carros.add(carro);
        return carro;
    }
    public Optional<Carro> actualizarCarro(Long id, Carro carro) {
        Optional<Carro> carroOptional = getCarroById(id);
        carroOptional.ifPresent(c -> {
            c.setMarca(carro.getMarca());
            c.setModelo(carro.getModelo());
            c.setPlaca(carro.getPlaca()); });
        return carroOptional;
    }
    public boolean EliminarCarro(Long id){
        return carros.removeIf(carro -> carro.getId().equals(id));
    }

}
