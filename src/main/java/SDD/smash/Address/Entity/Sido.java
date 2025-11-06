package SDD.smash.Address.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Sido {

    @Id
    @Column(name = "sido_code", length = 2)
    private String sidoCode;

    @Column(nullable = false)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "sido", fetch = FetchType.LAZY)
    private List<Sigungu> sigunguList;
}
