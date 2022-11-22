package com.tmp.spring_template.demo.database.jpa_repository;

import com.tmp.spring_template.demo.database.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DemoRepository extends JpaRepository<DemoEntity, Long> {

    DemoEntity findFirstByTextAndFlag(String text, boolean flag);

    Optional<DemoEntity> findFirstByText(String text);

    List<DemoEntity> findAllByFlag(boolean flag);

    @Query(value = "SELECT D.* FROM demo_table D WHERE :flag IS NULL OR (D.flag IS NOT NULL AND D.flag = :flag)", nativeQuery = true)
    List<DemoEntity> getEntitiesNative(Boolean flag);

    @Query("SELECT D FROM DemoEntity D WHERE D.text = :textFilter")
    List<DemoEntity> getEntities(@Param("textFilter") String text);

    /**
    DİKKAT ET!: Buradaki tablo isimleri case-sensitive
     */
    @Query(value = "SELECT D.* FROM demo_table D WHERE D.text = :textFilter AND D.flag = :flagFilter", nativeQuery = true)
    List<DemoEntity> getEntities(@Param("textFilter") String text, @Param("flagFilter") boolean flag);

    @Modifying
    @Query("UPDATE DemoEntity D SET D.text = :textValue, D.numValue = :number WHERE D.id = :id")
    int updateTextField(@Param("id") Long recordId, String textValue, @Param("number") Long numValue);

    /**
     DİKKAT ET!: Buradaki tablo isimleri case-sensitive
     */
    @Modifying
    @Query(value = "UPDATE demo_table D SET D.text = :text, D.num_value = :numValue WHERE D.id = :id", nativeQuery = true)
    int updateTextFieldNative(@Param("id") Long recordId, @Param("text") String textValue, Long numValue);

}
