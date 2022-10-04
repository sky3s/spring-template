package com.tmp.springtemplate.database.jpa_repository;

import com.tmp.springtemplate.database.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DemoRepository extends JpaRepository<DemoEntity, Long> {

    DemoEntity findFirstByTextAndFlag(String text, boolean flag);

    Optional<DemoEntity> findFirstByText(String text);

    List<DemoEntity> findAllByFlag(boolean flag);

    @Query("SELECT D FROM DemoEntity D WHERE D.text = :textFilter")
    List<DemoEntity> getEntities(@Param("textFilter") String text);

    /*
    DİKKAT ET!: Buradaki tablo isimleri case-sensitive
     */
    @Query(value = "SELECT D.* FROM demo_table D WHERE D.TEXT = :textFilter AND D.FLAG = :flagFilter", nativeQuery = true)
    List<DemoEntity> getEntities(@Param("textFilter") String text, @Param("flagFilter") boolean flag);

}
