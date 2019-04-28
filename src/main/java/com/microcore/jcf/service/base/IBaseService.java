package com.microcore.jcf.service.base;

/**
 * 公共Service接口
 *
 * @param <T> 泛型实体需继承BaseEntity
 * @author leizhenyang
 */
public interface IBaseService<T> extends ICRUDService<T>, IVariableQueryService
{

}
