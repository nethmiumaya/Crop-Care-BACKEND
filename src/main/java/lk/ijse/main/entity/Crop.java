package lk.ijse.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class Crop implements SuperEntity{
    @Id
    private String code;
    private String commonName;
    private String scientificName;
    @Column(name = "crop_image",columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String season;
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToMany(mappedBy = "crops")
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();
}
