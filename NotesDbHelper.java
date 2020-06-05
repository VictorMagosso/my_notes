//criação do banco de dados, normal
public class NotesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TABLE_NOTES";
    public static final String TABLE_NOTES = "notes";
    public static final int DATABASE_VERSION = 1;

    public NotesDbHelper(@Nullable Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE IF NOT EXISTS " + TABLE_NOTES
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT NOT NULL, " +
                " title TEXT NOT NULL" + ")";
    db.execSQL( sql );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NOTES );
            onCreate( db );
    }

}
