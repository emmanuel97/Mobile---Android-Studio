package com.example.emmanuelbulacio.agendacontatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper
{
    private static	final	String	DB_NAME	=	"ifrn.db";
    private static	final	int	DB_VERSION	=	1;
    private static	final	String	DB_TABLE_1	=
				"create	table cursos(nome	text, area	text,tempo text);";

    Banco (Context context)	{
        super(context,	DB_NAME,	null,	DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)	{
        db.execSQL(DB_TABLE_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,	int oldVersion,
                          int newVersion)	{
        db.execSQL("DROP TABLE IF EXISTS	cursos");
        onCreate(db);
    }
}