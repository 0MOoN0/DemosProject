package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
<#assign object=entity?uncap_first/>
<#assign objectSearchAO=entity?uncap_first+"SearchAO"/>
<#assign objectEditAO=entity?uncap_first+"EditAO"/>
<#assign objectVO=entity?uncap_first+"VO"/>

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
    * 分页查询${entity}
    * @param ${entity?uncap_first}searchAO 分页查询AO对象
    * @return 结果
    */
    R<Ipage<${entity?uncap_first}VO>> selectPage(${entity}searchAO ${entity?uncap_first}searchAO){
        ${entity} ${object} = new ${entity}();
        LambdaQueryWrapper<${entity}> queryWrapper = Wrappers.lambdaQuery(${entity});
 <#list table.fields as field>
<#--  对创建时间进行范围查询-->
     <#if "createTime"==field.propertyName>
        // 对时间进行范围查询
        queryWrapper.ge(StringUtils.isNotNull(${objectSearchAO}.getStartCreateTime()),${entity}::getCreateTime, ${objectSearchAO}.getStartCreateTime());
        queryWrapper.le(StringUtils.isNotNull(${objectSearchAO}.getEndCreateTime()),${entity}::getCreateTime, ${objectSearchAO}.getEndCreateTime());
     <#elseif !cfg.searchAOExclude?seq_contains(field.propertyName)>
        ${object}.set${field.propertyName?cap_first}(${objectSearchAO}.get${field.propertyName?cap_first}());
     </#if>
 </#list>
        // 分页设置
        Page<${entity}> page = new Page<>(${objectSearchAO}.getPage(),${objectSearchAO}.getPageSize());
        // 分页查询
        Page<${entity}> selectPage = getBaseMapper().selectPage(page,queryWrapper);
        // DO to VO
        return selectPage.convert(DO->{
            ${objectVO?cap_first} ${objectVO} = new ${objectVO?cap_first}();
 <#list table.fields as field>
  <#if !cfg.voExclude?seq_contains(field.propertyName)>
            ${objectVO}.set${field.propertyName?cap_first}(DO.get${field.propertyName?cap_first}());
  </#if>
 </#list>
            return ${objectVO};
       });
   }

    /**
    * 新增或更新${entity}
    * @param ${entity?uncap_first}searchAO 新增或更新的${entity}的AO对象
    * @return 结果
    */
    Boolean saveOrUpdate(${entity}EditAO ${entity?uncap_first}EditAO){
        // AO to DO
        ${entity} ${object} = new ${entity}();
 <#list table.fields as field>
  <#if !cfg.editAoExclude?seq_contains(field.propertyName)>
        ${object}.set${field.propertyName?cap_first}(${objectSearchAO}.get${field.propertyName?cap_first}());
  </#if>
 </#list>
        ${object}.setUpdateTime(new Date());
        // 如果ID为空，则表示新增
        if(StringUtils.isNull(${objectEditAO}.getId())){
            ${object}.setIsDelete(false);
            ${object}.setCreateTime(new Date());
        }
        return saveOrUpdate(${object});
    }

    /**
    * 批量逻辑删除${entity}
    * @param ids 要删除的${entity}对象的ID
    * @return 结果
    */
    Boolean deleteByIds(Long[] ids){
        List<User> list = Arrays.stream(ids).map(id -> {
            ${entity} ${object} = new ${entity}();
            ${object}.setId(id);
            ${object}.setIsDelete(true);
            return ${object};
        }).collect(Collectors.toList());
        return updateBatchById(list);
    }
}
</#if>
