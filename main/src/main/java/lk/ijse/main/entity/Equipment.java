package lk.ijse.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment")

public class Equipment implements SuperEntity  {
    @Id
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff assignedStaffDetails;
    @ManyToOne
    @JoinColumn(name = "field_id")
    @ToString.Exclude
    private Field assignedFieldDetails;
}