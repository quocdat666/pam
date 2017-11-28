package utils;

import bean.ExpressionInfo;
import io.ebean.Expr;
import io.ebean.ExpressionList;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import play.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ExpressionBuildTool {

    public static ExpressionList buildExpressions(ExpressionList source, ExpressionInfo expObj) {
        long t1 = System.currentTimeMillis();

        Arrays.stream(expObj.getClass().getDeclaredFields()).forEach(e -> {
            try {
                Condition annotation = e.getAnnotation(Condition.class);
                Object objValue = PropertyUtils.getProperty(expObj, e.getName());
                if (annotation != null && objValue != null && !String.valueOf(objValue).isEmpty()) {
                    switch (annotation.operator()) {
                        case EQUAL:
                            createEqualExp(source, expObj, e, annotation);
                            break;
                        case GE:
                            createGeExp(source, expObj, e, annotation);
                            break;
                        case GT:
                            createGtExp(source, expObj, e, annotation);
                            break;
                        case LE:
                            createLeExp(source, expObj, e, annotation);
                            break;
                        case LT:
                            createLtExp(source, expObj, e, annotation);
                            break;
                        case START_WITH:
                            createStartWithExp(source, expObj, e, annotation);
                            break;
                        case END_WITH:
                            createEndWithExp(source, expObj, e, annotation);
                            break;
                        case LIKE:
                            createLikeExp(source, expObj, e, annotation);
                            break;
                        case IN:
                            createInExp(source, expObj, e, annotation);
                            break;
                        case NOT_EQUAL:
                            createNotEqualExp(source, expObj, e, annotation);
                            break;
                        case NOT_LIKE:
                            createNotLikeExp(source, expObj, e, annotation);
                            break;
                        case NOT_IN:
                            createNotInExp(source, expObj, e, annotation);
                            break;
                        default:
                            Logger.of("application").warn("Build Expression with unsupported operator: " + annotation.operator());
                    }
                }
            } catch (Exception ex) {
                Logger.of("application").debug("Build expression with an error", ex);
            }
        });

        long t2 = System.currentTimeMillis();
        Logger.of("application").debug("Build expressions in " + (t2 - t1) + " miniseconds");

        return source;
    }

    private static void createNotInExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.notIn(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
    }

    private static void createNotLikeExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.not(Expr.like(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName()))));
    }

    private static void createNotEqualExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.ne(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName())));
    }

    private static void createInExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.in(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
    }

    private static void createLikeExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        String value = String.valueOf(PropertyUtils.getProperty(expObj, e.getName()));
        if (annotation.ignoreCase()) {
            source.ilike(getTableColumnName(e, annotation), String.format("%%%s%%", value));
        } else {
            source.like(getTableColumnName(e, annotation), String.format("%%%s%%", value));
        }
    }

    private static void createEndWithExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        if (annotation.ignoreCase()) {
            source.iendsWith(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName())));
        } else {
            source.endsWith(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName())));
        }
    }

    private static void createStartWithExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        if (annotation.ignoreCase()) {
            source.istartsWith(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName())));
        } else {
            source.startsWith(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName())));
        }
    }

    private static void createLtExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.lt(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
    }

    private static void createLeExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.le(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
    }

    private static void createGtExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.gt(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
    }

    private static void createGeExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        source.ge(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
    }

    private static void createEqualExp(ExpressionList source, ExpressionInfo expObj, Field e, Condition annotation) throws Exception {
        if (annotation.ignoreCase()) {
            source.ieq(getTableColumnName(e, annotation), String.valueOf(PropertyUtils.getProperty(expObj, e.getName())));
        } else {
            source.eq(getTableColumnName(e, annotation), PropertyUtils.getProperty(expObj, e.getName()));
        }
    }

    private static String getTableColumnName(Field e, Condition annotation) {
        return StringUtils.isEmpty(annotation.columnName()) ? e.getName() : annotation.columnName();
    }

}
