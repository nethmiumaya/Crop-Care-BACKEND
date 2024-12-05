package lk.ijse.main.repository;

import lk.ijse.main.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {
    @Query("SELECT s FROM Staff s WHERE s.email = :email")
    Optional<Staff> findByEmail(String email);
}
