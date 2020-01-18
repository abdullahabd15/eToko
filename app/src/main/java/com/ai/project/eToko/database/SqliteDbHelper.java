package com.ai.project.eToko.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ai.project.eToko.contract.Contract;

import java.util.ArrayList;
import java.util.List;

public class SqliteDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tokoManager.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private List<Contract> contractList = new ArrayList<>();

    public SqliteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.contractList = Contract.getContractList();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Contract contract: contractList) {
            db.execSQL(getQueryCreateTable(contract));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();

            for(Contract contract: contractList) {
                db.execSQL(getQueryDropTable(contract));
            }

            onCreate(db);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private String getQueryDropTable(Contract contract) {
        return "DROP TABLE IF EXISTS " + contract.getTableName();
    }

    private String getQueryCreateTable(Contract contract) {
        String[] columnList = contract.getListColumn();
        String[] pkList = contract.getListPrimaryKey();
        String[] typeList = contract.getListType();

        String txtColumn = "";
        String txtPK = "";

        for(int index = 0; index < columnList.length; index++) {
            txtColumn += " " + columnList[index] + " " + typeList[index] + " NOT NULL";
            if(index != columnList.length -1) {
                txtColumn += ", ";
            }
        }

        for(int index = 0; index < pkList.length; index++) {
            txtPK += " " + pkList[index];
            if(index != pkList.length -1) {
                txtPK += ", ";
            }
        }

        return "CREATE TABLE " + " " + contract.getTableName() + " " +
                " (" + txtColumn + ", PRIMARY KEY(" + txtPK +"));";
    }
}
