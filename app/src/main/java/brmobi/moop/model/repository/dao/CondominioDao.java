package brmobi.moop.model.repository.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import brmobi.moop.model.entities.Condominio;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONDOMINIO".
*/
public class CondominioDao extends AbstractDao<Condominio, Long> {

    public static final String TABLENAME = "CONDOMINIO";

    /**
     * Properties of entity Condominio.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nome = new Property(1, String.class, "nome", false, "NOME");
        public final static Property Cep = new Property(2, String.class, "cep", false, "CEP");
        public final static Property Logradouro = new Property(3, String.class, "logradouro", false, "LOGRADOURO");
        public final static Property IsHorizontal = new Property(4, boolean.class, "isHorizontal", false, "IS_HORIZONTAL");
        public final static Property IsLiberado = new Property(5, boolean.class, "isLiberado", false, "IS_LIBERADO");
        public final static Property IsSindico = new Property(6, boolean.class, "isSindico", false, "IS_SINDICO");
    }


    public CondominioDao(DaoConfig config) {
        super(config);
    }
    
    public CondominioDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONDOMINIO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NOME\" TEXT," + // 1: nome
                "\"CEP\" TEXT," + // 2: cep
                "\"LOGRADOURO\" TEXT," + // 3: logradouro
                "\"IS_HORIZONTAL\" INTEGER NOT NULL ," + // 4: isHorizontal
                "\"IS_LIBERADO\" INTEGER NOT NULL ," + // 5: isLiberado
                "\"IS_SINDICO\" INTEGER NOT NULL );"); // 6: isSindico
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONDOMINIO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Condominio entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nome = entity.getNome();
        if (nome != null) {
            stmt.bindString(2, nome);
        }
 
        String cep = entity.getCep();
        if (cep != null) {
            stmt.bindString(3, cep);
        }
 
        String logradouro = entity.getLogradouro();
        if (logradouro != null) {
            stmt.bindString(4, logradouro);
        }
        stmt.bindLong(5, entity.getIsHorizontal() ? 1L: 0L);
        stmt.bindLong(6, entity.getIsLiberado() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsSindico() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Condominio entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nome = entity.getNome();
        if (nome != null) {
            stmt.bindString(2, nome);
        }
 
        String cep = entity.getCep();
        if (cep != null) {
            stmt.bindString(3, cep);
        }
 
        String logradouro = entity.getLogradouro();
        if (logradouro != null) {
            stmt.bindString(4, logradouro);
        }
        stmt.bindLong(5, entity.getIsHorizontal() ? 1L: 0L);
        stmt.bindLong(6, entity.getIsLiberado() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsSindico() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Condominio readEntity(Cursor cursor, int offset) {
        Condominio entity = new Condominio( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nome
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // cep
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // logradouro
            cursor.getShort(offset + 4) != 0, // isHorizontal
            cursor.getShort(offset + 5) != 0, // isLiberado
            cursor.getShort(offset + 6) != 0 // isSindico
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Condominio entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNome(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCep(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLogradouro(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsHorizontal(cursor.getShort(offset + 4) != 0);
        entity.setIsLiberado(cursor.getShort(offset + 5) != 0);
        entity.setIsSindico(cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Condominio entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Condominio entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Condominio entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}