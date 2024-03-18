<<<<<<< HEAD
package grupoLem.appGestion.repository;


import grupoLem.appGestion.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
   Optional<UserEntity>findByUsername(String username);

  public Optional<UserEntity> findById(Integer idUserentity);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}
=======
package grupoLem.appGestion.repository;


import grupoLem.appGestion.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
   Optional<UserEntity>findByUsername(String username);

  public Optional<UserEntity> findById(Integer idUserentity);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
