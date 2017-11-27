package models.filter.utils;

import models.filter.query.DefaultGroupFilter;
import models.filter.query.annotation.GroupFilter;
import io.ebean.*;
import models.filter.query.annotation.GroupsFilter;
import models.filter.query.annotation.QueryFilter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FilterUtils {

    public static <E extends PagedList> ExpressionList buildQuery(E searchEntity, Finder<?, ?> finder) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ExpressionList expressions = finder.query().where();
        Map<GroupFilter, List<Field>> fieldGroup = buildGroupFilter(searchEntity);
        for (GroupFilter _groupFilter : fieldGroup.keySet()) {
            Junction junction = null;
            junction = invokeJfunction(Junction.class, expressions, _groupFilter.opatator().toString().toLowerCase(), null);
            for (Field _field : fieldGroup.get(_groupFilter)) {
                QueryFilter queryFilter = _field.getAnnotation(QueryFilter.class);
                if (queryFilter == null) continue;
                Object filterVal = PropertyUtils.getProperty(searchEntity, _field.getName());
                invokeJfunctionProcess(Junction.class, junction, queryFilter.operator(), new Class[]{String.class, Object.class}, queryFilter.value(), filterVal);
            }
            junction.endJunction();
        }

        return expressions;

    }

    private static void invokeJfunctionProcess(Class clazz, Object obj, String methodName, Class[] types, String fieldName, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //check type: -> Collection -> Rebuild expression
        if (value instanceof Collection && !"in".equals(methodName)) {
            Collection collection = (Collection) value;
            for (Object _value : collection) {
                invokeJfunction(clazz, obj, methodName, types, fieldName, _value);
            }
        } else invokeJfunction(clazz, obj, methodName, types, fieldName, value);
    }

    private static <T> T invokeJfunction(Class clazz, Object obj, String methodName, Class[] types, Object... params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodInvoke = null;
        if (types == null || types.length < 1) {
            methodInvoke = clazz.getMethod(methodName);
            return (T) methodInvoke.invoke(obj, params);
        }
        methodInvoke = clazz.getMethod(methodName, types);
        return (T) methodInvoke.invoke(obj, params);

    }

    //Build group conditions
    private static Map<GroupFilter, List<Field>> buildGroupFilter(Object searchEntity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field[] fields = searchEntity.getClass().getDeclaredFields();
        Map<GroupFilter, List<Field>> fieldGroups = new HashMap<>();
        GroupsFilter gFilters = searchEntity.getClass().getAnnotation(GroupsFilter.class);

        for (Field field : fields) {
            Object filterVal = PropertyUtils.getProperty(searchEntity, field.getName());
            if (filterVal == null || (filterVal instanceof String && StringUtils.isBlank(filterVal.toString()))) continue;
            QueryFilter queryFilter = field.getAnnotation(QueryFilter.class);
            GroupFilter groupFilter = getGrFilterByName(queryFilter.groupName(), gFilters);
            //ignore field if annotation not exist
            if (queryFilter == null) continue;
            List<Field> _fields = fieldGroups.get(groupFilter);
            if (_fields == null) _fields = new ArrayList<>();
            _fields.add(field);
            fieldGroups.put(groupFilter, _fields);
        }
        return fieldGroups;
    }

    private static GroupFilter getGrFilterByName(String name, GroupsFilter groupsFilter) {
        if (groupsFilter == null) return DefaultGroupFilter.class.getAnnotation(GroupFilter.class);
        GroupFilter groupFilter = null;
        for (GroupFilter _groupFilter : groupsFilter.value()) {
            if (name.equals(_groupFilter.name())) {
                groupFilter = _groupFilter;
                break;
            }
        }
        return groupFilter == null ? DefaultGroupFilter.class.getAnnotation(GroupFilter.class) : groupFilter;
    }


}
