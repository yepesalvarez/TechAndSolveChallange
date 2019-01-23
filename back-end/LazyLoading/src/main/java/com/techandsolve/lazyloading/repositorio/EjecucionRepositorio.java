package com.techandsolve.lazyloading.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techandsolve.lazyloading.dominio.Ejecucion;

@Repository
public interface EjecucionRepositorio extends CrudRepository<Ejecucion, Long> {

}
