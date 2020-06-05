//é bom criar uma interface para a DAO pra poder facilitar nos metodos "salvar / atualizar / listar" dos dados
public class NotesDAO implements iNotesDAO {
//nomes didáticos apenas para facillitar a compreenção. Você pode instanciar diretamente o "getReadableDatabase()" do db
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public NotesDAO(Context context) {
        NotesDbHelper db = new NotesDbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }
    @Override
    public boolean salvar(Notes notes) {
        ContentValues cv = new ContentValues();
        cv.put("nome",notes.getNote());
        cv.put("title",notes.getTitle());

        try{
            escreve.insert(NotesDbHelper.TABLE_NOTES,null,cv);
        }catch (Exception e){
            Log.i("INFO","Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }



    @Override
    public boolean atualizar(Notes notes) {
        ContentValues cv = new ContentValues();
        cv.put("nome",notes.getTitle());
        cv.put("nome",notes.getNote());

        try{
            //para atualizar, é necessário criar um array string e passar um valor dinamico
            //pois é o usuario que determninará o que ira atualziar
            String[] args = {String.valueOf( notes.getId() )};
            escreve.update(NotesDbHelper.TABLE_NOTES,cv,"id=?", args);
            Log.i("INFO","Sucesso ao atualizar tarefa ");
        }catch (Exception e) {
            Log.i("INFO", "Erro ao atualizar tarefa " + e.getMessage());

            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Notes notes) {
        try {
            String[] args = {String.valueOf( notes.getId() )};
            escreve.delete(NotesDbHelper.TABLE_NOTES, "id=?", args);
            Log.i("INFO", "Sucesso ao deletar tarefa ");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao deletar tarefa " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Notes> listar() {

        List<Notes> listaNotes = new ArrayList<>();

        String sql = "SELECT * FROM " + NotesDbHelper.TABLE_NOTES + " ;";
        Cursor c = le.rawQuery(sql,null);

        while (c.moveToNext()){

            Notes notes = new Notes();

            int id = c.getInt(c.getColumnIndex("id"));
           
            //String nomeNote = c.getString(c.getColumnIndex("nome"));
            String titleNote = c.getString(c.getColumnIndex("title"));

            notes.setId(id);
           // notes.setNote(nomeNote);
            notes.setTitle( titleNote );

            listaNotes.add(notes);

        }

        return listaNotes;
    }
}
