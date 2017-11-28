package models.filter.utils;

import akka.parboiled2.RuleTrace;
import models.filter.query.DefaultGroupFilter;
import models.filter.query.annotation.GroupExpression;
import io.ebean.*;
import models.filter.query.annotation.GroupsExpression;
import models.filter.query.annotation.Expression;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class ExpressionUtils {

    public static <E extends PagedList> ExpressionList buildQuery(E searchEntity, Finder<?, ?> finder) {
        ExpressionList expressions = finder.query().where();
        Map<GroupExpression, List<Field>> fieldGroup = buildGroupFilter(searchEntity);

        fieldGroup.forEach((group,fields)->{
            final Junction junction = invokeJfunction(Junction.class, expressions, group.opatator().toString().toLowerCase(), null);;
            fields.stream().filter(field -> field.getAnnotation(Expression.class)!=null)
                    .forEach(field -> {
                        Expression queryFilter = field.getAnnotation(Expression.class);
                        invokeJfunctionProcess(Junction.class, junction, queryFilter.operator(), new Class[]{String.class, Object.class}, queryFilter.value(), getProperty(searchEntity,field.getName()));
                    });
            junction.endJunction();
        });
        return expressions;

    }

    private static void invokeJfunctionProcess(Class clazz, Object obj, String methodName, Class[] types, String fieldName, Object value) throws RuntimeException {
        //check type: -> Collection -> Rebuild expression
        if (value instanceof Collection && !"in".equals(methodName)) {
            Collection collection = (Collection) value;
            collection.forEach(_value->invokeJfunction(clazz, obj, methodName, types, fieldName, _value));
        } else invokeJfunction(clazz, obj, methodName, types, fieldName, value);
    }

    private static <T> T invokeJfunction(Class clazz, Object obj, String methodName, Class[] types, Object... params) throws RuntimeException {
        try {
            Method methodInvoke = null;
            methodInvoke=(types==null || types.length<1)? clazz.getMethod(methodName):clazz.getMethod(methodName, types);
            return (T) methodInvoke.invoke(obj,params);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }

    //Build group conditions
    private static Map<GroupExpression, List<Field>> buildGroupFilter(Object searchEntity) throws RuntimeException {
        Field[] fields = searchEntity.getClass().getDeclaredFields();
        Map<GroupExpression, List<Field>> fieldGroups = new HashMap<>();
        GroupsExpression gFilters = searchEntity.getClass().getAnnotation(GroupsExpression.class);

        Stream.of(fields)
                .filter(field->getProperty(searchEntity, field.getName())!=null)
                .filter(field->field.getAnnotation(Expression.class)!=null)
                .forEach(field->{
            Expression queryFilter = field.getAnnotation(Expression.class);
            GroupExpression groupFilter = getGrFilterByName(queryFilter.groupName(), gFilters);
            List<Field> _fields = fieldGroups.get(groupFilter);
            _fields=_fields==null?new ArrayList<>():_fields;
            _fields.add(field);
            fieldGroups.put(getGrFilterByName(queryFilter.groupName(), gFilters), _fields);
        });
        return fieldGroups;
    }

    private static GroupExpression getGrFilterByName(String name, GroupsExpression groupsFilter) {
        if (groupsFilter == null) return DefaultGroupFilter.class.getAnnotation(GroupExpression.class);
        GroupExpression groupFilter= Stream.of(groupsFilter.value())
                .filter(_groupFilter->name.equals(_groupFilter.name()))
                .findAny()
                .orElse(DefaultGroupFilter.class.getAnnotation(GroupExpression.class));
        return groupFilter;

    }

    private static Object getProperty(Object obj,String name) throws RuntimeException{
        try{
            return PropertyUtils.getProperty(obj, name);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


}
