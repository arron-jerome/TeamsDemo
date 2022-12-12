package com.disney.teams.common.dao.service.api;

import com.disney.teams.model.criteria.GenericCriteria;
import com.disney.teams.model.entity.common.GenericEntity;

import java.io.Serializable;

public interface IFindService<ID extends Serializable, PO extends GenericEntity<ID>> {
    PO findFirst();

    PO findByCriteria(GenericCriteria<PO> var1);

    PO findByPropertyAndValue(String var1, Object var2);
}
