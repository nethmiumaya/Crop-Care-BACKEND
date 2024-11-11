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
@Table(name = "monitoring_log")
public class MonitoringLog implements SuperEntity{
    @Id
    @Column(name = "log_code")
    private String logCode;
    @Column(name = "log_date")
    private Date logDate;
    private String observation;
    @Column(name = "observed_image",columnDefinition = "LONGTEXT")
    private String observedImage;

    @ManyToOne
    @JoinColumn(name = "field_code")
    private Field field;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monitoring_log_crop",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "crop_code")
    )
    private List<Crop> crops = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monitoring_log_staff",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<Staff> staff = new ArrayList<>();
}