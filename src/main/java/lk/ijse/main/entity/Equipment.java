package lk.ijse.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Staff assignedStaffDetails;

    @ManyToOne
    @JoinColumn(name = "field_id",referencedColumnName = "fieldCode")
    private Field assignedFieldDetails;

}