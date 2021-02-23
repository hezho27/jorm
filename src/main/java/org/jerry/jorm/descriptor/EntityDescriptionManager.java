package org.jerry.jorm.descriptor;

import java.util.Map;
import java.util.Set;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class EntityDescriptionManager {

    private Map<String, EntityDescriptor> entityDescriptorMap;

    public EntityDescriptionManager(Set<Class<?>> classes) {
        entityDescriptorMap = EntityDescriptionGenerator.buildEntityDescriptions(classes);
    }

    public Map<String, EntityDescriptor> getEntityDescriptorMap() {
        return entityDescriptorMap;
    }

    public void setEntityDescriptorMap(Map<String, EntityDescriptor> entityDescriptorMap) {
        this.entityDescriptorMap = entityDescriptorMap;
    }

    public EntityDescriptor getEntityDescriptor(Class<?> clazz){
        return entityDescriptorMap.get(clazz.getName());
    }
}
