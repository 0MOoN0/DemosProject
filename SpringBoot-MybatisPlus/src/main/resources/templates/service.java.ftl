package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * 分页查询${entity}
     * @param ${entity?uncap_first}searchAO 分页查询AO对象
     * @return 结果
     */
    R<Ipage<${entity?uncap_first}VO>> selectPage(${entity}searchAO ${entity?uncap_first}searchAO);

     /**
     * 新增或更新${entity}
     * @param ${entity?uncap_first}searchAO 新增或更新的${entity}的AO对象
     * @return 结果
     */
     Boolean saveOrUpdate(${entity}EditAO ${entity?uncap_first}EditAO);

     /**
     * 批量逻辑删除${entity}
     * @param ids 要删除的${entity}对象的ID
     * @return 结果
     */
     Boolean deleteByIds(Long[] ids);
 }
</#if>
