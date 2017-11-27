package repository;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.PagedList;
import models.filter.utils.ExpressionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class CurdRepository<T extends Model, ID extends Serializable> {
    public Finder<ID, T> find;
    private Class<T> _class;

    public CurdRepository(Class<T> _class) {
        this._class = _class;
        find = new Finder<ID, T>(_class);
    }

    public T findOne(ID id) {
        return find.byId(id);
    }

    public List<T> findAll() {
        return find.query().findList();
    }

    public void insert(T entity) {
        entity.save();
    }

    public boolean delete(ID id) {
        return find.byId(id).delete();
    }

    public <E extends PagedList> PagedList<T> filter(E filterEntity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ExpressionList exps = ExpressionUtils.buildQuery(filterEntity, find);
//        int totalRows = exps.findCount();
        //paging
        exps.setFirstRow(filterEntity.getPageIndex()  * filterEntity.getPageSize());
        exps.setMaxRows(filterEntity.getPageSize());
        PagedList<T> dataPage=exps.findPagedList();
        return dataPage;
    }

}
