package com.clasee.Crudd.modelo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carro {
    //public Object set;
    private Long id;
    private String Marca;
    private String Modelo;
    private String Placa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return Marca; }
    public void setMarca(String Marca) { this.Marca = Marca; }
    public String getModelo() { return Modelo; }
    public void setModelo(String Modelo) { this.Modelo = Modelo; }
    public String getPlaca() { return Placa; }
    public void setPlaca(String Placa) { this.Placa = Placa; }


}
