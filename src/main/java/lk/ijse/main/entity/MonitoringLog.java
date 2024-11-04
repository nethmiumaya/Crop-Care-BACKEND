package lk.ijse.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "monitoringLog")
public class MonitoringLog implements SuperEntity{
    @Id
    private String logCode;
    private Date logDate;
    private String observation;
    @Column(name = "observed_image",columnDefinition = "LONGTEXT")
    private String observedImage;

    @ManyToOne
    @JoinColumn(name = "field_code",referencedColumnName = "fieldCode")
    private Field field;

    @ManyToMany
    @JoinTable(
            name = "monitoring_log_crop",
            joinColumns = @JoinColumn(name = "log_code",referencedColumnName = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "crop_code",referencedColumnName = "code")
    )
    private List<Crop> crops = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "monitoring_log_staff",
            joinColumns = @JoinColumn(name = "log_code",referencedColumnName = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "staff_id",referencedColumnName = "id")
    )
    private List<Staff> staff = new ArrayList<>();
}