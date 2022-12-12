package com.disney.teams.common.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 带数据的分页接口
 * @param <T>
 */
public interface PagedList<T> extends Pagination, Cloneable, Serializable{
    
    List<T> getContent();
    
}
