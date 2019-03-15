package com.quinnox.flm.tms.generic.service;

import java.io.Serializable;

/**
 * @author AmareshP
 */
public interface GenericService<T,PK extends Serializable> {

    T get(PK id);
    void remove(PK id);
}
