package com.tz.fooddelivery.data.local.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tz.fooddelivery.data.local.entities.MealEntity;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MealsDao_Impl extends MealsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MealEntity> __insertionAdapterOfDishEntity;

  public MealsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDishEntity = new EntityInsertionAdapter<MealEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `dishes` (`dishId`,`dishImage`,`dishTitle`,`dishCategory`,`description`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MealEntity value) {
        if (value.getDishId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getDishId());
        }
        if (value.getDishImage() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDishImage());
        }
        if (value.getDishTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDishTitle());
        }
        if (value.getDishCategory() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDishCategory());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescription());
        }
      }
    };
  }

  @Override
  public void insertAll(final List<MealEntity> dishes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDishEntity.insert(dishes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<MealEntity> getAllDishes() {
    final String _sql = "SELECT * FROM dishes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfDishId = CursorUtil.getColumnIndexOrThrow(_cursor, "dishId");
      final int _cursorIndexOfDishImage = CursorUtil.getColumnIndexOrThrow(_cursor, "dishImage");
      final int _cursorIndexOfDishTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "dishTitle");
      final int _cursorIndexOfDishCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "dishCategory");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final List<MealEntity> _result = new ArrayList<MealEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MealEntity _item;
        final String _tmpDishId;
        if (_cursor.isNull(_cursorIndexOfDishId)) {
          _tmpDishId = null;
        } else {
          _tmpDishId = _cursor.getString(_cursorIndexOfDishId);
        }
        final String _tmpDishImage;
        if (_cursor.isNull(_cursorIndexOfDishImage)) {
          _tmpDishImage = null;
        } else {
          _tmpDishImage = _cursor.getString(_cursorIndexOfDishImage);
        }
        final String _tmpDishTitle;
        if (_cursor.isNull(_cursorIndexOfDishTitle)) {
          _tmpDishTitle = null;
        } else {
          _tmpDishTitle = _cursor.getString(_cursorIndexOfDishTitle);
        }
        final String _tmpDishCategory;
        if (_cursor.isNull(_cursorIndexOfDishCategory)) {
          _tmpDishCategory = null;
        } else {
          _tmpDishCategory = _cursor.getString(_cursorIndexOfDishCategory);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item = new MealEntity(_tmpDishId,_tmpDishImage,_tmpDishTitle,_tmpDishCategory,_tmpDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MealEntity> getDishesByCategory(final String category) {
    final String _sql = "SELECT * FROM dishes WHERE dishCategory = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfDishId = CursorUtil.getColumnIndexOrThrow(_cursor, "dishId");
      final int _cursorIndexOfDishImage = CursorUtil.getColumnIndexOrThrow(_cursor, "dishImage");
      final int _cursorIndexOfDishTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "dishTitle");
      final int _cursorIndexOfDishCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "dishCategory");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final List<MealEntity> _result = new ArrayList<MealEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MealEntity _item;
        final String _tmpDishId;
        if (_cursor.isNull(_cursorIndexOfDishId)) {
          _tmpDishId = null;
        } else {
          _tmpDishId = _cursor.getString(_cursorIndexOfDishId);
        }
        final String _tmpDishImage;
        if (_cursor.isNull(_cursorIndexOfDishImage)) {
          _tmpDishImage = null;
        } else {
          _tmpDishImage = _cursor.getString(_cursorIndexOfDishImage);
        }
        final String _tmpDishTitle;
        if (_cursor.isNull(_cursorIndexOfDishTitle)) {
          _tmpDishTitle = null;
        } else {
          _tmpDishTitle = _cursor.getString(_cursorIndexOfDishTitle);
        }
        final String _tmpDishCategory;
        if (_cursor.isNull(_cursorIndexOfDishCategory)) {
          _tmpDishCategory = null;
        } else {
          _tmpDishCategory = _cursor.getString(_cursorIndexOfDishCategory);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item = new MealEntity(_tmpDishId,_tmpDishImage,_tmpDishTitle,_tmpDishCategory,_tmpDescription);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
