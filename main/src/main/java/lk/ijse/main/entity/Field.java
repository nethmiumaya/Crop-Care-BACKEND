package lk.ijse.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.geo.Point;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "field")
public class Field implements SuperEntity{
    @Id
    private String fieldCode;
    @Column(name = "field_name")
    private String fieldName;
    @Column(name = "field_location")
    private Point fieldLocation;
    @Column(name = "extent_size")
    private double extentSize;
    @Column(name = "field_image1",columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(name = "field_image2",columnDefinition = "LONGTEXT")
    private String fieldImage2;

    @OneToMany(mappedBy = "field")
    @ToString.Exclude
    private List<Crop> crops = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    @ToString.Exclude
    private List<Staff> staffList = new ArrayList<>();
    @OneToMany(mappedBy = "assignedFieldDetails")
    @ToString.Exclude
    private List<Equipment> equipmentList = new ArrayList<>();
    @OneToMany(mappedBy = "field")
    @ToString.Exclude
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();
}