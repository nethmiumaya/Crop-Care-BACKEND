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
@Table(name = "staff")
public class Staff implements SuperEntity{
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "join_date")
    private Date joinDate;
    private Date DOB;
    private String addLine01;
    private String addLine02;
    private String addLine03;
    private String addLine04;
    private String addLine05;
    @Column(name = "contact_no")
    private String conNo;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "fieldCode")
    )
    private List<Field> assignedFieldDetails = new ArrayList<>();

    @ManyToMany(mappedBy = "staff")
    private List<MonitoringLog> monitoringCropDetails = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<Vehicle> vehicles = new ArrayList<>();
}