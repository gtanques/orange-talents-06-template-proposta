package com.orange.proposta.biometrias.repository;

import com.orange.proposta.biometrias.Biometria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometriaRepository extends JpaRepository<Biometria, String> {
}
