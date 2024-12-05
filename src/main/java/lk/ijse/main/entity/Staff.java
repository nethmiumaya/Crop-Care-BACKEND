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
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "join_date")
    private Date joinDate;
    private Date DOB;
    private String addLine01;
    @Column(name = "contact_no")
    private String conNo;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "staffList")
    private List<Field> assignedFieldDetails = new ArrayList<>();

    @ManyToMany(mappedBy = "staff")
    private List<MonitoringLog> monitoringCropDetails = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<Vehicle> vehicles = new ArrayList<>();
}