package lk.ijse.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
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
    private List<Crop> crops = new ArrayList<>();

    @ManyToMany(mappedBy = "assignedFieldDetails")
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "assignedFieldDetails")
    private List<Equipment> equipmentList = new ArrayList<>();

    @OneToMany(mappedBy = "field")
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();


}