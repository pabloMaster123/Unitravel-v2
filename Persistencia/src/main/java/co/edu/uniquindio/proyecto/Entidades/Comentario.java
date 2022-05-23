package co.edu.uniquindio.proyecto.Entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario implements Serializable {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false)
    @Max(5)
    private Integer calificacion;

    @Column(nullable = false)
    @NotBlank
    private String comentario;

    @ManyToOne
    @ToString.Exclude
    private Hotel hotel;

    public Comentario(int calificacion, String comentario, Hotel hotel) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.hotel = hotel;
    }

    //Falta el cliente

}
